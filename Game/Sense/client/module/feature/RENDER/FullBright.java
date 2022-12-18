package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright extends Module {

    private final ListSetting brightMode = new ListSetting("FullBright Mode", "Gamma", () -> true, "Gamma", "Potion");;

    public FullBright() {
        super("FullBright", "Убирает темноту в игре", ModuleCategory.RENDER);
        addSettings(brightMode);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (isEnabled()) {
            String mode = brightMode.getOptions();
            if (mode.equalsIgnoreCase("Gamma")) {
                mc.gameSettings.gammaSetting = 10f;
            }
            if (mode.equalsIgnoreCase("Potion")) {
                mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 817, 1));
            } else {
                mc.player.removePotionEffect(Potion.getPotionById(16));
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = 0.1f;
        mc.player.removePotionEffect(Potion.getPotionById(16));
    }
}
