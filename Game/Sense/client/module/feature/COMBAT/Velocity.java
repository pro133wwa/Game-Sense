package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {
    public static BooleanSetting cancelOtherDamage;
    public static ListSetting velocityMode;


    public Velocity() {
        super("Velocity", "Вы не будете откидываться", ModuleCategory.COMBAT);
    }

    @EventTarget
    public void onReceive(EventSendPacket e) {
        if (e.getPacket() instanceof SPacketEntityVelocity) {
            SPacketEntityVelocity packet = (SPacketEntityVelocity) e.getPacket();
            if (packet.getEntityID() == mc.player.getEntityId()) {
                e.isCancelled();
            }
        }
        if (e.getPacket() instanceof SPacketExplosion) {
            e.isCancelled();
        }
    }

}