package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.GameSense;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

public class Button extends Module {

    public static BooleanSetting hitBox = new BooleanSetting("VisualBox", true, () -> true);
    public static BooleanSetting kur = new BooleanSetting("Grab", true, () -> true);
    public Button(){
        super("Button", ModuleCategory.RENDER);
        addSettings(kur, hitBox);
    }

    @Override
    public void onEnable() {
        GameSense.instance.featureManager.getFeature(Button.class).setEnabled(false);
        super.onEnable();
    }
}
