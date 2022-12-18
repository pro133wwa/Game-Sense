package Game.Sense.client.Helper.events.impl.packet;

import Game.Sense.client.Helper.events.Event;
import Game.Sense.client.Helper.events.callables.EventCancellable;
public class EventMessage extends EventCancellable implements Event {

    public String message;

    public EventMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
