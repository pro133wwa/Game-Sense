package Game.Sense.client.feature.impl.movement;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;

public class AirJump extends Feature {

    public AirJump() {
        super("AirJump", "Позволяет прыгать по воздуху", FeatureCategory.Movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if(mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.jump();
        }
    }
}
