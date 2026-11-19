package com.novaagent.app.core.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.novaagent.app.core.di.ServiceLocator;
import com.novaagent.app.core.skills.SkillResult;
import com.novaagent.app.data.model.ActionCommandDto;
import com.novaagent.app.services.notification.NovaNotificationListenerService;

import java.net.URLEncoder;
import java.util.List;

/**
 * ASISTEN PESAN WHATSAPP (BAGIAN 7)
 * Membaca pesan masuk WhatsApp, membacakan secara aman,
 * dan menyiapkan draft pesan tanpa mengirim otomatis tanpa persetujuan.
 */
public class WhatsAppMessageAssistant {
    private final Context context;

    public static class DraftReply {
        public final String recipient;
        public final String replyText;
        public boolean isApproved = false;

        public DraftReply(String recipient, String replyText) {
            this.recipient = recipient;
            this.replyText = replyText;
        }
    }

    private static DraftReply pendingDraft = null;

    public WhatsAppMessageAssistant(Context context) {
        this.context = context != null ? context.getApplicationContext() : null;
    }

    public SkillResult checkAndReadMessages(String specificSender) {
        if (context == null) return SkillResult.failure("Konteks sistem tidak tersedia.");

        if (!NovaNotificationListenerService.isNotificationAccessEnabled(context)) {
            Intent settingsIntent = NovaNotificationListenerService.getNotificationAccessSettingsIntent();
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try { context.startActivity(settingsIntent); } catch (Exception ignored) {}
            return SkillResult.failure("Izin akses notifikasi belum aktif. Silakan aktifkan NOVA di menu Akses Notifikasi agar saya bisa membacakan pesan WhatsApp.");
        }

        NovaNotificationListenerService service = null;
        try {
            service = ServiceLocator.getInstance().resolve(NovaNotificationListenerService.class);
        } catch (Exception ignored) {}

        if (service == null) {
            return SkillResult.success("Layanan pembaca notifikasi sedang memulai. Belum ada pesan WhatsApp baru yang terekam.");
        }

        List<NovaNotificationListenerService.ChatMessage> messages = service.getUnreadWhatsAppMessages();
        if (messages.isEmpty()) {
            return SkillResult.success("Tidak ada pesan WhatsApp baru yang belum dibaca, bos.");
        }

        if (specificSender != null && !specificSender.trim().isEmpty()) {
            String lowerSender = specificSender.toLowerCase().trim();
            for (NovaNotificationListenerService.ChatMessage msg : messages) {
                if (msg.sender.toLowerCase().contains(lowerSender)) {
                    msg.isRead = true;
                    return SkillResult.success("Pesan dari " + msg.sender + ": \"" + msg.text + "\".");
                }
            }
            return SkillResult.success("Tidak ditemukan pesan baru dari " + specificSender + ".");
        }

        // Bacakan pesan terbaru
        NovaNotificationListenerService.ChatMessage latest = messages.get(0);
        latest.isRead = true;
        if (messages.size() == 1) {
            return SkillResult.success("Ada 1 pesan baru dari " + latest.sender + ": \"" + latest.text + "\".");
        } else {
            return SkillResult.success("Ada " + messages.size() + " pesan baru. Pesan terbaru dari " + latest.sender + ": \"" + latest.text + "\".");
        }
    }

    public SkillResult prepareReplyDraft(String replyText) {
        if (replyText == null || replyText.trim().isEmpty()) {
            return SkillResult.failure("Isi pesan balasan tidak boleh kosong.");
        }

        NovaNotificationListenerService service = null;
        try {
            service = ServiceLocator.getInstance().resolve(NovaNotificationListenerService.class);
        } catch (Exception ignored) {}

        String targetRecipient = "Penerima Terakhir";
        if (service != null && service.getLatestUnreadMessage() != null) {
            targetRecipient = service.getLatestUnreadMessage().sender;
        }

        pendingDraft = new DraftReply(targetRecipient, replyText.trim());

        return SkillResult.success("Draft balasan untuk " + targetRecipient + ": \"" + replyText.trim() + "\". Apakah Anda setuju untuk mengirim pesan ini?",
                new ActionCommandDto("approve_action", replyText.trim()));
    }

    public void clearPendingReply() {
        this.pendingDraft = null;
    }

    public boolean hasPendingReply() {
        return this.pendingDraft != null;
    }

    public SkillResult executeApprovedReply() {
        if (pendingDraft == null) {
            return SkillResult.failure("Tidak ada draft pesan yang menunggu persetujuan.");
        }

        String recipient = pendingDraft.recipient;
        String text = pendingDraft.replyText;
        pendingDraft = null;

        try {
            String url = "https://api.whatsapp.com/send?text=" + URLEncoder.encode(text, "UTF-8");
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            sendIntent.setPackage("com.whatsapp");
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(sendIntent);
            return SkillResult.success("Membuka WhatsApp untuk mengirim pesan ke " + recipient + ".");
        } catch (Exception e) {
            return SkillResult.failure("Gagal membuka WhatsApp: " + e.getMessage());
        }
    }
}
