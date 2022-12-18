package Game.Sense.client.draggable.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.draggable.component.DraggableComponent;
import Game.Sense.client.feature.impl.hud.Hud;

public class DraggableWaterMark extends DraggableComponent {
    public DraggableWaterMark() {
        super("WaterMark", 0, 1, 4, 1);
    }

    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.waterMark.getBoolValue();
    }
}
