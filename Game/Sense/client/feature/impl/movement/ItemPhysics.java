package Game.Sense.client.feature.impl.movement;


import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.NumberSetting;

public class ItemPhysics extends Feature {
    public static NumberSetting physicsSpeed;

    public ItemPhysics() {
        super("ItemPhysics", FeatureCategory.Visuals);
                physicsSpeed = new NumberSetting("Physics Speed", 0.5F, 0.1F, 5.0F, 0.5F, () -> true);
        addSettings(physicsSpeed);
    }
}
