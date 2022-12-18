package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AutoTool extends Module {
    private BooleanSetting swapBack = new BooleanSetting("Swap Back", true, () -> true);
    private BooleanSetting saveItem = new BooleanSetting("Save Item", true, () -> true);
    public BooleanSetting silentSwitch = new BooleanSetting("Silent Switch", true, () -> true);

    public int itemIndex;
    private boolean swap;
    private long swapDelay;
    private ItemStack swapedItem = null;
    private List<Integer> lastItem = new ArrayList<>();

    public AutoTool() {
        super("AutoTool", "Автоматически берет лучший инструмент в руки при ломании блока", ModuleCategory.PLAYER);
        addSettings(swapBack, saveItem, silentSwitch);
    }


    @EventTarget
    public void onUpdate(EventUpdate update) {

        Block hoverBlock = null;
        if (mc.objectMouseOver.getBlockPos() == null) return;
        hoverBlock = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
        List<Integer> bestItem = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (hoverBlock == null) break;
            if (!(mc.player.inventory.getStackInSlot(i).getMaxDamage() - mc.player.inventory.getStackInSlot(i).getItemDamage() > 1) && saveItem.getBoolValue())
                continue;
            if(mc.player.getDigSpeed(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()), mc.player.inventory.getStackInSlot(i)) > 1)
                bestItem.add(i);

        }

        bestItem.sort(Comparator.comparingDouble(x -> -mc.player.getDigSpeed(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()), mc.player.inventory.getStackInSlot(x))));


        if (!bestItem.isEmpty() && mc.gameSettings.keyBindAttack.pressed) {
            if (mc.player.inventory.getCurrentItem() != swapedItem) {
                lastItem.add(mc.player.inventory.currentItem);
                if (silentSwitch.getBoolValue())
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(bestItem.get(0)));
                else
                    mc.player.inventory.currentItem = bestItem.get(0);

                itemIndex = bestItem.get(0);
                swap = true;
            }
            swapDelay = System.currentTimeMillis();
        } else if (swap && !lastItem.isEmpty() && System.currentTimeMillis() >= swapDelay + 300 && swapBack.getBoolValue()) {

            if (silentSwitch.getBoolValue())
                mc.player.connection.sendPacket(new CPacketHeldItemChange(lastItem.get(0)));
            else
                mc.player.inventory.currentItem = lastItem.get(0);

            itemIndex = lastItem.get(0);
            lastItem.clear();
            swap = false;
        }
    }
}