package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

public class AirJump extends Module {

    public AirJump() {
        super("AirJump", "Позволяет прыгать по воздуху", ModuleCategory.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if(mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.jump();
        }
    }
}
