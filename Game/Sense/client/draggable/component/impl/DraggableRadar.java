package Game.Sense.client.draggable.component.impl;
import Game.Sense.client.GameSense;
import Game.Sense.client.draggable.component.DraggableComponent;
import Game.Sense.client.feature.impl.hud.Hud;

public class DraggableRadar extends DraggableComponent {
    public DraggableRadar() {
        super("Radar", 0, 10, 4, 1);
    }

    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.Radar.getBoolValue();
    }
}