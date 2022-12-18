package Game.Sense.client.feature.impl.misc;

import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;

public class NameProtect extends Feature {
    public static BooleanSetting myName = new BooleanSetting("My Name", true, () -> true);
    public static BooleanSetting friends = new BooleanSetting("Friends Spoof", true, () -> true);
    public static BooleanSetting otherName = new BooleanSetting("Other Names", false, () -> true);
    public static BooleanSetting tabSpoof = new BooleanSetting("Tab Spoof", false, () -> true);
    public static BooleanSetting scoreboardSpoof = new BooleanSetting("Scoreboard Spoof", true, () -> true);

    public NameProtect() {
        super("NameProtect", "Позволяет скрывать информацию о себе и других игроках", FeatureCategory.Misc);
        addSettings(myName, otherName,friends, tabSpoof, scoreboardSpoof);
    }
}
