package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;

public class AutoGapple extends Module {
    private boolean isActive;
    public static NumberSetting health;

    public AutoGapple() {
        super("AutoGapple", "Кушает гепл при определенном хп", ModuleCategory.COMBAT);
        health = new NumberSetting("Health Amount", 15, 1, 20, 1, () -> true);
        addSettings(health);
    }

    @EventTarget
    public void onUpdate(EventPreMotion eventUpdate) {
        this.setSuffix("" + (int) health.getNumberValue());
        if (mc.player == null || mc.world == null)
            return;
        if (isGoldenApple(mc.player.getHeldItemOffhand()) && mc.player.getHealth() <= health.getNumberValue()) {
            isActive = true;
            mc.gameSettings.keyBindUseItem.pressed = true;
        } else if (isActive) {
            mc.gameSettings.keyBindUseItem.pressed = false;
            isActive = false;
        }
    }

    private boolean isGoldenApple(ItemStack itemStack) {
        return (itemStack != null && !itemStack.func_190926_b() && itemStack.getItem() instanceof ItemAppleGold);
    }
}
