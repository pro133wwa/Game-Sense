package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.render.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class DeathSound
        extends Module {
    public ListSetting deathSoundMode = new ListSetting("DeathSound Mode", "Wendovsky", () -> true, "Wendovsky");
    public NumberSetting volume = new NumberSetting("Volume", 50.0f, 1.0f, 100.0f, 1.0f, () -> true);

    public DeathSound() {
        super("DeathSounds", "Воспроизводит звуки при смерти какого либо игрока", ModuleCategory.PLAYER);
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