package Game.Sense.client.module.feature.AAA;


import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.NumberSetting;

public class PushAttack
        extends Module {
    private final NumberSetting clickCoolDown = new NumberSetting("Click CoolDown", 1.0f, 0.5f, 1.0f, 0.1f, () -> true);

    public PushAttack() {
        super("PushAttack", "Позволяет бить на ЛКМ не смотря на использование предметов", ModuleCategory.COMBAT);
        addSettings(clickCoolDown);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.getCooledAttackStrength(0.0f) == clickCoolDown.getNumberValue() && mc.gameSettings.keyBindAttack.pressed) {
            mc.clickMouse();
        }
    }
}
