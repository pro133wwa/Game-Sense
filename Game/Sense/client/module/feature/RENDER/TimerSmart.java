package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.impl.DraggableTimer;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.render.EventRender2D;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.module.feature.MOVEMENT.Timer;
import Game.Sense.client.UI.UwU.PaletteHelper;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.Helper.Utility.math.MathematicHelper;
import Game.Sense.client.Helper.Utility.other.ChatUtils;
import Game.Sense.client.Helper.Utility.render.ClientHelper;
import Game.Sense.client.Helper.Utility.render.ColorUtils2;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import Game.Sense.client.Helper.Utility.render.RoundedUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static Game.Sense.client.module.feature.MOVEMENT.Timer.smart;

public class TimerSmart extends Module {
    public float animWidth;
    public float animatedCircleEnd;
    public int x = 160;
    public int y = 400;
    public static ListSetting SmartMode = new ListSetting("Smart Mode", "Type_1", () -> true, "Type_1", "Type_2", "Type_3");
    public TimerSmart(){
        super("TimerSmart", ModuleCategory.RENDER);
        addSettings(SmartMode);
    }


    @EventTarget
    public void onRender(EventRender2D event2D) {

        if (!smart.getBoolValue()) {
            ChatUtils.addChatMessage(ChatFormatting.RED + "Включите Smart в Timer");
        }

        if(SmartMode.currentMode.equals("Type_1")) {
            if (smart.getBoolValue()) {
                DraggableTimer dt = (DraggableTimer) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableTimer.class);

                dt.setWidth(150);
                dt.setHeight(25);
                Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
                Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());
                Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0, onecolor, twocolor);
                Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90, onecolor, twocolor);
                Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor, twocolor);
                Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor, twocolor);/* 61 */

                    RoundedUtil.drawGradientRound((dt.getX() - 50), dt.getY(), (100.0F - Timer.ticks * 2.0F), 10.0f, 4, gradientColor1.brighter(), gradientColor2.brighter(), gradientColor3.brighter(), gradientColor4.brighter());
                    mc.sfui18.drawCenteredString("" + MathematicHelper.round(100.0F - Timer.ticks * 2.0F, 1) + "%", dt.getX(), (dt.getY() + 2), -1);
                    //RenderUtils.drawRect2(dt.getX()-26, (dt.getY() + 14),52,11,Color.BLACK.getRGB());
                    mc.sfui18.drawCenteredString("Smart Timer", dt.getX(), (dt.getY() + 16), -1);
                float y = dt.getY();
                float x = dt.getX();
                if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                    if (Button.hitBox.getBoolValue()){
                        RoundedUtil.drawRound(x, y, dt.getWidth(), dt.getHeight(), 2, new Color(35, 35, 35, 150));
                    }
                    if (Button.kur.getBoolValue()){
                        RenderUtils.drawImage(new ResourceLocation("GameSense/Kur.png"), x, y, 50, 50, Color.WHITE);
                    }

                }
                }
            }
        if(SmartMode.currentMode.equals("Type_2")){
            if (smart.getBoolValue()) {
                DraggableTimer dd = (DraggableTimer) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableTimer.class);
                dd.setWidth((int) 153.0f);
                dd.setHeight((int) 42.0f);
                float end;
                RenderUtils.drawBlurredShadow(dd.getX(), dd.getY(), 42.0f, 42.0f, 5, new Color(7, 7, 7, 203));
                this.animWidth = (float) RenderUtils.interpolate(100.0f - Timer.ticks * 2.0f, this.animWidth, 0.05f);
                int getX = (int) dd.getX();
                int getY = (int) dd.getY();
                RenderUtils.drawBlurredShadow(getX, getY, 40.0f, 40.0f, 3, new Color(25, 25, 25, 180));
                this.mc.rubik_18.drawCenteredString("Timer", (float) (getX + 21), (float) (getY + 14 - 10), -1);
                this.drawCircle(getX + 16 + 5, (double) getY + 23.5, 11.5, -5.0f, 360.0f, Color.DARK_GRAY.darker().getRGB(), 5.5f);
                float coef = this.animWidth / 100.0f;
                this.animatedCircleEnd = end = coef * 360.0f;
                this.drawCircle(getX + 16 + 5, (double) getY + 23.5, 11.5, -5.0f, this.animatedCircleEnd, PaletteHelper.astolfoColors(1, 1), 5.5f);
                this.mc.rubik_15.drawCenteredString(String.valueOf(String.valueOf(Math.round(this.animWidth))) + "%", (float) (getX + 21), (float) (getY + 22), -1);
                float y = dd.getY();
                float x = dd.getX();
                if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                    if (Button.hitBox.getBoolValue()){
                        RoundedUtil.drawRound(x, y, dd.getWidth(), dd.getHeight(), 2, new Color(35, 35, 35, 150));
                    }
                    if (Button.kur.getBoolValue()){
                        RenderUtils.drawImage(new ResourceLocation("GameSense/Kur.png"), x, y, 50, 50, Color.WHITE);
                    }

                }
            }
        }

        if(SmartMode.currentMode.equals("Type_3")){
            if (smart.getBoolValue()) {
                DraggableTimer dt = (DraggableTimer) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableTimer.class);
                dt.setWidth(150);
                dt.setHeight(25);
                RenderUtils.drawBlurredShadow((float) (dt.getX() - 55), (float) (dt.getY()), 60.0f, 12f, 20, new Color(7, 7, 7, 255));
                RenderUtils.drawBlurredShadow((float) (dt.getX() - 49.8), (float) (dt.getY()), 50.0f, 11.9f, 0, new Color(7, 7, 7, 255));
                RenderUtils.drawBlurredShadow(dt.getX() - 37, dt.getY() - -11, 26.0f, 10.0f, 3, new Color(7, 7, 7, 255));
                mc.rubik_18.drawCenteredString("timer", dt.getX() - 24, dt.getY() - -14, ClientHelper.getClientColor().getRGB());

                RenderUtils.drawGradientRected(dt.getX() - 50, dt.getY(), 50.0f - (float) Timer.ticks * 1.0f, 12.0, ClientHelper.getClientColor().getRGB(), ClientHelper.getClientColor().brighter().getRGB());
                Timer.mc.sfui18.drawCenteredString(MathematicHelper.round(100 - (float) Timer.ticks * 2.0f, 1) + "%", dt.getX() - 24, dt.getY() - -3, -1);
                float y = dt.getY();
                float x = dt.getX();
                if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                    if (Button.hitBox.getBoolValue()){
                        RoundedUtil.drawRound(x, y, dt.getWidth(), dt.getHeight(), 2, new Color(35, 35, 35, 150));
                    }
                    if (Button.kur.getBoolValue()){
                        RenderUtils.drawImage(new ResourceLocation("GameSense/Kur.png"), x, y, 50, 50, Color.WHITE);
                    }

                }

            }
        }



    }

    private void drawCircle(double x, double y, double radius, float startAngle, float endAngle, int color, float lineWidth) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glEnable(2848);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);

        for(int i = (int)((double)startAngle / 360.0D * 100.0D); i <= (int)((double)endAngle / 360.0D * 100.0D); ++i) {
            double angle = 6.283185307179586D * (double)i / 100.0D + Math.toRadians(180.0D);
            if (color == 1337) {
                RenderUtils.color(PaletteHelper.astolfoColors(i * 5, 1));
            } else {
                RenderUtils.color(color);
            }

            GL11.glVertex2d(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius);
        }

        GL11.glEnd();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GL11.glDisable(2848);
        GlStateManager.popMatrix();
        GlStateManager.resetColor();
    }

}
