package Game.Sense.client.draggable.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.draggable.component.DraggableComponent;
import Game.Sense.client.feature.impl.combat.KillAura;
import Game.Sense.client.feature.impl.hud.TargetHUD;

public class DraggableTargetHUD extends DraggableComponent {

    public DraggableTargetHUD() {
        super("TargetHUD", 350, 25, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(TargetHUD.class).isEnabled() && KillAura.target != null;
    }
}
