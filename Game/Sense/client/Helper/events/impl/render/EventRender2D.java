package Game.Sense.client.Helper.events.impl.render;

import net.minecraft.client.gui.ScaledResolution;
import Game.Sense.client.Helper.events.Event;

public class EventRender2D implements Event {

    private final ScaledResolution resolution;

    public EventRender2D(ScaledResolution resolution) {
        this.resolution = resolution;
    }

    public ScaledResolution getResolution() {
        return resolution;
    }

}
