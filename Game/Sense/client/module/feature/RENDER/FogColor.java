package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.render.EventFogColor;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ColorSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.render.ClientHelper;
import Game.Sense.client.Helper.Utility.render.ColorUtils;

import java.awt.*;

public class FogColor extends Module {

    public static NumberSetting distance;
    public ListSetting colorMode;
    public ColorSetting customColor;

    public FogColor() {
        super("FogColor", "Меняет цвет тумана", ModuleCategory.RENDER);
        colorMode = new ListSetting("Fog Color", "Rainbow", () -> true, "Rainbow", "Client", "Custom");
        distance = new NumberSetting("Distance", 0.10F, 0.01F, 2, 0.01F, () -> true);
        customColor = new ColorSetting("Custom Fog", new Color(0xAB31CB).getRGB(), () -> colorMode.currentMode.equals("Custom"));
        addSettings(colorMode, distance, customColor);
    }

    @EventTarget
    public void onFogColor(EventFogColor event) {
        String colorModeValue = colorMode.getOptions();
        if (colorModeValue.equalsIgnoreCase("Rainbow")) {
            Color color = ColorUtils.rainbow(1, 1, 1);
            event.setRed(color.getRed());
            event.setGreen(color.getGreen());
            event.setBlue(color.getBlue());
        } else if (colorModeValue.equalsIgnoreCase("Client")) {
            Color clientColor = ClientHelper.getClientColor();
            event.setRed(clientColor.getRed());
            event.setGreen(clientColor.getGreen());
            event.setBlue(clientColor.getBlue());
        } else if (colorModeValue.equalsIgnoreCase("Custom")) {
            Color customColorValue = new Color(customColor.getColorValue());
            event.setRed(customColorValue.getRed());
            event.setGreen(customColorValue.getGreen());
            event.setBlue(customColorValue.getBlue());
        }
    }
}
