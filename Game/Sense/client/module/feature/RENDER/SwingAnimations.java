package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventTransformSideFirstPerson;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.module.feature.COMBAT.KillAura;
import Game.Sense.client.UI.Settings.Setting;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

public class SwingAnimations
        extends Module {
    public static boolean blocking;
    public static ListSetting swordAnim;
    public static BooleanSetting auraOnly;
    public static NumberSetting swingSpeed;
    public static NumberSetting spinSpeed;
    public static NumberSetting fapSmooth;

    public SwingAnimations() {
        super("SwingAnimations", "Добавляет анимацию на меч", ModuleCategory.RENDER);
        swordAnim = new ListSetting("Blocking Animation Mode", "GlobalSpin", () -> Boolean.valueOf(true), new String[] { "GlobalSpin", "Nursultan", "Glide" });
        auraOnly = new BooleanSetting("Aura Only", false, () -> Boolean.valueOf(true));
        swingSpeed = new NumberSetting("Swing Speed", "Скорость удара меча", 8.0F, 1.0F, 20.0F, 1.0F, () -> Boolean.valueOf(true));
        spinSpeed = new NumberSetting("Spin Speed", 4.0F, 1.0F, 10.0F, 1.0F, () -> Boolean.valueOf(swordAnim.currentMode.equals("GlobalSpin")));
        fapSmooth = new NumberSetting("Fap Smooth", 4.0F, 0.5F, 10.0F, 0.5F, () -> Boolean.valueOf(swordAnim.currentMode.equals("Nursultan")));
        addSettings(new Setting[] { (Setting)auraOnly, (Setting)swordAnim, (Setting)spinSpeed, (Setting)fapSmooth, (Setting)swingSpeed });
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String anim = swordAnim.getOptions();
        blocking = (GameSense.instance.featureManager.getFeature(KillAura.class).isEnabled() && KillAura.target != null);
        setSuffix(anim);
    }


    @EventTarget
    public void onSidePerson(EventTransformSideFirstPerson event) {
        if (blocking &&
                event.getEnumHandSide() == EnumHandSide.RIGHT)
            GlStateManager.translate(0.29D, 0.1D, -0.31D);
    }
}