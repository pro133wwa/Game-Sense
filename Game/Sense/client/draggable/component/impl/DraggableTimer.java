package Game.Sense.client.draggable.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.draggable.component.DraggableComponent;

import Game.Sense.client.feature.impl.hud.TimerSmart;
import Game.Sense.client.feature.impl.movement.Timer;

public class DraggableTimer extends DraggableComponent {

    public DraggableTimer() {
        super("Timer", 160, 400, 1, 1);
    }
    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(TimerSmart.class).isEnabled() && Timer.smart.getBoolValue();
    }
}



