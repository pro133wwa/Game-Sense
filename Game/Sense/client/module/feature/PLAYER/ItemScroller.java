package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.NumberSetting;

public class ItemScroller extends Module {

    public static NumberSetting scrollerDelay;

    public ItemScroller() {
        super("ItemScroller", "Позволяет быстро лутать сундуки при нажатии на шифт и ЛКМ", ModuleCategory.PLAYER);

        scrollerDelay = new NumberSetting("Scroller Delay", 80, 0, 1000, 1, () -> true);
        addSettings(scrollerDelay);

    }
}