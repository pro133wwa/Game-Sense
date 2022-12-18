package Game.Sense.client.feature.impl.movement;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.notification.NotificationMode;
import Game.Sense.client.ui.notification.NotificationRenderer;
import Game.Sense.client.ui.settings.impl.BooleanSetting;
import Game.Sense.client.utils.movement.MovementUtils;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketEntityAction;

public class Strafe extends Feature {
    public static BooleanSetting smart = new BooleanSetting("Smart", false, () -> true);

    public Strafe() {
        super("Strafe", "Позволяет стрейфить на матриксе", FeatureCategory.Movement);
        addSettings(smart);
    }

    public double boost;
    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {

            if (mc.player.onGround) {
                return;
            }
            int eIndex = -1;
            for (int i = 0; i < 45; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA) {
                    eIndex = i;
                    break;
                }
            }

            if (eIndex == -1) {
                if (mc.player.getHeldItemOffhand().getItem() != Items.ELYTRA) {
                    NotificationRenderer.queue("§6Matrix Exploit", "§cВозьмите элитры в инвентарь!", 6, NotificationMode.WARNING);
                    toggle();
                    return;
                }
            }

            if (mc.player.ticksExisted % 4 == 0) {
                mc.playerController.windowClick(0, eIndex, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
            }
        if (!mc.player.onGround) {
            MovementUtils.strafe();
        }

            if (mc.player.ticksExisted % 4 == 0) {
                mc.player.motionX *= 0.8D;
                mc.player.motionZ *= 0.8D;
                mc.player.connection
                        .sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
            mc.player.motionX *= 1.1D;
            mc.player.motionZ *= 1.1D;
            if (mc.player.ticksExisted % 4 == 0) {
                mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, eIndex, 1, ClickType.PICKUP, mc.player);
            }
        }
    }








