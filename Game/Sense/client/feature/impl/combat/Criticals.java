package Game.Sense.client.feature.impl.combat;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;
import Game.Sense.client.utils.movement.MovementUtils;

public class Criticals extends Feature {
    public static BooleanSetting smart = new BooleanSetting("Smart", false, () -> true);

    public Criticals() {
        super("Criticals", "", FeatureCategory.Combat);
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
