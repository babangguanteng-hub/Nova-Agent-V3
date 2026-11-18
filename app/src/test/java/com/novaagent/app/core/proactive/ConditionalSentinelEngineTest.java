package com.novaagent.app.core.proactive;

import android.os.BatteryManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConditionalSentinelEngineTest {

    @Test
    public void testLowBatteryTrigger() {
        ConditionalSentinelEngine sentinel = new ConditionalSentinelEngine(null);
        assertFalse(sentinel.isLowBatteryAlerted());

        // Level 12%, tidak sedang dicas
        sentinel.evaluateHealthConditions(12, BatteryManager.BATTERY_STATUS_DISCHARGING, 320);
        assertTrue(sentinel.isLowBatteryAlerted());

        // Setelah dicas ke 50%, alert harus ter-reset
        sentinel.evaluateHealthConditions(50, BatteryManager.BATTERY_STATUS_CHARGING, 320);
        assertFalse(sentinel.isLowBatteryAlerted());
    }

    @Test
    public void testHighTemperatureOverheatTrigger() {
        ConditionalSentinelEngine sentinel = new ConditionalSentinelEngine(null);
        assertFalse(sentinel.isHighTempAlerted());

        // Suhu normal 34.0°C (340 tenths)
        sentinel.evaluateHealthConditions(80, BatteryManager.BATTERY_STATUS_DISCHARGING, 340);
        assertFalse(sentinel.isHighTempAlerted());

        // Suhu panas 46.5°C (465 tenths > 450)
        sentinel.evaluateHealthConditions(80, BatteryManager.BATTERY_STATUS_DISCHARGING, 465);
        assertTrue(sentinel.isHighTempAlerted());

        // Suhu mendingin kembali ke 36.0°C (360 tenths < 400)
        sentinel.evaluateHealthConditions(80, BatteryManager.BATTERY_STATUS_DISCHARGING, 360);
        assertFalse(sentinel.isHighTempAlerted());
    }
}
