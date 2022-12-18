package Game.Sense.client.feature.impl.hud;

import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;
import Game.Sense.client.ui.settings.impl.ListSetting;

public class Notifications extends Feature {
    public static ListSetting notifMode;

    public static BooleanSetting state;

    public Notifications() {
        super("Notifications", "Показывает необходимую информацию о модулях", FeatureCategory.Hud);
        state = new BooleanSetting("Module State", true, () -> true);
        notifMode = new ListSetting("Notification Mode", "Rect", () -> true, "Rect", "Chat");
        addSettings(notifMode,state);
    }
}