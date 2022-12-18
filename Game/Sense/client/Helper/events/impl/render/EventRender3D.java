package Game.Sense.client.Helper.events.impl.render;

import Game.Sense.client.Helper.events.Event;

public class EventRender3D implements Event {
    private final float partialTicks;

    public EventRender3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
