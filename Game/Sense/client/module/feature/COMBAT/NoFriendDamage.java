package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.network.play.client.CPacketUseEntity;

public class NoFriendDamage extends Module {

    public NoFriendDamage() {
        super("NoFriendDamage", "Не дает ударить друга", ModuleCategory.COMBAT);
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
