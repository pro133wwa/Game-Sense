package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.Utility.Helper;
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
    private final ListSetting noSlowMode = new ListSetting("NoSlow Mode", "Matrix", () -> true, "Vanilla", "Matrix");
    public int usingTicks;

    public NoSlowDown() {
        super("NoSlowDown", "Позволяет есть на ходу", ModuleCategory.PLAYER);
        addSettings(noSlowMode, percentage);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket eventSendPacket) {
        CPacketPlayer packet = (CPacketPlayer) eventSendPacket.getPacket();
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        usingTicks = Helper.mc.player.isUsingItem() ? ++usingTicks : 0;
        if (!this.isEnabled() || !Helper.mc.player.isUsingItem()) {
            return;
        }
        if (noSlowMode.currentMode.equals("Matrix")) {
            if (Helper.mc.player.isUsingItem()) {
                if (Helper.mc.player.onGround && !Helper.mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (Helper.mc.player.ticksExisted % 2 == 0) {
                        Helper.mc.player.motionX *= 0.35;
                        Helper.mc.player.motionZ *= 0.35;
                    }
                } else if (Helper.mc.player.fallDistance > 0.2) {
                    Helper.mc.player.motionX *= 0.9100000262260437;
                    Helper.mc.player.motionZ *= 0.9100000262260437;
                }
            }
        }
    }
}