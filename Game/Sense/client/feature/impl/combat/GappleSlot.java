package Game.Sense.client.feature.impl.combat;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;
import Game.Sense.client.ui.settings.impl.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

public class GappleSlot
        extends Feature {
    public static BooleanSetting definiteHealth;
    public static NumberSetting health;

    public GappleSlot() {
        super("GappleSlot", "Берёт автоматически щит", FeatureCategory.Combat);
        health = new NumberSetting("Health Amount", 10, 1, 20, 0.5F, () -> definiteHealth.getBoolValue());
        definiteHealth = new BooleanSetting("Definite Health", false, () -> true);
        addSettings(health, definiteHealth);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        block4: {
            if (Minecraft.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
                if (!((double)Minecraft.player.getHealth() <= this.health.getNumberValue())) break block4;
            }
        }
        if (Minecraft.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE && this.findGapple() != -1) {
            if (GappleSlot.mc.currentScreen instanceof GuiInventory || GappleSlot.mc.currentScreen == null) {
                if ((double)Minecraft.player.getHealth() <= this.health.getNumberValue()) {
                    GappleSlot.mc.playerController.windowClick(0, this.findGapple(), 1, ClickType.PICKUP, Minecraft.player);
                    GappleSlot.mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, Minecraft.player);
                }
            }
        }
    }

    public int findGapple() {
        for (int i = 0; i < 45; ++i) {
            ItemStack itemStack = Minecraft.player.inventoryContainer.getSlot(i).getStack();
            if (itemStack.getItem() != Items.GOLDEN_APPLE) continue;
            return i;
        }
        return -1;
    }
}
