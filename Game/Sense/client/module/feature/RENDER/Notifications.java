package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;

public class Notifications extends Module {
    public static ListSetting notifMode;

    public static BooleanSetting state;

    public Notifications() {
        super("Notifications", "Показывает необходимую информацию о модулях", ModuleCategory.RENDER);
        state = new BooleanSetting("Module State", true, () -> true);
        notifMode = new ListSetting("Notification Mode", "Rect", () -> true, "Rect", "Chat");
        addSettings(notifMode,state);
    }
}