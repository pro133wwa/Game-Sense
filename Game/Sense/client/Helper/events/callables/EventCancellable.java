package Game.Sense.client.Helper.events.callables;

import Game.Sense.client.Helper.events.Cancellable;
import Game.Sense.client.Helper.events.Event;

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
