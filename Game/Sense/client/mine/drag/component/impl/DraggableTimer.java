package Game.Sense.client.mine.drag.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.DraggableComponent;

import Game.Sense.client.module.feature.RENDER.TimerSmart;
import Game.Sense.client.module.feature.MOVEMENT.Timer;

public class DraggableTimer extends DraggableComponent {

    public DraggableTimer() {
        super("Timer", 160, 400, 1, 1);
    }
    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(TimerSmart.class).isEnabled() && Timer.smart.getBoolValue();
    }
}



