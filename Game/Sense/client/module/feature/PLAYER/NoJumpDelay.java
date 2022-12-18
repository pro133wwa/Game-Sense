package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

public class NoJumpDelay extends Module {
    public NoJumpDelay() {
        super("NoJumpDelay", "Уберает задержку при прыжке", ModuleCategory.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (!isEnabled()) {
            return;
        }
        mc.player.jumpTicks = 0;
    }
}
