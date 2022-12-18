package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.NumberSetting;

public class HitBox extends Module {

    public static NumberSetting hitboxSize = new NumberSetting("HitBox Size", 0.25f, 0.1f, 2.f, 0.1f, () -> true);

    public HitBox() {
        super("HitBox", "Увеличивает хитбокс у игрока.", ModuleCategory.COMBAT);
        addSettings(hitboxSize);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        setSuffix("" + hitboxSize.getNumberValue());
    }
}
