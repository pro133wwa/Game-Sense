package Game.Sense.client.feature.impl.movement;

import Game.Sense.client.event.EventTarget;


import Game.Sense.client.event.events.impl.player.EventPreMotion;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.network.play.client.CPacketPlayer;


public class KTLeave extends Feature {

    public KTLeave() {
        super("KTLeave", "Ливает в небо", FeatureCategory.Movement);
    }


    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        float endX = 1337.0F;
        float endZ = 2674.0F;
        float endY = 75.0F;
        if (mc.player.ticksExisted % 8 == 0) {
            mc.player.motionY += 1.0;
            this.mc.player.connection.sendPacket(new CPacketPlayer.Position(endX + 0.5D, endY + 0.1D, endZ - 0.5D, true));
            this.mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY, endZ, false));
            this.mc.player.connection.sendPacket( new CPacketPlayer.Position(endX + 0.5D, endY + 0.1D, endZ - 0.5D, true));
            return;
        }
        if (mc.player.posX == endX && mc.player.posZ == endZ) {
            ChatUtils.addChatMessage("leaved");
            toggle();
        }
    }
}