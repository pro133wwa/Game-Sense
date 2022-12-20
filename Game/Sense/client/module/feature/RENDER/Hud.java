package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.UI.UwU.UID;
import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.impl.*;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.render.EventRender2D;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.ResourceLocation;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.Helper.Utility.math.MathematicHelper;
import Game.Sense.client.Helper.Utility.math.TPSUtils;
import Game.Sense.client.Helper.Utility.render.*;
import Game.Sense.client.Helper.Utility.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import optifine.CustomColors;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static Game.Sense.client.module.feature.MOVEMENT.Jesus.mode;

public class Hud extends Module {
    public static BooleanSetting waterMark = new BooleanSetting("WaterMark", true, () -> true);
    public static ListSetting waterMarkMode = new ListSetting("WaterMark Mode", "Nursultan", () -> waterMark.getBoolValue(), "Celestial", "Nursultan","Simple","Akrien");
    public static BooleanSetting coords = new BooleanSetting("Coordinates", false, () -> true);
    public static BooleanSetting sessionInfo = new BooleanSetting("Info of Session", true, () -> true);
    public static BooleanSetting Radar = new BooleanSetting("Radar", false, () -> true);
    public static BooleanSetting user_info = new BooleanSetting("User Info", false, () -> true);
    public static BooleanSetting armor = new BooleanSetting("Armor Status", true, () -> true);

    public static BooleanSetting potions = new BooleanSetting("Potion Info", true, () -> true);
    int x2,y2;
    int height;
    public static float progress;
    private long lastMS;
    public static boolean a = false;
    public static ListSetting mode = new ListSetting("Mode", "Rockstar Styled", () -> true, "Rockstar", "Rockstar Styled", "Rockstar New");
    public static float indicatorTimer;
    private double cooldownBarWidth;
    private double hurttimeBarWidth;
    public float scale = 2;

    public Hud() {
        super("Hud", "Показывает информацию на экране", ModuleCategory.RENDER);
        addSettings(sessionInfo, potions, armor);
    }

