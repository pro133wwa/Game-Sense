package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;

public class Cosmetics extends Module {
    public static ListSetting wings = new ListSetting("Wings Mode", "White", () -> true, "White", "Gray");
    public static NumberSetting scaleWings = new NumberSetting("Scale", 0.3f, 0.4f, 2.0f, 0.1f, () -> true);

    public Cosmetics() {
        super("Cosmetics", ModuleCategory.RENDER);
        addSettings(wings, scaleWings);
    }
}
