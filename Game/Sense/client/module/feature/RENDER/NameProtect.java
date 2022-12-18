package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;

public class NameProtect extends Module {
    public static BooleanSetting myName = new BooleanSetting("My Name", true, () -> true);
    public static BooleanSetting friends = new BooleanSetting("Friends Spoof", true, () -> true);
    public static BooleanSetting otherName = new BooleanSetting("Other Names", false, () -> true);
    public static BooleanSetting tabSpoof = new BooleanSetting("Tab Spoof", false, () -> true);
    public static BooleanSetting scoreboardSpoof = new BooleanSetting("Scoreboard Spoof", true, () -> true);

    public NameProtect() {
        super("NameProtect", "Позволяет скрывать информацию о себе и других игроках", ModuleCategory.RENDER);
        addSettings(myName, otherName,friends, tabSpoof, scoreboardSpoof);
    }
}
