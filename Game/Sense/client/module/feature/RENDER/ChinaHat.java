package Game.Sense.client.module.feature.RENDER;


import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.render.EventRender3D;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ColorSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.render.ClientHelper;
import Game.Sense.client.Helper.Utility.render.ColorUtils;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ChinaHat extends Module {
    final ListSetting colorMode = new ListSetting("Hat Color", "Astolfo", () -> true, "Astolfo", "Pulse", "China", "Custom", "Client", "Static");
    final ColorSetting onecolor = new ColorSetting("One Color", new Color(255, 255, 255).getRGB(), () -> colorMode.currentMode.equalsIgnoreCase("Static") || colorMode.currentMode.equalsIgnoreCase("Custom"));
    final ColorSetting twocolor = new ColorSetting("Two Color", new Color(255, 255, 255).getRGB(), () -> colorMode.currentMode.equalsIgnoreCase("Custom"));
    final NumberSetting saturation = new NumberSetting("Saturation", 0.7f, 0.1f, 1f, 0.1f, () -> colorMode.currentMode.equalsIgnoreCase("Astolfo"));
    final BooleanSetting hideInFirstPerson = new BooleanSetting("Hide In First Person", true, () -> true);

    public ChinaHat() {
        super("ChinaHat", "���������� ��������� �����", ModuleCategory.RENDER);
        addSettings(colorMode, onecolor, twocolor, saturation, hideInFirstPerson);
    }

    @EventTarget
    public void asf(EventRender3D event) {
        if (ChinaHat.mc.gameSettings.thirdPersonView == 0 && this.hideInFirstPerson.getBoolValue()) {
            return;
        }
        ItemStack stack = mc.player.getEquipmentInSlot(4);
        final double height = stack.getItem() instanceof ItemArmor ? mc.player.isSneaking() ? -0.1
                : 0.12 : GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled() &&
                CustomModel.modelMode.currentMode.equalsIgnoreCase("Amogus") && mc.player.isSneaking() ? -0.42f :
                GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled() &&
                        CustomModel.modelMode.currentMode.equalsIgnoreCase("GameSense")
                        && mc.player.isSneaking() ?
                        0.1f : CustomModel.modelMode.currentMode.equalsIgnoreCase("Jeff Killer") && mc.player.isSneaking() ?
                        0.09f : CustomModel.modelMode.currentMode.equalsIgnoreCase("Rabbit") && mc.player.isSneaking() ?
                        -0.01 : mc.player.isSneaking() ? -0.22 : GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled()
                        && CustomModel.modelMode.currentMode.equalsIgnoreCase("Amogus") ? -0.42f :
                        GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled() &&
                                CustomModel.modelMode.currentMode.equalsIgnoreCase("GameSense") ? 0.2f :
                                CustomModel.modelMode.currentMode.equalsIgnoreCase("Jeff Killer") ? 0.09f : 0;
        GlStateManager.pushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glTranslatef(0f, (float) (mc.player.height + height), 0f);
        GL11.glRotatef(-mc.player.rotationYaw, 0f, 1f, 0f);
        Color color2 = Color.WHITE;
        Color firstcolor2 = new Color(onecolor.getColorValue());
        switch (colorMode.currentMode) {
            case "Client":
                color2 = ClientHelper.getClientColor();
                break;
            case "Astolfo":
                color2 = ColorUtils.astolfo(5, 5, saturation.getNumberValue(), 10);
                break;
            case "Pulse":
                color2 = ColorUtils.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (1 / 16) / 60);
                break;
            case "China":
                color2 = ColorUtils.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (0 / 16) / 60);
                break;
            case "Custom":
                color2 = ColorUtils.TwoColoreffect(new Color(onecolor.getColorValue()), new Color(twocolor.getColorValue()), Math.abs(System.currentTimeMillis() / 10) / 100.0 + 3.0F * (1 / 16) / 60);
                break;
            case "Static":
                color2 = firstcolor2;
                break;
        }

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        RenderUtils.glColor(color2, 255);
        GL11.glVertex3d(0.0, 0.3, 0.0);

        for (float i = 0; i < 360.5; i += 1) {
            Color color = Color.WHITE;
            Color firstcolor = new Color(onecolor.getColorValue());
            switch (colorMode.currentMode) {
                case "Client":
                    color = ClientHelper.getClientColor();
                    break;
                case "Astolfo":
                    color = ColorUtils.astolfo(i - i + 1, i, saturation.getNumberValue(), 10);
                    break;
                case "Pulse":
                    color = ColorUtils.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (i / 16) / 60);
                    break;
                case "China":
                    color = ColorUtils.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (i - i / 16) / 60);
                    break;
                case "Custom":
                    color = ColorUtils.TwoColoreffect(new Color(onecolor.getColorValue()), new Color(twocolor.getColorValue()), Math.abs(System.currentTimeMillis() / 10) / 100.0 + 3.0F * (i / 16) / 60);
                    break;
                case "Static":
                    color = firstcolor;
                    break;
            }

            RenderUtils.glColor(color, 180);
            GL11.glVertex3d(Math.cos(i * Math.PI / 180.0) * 0.66, 0, Math.sin(i * Math.PI / 180.0) * 0.66);

        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.resetColor();
        GlStateManager.popMatrix();
    }
}
