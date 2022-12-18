package Game.Sense.client.utils.render;

import Game.Sense.client.feature.impl.hud.FeatureList;
import Game.Sense.client.feature.impl.hud.TargetHUD;
import Game.Sense.client.feature.impl.visual.EntityESP;
import Game.Sense.client.utils.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

import java.awt.*;

public class ClientHelper implements Helper {
    public static ServerData serverData;

    public static Color getClientColor() {
        Color color = Color.white;
        Color onecolor = new Color(FeatureList.oneColor.getColorValue());
        Color twoColor = new Color(FeatureList.twoColor.getColorValue());
        double time = 10;
        String mode = FeatureList.colorList.getOptions();
        float yDist = (float) 4;
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().neverlose900_18.getFontHeight() + 5;
        }
        if (mode.equalsIgnoreCase("Rainbow")) {
            color = ColorUtils.rainbow((int) (1 * 200 * 0.1f), 1, 1.0f);
        } else if (mode.equalsIgnoreCase("Astolfo")) {
            color = ColorUtils.astolfo((int) yDist, yTotal);
        } else if (mode.equalsIgnoreCase("Custom")) {
            color = ColorUtils.TwoColoreffect(new Color(onecolor.getRGB()), new Color(twoColor.getRGB()), Math.abs(System.currentTimeMillis() / time) / 100.0 + 3.0F * (yDist * 2.55) / 60);
        } else if (mode.equalsIgnoreCase("Fade")) {
            color = ColorUtils.TwoColoreffect(new Color(onecolor.getRGB()), new Color(onecolor.darker().darker().getRGB()), Math.abs(System.currentTimeMillis() / time) / 100.0 + 3.0F * (yDist * 2.55) / 60);
        }
        return color;
    }

    public static Color getESPColor() {
        Color color = Color.white;
        Color onecolor = new Color(EntityESP.colorEsp.getColorValue());
        double time = 10;
        String mode = EntityESP.colorMode.getOptions();
        float yDist = (float) 4;
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().neverlose900_18.getFontHeight() + 5;
        }
        if (mode.equalsIgnoreCase("Rainbow")) {
            color = ColorUtils.rainbow((int) (1 * 200 * 0.1f), 0.5f, 1.0f);
        } else if (mode.equalsIgnoreCase("Astolfo")) {
            color = ColorUtils.astolfoColors(5000.0F, 1);
        } else if (mode.equalsIgnoreCase("Custom")) {
            color = new Color(onecolor.getRGB());
        } else if (mode.equalsIgnoreCase("Client")) {
            color = ClientHelper.getClientColor();
        }
        return color;
    }
    public static Color getHealthColor() {
        Color color = Color.white;
        Color onecolor = new Color(EntityESP.healColor.getColorValue());
        double time = 10;
        String mode = EntityESP.healcolorMode.getOptions();
        float yDist = (float) 4;
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().neverlose900_18.getFontHeight() + 5;
        }
        if (mode.equalsIgnoreCase("Rainbow")) {
            color = ColorUtils.rainbow((int) (1 * 200 * 0.1f), 0.5f, 1.0f);
        } else if (mode.equalsIgnoreCase("Astolfo")) {
            color = ColorUtils.astolfoColors(5000.0F, 1);
        } else if (mode.equalsIgnoreCase("Custom")) {
            color = new Color(onecolor.getRGB());
        } else if (mode.equalsIgnoreCase("Client")) {
            color = ClientHelper.getClientColor();
        }
        return color;
    }
    public static Color getTargetHudColor() {
        Color color = Color.white;
        Color onecolor = new Color(TargetHUD.targetHudColor.getColorValue());
        String mode = TargetHUD.thudColorMode.getOptions();
        float yDist = (float) 4;
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().neverlose900_18.getFontHeight() + 5;
        }
        if (mode.equalsIgnoreCase("Rainbow")) {
            color = ColorUtils.rainbow((int) (1 * 200 * 0.1f), 0.5f, 1.0f);
        } else if (mode.equalsIgnoreCase("Astolfo")) {
            color = ColorUtils.astolfoColors(5000.0F, 1);
        } else if (mode.equalsIgnoreCase("Custom")) {
            color = new Color(onecolor.getRGB());
        } else if (mode.equalsIgnoreCase("Client")) {
            color = ClientHelper.getClientColor();
        }
        return color;
    }
    public static Color getClientColor(float yStep, float yStepFull, int speed) {
        Color color = Color.white;
        Color onecolor = new Color(FeatureList.oneColor.getColorValue());
        Color twoColor = new Color(FeatureList.twoColor.getColorValue());
        double time = 10;

        String mode =  FeatureList.colorList.getOptions();
        float yDist = (float) 4;
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().neverlose900_18.getFontHeight() + 5;
        }
        if (mode.equalsIgnoreCase("Rainbow")) {
            color = ColorUtils.rainbow((int) (yStep * time), 0.5f, 1.0f);
        } else if (mode.equalsIgnoreCase("Astolfo")) {
            color = ColorUtils.astolfo(yStep, yStepFull, 2F, speed);
        } else if (mode.equalsIgnoreCase("Custom")) {
            color = ColorUtils.TwoColoreffect(new Color(onecolor.getRGB()), new Color(twoColor.getRGB()), Math.abs(System.currentTimeMillis() / time) / 100.0 + 3.0F * (yStep * 2.55) / 60);
        } else if (mode.equalsIgnoreCase("Fade")) {
            color = ColorUtils.TwoColoreffect(new Color(onecolor.getRGB()), new Color(onecolor.darker().darker().getRGB()), Math.abs(System.currentTimeMillis() / time) / 100.0 + 3.0F * (yStep * 2.55) / 60);
        }
        return color;
    }

    public static Color getClientColor(float yStep, float astolfoastep, float yStepFull, int speed) {
        Color color = Color.white;
        Color onecolor = new Color(FeatureList.oneColor.getColorValue());
        Color twoColor = new Color(FeatureList.twoColor.getColorValue());
        double time = 11;
        String mode =  FeatureList.colorList.getOptions();
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().neverlose900_18.getFontHeight() + 5;
        }
        if (mode.equalsIgnoreCase("Rainbow")) {
            color = ColorUtils.rainbowCol(yStep, yStepFull, 0.5f, speed);
        } else if (mode.equalsIgnoreCase("Astolfo")) {
            color = ColorUtils.astolfo(astolfoastep, yStepFull, 2f, speed);
        } else if (mode.equalsIgnoreCase("Custom")) {
            color = ColorUtils.TwoColoreffect(new Color(onecolor.getRGB()), new Color(twoColor.getRGB()), Math.abs(System.currentTimeMillis() / time) / 100.0 + 3.0F * (yStep * 2.55) / 60);
        } else if (mode.equalsIgnoreCase("Fade")) {
            color = ColorUtils.TwoColoreffect(new Color(onecolor.getRGB()), new Color(onecolor.darker().darker().getRGB()), Math.abs(System.currentTimeMillis() / time) / 100.0 + 3.0F * (yStep * 2.55) / 60);
        }
        return color;
    }

//    public static MCFontRenderer getFontRender() {
//        Minecraft mc = Minecraft.getMinecraft();
//        MCFontRenderer font = mc.neverlose900_18;
//        String mode = FeatureList.fontList.getOptions();
//        switch (mode) {
//            case "Neverlose":
//                font = mc.neverlose900_16;
//                break;
//            case "Comfortaa":
//                font = mc.Comfortaa;
//                break;
//            case "Monserrat":
//                font = mc.Monserrat ;
//                break;
//            case "Poppins":
//                font = mc.poppins ;
//                break;
//        }
//        return font;
//    }
}