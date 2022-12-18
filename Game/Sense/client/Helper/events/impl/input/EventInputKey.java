package Game.Sense.client.Helper.events.impl.input;

import Game.Sense.client.Helper.events.Event;

public class EventInputKey implements Event {

    private int key;

    public EventInputKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
