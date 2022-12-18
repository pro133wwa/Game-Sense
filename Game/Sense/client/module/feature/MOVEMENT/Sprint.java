package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Автоматически включает спринт", ModuleCategory.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (mc.player.getFoodStats().getFoodLevel() / 2 > 3) {
            mc.player.setSprinting(MovementUtils.isMoving());
        }
    }

    @Override
    public void onDisable() {
        mc.player.setSprinting(false);
        super.onDisable();
    }
}
