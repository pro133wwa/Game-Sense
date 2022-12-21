package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

public class ContainerBlur
        extends Module {
    public static NumberSetting blurStrength;

    public ContainerBlur() {
        super("ContainerBlur", "\u0420\u0430\u0437\u043c\u044b\u0432\u0430\u0435\u0442 \u0437\u0430\u0434\u043d\u0438\u0439 \u0444\u043e\u043d \u0432 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435, \u0441\u0443\u043d\u0434\u0443\u043a\u0435 \u0438 \u0442.\u0434", ModuleCategory.RENDER);
        blurStrength = new NumberSetting("Blur Strength", 20.0f, 5.0f, 50.0f, 1.0f, () -> true);
        this.addSettings(blurStrength);
    }
}

