package Game.Sense.client.feature.impl.player;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;

public class FastPlace extends Feature {
    public FastPlace() {
        super("FastPlace", FeatureCategory.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        mc.rightClickDelayTimer = 0;
    }

    @Override
    public void onDisable() {
        mc.rightClickDelayTimer = 6;
        super.onDisable();
    }
}
