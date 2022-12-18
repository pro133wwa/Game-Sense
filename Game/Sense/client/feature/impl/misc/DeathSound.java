package Game.Sense.client.feature.impl.misc;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.ListSetting;
import Game.Sense.client.ui.settings.impl.NumberSetting;
import Game.Sense.client.utils.render.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class DeathSound
        extends Feature {
    public ListSetting deathSoundMode = new ListSetting("DeathSound Mode", "Wendovsky", () -> true, "Wendovsky");
    public NumberSetting volume = new NumberSetting("Volume", 50.0f, 1.0f, 100.0f, 1.0f, () -> true);

    public DeathSound() {
        super("DeathSounds", "Воспроизводит звуки при смерти какого либо игрока", FeatureCategory.Misc);
        this.addSettings(this.deathSoundMode, this.volume);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setSuffix(this.deathSoundMode.currentMode);
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == null || !(entity instanceof EntityPlayer) || !(((EntityPlayer) entity).getHealth() <= 0.0f) || ((EntityLivingBase) entity).deathTime >= 1 || !(mc.player.getDistanceToEntity(entity) < 10.0f) || entity.ticksExisted <= 5)
                continue;
            float volume = this.volume.getNumberValue() / 10.0f;
            if (deathSoundMode.currentMode.equalsIgnoreCase("Wendovsky")) {
                SoundUtils.playSound("mem.wav", -30.0f + volume * 3.0f, false);
            }
        }
    }
}