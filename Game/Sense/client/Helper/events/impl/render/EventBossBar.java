package Game.Sense.client.Helper.events.impl.render;

import Game.Sense.client.Helper.events.Event;

public class EventBossBar implements Event {
    public String bossName;

    public EventBossBar(String bossName) {
        this.bossName = bossName;
    }

}
