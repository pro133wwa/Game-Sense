package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoSlowDown extends Module {
    public static NumberSetting percentage = new NumberSetting("Percentage", 100, 0, 100, 1, () -> true, NumberSetting.NumberType.PERCENTAGE);
    private final ListSetting noSlowMode = new ListSetting("NoSlow Mode", "Matrix", () -> true, "Vanilla", "Matrix", "Matrix New");
    public int usingTicks;

    public NoSlowDown() {
        super("NoSlowDown", "Убирает замедление при использовании еды и других предметов", ModuleCategory.PLAYER);
        addSettings(noSlowMode, percentage);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket eventSendPacket) {
        this.setSuffix(noSlowMode.getCurrentMode() + ", " + percentage.getNumberValue() + "%");
        CPacketPlayer packet = (CPacketPlayer) eventSendPacket.getPacket();
        if (noSlowMode.currentMode.equals("Matrix New")) {
            if (mc.player.isHandActive() && MovementUtils.isMoving() && !mc.gameSettings.keyBindJump.pressed) {
                packet.y = mc.player.ticksExisted % 2 == 0 ? packet.y + 0.0006 : packet.y + 0.0002;
                packet.onGround = false;
                mc.player.onGround = false;
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setSuffix(noSlowMode.getCurrentMode() + ", " + percentage.getNumberValue() + "%");
        usingTicks = mc.player.isUsingItem() ? ++usingTicks : 0;
        if (!this.isEnabled() || !mc.player.isUsingItem()) {
            return;
        }
        if (noSlowMode.currentMode.equals("Matrix")) {
            if (mc.player.isUsingItem()) {
                if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (mc.player.ticksExisted % 2 == 0) {
                        mc.player.motionX *= 0.35;
                        mc.player.motionZ *= 0.35;
                    }
                } else if (mc.player.fallDistance > 0.2) {
                    mc.player.motionX *= 0.9100000262260437;
                    mc.player.motionZ *= 0.9100000262260437;
                }
            }
        }
    }
}
