package Game.Sense.client.feature.impl.movement;

import Game.Sense.client.event.EventTarget;


import Game.Sense.client.event.events.impl.player.EventPreMotion;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.network.play.client.CPacketPlayer;


public class KTLeave extends Feature
{

    public KTLeave() {
        super("KTLeave", "", FeatureCategory.Movement);
    }


    @EventTarget
    public void onUpdate(final EventUpdate event) {
        float endX = 15900;
        float endZ = -1000;
        float endY = 70;
        if(mc.player.isSneaking() && mc.player.ticksExisted % 8 == 0) {
            ChatUtils.addChatMessage("  ??????????? ?? ???????? ??? ?????????? " + endX + " " + endY + " " + endZ);
            if (mc.player.posX != endX && mc.player.posZ != endZ) {
                mc.player.motionY = 0.05f;
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX + 0.5, endY, endZ - 0.5, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY + 109, endZ, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX + 0.5, endY, endZ - 0.5, true));
            }

        }
    }
}
