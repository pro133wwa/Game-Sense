package Game.Sense.client.mine.drag.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.DraggableComponent;
import Game.Sense.client.module.feature.RENDER.Hud;

public class DraggableUserInfo extends DraggableComponent {

    public DraggableUserInfo() {
        super("User Info", 500, 25, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.user_info.getBoolValue();
    }
}
