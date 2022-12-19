package Game.Sense.client.mine.drag.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.DraggableComponent;
import Game.Sense.client.module.feature.COMBAT.KillAura;
import Game.Sense.client.module.feature.RENDER.TargetHUD;

public class DraggableTargetHUD extends DraggableComponent {

    public DraggableTargetHUD() {
        super("TargetHUD", 350, 25, 1, 1);
    }


    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(TargetHUD.class).isEnabled() && KillAura.target != null;
    }
}
