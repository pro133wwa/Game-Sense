package Game.Sense.client.feature.impl.player;

import Game.Sense.client.GameSense;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import net.minecraft.entity.Entity;

public class NoClip extends Feature {
    public NoClip() {
        super("NoClip", "Позволяет ходить сквозь стены", FeatureCategory.Player);

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
