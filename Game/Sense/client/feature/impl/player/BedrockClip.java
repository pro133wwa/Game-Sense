package Game.Sense.client.feature.impl.player;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import net.minecraft.network.play.client.CPacketPlayer;

public class BedrockClip extends Feature {
    public BedrockClip() {
        super("BedrockClip", "Телепортирует вас под бедрок когда вы на воде, или получили урон", FeatureCategory.Movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.isInWater() || mc.player.hurtTime > 0) {
            for (int i = 0; i < 10; i++) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY , mc.player.posZ, false));

            }
            for (int i = 0; i < 10; i++) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - (mc.player.posY + 2),mc.player.posZ, false));
            }
            mc.player.setPosition(mc.player.posX,  mc.player.posY - (mc.player.posY + 2),  mc.player.posZ);

            toggle();
        }
    }

}
