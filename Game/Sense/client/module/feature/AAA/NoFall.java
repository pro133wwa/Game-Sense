package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoFall extends Module {
    public static ListSetting noFallMode = new ListSetting("NoFall Mode", "Vanilla", () -> true, "Vanilla", "Matrix");

    public NoFall() {
        super("NoFall", "ѕозвол€ет получить меньший дамаг при падении", ModuleCategory.PLAYER);
        addSettings(noFallMode);
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        String mode = noFallMode.getOptions();
        this.setSuffix(mode);
        if (mode.equalsIgnoreCase("Vanilla")) {
            if (mc.player.fallDistance > 3) {
                event.setOnGround(true);
                mc.player.connection.sendPacket(new CPacketPlayer(true));
            }
        } else if (mode.equalsIgnoreCase("Matrix")) {
            if (mc.player.fallDistance >= 2) {
                mc.timer.timerSpeed = 0.01f;
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
                mc.timer.timerSpeed = 1f;
                mc.player.fallDistance = 0;
            }
        }
    }
}
