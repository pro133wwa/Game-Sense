package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.dop.Notif.NotificationMode;
import Game.Sense.client.UI.dop.Notif.NotificationRenderer;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketEntityAction;

public class ElytraStrafe extends Module {
    public ElytraStrafe() {
        super("ElytraStrafe", "Позволяет стрейфить как ебанутый на элитрах", ModuleCategory.MOVEMENT);
    }

    @EventTarget
    public void onPreMotion(EventPreMotion eventPreMotion) {
        if (mc.player.onGround) {
            return;
        }
        int eIndex = -1;
        for (int i = 0; i < 45; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                eIndex = i;
            }
        }
        if (mc.player.ticksExisted % 7 == 0) {
            mc.playerController.windowClick(0, eIndex, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        }
        if (!mc.player.onGround) {
            MovementUtils.strafe();
        }
        if (mc.player.ticksExisted % 7 == 0) {
            mc.player.motionX *= 0.8D;
            mc.player.motionZ *= 0.8D;
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
        mc.player.motionX *= 1.1D;
        mc.player.motionZ *= 1.1D;
        if (mc.player.ticksExisted % 7 == 0) {
            mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, eIndex, 1, ClickType.PICKUP, mc.player);
        }

        if (eIndex == -1) {
            if (mc.player.getHeldItemOffhand().getItem() != Items.ELYTRA) {
                NotificationRenderer.queue("§6Matrix Exploit", "§cВозьмите элитры в инвентарь!", 6, NotificationMode.WARNING);
                toggle();
            }
        }
    }

}
