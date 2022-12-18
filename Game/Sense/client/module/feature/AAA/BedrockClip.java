package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.network.play.client.CPacketPlayer;

public class BedrockClip extends Module {
    public BedrockClip() {
        super("BedrockClip", "Телепортирует вас под бедрок когда вы на воде, или получили урон", ModuleCategory.MOVEMENT);
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
