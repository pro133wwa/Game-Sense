package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.render.EventRenderScoreboard;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;

public class ScoreboardFeatures extends Module {

    public static BooleanSetting noScore;
    public NumberSetting x;
    public NumberSetting y;

    public ScoreboardFeatures() {
        super("Scoreboard", "Позволяет настроить скорборд на сервере", ModuleCategory.RENDER);
        noScore = new BooleanSetting("No Scoreboard", false, () -> true);
        x = new NumberSetting("Scoreboard X", 0, -1000, 1000, 1, () -> !noScore.getBoolValue());
        y = new NumberSetting("Scoreboard Y", 0, -500, 500, 1, () -> !noScore.getBoolValue());
        addSettings(noScore, x, y);
    }

    @EventTarget
    public void onRenderScoreboard(EventRenderScoreboard event) {
        if (event.isPre()) {
            GlStateManager.translate(-x.getNumberValue(), y.getNumberValue(), 12);
        } else {
            GlStateManager.translate(x.getNumberValue(), -y.getNumberValue(), 12);
        }
    }
}
