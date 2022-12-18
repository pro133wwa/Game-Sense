package Game.Sense.client.event.events.callables;

import Game.Sense.client.event.events.Cancellable;
import Game.Sense.client.event.events.Event;

public abstract class EventCancellable implements Event, Cancellable {

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

}
