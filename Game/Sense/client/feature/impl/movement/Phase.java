package Game.Sense.client.feature.impl.movement;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.ListSetting;
import Game.Sense.client.utils.math.MathematicHelper;
import Game.Sense.client.utils.movement.MovementUtils;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

public class Phase extends Feature {
    private final ListSetting phaseMode = new ListSetting("Phase Mode", "Matrix", () -> true, "Matrix");

    public Phase() {
        super("Phase", FeatureCategory.Movement);
        addSettings(phaseMode);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if(phaseMode.currentMode.equals("Matrix")) {
            if (mc.player.isCollidedHorizontally) {
                MovementUtils.setSpeed(0);

                float yaw = MovementUtils.getAllDirection();
                double x = mc.player.posX + -MathHelper.sin(yaw) * 0.00000001;
                double z = mc.player.posZ + MathHelper.cos(yaw) * 0.00000001;

                double x2 = mc.player.posX + -MathHelper.sin(yaw) * 0.85;
                double z2 = mc.player.posZ + MathHelper.cos(yaw) * 0.85;
                double y = mc.player.posY;
                mc.player.connection.sendPacket(new CPacketConfirmTeleport((int) MathematicHelper.getRandomFloat(4, 1)));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(x2, y, z2, true));
            }
        }
    }

}
