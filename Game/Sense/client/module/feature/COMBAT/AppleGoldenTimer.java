package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.COMBAT.event.EventItemEat;
import Game.Sense.client.module.feature.COMBAT.event.GAppleEatEvent;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

public class AppleGoldenTimer extends Module {

    public AppleGoldenTimer() {
        super("GAppleCooldown", "Меняет кулдаун геплам", ModuleCategory.COMBAT);
        addSettings();
    }

    @EventTarget
    public void onEatGapple(GAppleEatEvent event) {
        if (Minecraft.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            event.setCancelled(true);
            Minecraft.gameSettings.keyBindUseItem.pressed = false;
        }
    }
    @EventTarget
    public void onEat(EventItemEat eventItemEat) {
        if (Minecraft.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && Minecraft.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE)) {
            Minecraft.gameSettings.keyBindUseItem.pressed = false;
            eventItemEat.setCancelled(true);
        }
        if (eventItemEat.itemStack.getItem() instanceof ItemAppleGold) {
            Minecraft.player.getCooldownTracker().setCooldown(Items.GOLDEN_APPLE, 62);
        }
    }

}
