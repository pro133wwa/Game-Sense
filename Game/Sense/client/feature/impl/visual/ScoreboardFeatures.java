package Game.Sense.client.feature.impl.visual;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.render.EventRenderScoreboard;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;
import Game.Sense.client.ui.settings.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;

public class ScoreboardFeatures extends Feature {

    public static BooleanSetting noScore;
    public NumberSetting x;
    public NumberSetting y;

    public ScoreboardFeatures() {
        super("Scoreboard", "Позволяет настроить скорборд на сервере", FeatureCategory.Visuals);
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
