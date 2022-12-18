package Game.Sense.client.feature.impl.player;

import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;

public class NoPush extends Feature {
    public static BooleanSetting water = new BooleanSetting("Water", true, () -> true);
    public static BooleanSetting players = new BooleanSetting("Entity", true, () -> true);
    public static BooleanSetting blocks = new BooleanSetting("Blocks", true, () -> true);

    public NoPush() {
        super("NoPush", "������� ������������ �� �������, ���� � ������", FeatureCategory.Player);
        addSettings(players, water, blocks);
    }
}
