package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

import java.util.concurrent.TimeUnit;

public class HighJump extends Module {
    public HighJump() {
        super("HighJump","Подкидывает высоко вверх", ModuleCategory.MOVEMENT);
    }
    @Override
    public void onEnable() {
        if (mc.player.onGround) {
            mc.player.jump();
        }
        new Thread(() -> {
            mc.player.motionY = 9f;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mc.player.motionY = 8.742f;
            this.toggle();
        }).start();
    }
}
