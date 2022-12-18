package Game.Sense.client.mine.drag;

import com.google.common.collect.Lists;
import Game.Sense.client.mine.drag.component.DraggableComponent;
import Game.Sense.client.mine.drag.component.impl.*;

import java.util.List;

public class DraggableHUD {

    private DraggableScreen screen;
    private final List<DraggableComponent> components;

    public DraggableHUD() {
        screen = new DraggableScreen();
        components = Lists.newArrayList();
        components.add(new DraggableTargetHUD());
        components.add(new DragModuleList());
        components.add(new DraggableWaterMark());
        components.add(new DraggableRadar());
        components.add(new DraggableStaff());
        components.add(new DraggableCoordsInfo());
        components.add(new DraggablePotionHUD());
        components.add(new DraggableSessionInfo());
        components.add(new DraggableUserInfo());
        components.add(new DraggableTimer());
        components.add(new DraggableIndicators());
    }

    public DraggableScreen getScreen() {
        return screen;
    }

    public List<DraggableComponent> getComponents() {
        return components;
    }

    public DraggableComponent getDraggableComponentByClass(Class<? extends DraggableComponent> classs) {
        for (DraggableComponent draggableComponent : components) {
            if (draggableComponent.getClass() == classs) {
                return draggableComponent;
            }
        }

        return null;
    }

}