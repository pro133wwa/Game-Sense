package Game.Sense.client.mine.drag.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.DraggableComponent;
import Game.Sense.client.module.feature.RENDER.FeatureList;
import Game.Sense.client.module.feature.RENDER.ModuleList;


public class DragModuleList extends DraggableComponent {

    public DragModuleList() {
        super("ModuleList", 350, 25, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(ModuleList.class).isEnabled();
    }
}