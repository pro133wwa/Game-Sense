package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;

public class Criticals extends Module {
    public static BooleanSetting smart = new BooleanSetting("Smart", false, () -> true);

    public Criticals() {
        super("Criticals", "", ModuleCategory.COMBAT);
        addSettings(smart);
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
