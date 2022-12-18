package Game.Sense.client.feature.impl.combat;

import Game.Sense.client.GameSense;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.packet.EventSendPacket;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import net.minecraft.network.play.client.CPacketUseEntity;

public class NoFriendDamage extends Feature {

    public NoFriendDamage() {
        super("NoFriendDamage", "Не дает ударить друга", FeatureCategory.Combat);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity cpacketUseEntity = (CPacketUseEntity) event.getPacket();
            if (cpacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK) && GameSense.instance.friendManager.isFriend(mc.objectMouseOver.entityHit.getName())) {
                event.setCancelled(true);
            }
        }
    }
}