    @EventTarget
    public void onRender(EventRender2D eventRender2D) {
        if (waterMark.getBoolValue()) {
            if (waterMarkMode.currentMode.equals("Celestial")) {
                GLUtils.INSTANCE.rescale(scale);
                RoundedUtil.drawGradientCornerLR(36, 12, 80, 28, 4, new Color(ClientHelper.getClientColor().getRGB()), Color.white);
                RoundedUtil.drawGradientRound(5, 6, 40, 40, 20, new Color(ClientHelper.getClientColor().getRGB()), new Color(ClientHelper.getClientColor().getRGB()), Color.white, new Color(ClientHelper.getClientColor().getRGB()));
                mc.neverlose900_18.drawStringWithShadow(ChatFormatting.WHITE + "GameSense Recode", 48, 18, new Color(255, 255, 255).getRGB());
                mc.neverlose900_18.drawStringWithShadow(ChatFormatting.WHITE + "Free" + ChatFormatting.GRAY + " Version", 48, 28, new Color(255, 255, 255).getRGB());
                RenderUtils.drawImage(new ResourceLocation("rich/logo.png"), 9, 10, 30, 30, Color.WHITE);
                GLUtils.INSTANCE.rescaleMC();
            } else if (waterMarkMode.currentMode.equals("Nursultantest")) {
                Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
                Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());

                Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0, onecolor, twocolor);
                Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90, onecolor, twocolor);
                Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor, twocolor);
                Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor, twocolor);
                RoundedUtil.drawGradientRound(5, 6, 115, 16, 8, gradientColor1, gradientColor2, gradientColor3, gradientColor4);
                RenderUtils.drawBlurredShadow(5, 6, 123, 18, 50, gradientColor1);
                mc.nurik.drawString(ChatFormatting.WHITE + GameSense.instance.name + " | delay: 20 | 0.1", 10, 11, new Color(255, 255, 255).getRGB());
            } else if (waterMarkMode.currentMode.equals("Simple")) {
                DraggableWaterMark dwm = (DraggableWaterMark) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableWaterMark.class);
                dwm.setWidth(150);
                dwm.setHeight(15);
                GLUtils.INSTANCE.rescale(this.scale);
                String text = "GameSense | " + mc.player.getName() + " | " + mc.getDebugFPS() + " fps" + " | " + mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime() + "ms";
                RenderUtils.drawBlurredShadow(5, 5, 135, 15, 30, new Color(ColorUtils.rainbow((int) (1 * 200 * 0.1f), 0.5f, 1.0f).getRGB()));
                RoundedUtil.drawRound(5, 6, mc.neverlose900_16.getStringWidth(text) + 12, 12, 4, new Color(ColorUtils.rainbow((int) (1 * 200 * 0.1f), 0.5f, 1.0f).getRGB()));
                mc.rubik_18.drawString(ChatFormatting.WHITE + "GameSense" + ChatFormatting.WHITE + " | " + "0.5" + " | " + mc.player.getName() + " | " + ChatFormatting.WHITE + "FPS: " + ChatFormatting.WHITE + mc.getDebugFPS(), 7, 9.3f, new Color(255, 255, 255).getRGB());
                GLUtils.INSTANCE.rescaleMC();
            }
            if (Hud.waterMarkMode.currentMode.equals("Nursultan")) {

                DraggableWaterMark dwm = (DraggableWaterMark) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableWaterMark.class);

                dwm.setWidth(134);

                dwm.setHeight(15);
                int x = (int) dwm.getX();
                int y = (int) dwm.getY();

                GLUtils.INSTANCE.rescale(this.scale);

                GLUtils.INSTANCE.rescale(this.scale);
                Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
                Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());
                Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0, onecolor, twocolor);
                Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90, onecolor, twocolor);
                Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor, twocolor);
                Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor, twocolor);


                final String server = Hud.mc.isSingleplayer() ? "local" : ((Hud.mc.getCurrentServerData() != null) ? Hud.mc.getCurrentServerData().serverIP.toLowerCase() : "null");

                final String name = GameSense.instance.name + ChatFormatting.WHITE + " | " + ChatFormatting.RESET + "delay - 0ms" + ChatFormatting.WHITE + " | " + ChatFormatting.WHITE + GameSense.instance.version;

                final String time = GameSense.instance.name + ChatFormatting.WHITE + " | " + ChatFormatting.RESET + "delay - 0ms" + ChatFormatting.WHITE + " | " + ChatFormatting.WHITE + GameSense.instance.version;

                float headerHeight = 0;

                RoundedUtil.drawGradientRound(dwm.getX() + 7, dwm.getY() + 1, dwm.getWidth(), dwm.getHeight(), 6.0f, ColorUtils2.applyOpacity(gradientColor4, 10.85f).brighter(), gradientColor1.brighter(), gradientColor3.brighter(), gradientColor2.brighter());

                RoundedUtil.drawGradientRound(dwm.getX() + 8, dwm.getY() + 2, dwm.getWidth() - 2, dwm.getHeight() - 2, 6.0f, ColorUtils2.applyOpacity(gradientColor4, 10.85f).darker(), gradientColor1.darker(), gradientColor3.darker(), gradientColor2.darker());

                RenderUtils.drawBlurredShadow(dwm.getX() + 4.9f, dwm.getY() + 1.9f, dwm.getWidth(), dwm.getHeight(), 12, new Color(152, 152, 152, 111));

                Minecraft.getMinecraft().rubik_17.drawStringWithShadow("", dwm.getX() + 13, dwm.getY() + 6.0f, -1);

                Minecraft.getMinecraft().rubik_17.drawString(time, (float) (dwm.getX() + 11 + Minecraft.getMinecraft().rubik_17.getStringWidth("")), (float) (dwm.getY() + 6.0), -1);
                if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                    if (Button.hitBox.getBoolValue()){
                        RoundedUtil.drawRound(x, y, dwm.getWidth(), dwm.getHeight(), 2, new Color(35, 35, 35, 150));
                    }
                    if (Button.kur.getBoolValue()){
                        RenderUtils.drawImage(new ResourceLocation("GameSense/Kur.png"), x, y, 50, 50, Color.WHITE);
                    }

                }
                GLUtils.INSTANCE.rescaleMC();

            } else if (waterMarkMode.currentMode.equals("Akrien")) {
                DraggableWaterMark dwm = (DraggableWaterMark) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableWaterMark.class);
                int xx = (int) dwm.getX();
                int yy = (int) dwm.getY();
                RenderUtils.drawRect2(8, 8, 3, 10, ClientHelper.getClientColor().getRGB());
                RenderUtils.drawBlurredShadow(8, 8, 3, 10, 6, ClientHelper.getClientColor());
                RenderUtils.drawRect2(10, 8, 40, 10, new Color(0x28282F).getRGB());
                mc.neverlose900_16.drawString("GameSense b1", 12, 11, Color.WHITE.getRGB());
            } else if (waterMarkMode.currentMode.equals("AkrienBeta")) {
                RoundedUtil.drawRound(8, 8, 30, 30, 20, Color.WHITE);
                RenderUtils.drawImage(new ResourceLocation("rich/logo.png"), 9, 10, 25, 25, new Color(ClientHelper.getClientColor().getRGB()));
                RoundedUtil.drawRound(28, 8, 60, 20, 4, Color.white);


            }
        }
        if (sessionInfo.getBoolValue()) {
            DraggableSessionInfo dsi = (DraggableSessionInfo) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableSessionInfo.class);
            dsi.setWidth(145);
            dsi.setHeight(135);
            int x = (int) dsi.getX();
            int y = (int) dsi.getY();
            GLUtils.INSTANCE.rescale(scale);
            String server = mc.isSingleplayer() ? "Одиночный режим" : mc.getCurrentServerData() != null ? mc.getCurrentServerData().serverIP.toLowerCase() : "null";
            String name = GameSense.instance.featureManager.getFeature(NameProtect.class).isEnabled() && NameProtect.myName.getBoolValue() ? "" + "Protected" : " " + mc.player.getName();
            String time;
            String time2 = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
            String xCoord = "" + Math.round(mc.player.posX);
            String yCoord = "" + Math.round(mc.player.posY);
            String zCoord = "" + Math.round(mc.player.posZ);
            String ping = String.valueOf(Hud.mc.getConnection().getPlayerInfo(Hud.mc.player.getUniqueID()).getResponseTime());
            if (Minecraft.getMinecraft().isSingleplayer()) {
                long durationInMillis = System.currentTimeMillis() - GameSense.playTimeStart;
                long second = (durationInMillis / 1000) % 60;
                long minute = (durationInMillis / (1000 * 60)) % 60;
                long hour = (durationInMillis / (1000 * 60 * 60)) % 24;
                time = String.format("%02dh %02dm %02ds", hour, minute, second);
            } else {
                long durationInMillis = System.currentTimeMillis() - GameSense.playTimeStart;
                long second = (durationInMillis / 1000) % 60;
                long minute = (durationInMillis / (1000 * 60)) % 60;
                long hour = (durationInMillis / (1000 * 60 * 60)) % 24;
                time = String.format("%02dh %02dm %02ds", hour, minute, second);
            }
            //RoundedUtil.drawRoundOutline(dsi.getX(),dsi.getY(), 130, 56, 10,3,new Color(0,0,0, 0), new Color(255, 255, 255));
            //RoundedUtil.drawGradientRound(dsi.getX(), dsi.getY(), 130,56,10, ClientHelper.getClientColor(getY(), getY(), 20), new Color(208, 208, 208), new Color(190, 190, 190), ClientHelper.getClientColor(getY(), getY(), 20));
            Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
            Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());
            Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0, onecolor, twocolor);
            Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90, onecolor, twocolor);
            Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor, twocolor);
            Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor, twocolor);


            RenderUtils.drawBlurredShadow(dsi.getX(), dsi.getY(), 145, 135, 25, new Color(255, 255, 255));
            //RoundedUtil.drawRoundOutline(dsi.getX(),dsi.getY(), 140, 105, 10,3,new Color(0,0,0, 0), new Color(255, 255, 255));
            //RoundedUtil.drawGradientRound(dsi.getX(), dsi.getY(), 130,56,10, ClientHelper.getClientColor(getY(), getY(), 20), new Color(208, 208, 208), new Color(190, 190, 190), ClientHelper.getClientColor(getY(), getY(), 20));
            RoundedUtil.drawGradientRound(dsi.getX(), dsi.getY(), 145, 135, 4, ColorUtils2.applyOpacity(gradientColor4, .85f), gradientColor1, gradientColor3, gradientColor2);


            mc.rubik_18.drawString(ChatFormatting.WHITE + GameSense.instance.name + ": ", dsi.getX() + 5, dsi.getY() + 7.3f, -1);
            RenderUtils.drawRect2(dsi.getX(), dsi.getY() + 20, dsi.getWidth(), 0.4f, Color.white.getRGB());
            mc.rubik_18.drawString("Сервер: " + ChatFormatting.WHITE + server, dsi.getX() + 5, dsi.getY() + 25, -1);
            mc.rubik_18.drawString("Ник:" + ChatFormatting.WHITE + name, dsi.getX() + 5, dsi.getY() + 35f, -1);
            mc.rubik_18.drawString("Пинг: " + ChatFormatting.WHITE + ping, dsi.getX() + 5, dsi.getY() + 45, -1);
            mc.rubik_18.drawString("Корды: " + ChatFormatting.WHITE + xCoord + "x " + yCoord + "y " + zCoord + "z ", dsi.getX() + 5, dsi.getY() + 55, -1);
            mc.rubik_18.drawString("ФПС: " + ChatFormatting.WHITE + Minecraft.getDebugFPS(), dsi.getX() + 5, dsi.getY() + 65, -1);
            mc.rubik_18.drawString("Време: " + ChatFormatting.WHITE + time2, dsi.getX() + 5, dsi.getY() + 75, -1);
            mc.rubik_18.drawString("Версия: " + ChatFormatting.WHITE + GameSense.instance.version, dsi.getX() + 5, dsi.getY() + 85, -1);
            mc.rubik_18.drawString("Релиз: " + ChatFormatting.WHITE + GameSense.instance.type, dsi.getX() + 5, dsi.getY() + 95, -1);
            mc.rubik_18.drawString("Coder: " + ChatFormatting.WHITE + GameSense.instance.coder, dsi.getX() + 5, dsi.getY() + 105, -1);
            mc.rubik_18.drawString("UID: " + ChatFormatting.WHITE + UID.getLine(), dsi.getX() + 5, dsi.getY() + 115, -1);
            mc.rubik_18.drawString("User: " + ChatFormatting.WHITE + UID.getUser(), dsi.getX() + 5, dsi.getY() + 125, -1);
            if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                if (Button.hitBox.getBoolValue()){
                    RoundedUtil.drawRound(x, y, dsi.getWidth(), dsi.getHeight(), 2, new Color(35, 35, 35, 150));
                }
                if (Button.kur.getBoolValue()){
                    RenderUtils.drawImage(new ResourceLocation("GameSense/Kur.png"), x, y, 50, 50, Color.WHITE);
                }

            }
            GLUtils.INSTANCE.rescaleMC();

        }

        if (Radar.getBoolValue()) {
            DraggableRadar dci = (DraggableRadar) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableRadar.class);
            dci.setWidth(200);
            dci.setHeight(200);
            int x = (int) dci.getX();
            int y = (int) dci.getY();
            GLUtils.INSTANCE.rescale(scale);
            Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
            Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());
            Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0, onecolor, twocolor);
            Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90, onecolor, twocolor);
            Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor, twocolor);
            Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor, twocolor);


            RoundedUtil.drawGradientRound(x, y, 60, 60, 6, ColorUtils2.applyOpacity(gradientColor4, .85f), gradientColor1, gradientColor3, gradientColor2);

            GLUtils.INSTANCE.rescaleMC();
        }

        if (user_info.getBoolValue()) {
            DraggableUserInfo dci = (DraggableUserInfo) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableUserInfo.class);
            dci.setWidth(168);
            dci.setHeight(18);
            GLUtils.INSTANCE.rescale(scale);
            String buildStr = ChatFormatting.GRAY + "Version - " + ChatFormatting.WHITE + GameSense.instance.version + ChatFormatting.WHITE + " |" + ChatFormatting.GRAY + " Username - " + ChatFormatting.WHITE + "Foz1ch";
            String tpsStr = mc.isSingleplayer() ? ChatFormatting.WHITE + "TPS - " + ChatFormatting.WHITE + "local" : ChatFormatting.GRAY + "ТПС - " + ChatFormatting.WHITE + MathematicHelper.round(TPSUtils.getTickRate(), 1);
            mc.neverlose900_18.drawStringWithShadow(buildStr, getX2(), getY2(), -1);
            mc.neverlose900_18.drawStringWithShadow(tpsStr, getX2() + mc.neverlose900_18.getStringWidth(buildStr) - 1 - mc.neverlose900_18.getStringWidth(tpsStr), getY2() - 10, -1);
            GLUtils.INSTANCE.rescaleMC();
        }
        if (coords.getBoolValue()) {
            DraggableCoordsInfo dci = (DraggableCoordsInfo) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableCoordsInfo.class);
            dci.setWidth(90);
            dci.setHeight(25);
            GLUtils.INSTANCE.rescale(scale);

            String xCoord = "" + Math.round(mc.player.posX);
            String yCoord = "" + Math.round(mc.player.posY);
            String zCoord = "" + Math.round(mc.player.posZ);
            String fps = "" + Math.round(Minecraft.getDebugFPS());
            mc.neverlose900_15.drawStringWithShadow("FPS:", getX(), getY() + 0.5f, ClientHelper.getClientColor().getRGB());
            mc.neverlose900_15.drawStringWithShadow(fps, getX() + 19, getY() + 0.5f, -1);
            mc.neverlose900_15.drawStringWithShadow("Coords: ", getX(), getY() + 11, ClientHelper.getClientColor().getRGB());
            mc.neverlose900_15.drawStringWithShadow(xCoord, getX() + 34, getY() + 11, -1);
            mc.neverlose900_15.drawStringWithShadow(" ,", getX() + 50 + mc.neverlose900_15.getStringWidth(xCoord) - 17, getY() + 11, ClientHelper.getClientColor().getRGB());
            mc.neverlose900_15.drawStringWithShadow(yCoord, getX() + 55 + mc.neverlose900_15.getStringWidth(xCoord) - 17, getY() + 11, -1);
            mc.neverlose900_15.drawStringWithShadow(",", getX() + 78 + mc.neverlose900_15.getStringWidth(xCoord) - 23 + mc.neverlose900_15.getStringWidth(yCoord) - 17, getY() + 11, ClientHelper.getClientColor().getRGB());
            mc.neverlose900_15.drawStringWithShadow(zCoord, getX() + 82 + mc.neverlose900_15.getStringWidth(xCoord) - 23 + mc.neverlose900_15.getStringWidth(yCoord) - 17, getY() + 11, -1);
            GLUtils.INSTANCE.rescaleMC();


        }

        if (potions.getBoolValue()) {
            DraggablePotionHUD dph = (DraggablePotionHUD) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggablePotionHUD.class);
            dph.setWidth(100);
            dph.setHeight(50);
            int x2 = dph.getX();
            int y2 = dph.getY();
            int x = dph.getX();
            int y = dph.getY();
            if (this.mc.gameSettings.showDebugInfo) {
                return;
            }
            GlStateManager.pushMatrix();
            GlStateManager.enableTexture2D();
            GlStateManager.pushMatrix();
            GlStateManager.enableAlpha();
            List<PotionEffect> potions = new ArrayList<>(mc.player.getActivePotionEffects());
            potions.sort(Comparator.comparingDouble(effect -> mc.fontRendererObj.getStringWidth((Objects.requireNonNull(Potion.getPotionById(CustomColors.getPotionId(effect.getEffectName()))).getName()))));
            if (ClickGUI.mode.currentMode.equalsIgnoreCase("Rockstar New")) {
                RoundedUtil.drawHorizontalGradientOutlinedRoundedRectWithGlow(x2, y2, 100, 15 + height, 5, 1, 15, ClientHelper.getClientColor(), ClientHelper.getClientColor().darker());
                mc.rubik_18.drawCenteredString("Зелья", x2 + 50, y2 + 3, -1);
            } else if (ClickGUI.mode.currentMode.equalsIgnoreCase("Rockstar")) {
                RoundedUtil.drawGradientRound(x2, y2, 100, 15 + height, 5, ClientHelper.getClientColor(), ClientHelper.getClientColor(), ClientHelper.getClientColor().darker(), ClientHelper.getClientColor().darker());
                mc.rubik_18.drawCenteredString("Зелья", x2 + 50, y2 + 3, -1);
            } else if (ClickGUI.mode.getCurrentMode().equalsIgnoreCase("Rockstar Styled")) {
                Color l1 = ClientHelper.getClientColor().brighter();
                Color l2 = ClientHelper.getClientColor();
                RenderUtils.drawBlurredShadow(x2, y2, 100, 15 + height - 0.5f, 25, new Color(50, 50, 50));
                RoundedUtil.drawRound(x2, y2, 100, 15 + height - 0.5f, 2, new Color(50, 50, 50));
                Gui.drawRect(x2 + 1, y2 - 12 + 13 + 14, x2 + 2.5f + 15, y2 - 12 + 12 + 5 + 14, new Color(50, 50, 50).getRGB());
                mc.rubik_18.drawString("Зелья", x2 + 5, y2 + 4, -1);
                RenderUtils.drawRect2(x2 + 3, y2 + 13, 94, 0.5f, Color.white.getRGB());

            }

            if (potions.isEmpty()) {
                if (mode.getCurrentMode().equalsIgnoreCase("Rockstar Styled")) {
                    mc.rubik_18.drawString("", x2 + 4, y2 + height + 6.5f, Color.GRAY.getRGB());
                } else {
                    mc.rubik_18.drawString("", x2 + 5, y2 + height + 6, Color.GRAY.getRGB());
                }
                height = 12;
                if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                    if (Button.hitBox.getBoolValue()){
                        RoundedUtil.drawRound(x2, y2, dph.getWidth(), dph.getHeight(), 2, new Color(35, 35, 35, 150));
                    }
                    if (Button.kur.getBoolValue()){
                        RenderUtils.drawImage(new ResourceLocation("GameSense/Kur.png"), x, y, 50, 50, Color.WHITE);
                    }

                }
            } else {

                for (PotionEffect potion : potions) {
                    Potion effect = Potion.getPotionById(CustomColors.getPotionId(potion.getEffectName()));
                    String level = I18n.format(effect.getName());
                    if (potion.getAmplifier() == 1) {
                        level = level + " " + I18n.format("enchantment.level.2");
                    } else if (potion.getAmplifier() == 2) {
                        level = level + " " + I18n.format("enchantment.level.3");
                    } else if (potion.getAmplifier() == 3) {
                        level = level + " " + I18n.format("enchantment.level.4");
                    }
                    int getPotionColor = -1;
                    if ((potion.getDuration() < 200)) {
                        getPotionColor = new Color(255, 103, 32).getRGB();
                    } else if (potion.getDuration() < 400) {
                        getPotionColor = new Color(231, 143, 32).getRGB();
                    } else if (potion.getDuration() > 400) {
                        getPotionColor = Color.GRAY.getRGB();
                    }

                    String durationString = Potion.getDurationString(potion);


                    mc.rubik_18.drawString(level, x2 + 5, y2 + ((potions.indexOf(potion) + 1) * 12) + 6, Color.GRAY.getRGB());

                    mc.rubik_18.drawString(durationString, x2 + 95 - mc.rubik_18.getStringWidth(durationString), y2 + ((potions.indexOf(potion) + 1) * 12) + 6, getPotionColor);


                    height = potions.size() * 12;
                    if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                        if (Button.hitBox.getBoolValue()){
                            RoundedUtil.drawRound(x2, y2, dph.getWidth(), dph.getHeight(), 2, new Color(35, 35, 35, 150));
                        }
                        if (Button.kur.getBoolValue()){
                            RenderUtils.drawImage(new ResourceLocation("GameSense/Kur.png"), x, y, 50, 50, Color.WHITE);
                        }

                    }
                    if (mc.world == null) {
                        potions.remove(potions.indexOf(potion));
                    }

                }
            }


            GlStateManager.popMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
            GlStateManager.popMatrix();
        }

    }

    private double calculateBPS() {
        double bps = (Math.hypot(mc.player.posX - mc.player.prevPosX, mc.player.posZ - mc.player.prevPosZ) * mc.timer.timerSpeed) * 20;
        return Math.round(bps * 100.0) / 100.0;
    }

    public float getX2() {
        DraggableUserInfo dci = (DraggableUserInfo) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableUserInfo.class);

        return dci.getX();
    }

    public float getY2() {
        DraggableUserInfo dci = (DraggableUserInfo) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableUserInfo.class);

        return dci.getY() + 10;
    }

    public float getX() {
        DraggableCoordsInfo dci = (DraggableCoordsInfo) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableCoordsInfo.class);

        return dci.getX();
    }

    public float getY() {
        DraggableCoordsInfo dci = (DraggableCoordsInfo) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableCoordsInfo.class);

        return dci.getY() + 15;
    }



    @Override
    public void onEnable() {
        super.onEnable();
    }
}
