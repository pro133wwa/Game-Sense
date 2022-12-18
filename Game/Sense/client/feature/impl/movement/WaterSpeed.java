package Game.Sense.client.feature.impl.movement;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;
import Game.Sense.client.ui.settings.impl.NumberSetting;
import Game.Sense.client.utils.movement.MovementUtils;
import net.minecraft.init.MobEffects;

public class WaterSpeed extends Feature {

    private final NumberSetting speed;
    private final BooleanSetting speedCheck;

    public WaterSpeed() {
        super("WaterSpeed", "Делает вас быстрее в воде" , FeatureCategory.Movement);
        speed = new NumberSetting("Speed Amount", 0.4f, 0.1F, 4, 0.01F, () -> true);
        speedCheck = new BooleanSetting("Speed Potion Check", false, () -> true);
        addSettings(speedCheck, speed);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!mc.player.isPotionActive(MobEffects.SPEED) && speedCheck.getBoolValue()) {
            return;
        }
        if (mc.player.isInWater() || mc.player.isInLava()) {
            MovementUtils.setSpeed(speed.getNumberValue());
        }
    }
}
