package Game.Sense.client.event.events.impl.render;

import Game.Sense.client.event.events.Event;
import Game.Sense.client.event.types.EventType;

public class EventRenderScoreboard implements Event {

    private EventType state;

    public EventRenderScoreboard(EventType state) {
        this.state = state;
    }

    public EventType getState() {
        return this.state;
    }

    public void setState(EventType state) {
        this.state = state;
    }

    public boolean isPre() {
        return this.state == EventType.PRE;
    }
}
