package Game.Sense.client.draggable.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.draggable.component.DraggableComponent;
import Game.Sense.client.feature.impl.hud.Hud;

public class DraggablePotionHUD extends DraggableComponent {

    public DraggablePotionHUD() {
        super("Potion Status", 50, 100, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.potions.getBoolValue();
    }
}
