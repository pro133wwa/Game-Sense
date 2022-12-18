package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

public class AppleGoldenTimer extends Module {
    public static boolean cooldown;
    private boolean isEated;
    public static BooleanSetting smart = new BooleanSetting("Smart", true, () -> true);


    public AppleGoldenTimer() {
        super("AppleGoldenTimer", ModuleCategory.COMBAT);
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
