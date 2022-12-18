package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventReceivePacket;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoServerRotations extends Module {

    public NoServerRotations() {
        super("NoServerRotation", "Убирает ротацию со стороны сервера", ModuleCategory.PLAYER);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
            packet.yaw = mc.player.rotationYaw;
            packet.pitch = mc.player.rotationPitch;
        }
    }
}