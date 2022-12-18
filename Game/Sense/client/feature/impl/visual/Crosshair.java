package Game.Sense.client.feature.impl.visual;

import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;

public class Crosshair extends Feature {
    public static BooleanSetting smart = new BooleanSetting("Smart", false, () -> true);

    public Crosshair() {
        super("Crosshair", "", FeatureCategory.Visuals);
        addSettings(smart);
    }

   
}
