package com.novaagent.app.services.notification;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.novaagent.app.core.di.ServiceLocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * PENDENGAR NOTIFIKASI RESMI (BAGIAN 7)
 * Membaca pesan masuk WhatsApp secara sah dengan izin pengguna,
 * memfilter pesan yang belum dibaca, dan menyiapkan draft balasan aman.
 */
public class NovaNotificationListenerService extends NotificationListenerService {
    private static final String TAG = "NovaNotificationService";
    public static final String PACKAGE_WHATSAPP = "com.whatsapp";

    public static class ChatMessage {
        public final String sender;
        public final String text;
        public final long timestamp;
        public final String packageName;
        public boolean isRead = false;

        public ChatMessage(String sender, String text, long timestamp, String packageName) {
            this.sender = sender != null ? sender : "Pengirim Tidak Dikenal";
            this.text = text != null ? text : "";
            this.timestamp = timestamp;
            this.packageName = packageName;
        }

        @Override
        public String toString() {
            return sender + ": \"" + text + "\"";
        }
    }

    private static final List<ChatMessage> unreadMessages = new CopyOnWriteArrayList<>();
    private static volatile boolean isServiceConnected = false;

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        isServiceConnected = true;
        ServiceLocator.getInstance().register(NovaNotificationListenerService.class, this);
        Log.i(TAG, "Nova Notification Listener Service Terhubung.");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        isServiceConnected = false;
        try {
            ServiceLocator.getInstance().register(NovaNotificationListenerService.class, null);
        } catch (Exception ignored) {}
        Log.w(TAG, "Nova Notification Listener Service Terputus.");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn == null || sbn.getNotification() == null) return;
        String pkg = sbn.getPackageName();

        // Fokus pada pesan WhatsApp
        if (PACKAGE_WHATSAPP.equals(pkg)) {
            Notification notification = sbn.getNotification();
            Bundle extras = notification.extras;
            if (extras == null) return;

            CharSequence titleSeq = extras.getCharSequence(Notification.EXTRA_TITLE);
            CharSequence textSeq = extras.getCharSequence(Notification.EXTRA_TEXT);

            if (titleSeq != null && textSeq != null) {
                String sender = titleSeq.toString().trim();
                String content = textSeq.toString().trim();

                // Abaikan notifikasi sistem umum atau status syncing WhatsApp
                if (!content.isEmpty() && !content.toLowerCase().contains("memeriksa pesan baru") &&
                    !content.toLowerCase().contains("checking for new messages")) {
                    ChatMessage msg = new ChatMessage(sender, content, sbn.getPostTime(), pkg);
                    unreadMessages.add(0, msg); // Simpan pesan terbaru di depan
                    // Batasi maksimal 20 pesan terakhir
                    while (unreadMessages.size() > 20) {
                        unreadMessages.remove(unreadMessages.size() - 1);
                    }
                    Log.i(TAG, "Pesan WhatsApp baru ditangkap dari: " + sender);
                }
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Notifikasi dibersihkan dari drawer
    }

    public static boolean isNotificationAccessEnabled(Context context) {
        if (context == null) return false;
        String enabledListeners = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        return enabledListeners != null && enabledListeners.contains(context.getPackageName());
    }

    public static Intent getNotificationAccessSettingsIntent() {
        return new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
    }

    public List<ChatMessage> getUnreadWhatsAppMessages() {
        List<ChatMessage> list = new ArrayList<>();
        for (ChatMessage m : unreadMessages) {
            if (!m.isRead) list.add(m);
        }
        return list;
    }

    public ChatMessage getLatestUnreadMessage() {
        for (ChatMessage m : unreadMessages) {
            if (!m.isRead) return m;
        }
        return null;
    }

    public void markAllAsRead() {
        for (ChatMessage m : unreadMessages) {
            m.isRead = true;
        }
    }
}
