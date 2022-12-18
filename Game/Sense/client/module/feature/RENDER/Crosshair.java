package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;

public class Crosshair extends Module {
    public static BooleanSetting smart = new BooleanSetting("Smart", false, () -> true);

    public Crosshair() {
        super("Crosshair", "", ModuleCategory.RENDER);
        addSettings(smart);
    }

   
}
