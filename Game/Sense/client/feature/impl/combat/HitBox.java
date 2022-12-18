package Game.Sense.client.feature.impl.combat;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.NumberSetting;

public class HitBox extends Feature {

    public static NumberSetting hitboxSize = new NumberSetting("HitBox Size", 0.25f, 0.1f, 2.f, 0.1f, () -> true);

    public HitBox() {
        super("HitBox", "Увеличивает хитбокс у игрока.", FeatureCategory.Combat);
        addSettings(hitboxSize);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        setSuffix("" + hitboxSize.getNumberValue());
    }
}
