package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;
import net.minecraft.init.MobEffects;

public class WaterSpeed extends Module {

    private final NumberSetting speed;
    private final BooleanSetting speedCheck;

    public WaterSpeed() {
        super("WaterSpeed", "Делает вас быстрее в воде" , ModuleCategory.MOVEMENT);
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
