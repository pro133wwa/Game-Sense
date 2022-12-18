package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;

public class NoPush extends Module {
    public static BooleanSetting water = new BooleanSetting("Water", true, () -> true);
    public static BooleanSetting players = new BooleanSetting("Entity", true, () -> true);
    public static BooleanSetting blocks = new BooleanSetting("Blocks", true, () -> true);

    public NoPush() {
        super("NoPush", "Убирает отталкивание от игроков, воды и блоков", ModuleCategory.MOVEMENT);
        addSettings(players, water, blocks);
    }
}
