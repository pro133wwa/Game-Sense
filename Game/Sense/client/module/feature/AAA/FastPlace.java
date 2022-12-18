package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", ModuleCategory.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        mc.rightClickDelayTimer = 0;
    }

    @Override
    public void onDisable() {
        mc.rightClickDelayTimer = 6;
        super.onDisable();
    }
}
