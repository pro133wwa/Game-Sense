package Game.Sense.client.mine.drag.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.DraggableComponent;
import Game.Sense.client.module.feature.RENDER.Hud;

public class DraggablePotionHUD extends DraggableComponent {

    public DraggablePotionHUD() {
        super("Potion Status", 50, 100, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.potions.getBoolValue();
    }
}
