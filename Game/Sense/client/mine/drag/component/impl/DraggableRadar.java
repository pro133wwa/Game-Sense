package Game.Sense.client.mine.drag.component.impl;
import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.DraggableComponent;
import Game.Sense.client.module.feature.RENDER.Hud;

public class DraggableRadar extends DraggableComponent {
    public DraggableRadar() {
        super("Radar", 0, 10, 4, 1);
    }

    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.Radar.getBoolValue();
    }
}