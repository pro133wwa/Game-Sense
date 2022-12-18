package Game.Sense.client.feature.impl.visual;

import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.ListSetting;
import Game.Sense.client.ui.settings.impl.NumberSetting;

public class Cosmetics extends Feature {
    public static ListSetting wings = new ListSetting("Wings Mode", "White", () -> true, "White", "Gray");
    public static NumberSetting scaleWings = new NumberSetting("Scale", 0.3f, 0.4f, 2.0f, 0.1f, () -> true);

    public Cosmetics() {
        super("Cosmetics", FeatureCategory.Visuals);
        addSettings(wings, scaleWings);
    }
}
