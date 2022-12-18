package Game.Sense.client.feature.impl.combat;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventPreMotion;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

public class AppleGoldenTimer extends Feature {
    public static boolean cooldown;
    private boolean isEated;
    public static BooleanSetting smart = new BooleanSetting("Smart", true, () -> true);


    public AppleGoldenTimer() {
        super("AppleGoldenTimer", FeatureCategory.Combat);
        addSettings();
    }

    @EventTarget
    public void onUpdate(EventPreMotion eventUpdate) {
        if (mc.player.getHeldItemOffhand().isOnFinish() || mc.player.getHeldItemMainhand().isOnFinish() && mc.player.getActiveItemStack().getItem() == Items.GOLDEN_APPLE) {
            isEated = true;
        }
        if (isEated) {
            mc.player.getCooldownTracker().setCooldown(Items.GOLDEN_APPLE, 55);
            isEated = false;
        }
        if (mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE) && mc.player.getActiveItemStack().getItem() == Items.GOLDEN_APPLE) {
            mc.gameSettings.keyBindUseItem.setPressed(false);
        } else if (Mouse.isButtonDown(1) && !(mc.currentScreen instanceof GuiContainer)) {
            mc.gameSettings.keyBindUseItem.setPressed(true);
        }
    }

    private boolean isGoldenApple(ItemStack itemStack) {
        return (itemStack != null && !itemStack.func_190926_b() && itemStack.getItem() instanceof ItemAppleGold);
    }
}
