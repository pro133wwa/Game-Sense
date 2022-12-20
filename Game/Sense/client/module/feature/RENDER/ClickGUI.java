package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.GameSense;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ColorSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ClickGUI extends Module {
    public static BooleanSetting girl = new BooleanSetting("Anime", false, () -> true);
    public static BooleanSetting potato_mode = new BooleanSetting("Potato Mode", false, () -> true);
    public static ListSetting backGroundColor = new ListSetting("Background Color", "Static", () -> !potato_mode.getBoolValue(), "Astolfo", "Rainbow", "Static");
    public static ListSetting girlmode = new ListSetting("Anime Mode", "Girl", () -> girl.getBoolValue(), "Girl", "Rem", "Gachi", "Violet", "Kirshtein", "002");
    public static ListSetting panelMode = new ListSetting("Panel Mode", "Rect", () -> girl.getBoolValue(), "Rect", "Blur");
    public static BooleanSetting glow = new BooleanSetting("Glow", true, () -> !potato_mode.getBoolValue());
    public static BooleanSetting glowGUI = new BooleanSetting("Glow", false, () -> true);
    public static BooleanSetting particles = new BooleanSetting("Particles", false, () -> !potato_mode.getBoolValue());

    public static BooleanSetting blur = new BooleanSetting("Blur", false, () -> !potato_mode.getBoolValue());
    public static NumberSetting blurInt = new NumberSetting("Blur Amount", 5, 5, 10, 1, () -> blur.getBoolValue() && !potato_mode.getBoolValue());
    public static ColorSetting color;

    public static ColorSetting outlinecolor;
    public static ColorSetting bgonecolor;
    public static ColorSetting bgtwocolor;
    public static ListSetting mode = new ListSetting("Mode", "Rockstar Styled", () -> true, "Rockstar", "Rockstar Styled", "Rockstar New");
    public static NumberSetting speed = new NumberSetting("Speed", 35, 10, 100, 1, () -> true);
    public static NumberSetting glowRadius2 = new NumberSetting("Glow Radius", 10, 5, 10, 1, () -> !potato_mode.getBoolValue());

    public ClickGUI() {
        super("ClickGUI", "Меню чита", ModuleCategory.RENDER);
        setBind(Keyboard.KEY_RSHIFT);
        color = new ColorSetting("Text Color", new Color(255, 255, 255, 255).getRGB(), () -> true);
        outlinecolor = new ColorSetting("Outline Color", new Color(255, 255, 255, 255).getRGB(), () -> true);
        bgonecolor = new ColorSetting("Gui Color 1", new Color(255, 0, 0, 255).getRGB(), () -> true);
        bgtwocolor = new ColorSetting("Gui Color 2", new Color(0, 44, 255, 255).getRGB(), () -> true);
        addSettings(color, bgonecolor, bgtwocolor, glowGUI);

    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(GameSense.instance.clickGui);
        GameSense.instance.featureManager.getFeature(ClickGUI.class).setEnabled(false);
        super.onEnable();
    }
}