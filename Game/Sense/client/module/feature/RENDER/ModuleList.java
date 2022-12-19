package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.impl.DragModuleList;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.render.EventRender2D;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ColorSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.Helper.Utility.Helper;
import Game.Sense.client.Helper.Utility.math.AnimationHelper;
import Game.Sense.client.Helper.Utility.render.ClientHelper;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class ModuleList extends Module {
    public float scale = 2;

    public static ListSetting colorList = new ListSetting("ArrayList Color", "Custom", () -> true, "Custom");
    public static ColorSetting oneColor = new ColorSetting("One Color", new Color(0x00FDF5).getRGB(), () -> colorList.currentMode.equals("Custom") || colorList.currentMode.equals("Fade"));
    public static ColorSetting twoColor = new ColorSetting("Two Color", new Color(0xFFFFFF).getRGB(), () -> colorList.currentMode.equals("Custom"));
    public BooleanSetting onlyBinds = new BooleanSetting("Only Binds", false, () -> true);
    public BooleanSetting noVisualModules = new BooleanSetting("No Visual", true, () -> true);
    public ModuleList() {
        super("ModuleList", "���������� ������ ���������� ������� �� ������", ModuleCategory.RENDER);
        addSettings(colorList,oneColor,twoColor, noVisualModules, onlyBinds);
    }


    @EventTarget
    public void Event2D(EventRender2D event) {
        if (!isEnabled()) return;

        DragModuleList di = (DragModuleList) GameSense.instance.draggableHUD.getDraggableComponentByClass(DragModuleList.class);
        di.setWidth(145);
        di.setHeight(100);
        int stringWidth;
        List<Module> activeModules = GameSense.instance.featureManager.getAllFeatures();
        activeModules.sort(Comparator.comparingDouble(s -> -Helper.mc.rubik_13.getStringWidth(s.getLabel().toLowerCase())));
        float displayWidth = di.getX();
        float y = di.getY();
        float x = di.getX();
            RenderUtils.drawImage(new ResourceLocation("GameSense/Kur.png"), x, y, 50, 50, Color.WHITE);
        ScaledResolution rs = new ScaledResolution(this.mc);
        int width = GameSense.scale.calc(rs.getScaledWidth());
        int height = GameSense.scale.calc(rs.getScaledHeight());
        boolean reverse = displayWidth > (float) (width / 2);
        boolean reverseY = y > (float) (height / 2);
        int yTotal = 0;
        int offset = 1;
        for (int i = 0; i < GameSense.instance.featureManager.getAllFeatures().size(); ++i) {
            yTotal += Helper.mc.rubik_13.getFontHeight() + 3;
        }
        if (reverse) {
            for (Module module : activeModules) {
                module.animYto = AnimationHelper.Move(module.animYto, (float) (module.isEnabled() ? 1 : 0), (float) (6.5f * GameSense.deltaTime()), (float) (6.5f * GameSense.deltaTime()), (float) GameSense.deltaTime());
                if (module.animYto > 0.01f) {
                    if (module.getSuffix().equals("ClickGui") || noVisualModules.getBoolValue() && module.getCategory() == ModuleCategory.RENDER || onlyBinds.getBoolValue() && module.getBind() == 0)
                        continue;
                    stringWidth = this.mc.rubik_13.getStringWidth(module.getLabel().toLowerCase()) + 3;
                    RenderUtils.drawRect4(displayWidth + 50 - Helper.mc.rubik_13.getStringWidth(module.getLabel().toLowerCase()) - 5, y, displayWidth + 50, y + (float) offset + 8.2f, RenderUtils.injectAlpha(ClientHelper.getClientColor(y, yTotal, 20), 255).getRGB());
                    RenderUtils.drawRect4(displayWidth + 49, y, displayWidth + 51.5f, y + 8.2f + (float) offset, Color.WHITE.getRGB());
                    y += 8 * module.animYto;
                }

            }
        }


        if (!reverse) {
            for (Module module : activeModules) {
                module.animYto = AnimationHelper.Move(module.animYto, (float) (module.isEnabled() ? 1 : 0), (float) (6.5f * GameSense.deltaTime()), (float) (6.5f * GameSense.deltaTime()), (float) GameSense.deltaTime());


                if (module.animYto > 0.01f) {
                    if (module.getSuffix().equals("ClickGui") || noVisualModules.getBoolValue() && module.getCategory() == ModuleCategory.RENDER || onlyBinds.getBoolValue() && module.getBind() == 0)
                        continue;
                    stringWidth = this.mc.rubik_13.getStringWidth(module.getLabel().toLowerCase()) + 3;
                    GlStateManager.pushMatrix();
                    RenderUtils.drawBlurredShadow(displayWidth - 2, y + (float) offset - 3.5f + 2, stringWidth + 5f, 10, 5, RenderUtils.injectAlpha(ClientHelper.getClientColor(y, yTotal, 10), 150));
                    GL11.glTranslated(1, y, 1);
                    GL11.glTranslated(-1, -y, 1);
                    RenderUtils.drawRect(displayWidth, y - 0.5f - 2 + offset + 2, displayWidth + (float) stringWidth + 3.5f, y + (float) offset + 8.0f, RenderUtils.injectAlpha(ClientHelper.getClientColor(y, yTotal, 10), (int) (animYto * 255)).getRGB());
                    this.mc.rubik_13.drawString(module.getLabel().toLowerCase(), displayWidth + 3.5f, y + (float) offset + 2, Color.WHITE.getRGB());
                    RenderUtils.drawRect(displayWidth - 1.5f, y - 0.5f - 2 + offset + 2, displayWidth + 1, y + (float) offset + 8f, Color.WHITE.getRGB());
                    GlStateManager.popMatrix();

                    y += 8 * module.animYto * offset;

                }
            }
        }
    }
}