package Game.Sense.client.feature.impl.movement;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventPreMotion;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.NumberSetting;
import Game.Sense.client.utils.math.TimerHelper;
import Game.Sense.client.utils.movement.MovementUtils;

public class Spider extends Feature {
    TimerHelper spiderTimer = new TimerHelper();
    public NumberSetting climbSpeed = new NumberSetting("Climb Speed", 1, 0, 5, 0.1F, () -> true);

    public Spider() {
        super("Spider", "Позволяет взбираться вверх по стенам", FeatureCategory.Movement);
        addSettings(climbSpeed);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        setSuffix("" + climbSpeed.getNumberValue());
    }

    @EventTarget
    public void onPreMotion(EventPreMotion eventPreMotion) {
        if (MovementUtils.isMoving() && mc.player.isCollidedHorizontally && spiderTimer.hasReached(climbSpeed.getNumberValue() * 100)) {
            eventPreMotion.setOnGround(true);
            mc.player.jump();
            spiderTimer.reset();
        }
    }
}
