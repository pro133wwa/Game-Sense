package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.UI.Settings.impl.NumberSetting;

public class LongJump
        extends Module {
    public NumberSetting mode;
    public NumberSetting boostMultiplier;
    public NumberSetting motionBoost;
    public NumberSetting speed;

    public LongJump() {
        super("Long Jump", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u043f\u0440\u044b\u0433\u0430\u0442\u044c \u043d\u0430 \u0431\u043e\u043b\u044c\u0448\u0443\u044e \u0434\u043b\u0438\u043d\u043d\u0443", ModuleCategory.MOVEMENT);
    }

    @EventTarget
    public void onPreUpdate(EventPreMotion event) {
        if (LongJump.mc.player.hurtTime > 0) {
            LongJump.mc.timer.timerSpeed = 1.0f;
            if (LongJump.mc.player.fallDistance != 0.0f) {
                LongJump.mc.player.motionY += 0.039;
            }
            if (LongJump.mc.player.onGround) {
                LongJump.mc.player.jump();
            } else {
                LongJump.mc.timer.timerSpeed = 0.2f;
                LongJump.mc.player.motionY += 0.075;
                LongJump.mc.player.motionX *= (double)1.065f;
                LongJump.mc.player.motionZ *= (double)1.065f;
            }
    }}

    @Override
    public void onDisable() {
        LongJump.mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}
