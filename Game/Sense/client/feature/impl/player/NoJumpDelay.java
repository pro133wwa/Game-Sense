package Game.Sense.client.feature.impl.player;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;

public class NoJumpDelay extends Feature {
    public NoJumpDelay() {
        super("NoJumpDelay", "Уберает задержку при прыжке", FeatureCategory.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (!isEnabled()) {
            return;
        }
        mc.player.jumpTicks = 0;
    }
}
