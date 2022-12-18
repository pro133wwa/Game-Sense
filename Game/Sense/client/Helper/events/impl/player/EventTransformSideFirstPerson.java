package Game.Sense.client.Helper.events.impl.player;

import Game.Sense.client.Helper.events.Event;
import net.minecraft.util.EnumHandSide;

public class EventTransformSideFirstPerson implements Event {

    private final EnumHandSide enumHandSide;

    public EventTransformSideFirstPerson(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }
}
