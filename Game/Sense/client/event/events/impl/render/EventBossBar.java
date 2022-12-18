package Game.Sense.client.event.events.impl.render;

import Game.Sense.client.event.events.Event;

public class EventBossBar implements Event {
    public String bossName;

    public EventBossBar(String bossName) {
        this.bossName = bossName;
    }

}
