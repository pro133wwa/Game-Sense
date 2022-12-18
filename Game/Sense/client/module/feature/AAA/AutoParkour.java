package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

public class AutoParkour extends Module {
    public AutoParkour() {
        super("AutoParkour", "Автоматически прыгает с конца блока", ModuleCategory.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001)).isEmpty() && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.jump();
        }
    }
}
