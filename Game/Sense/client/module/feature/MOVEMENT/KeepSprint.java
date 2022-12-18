package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.math.MathematicHelper;

public class KeepSprint extends Module {

    public static NumberSetting speed;
    public static BooleanSetting setSprinting;

    public KeepSprint() {
        super("KeepSprint", "Повзоляет редактировать скорость игрока при ударе", ModuleCategory.MOVEMENT);
        speed = new NumberSetting("Keep Speed", 1, 0.5F, 2, 0.01F, () -> true);
        setSprinting = new BooleanSetting("Set Sprinting", true, () -> true);
        addSettings(setSprinting, speed);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setSuffix("" + MathematicHelper.round(speed.getNumberValue(), 2));
    }
}