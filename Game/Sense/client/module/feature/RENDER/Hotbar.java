package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.render.EventRender2D;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

public class Hotbar extends Module {
    public Hotbar() {
        super("Hotbar", "555",  ModuleCategory.RENDER);
    }

    @EventTarget
    public void hotbar(EventRender2D event2D) {
    }
}
