package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.Helper.events.impl.player.EventMove;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.UwU.InventoryUtil;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.Helper;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.network.play.client.CPacketEntityAction;

public class Strafe extends Module {
    public ListSetting strafeMode = new ListSetting("Strafe Mode", "Matrix", () -> true, "Matrix","Hard");
    public final NumberSetting strafeSpeed = new NumberSetting("Strafe Speed", 0.2F, 0.01f, 0.3f, 0.01F, () -> strafeMode.currentMode.equals("Elytra"));

    public Strafe() {
        super("Strafe", "ׁענויפû", ModuleCategory.MOVEMENT);
        addSettings(strafeMode, strafeSpeed);
    }

    @EventTarget
    public void onPre(EventMove eventPreMotion) {
        if (strafeMode.currentMode.equalsIgnoreCase("Hard")) {
            if (MovementUtils.isMoving()) {
                Helper.mc.player.setSprinting(true);
                if (MovementUtils.getSpeed() < 2.5000f) {
                    Helper.mc.player.jumpMovementFactor = 2.100f;
                    MovementUtils.strafe();
                        MovementUtils.strafe();
                    }
                    if (Helper.mc.player.onGround) {
                        MovementUtils.strafe();
                    }
                }
            }
        }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (!isEnabled()) {
            return;
        }
        if (strafeMode.currentMode.equals("Matrix")) {
            if (MovementUtils.isMoving()) {
                Helper.mc.player.setSprinting(true);
                if (MovementUtils.getSpeed() < 0.2177f) {
                    Helper.mc.player.jumpMovementFactor = 0.033f;
                    MovementUtils.strafe();
                    if (Math.abs(Helper.mc.player.movementInput.moveStrafe) < 0.1 && Helper.mc.gameSettings.keyBindForward.pressed) {
                        MovementUtils.strafe();
                    }
                    if (Helper.mc.player.onGround) {
                        MovementUtils.strafe();
                    }
                }
            }
        }
    }

    private void disabler() {

        int elytra = InventoryUtil.getItemIndex(Items.ELYTRA);

        if (Helper.mc.player.inventory.getItemStack().getItem() != Items.ELYTRA)
            Helper.mc.playerController.windowClick(0, elytra < 9 ? elytra + 36 : elytra, 1, ClickType.PICKUP, Helper.mc.player);

        Helper.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Helper.mc.player);
        Helper.mc.player.connection.sendPacket(new CPacketEntityAction(Helper.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        Helper.mc.player.connection.sendPacket(new CPacketEntityAction(Helper.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        Helper.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Helper.mc.player);
        Helper.mc.playerController.windowClick(0, elytra < 9 ? elytra + 36 : elytra, 1, ClickType.PICKUP, Helper.mc.player);

    }

    @EventTarget
    public void on(EventSendPacket event) {
        if (strafeMode.currentMode.equals("Elytra")) {
            if (Helper.mc.player.isOnLadder() || Helper.mc.player.isInLiquid() || InventoryUtil.getSlotWithElytra() == -1) {
                return;

            }
        }
    }
}
