package Game.Sense.client.feature.impl.combat;


import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.NumberSetting;

public class PushAttack
        extends Feature {
    private final NumberSetting clickCoolDown = new NumberSetting("Click CoolDown", 1.0f, 0.5f, 1.0f, 0.1f, () -> true);

    public PushAttack() {
        super("PushAttack", "Позволяет бить на ЛКМ не смотря на использование предметов", FeatureCategory.Combat);
        addSettings(clickCoolDown);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.getCooledAttackStrength(0.0f) == clickCoolDown.getNumberValue() && mc.gameSettings.keyBindAttack.pressed) {
            mc.clickMouse();
        }
    }
}
