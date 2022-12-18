package Game.Sense.client.feature.impl.combat;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.packet.EventSendPacket;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;
import Game.Sense.client.ui.settings.impl.ListSetting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Feature {
    public static BooleanSetting cancelOtherDamage;
    public static ListSetting velocityMode;


    public Velocity() {
        super("Velocity", "Вы не будете откидываться", FeatureCategory.Combat);
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