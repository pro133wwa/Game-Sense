package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.impl.DraggableTargetHUD;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.render.EventRender2D;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.module.feature.COMBAT.KillAura;
import Game.Sense.client.module.feature.OTHER.NameProtect;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ColorSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.Helper.Utility.math.AnimationHelper;
import Game.Sense.client.Helper.Utility.math.MathematicHelper;
import Game.Sense.client.Helper.Utility.math.TimerHelper;
import Game.Sense.client.Helper.Utility.other.Particles;
import Game.Sense.client.Helper.Utility.render.*;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class TargetHUD extends Module {
    private double scale = 0;
    private static EntityLivingBase curTarget = null;

    public static TimerHelper thudTimer = new TimerHelper();
    private float healthBarWidth;
    private ArrayList<Particles> particles = new ArrayList<>();
    public ListSetting targetHudMode = new ListSetting("TargetHUD Mode", "Style", () -> true, "Style", "Nurik","Celka");
    public static ListSetting thudColorMode = new ListSetting("TargetHUD Color", "Astolfo", () -> true, "Astolfo", "Rainbow", "Client", "Custom");
    public BooleanSetting particles2 = new BooleanSetting("Particles", thudColorMode.currentMode.equals("Custom"), () -> targetHudMode.currentMode.equals("Style") && thudColorMode.currentMode.equals("Custom"));
    public static ColorSetting targetHudColor = new ColorSetting("THUD Color", Color.PINK.getRGB(), () -> thudColorMode.currentMode.equals("Custom"));
    public BooleanSetting shadowThud = new BooleanSetting("Shadow", true, () -> true);
    public BooleanSetting blurThud = new BooleanSetting("Blur", true, () -> true);

    public TargetHUD() {
        super("TargetHUD", ModuleCategory.RENDER);
        addSettings(targetHudMode, thudColorMode, particles2, targetHudColor, shadowThud, blurThud);
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        if (targetHudMode.currentMode.equals("Style")) {
            DraggableTargetHUD dth = (DraggableTargetHUD) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableTargetHUD.class);
            float x = dth.getX();
            float y = dth.getY();
            dth.setWidth(130);
            dth.setHeight(42 - 5);
            if (KillAura.target != null) {
                curTarget = KillAura.target;
                scale = AnimationHelper.animation((float) scale, (float) 1, (float) (6 * GameSense.deltaTime()));
            } else {
                scale = AnimationHelper.animation((float) scale, (float) 0, (float) (6 * GameSense.deltaTime()));
            }
            EntityLivingBase target = KillAura.target;
            if (curTarget != null) {
                if (curTarget instanceof EntityPlayer) {
                    try {

                        GlStateManager.pushMatrix();
                        GlStateManager.resetColor();
                        GL11.glTranslated(x + 36, y + 26, 0);
                        GL11.glScaled(scale, scale, 0);
                        GL11.glTranslated(-(x + 36), -(y + 26), 0);
                        RenderUtils.drawSmoothRect(x, y, x + dth.getWidth(), y + dth.getHeight(), new Color(0, 0, 0, 100).getRGB());
                        mc.rubik_18.drawString(GameSense.instance.featureManager.getFeature(NameProtect.class).isEnabled() && NameProtect.otherName.getBoolValue() ? "Protected" : curTarget.getName(), x + 35, y + 5, -1);

                        mc.rubik_17.drawString((int) curTarget.getHealth() + " HP - " + (int) mc.player.getDistanceToEntity(curTarget) + "m", x + 35, y + 5 + 10, -1);

                        double healthWid = (curTarget.getHealth() / curTarget.getMaxHealth() * 90);
                        healthWid = MathHelper.clamp(healthWid, 0.0D, 90.0D);
                        healthBarWidth = AnimationHelper.animation((float) healthBarWidth, (float) healthWid, (float) (10 * GameSense.deltaTime()));


                        RenderUtils.drawRect2(x + 36, y + 25, 90, 5, new Colors(Color.decode("#191f13")).setAlpha(180).getColor().getRGB());

                        RenderUtils.drawGradientRected(x + 36, y + 25, (float) healthBarWidth, 5, ClientHelper.getTargetHudColor().darker().getRGB(), ClientHelper.getTargetHudColor().brighter().brighter().getRGB());
                        RenderUtils.drawBlurredShadow(x + 36, y + 25, (float) healthBarWidth + 2, 5, 8, ClientHelper.getTargetHudColor().darker());

                        if (particles2.getBoolValue() && thudColorMode.currentMode.equals("Custom")) {
                            for (final Particles p : particles) {
                                if (p.opacity > 4) p.render2D();
                            }

                            if (thudTimer.hasReached(15)) {
                                for (final Particles p : particles) {
                                    p.updatePosition();

                                    if (p.opacity < 1) particles.remove(p);
                                }
                                thudTimer.reset();
                            }

                            if (curTarget.hurtTime == 8) {
                                for (int i = 0; i < 1; i++) {
                                    final Particles p = new Particles();
                                    p.init((x + 15), y + 15, ((Math.random() - 0.5) * 2) * 1.9, ((Math.random() - 0.5) * 2) * 1.4, (float) Math.random() * 1, ClientHelper.getTargetHudColor());
                                    particles.add(p);
                                }
                            }
                        }
                        for (NetworkPlayerInfo targetHead : mc.player.connection.getPlayerInfoMap()) {
                            try {
                                if (mc.world.getPlayerEntityByUUID(targetHead.getGameProfile().getId()) == curTarget) {
                                    mc.getTextureManager().bindTexture(targetHead.getLocationSkin());
                                    final int scaleOffset = (int) (curTarget.hurtTime * 0.55f);

                                    float hurtPercent = getHurtPercent(curTarget);
                                    GL11.glPushMatrix();
                                    GL11.glColor4f(1, 1 - hurtPercent, 1 - hurtPercent, 1);
                                    Gui.drawScaledCustomSizeModalRect((int) x + 3, y + 3, 8.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f);
                                    GL11.glPopMatrix();
                                    GlStateManager.bindTexture(0);
                                }
                            } catch (Exception exception) {
                            }
                        }
                    } catch (Exception exception) {
                    } finally {
                        GlStateManager.popMatrix();
                    }
                }
            }
        }
        else if (targetHudMode.currentMode.equals("NewGen")) {
            DraggableTargetHUD dth = (DraggableTargetHUD) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableTargetHUD.class);
            float x = dth.getX();
            float y = dth.getY();
            dth.setWidth(153);
            dth.setHeight(42);
            if (KillAura.target != null) {
                curTarget = KillAura.target;
                scale = AnimationHelper.animation((float) scale, (float) 1, (float) (6 * GameSense.deltaTime()));
            } else {
                scale = AnimationHelper.animation((float) scale, (float) 0, (float) (6 * GameSense.deltaTime()));
            }
            EntityLivingBase target = KillAura.target;
            if (curTarget != null) {
                if (curTarget instanceof EntityPlayer) {
                    try {

                        GlStateManager.pushMatrix();
                        GlStateManager.resetColor();
                        GL11.glTranslated(x + 50, y + 31, 0);
                        GL11.glScaled(scale, scale, 0);
                        GL11.glTranslated(-(x + 50), -(y + 31), 0);
                        if (blurThud.getBoolValue()) {
                            RenderUtils.drawBlur(7, () -> {
                                RenderUtils.drawSmoothRect(x, y, x + dth.getWidth(), y + dth.getHeight(),new Color(17, 17, 17, 200).getRGB());
                            });
                        }
                        if (shadowThud.getBoolValue()) {
                            RenderUtils.drawShadow(5, 1, () -> {
                                RenderUtils.drawSmoothRect(x, y, x + dth.getWidth(), y + dth.getHeight(),new Color(17, 17, 17, 200).getRGB());
                            });
                        }
                        RenderUtils.drawSmoothRect(x, y, x + dth.getWidth(), y + dth.getHeight(),new Color(17, 17, 17, 200).getRGB());


                        double healthWid = (curTarget.getHealth() / curTarget.getMaxHealth() * 110);
                        healthWid = MathHelper.clamp(healthWid, 0.0D, 110.0D);
                        healthBarWidth = AnimationHelper.animation((float) healthBarWidth, (float) healthWid, (float) (10 * GameSense.deltaTime()));
                        String health = "" + MathematicHelper.round(curTarget.getHealth(), 1);
                        String distance = "" + MathematicHelper.round(mc.player.getDistanceToEntity(curTarget), 1);

                        mc.rubik_15.drawString("Name: " + curTarget.getName(), x + 42, y + 6, -1);
                        mc.rubik_15.drawString("Distance: " + distance, x + 42, y + 15, -1);

                        mc.rubik_14.drawString(curTarget.getHealth() >= 3 ? health : "", x + 24 + healthBarWidth, y + 26.5F, new Color(200, 200, 200).getRGB());

                        RenderUtils.drawSmoothRect(x + 38, y + 33, (float) x + 38 + healthBarWidth, y + 33 + 5, ClientHelper.getTargetHudColor().darker().getRGB());
                        RenderUtils.drawBlurredShadow(x + 38, y + 33, (float) healthBarWidth, 5, 12, RenderUtils.injectAlpha(ClientHelper.getTargetHudColor(), 100));
                        mc.getRenderItem().renderItemOverlays(mc.rubik_13, curTarget.getHeldItem(EnumHand.OFF_HAND), (int) x + 132, (int) y + 7);
                        mc.getRenderItem().renderItemIntoGUI(curTarget.getHeldItem(EnumHand.OFF_HAND), (int) x + 135, (int) y + 1);

                        for (NetworkPlayerInfo targetHead : mc.player.connection.getPlayerInfoMap()) {
                            try {
                                if (mc.world.getPlayerEntityByUUID(targetHead.getGameProfile().getId()) == curTarget) {
                                    mc.getTextureManager().bindTexture(targetHead.getLocationSkin());
                                    final int scaleOffset = (int) (curTarget.hurtTime * 0.55f);

                                    float hurtPercent = getHurtPercent(curTarget);
                                    GL11.glPushMatrix();
                                    GL11.glColor4f(1, 1 - hurtPercent, 1 - hurtPercent, 1);
                                    Gui.drawScaledCustomSizeModalRect((int) x + 3, y + 4, 8.0f, 8.0f, 8, 8, 32, 35, 64.0f, 64.0f);
                                    GL11.glPopMatrix();
                                    GlStateManager.bindTexture(0);
                                }
                            } catch (Exception exception) {
                            }
                        }
                    } catch (Exception exception) {
                    } finally {
                        GlStateManager.popMatrix();
                    }
                }
            }
        }





        else if (targetHudMode.currentMode.equals("Nurik")) {
            DraggableTargetHUD dth = (DraggableTargetHUD) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableTargetHUD.class);
            float x = dth.getX();
            float y = dth.getY();
            dth.setWidth(163);
            dth.setHeight(42);
            if (KillAura.target != null) {
                curTarget = KillAura.target;
                scale = AnimationHelper.animation((float) scale, (float) 1, (float) (6 * GameSense.deltaTime()));
            } else {
                scale = AnimationHelper.animation((float) scale, (float) 0, (float) (6 * GameSense.deltaTime()));
            }
            EntityLivingBase target = KillAura.target;
            if (curTarget != null) {
                if (curTarget instanceof EntityPlayer) {
                    try {

                        GlStateManager.pushMatrix();
                        GlStateManager.resetColor();
                        GL11.glTranslated(x + 50, y + 31, 0);
                        GL11.glScaled(scale, scale, 0);
                        GL11.glTranslated(-(x + 50), -(y + 31), 0);


                        Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
                        Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());
                        Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0,onecolor,twocolor);
                        Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90,onecolor,twocolor );
                        Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor,twocolor);
                        Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor,twocolor);

                        RoundedUtil.drawGradientRound(dth.getX() + 5, dth.getY() + 6, dth.getWidth(), dth.getHeight(), 6.0f, ColorUtils2.applyOpacity(gradientColor4, 10.85f), gradientColor1, gradientColor3, gradientColor2);

                        RoundedUtil.drawGradientRound(dth.getX() + 6, dth.getY() + 7, dth.getWidth() -2, dth.getHeight()-2, 6.0f, ColorUtils2.applyOpacity(gradientColor4, 10.85f).darker().darker(), gradientColor1.darker().darker(), gradientColor3.darker().darker(), gradientColor2.darker().darker());


                        double healthWid = (curTarget.getHealth() / curTarget.getMaxHealth() * 110);
                        healthWid = MathHelper.clamp(healthWid, 0.0D, 110.0D);
                        healthBarWidth = AnimationHelper.animation((float) healthBarWidth, (float) healthWid, (float) (10 * GameSense.deltaTime()));
                        String health = "" + MathematicHelper.round(curTarget.getHealth(), 1);
                        String distance = "" + MathematicHelper.round(mc.player.getDistanceToEntity(curTarget), 1);
                        mc.rubik_15.drawString("Name: " + curTarget.getName(), x + 52, y + 12, -1);
                        mc.rubik_14.drawString(curTarget.getHealth() >= 3 ? health : "", x + 24 + healthBarWidth, y + 20F, new Color(200, 200, 200).getRGB());
                        RoundedUtil.drawGradientRound(x+50,y+30,this.healthBarWidth,10,4,onecolor,onecolor,twocolor,twocolor);
                        mc.getRenderItem().renderItemOverlays(mc.rubik_13, curTarget.getHeldItem(EnumHand.OFF_HAND), (int) x + 142, (int) y + 12);
                        mc.getRenderItem().renderItemIntoGUI(curTarget.getHeldItem(EnumHand.OFF_HAND), (int) x + 145, (int) y + 5);

                        for (NetworkPlayerInfo targetHead : mc.player.connection.getPlayerInfoMap()) {
                            try {
                                if (mc.world.getPlayerEntityByUUID(targetHead.getGameProfile().getId()) == curTarget) {
                                    mc.getTextureManager().bindTexture(targetHead.getLocationSkin());
                                    final int scaleOffset = (int) (curTarget.hurtTime * 0.55f);

                                    float hurtPercent = getHurtPercent(curTarget);
                                    GL11.glPushMatrix();
                                    GL11.glColor4f(1, 1 - hurtPercent, 1 - hurtPercent, 1);
                                    Gui.drawScaledCustomSizeModalRect((int) x +12, y +9, 8.0f, 8.0f, 8, 8, 32, 35, 64.0f, 64.0f);
                                    GL11.glPopMatrix();
                                    GlStateManager.bindTexture(0);
                                }
                            } catch (Exception exception) {
                            }
                        }
                    } catch (Exception exception) {
                    } finally {
                        GlStateManager.popMatrix();
                    }
                }
            }
        }








             else if (targetHudMode.currentMode.equals("Simple")) {
            DraggableTargetHUD dth = (DraggableTargetHUD) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableTargetHUD.class);
            float x = dth.getX();
            float y = dth.getY();
            dth.setWidth(153);
            dth.setHeight(42);
            if (KillAura.target == null) {
                if (TargetHUD.mc.player != null && TargetHUD.mc.currentScreen instanceof GuiChat) {
                    curTarget = TargetHUD.mc.player;
                    this.scale = AnimationHelper.animation((float)this.scale, 1.0f, (float)(6.0 * GameSense.deltaTime()));
                } else {
                    this.scale = AnimationHelper.animation((float)this.scale, 0.0f, (float)(6.0 * GameSense.deltaTime()));
                }
            } else {
                curTarget = KillAura.target;
                this.scale = AnimationHelper.animation((float)this.scale, 1.0f, (float)(6.0 * GameSense.deltaTime()));
            }
            EntityLivingBase target = KillAura.target;
            if (curTarget != null) {
                try {
                    GlStateManager.pushMatrix();
                    GlStateManager.resetColor();
                    GL11.glTranslated((double) (x + 50.0f), (double) (y + 31.0f), (double) 0.0);
                    GL11.glScaled((double) this.scale, (double) this.scale, (double) 0.0);
                    GL11.glTranslated((double) (-(x + 50.0f)), (double) (-(y + 31.0f)), (double) 0.0);
                    if (this.blurThud.getBoolValue()) {
                        RenderUtils.drawBlur(7.0f, () -> RenderUtils.drawSmoothRect(x, y, x + (float) dth.getWidth(), y + (float) dth.getHeight(), new Color(64, 26, 201, 200).getRGB()));
                    }
                    if (this.shadowThud.getBoolValue()) {
                        RenderUtils.drawShadow(5.0f, 1.0f, () -> RenderUtils.drawSmoothRect(x, y, x + (float) dth.getWidth(), y + (float) dth.getHeight(), new Color(64, 26, 201, 200).getRGB()));
                    }
                    RenderUtils.drawSmoothRect(x, y, x + (float) dth.getWidth(), y + (float) dth.getHeight(), new Color(0, 0, 0, 200).getRGB());
                    double healthWid = curTarget.getHealth() / curTarget.getMaxHealth() * 110.0f;
                    healthWid = MathHelper.clamp(healthWid, 0.0, 110.0);
                    this.healthBarWidth = AnimationHelper.animation(this.healthBarWidth, (float) healthWid, (float) (10.0 * GameSense.deltaTime()));
                    String health = "" + MathematicHelper.round(curTarget.getHealth(), 1);
                    String distance = "" + MathematicHelper.round(TargetHUD.mc.player.getDistanceToEntity(curTarget), 1);
                    TargetHUD.mc.neverlose500_15.drawString("Name: " + curTarget.getName(), x + 42.0f, y + 6.0f, -1);
                    TargetHUD.mc.neverlose500_15.drawString("Distance: " + distance, x + 42.0f, y + 15.0f, -1);
                    TargetHUD.mc.neverlose500_14.drawString(curTarget.getHealth() >= 3.0f ? health : "", x + 24.0f + this.healthBarWidth, y + 26.5f, new Color(255, 255, 255).getRGB());
                    RenderUtils.drawSmoothRect(x + 38.0f, y + 33.0f, x + 38.0f + this.healthBarWidth, y + 33.0f + 5.0f, ClientHelper.getTargetHudColor().darker().getRGB());
                    RenderUtils.drawBlurredShadow(x + 38.0f, y + 33.0f, this.healthBarWidth, 5.0f, 12, RenderUtils.injectAlpha(ClientHelper.getTargetHudColor(), 100));
                    mc.getRenderItem().renderItemOverlays(TargetHUD.mc.rubik_13, curTarget.getHeldItem(EnumHand.OFF_HAND), (int) x + 132, (int) y + 7);
                    mc.getRenderItem().renderItemIntoGUI(curTarget.getHeldItem(EnumHand.OFF_HAND), (int) x + 135, (int) y + 1);
                    for (NetworkPlayerInfo targetHead : TargetHUD.mc.player.connection.getPlayerInfoMap()) {
                        if (TargetHUD.mc.world.getPlayerEntityByUUID(targetHead.getGameProfile().getId()) != curTarget)
                            continue;
                        mc.getTextureManager().bindTexture(targetHead.getLocationSkin());
                        int scaleOffset = (int) ((float) TargetHUD.curTarget.hurtTime * 0.55f);
                        float hurtPercent = TargetHUD.getHurtPercent(curTarget);
                        GL11.glPushMatrix();
                        GL11.glColor4f((float) 1.0f, (float) (1.0f - hurtPercent), (float) (1.0f - hurtPercent), (float) 1.0f);
                        Gui.drawScaledCustomSizeModalRect((float) ((int) x + 3), y + 4.0f, 8.0f, 8.0f, 8, 8, 32, 35, 64.0f, 64.0f);
                        GL11.glPopMatrix();
                        GlStateManager.bindTexture(0);
                    }
                } catch (Exception healthWid) {
                } finally {
                    GlStateManager.popMatrix();
                }
            }}else if (targetHudMode.currentMode.equals("GameSense")) {
            DraggableTargetHUD dth = (DraggableTargetHUD) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableTargetHUD.class);
            float x = dth.getX();
            float y = dth.getY();
            dth.setWidth(130);
            dth.setHeight(42 - 5);
            if (KillAura.target == null) {
                if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                    curTarget = mc.player;
                    scale = AnimationHelper.animation((float) scale, (float) 1, (float) (6 * GameSense.deltaTime()));
                } else {
                    scale = AnimationHelper.animation((float) scale, (float) 0, (float) (6 * GameSense.deltaTime()));
                }
            } else {
                curTarget = KillAura.target;
                scale = AnimationHelper.animation((float) scale, (float) 1, (float) (6 * GameSense.deltaTime()));
            }
            if (curTarget != null) {
                try {
                    GlStateManager.pushMatrix();
                    GlStateManager.resetColor();
                    GL11.glTranslated(x + 36, y + 26, 0);
                    GL11.glScaled(scale, scale, 0);
                    GL11.glTranslated(-(x + 36), -(y + 26), 0);
                    if (blurThud.getBoolValue()) {
                        RenderUtils.drawBlur(8, () -> {
                            RenderUtils.drawBlurredShadow(x, y, dth.getWidth(), dth.getHeight(), 12, new Color(0, 0, 0, 170));
                        });
                    }
                    if (shadowThud.getBoolValue()) {
                        RenderUtils.drawShadow(8, 1, () -> {
                            RenderUtils.drawBlurredShadow(x, y, dth.getWidth(), dth.getHeight(), 12, new Color(0, 0, 0, 170));
                        });
                    }
                    double healthWid = (curTarget.getHealth() / curTarget.getMaxHealth() * 80);
                    healthWid = MathHelper.clamp(healthWid, 0.0D, 80);
                    healthBarWidth = AnimationHelper.animation(healthBarWidth, (float) healthWid, (float) (10 * GameSense.deltaTime()));
                    String health = "" + MathematicHelper.round(curTarget.getHealth(), 1);
                    String distance = "" + MathematicHelper.round(mc.player.getDistanceToEntity(curTarget), 1);

                    RenderUtils.drawBlurredShadow(x, y, 150, 50, 6, new Color(0, 0, 0, 170));
                    mc.getRenderItem().renderItemOverlays(mc.rubik_15, curTarget.getHeldItem(EnumHand.OFF_HAND), (int) x + 121, (int) y + 10);
                    mc.getRenderItem().renderItemIntoGUI(curTarget.getHeldItem(EnumHand.OFF_HAND), (int) x + 121, (int) y + 5);
                    mc.rubik_15.drawString(curTarget.getHealth() >= 3 ? health : "", x + 24 + healthBarWidth, y + 25, new Color(200, 200, 200).getRGB());
                    RenderUtils.drawSmoothRect(x + 38, y + 33, x + 38 + healthBarWidth, y + 33 + 5, new Color(ClientHelper.getClientColor().getRGB()).getRGB());
                    mc.rubik_15.drawString(GameSense.instance.featureManager.getFeature(NameProtect.class).isEnabled() && NameProtect.otherName.getBoolValue() ? "Protected" : curTarget.getName(), x + 40, y + 12, new Color(255, 255, 255, 255).getRGB());
                    healthBarWidth = AnimationHelper.animation((float) healthBarWidth, (float) healthWid, (float) (10 * GameSense.deltaTime()));

                    if (particles2.getBoolValue() && thudColorMode.currentMode.equals("Custom")) {
                        for (final Particles p : particles) {
                            if (p.opacity > 4) p.render2D();
                        }

                        if (thudTimer.hasReached(15)) {
                            for (final Particles p : particles) {
                                p.updatePosition();

                                if (p.opacity < 1) particles.remove(p);
                            }
                            thudTimer.reset();
                        }

                        if (curTarget.hurtTime == 8) {
                            for (int i = 0; i < 1; i++) {
                                final Particles p = new Particles();
                                p.init((x + 15), y + 15, ((Math.random() - 0.5) * 2) * 1.9, ((Math.random() - 0.5) * 2) * 1.4, (float) Math.random() * 1, ClientHelper.getTargetHudColor());
                                particles.add(p);
                            }
                        }
                    }
                    for (NetworkPlayerInfo targetHead : mc.player.connection.getPlayerInfoMap()) {
                        try {
                            if (mc.world.getPlayerEntityByUUID(targetHead.getGameProfile().getId()) == curTarget && curTarget instanceof EntityPlayer) {
                                mc.getTextureManager().bindTexture(targetHead.getLocationSkin());
                                float hurtPercent = getHurtPercent(curTarget);
                                GL11.glPushMatrix();
                                GL11.glColor4f(1, 1 - hurtPercent, 1 - hurtPercent, 1);
                                Gui.drawScaledCustomSizeModalRect((int) x + 5, y + 10, 8.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f);
                                GL11.glPopMatrix();
                                GlStateManager.bindTexture(0);
                            }
                        } catch (Exception exception) {
                        }
                    }

                } catch (Exception exception) {
                } finally {
                    GlStateManager.popMatrix();
                }

            }
        }}



    public static float getRenderHurtTime(EntityLivingBase hurt) {
        return (float) hurt.hurtTime - (hurt.hurtTime != 0 ? mc.timer.renderPartialTicks : 0.0f);
    }

    public static float getHurtPercent(EntityLivingBase hurt) {
        return getRenderHurtTime(hurt) / (float) 10;
    }

    @Override
    public void onEnable() {
        if (this.mc.gameSettings.ofFastRender) {
            this.mc.gameSettings.ofFastRender = false;
        }
        super.onEnable();
    }
}
