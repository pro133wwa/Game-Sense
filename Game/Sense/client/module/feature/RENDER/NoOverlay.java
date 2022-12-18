package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.MobEffects;

public class NoOverlay extends Module {
    public static BooleanSetting rain = new BooleanSetting("Rain", true, () -> true);
    public static BooleanSetting noHurtCam = new BooleanSetting("HurtCam", true, () -> true);
    public static BooleanSetting cameraClip = new BooleanSetting("Camera Clip", true, () -> true);
    public static BooleanSetting antiTotem = new BooleanSetting("AntiTotemAnimation", false, () -> true);
    public static BooleanSetting noFire = new BooleanSetting("NoFireOverlay", false, () -> true);
    public static BooleanSetting noBossBar = new BooleanSetting("NoBossBar", false, () -> true);
    public static BooleanSetting noArmorStand = new BooleanSetting("ArmorStand", false, () -> true);
    public static BooleanSetting blindness = new BooleanSetting("Blindness", true, () -> true);

    public NoOverlay() {
        super("NoRender", "Убирает опредленные элементы рендера в игре", ModuleCategory.RENDER);
        addSettings(rain, noArmorStand, noHurtCam, cameraClip, antiTotem, noFire, blindness, noBossBar);

    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (rain.getBoolValue() && mc.world.isRaining()) {
            mc.world.setRainStrength(0);
            mc.world.setThunderStrength(0);
        }
        if (blindness.getBoolValue() && mc.player.isPotionActive(MobEffects.BLINDNESS) || mc.player.isPotionActive(MobEffects.NAUSEA)) {
            mc.player.removePotionEffect(MobEffects.NAUSEA);
            mc.player.removePotionEffect(MobEffects.BLINDNESS);
        }
        if (noArmorStand.getBoolValue()) {
            if (mc.player == null || mc.world == null) {
                return;
            }
            for (Entity entity : mc.world.loadedEntityList) {
                if (entity == null || !(entity instanceof EntityArmorStand)) continue;
                mc.world.removeEntity(entity);
            }
        }
    }

}
