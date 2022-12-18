package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.entity.Entity;

public class NoClip extends Module {
    public NoClip() {
        super("NoClip", "Позволяет ходить сквозь стены", ModuleCategory.MOVEMENT);

    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player != null) {
            mc.player.noClip = true;
            mc.player.motionY = 0.00001;

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.motionY = 0.4;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.motionY = -0.4;
            }
        }
    }


    public static boolean isNoClip(Entity entity) {
        if (GameSense.instance.featureManager.getFeature(NoClip.class).isEnabled() && mc.player != null && (mc.player.ridingEntity == null || entity == mc.player.ridingEntity))
            return true;
        return entity.noClip;

    }

    public void onDisable() {
        mc.player.noClip = false;
        super.onDisable();
    }
}
