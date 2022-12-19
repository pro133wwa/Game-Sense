package Game.Sense.client.module.feature.RENDER;


import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.Utility.render.ColorUtils3;
import Game.Sense.client.Helper.events.impl.render.EventRender3D;
import Game.Sense.client.UI.UwU.DrawHelper;
import Game.Sense.client.UI.UwU.MathUtil;
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
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ChinaHat extends Module {

    final BooleanSetting astolfo = new BooleanSetting("Astolfo", false, () -> true);

    final BooleanSetting hideInFirstPerson = new BooleanSetting("шл€пка ахуеена", true, () -> true);

    public ChinaHat() {
        super("ChinaHat", "epic hat", ModuleCategory.RENDER);
        addSettings(astolfo);
    }

    @EventTarget
    public void asf(EventRender3D event) {
        if (ChinaHat.mc.gameSettings.thirdPersonView == 0 && this.hideInFirstPerson.getBoolValue()) {
            return;
        }
        if (mc.player == null || mc.world == null || mc.player.isInvisible() || mc.player.isDead) return;
        if (!hideInFirstPerson.getBoolValue() && mc.gameSettings.thirdPersonView == 0) return;

        mc.getRenderManager(); double posX = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
        mc.getRenderManager(); double posY = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
        mc.getRenderManager(); double posZ = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;

        AxisAlignedBB axisalignedbb = mc.player.getEntityBoundingBox();
        double height = axisalignedbb.maxY - axisalignedbb.minY + 0.02D;
        double radius = axisalignedbb.maxX - axisalignedbb.minX;

        GL11.glPushMatrix();
        GlStateManager.disableCull();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glDisable(3553);
        GL11.glShadeModel(7425);
        GL11.glEnable(3042);
        GlStateManager.disableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);

        float yaw = MathUtil.interpolate(mc.player.prevRotationYaw, mc.player.rotationYaw, mc.timer.renderPartialTicks).floatValue();
        float pitchInterpolate = MathUtil.interpolate(mc.player.prevRenderArmPitch, mc.player.renderArmPitch, mc.timer.renderPartialTicks).floatValue();

        GL11.glTranslated(posX, posY, posZ);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glRotated(yaw, 0.0D, -1.0D, 0.0D);
        GL11.glRotated(pitchInterpolate / 3.0D, 0.0D, 0.0D, 0.0D);
        GL11.glTranslatef(0.0F, 0.0F, pitchInterpolate / 270.0F);
        GL11.glLineWidth(2.0F);
        GL11.glBegin(2);


        for (int i = 0; i <= 180; i++) {
            int color1 = !this.astolfo.getBoolValue() ? ColorUtils.rainbow(35, i * 4, 1.0F, 1.0F, 0.5F).getRGB() : DrawHelper.getColorWithOpacity(ColorUtils3.skyRainbow(7, i * 4), 150).getRGB();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderUtils.color(color1);
            GL11.glVertex3d(posX -
                    Math.sin((i * 6.2831855F / 90.0F)) * radius, posY + height - (
                    mc.player.isSneaking() ? 0.23D : 0.0D) - 0.002D, posZ +
                    Math.cos((i * 6.2831855F / 90.0F)) * radius);
        }

        GL11.glEnd();

        GL11.glBegin(6);
        int color12 = !this.astolfo.getBoolValue() ? ColorUtils.rainbow(35, 4, 1.0F, 1.0F, 0.2F).getRGB() : DrawHelper.getColorWithOpacity(ColorUtils3.skyRainbow(7, 4), 80).getRGB();
        RenderUtils.color(color12);
        GL11.glVertex3d(posX, posY + height + 0.3D - (mc.player.isSneaking() ? 0.23D : 0.0D), posZ);


        for (int j = 0; j <= 180; j++) {
            int color1 = !this.astolfo.getBoolValue() ? ColorUtils.rainbow(35, j * 4, 1.0F, 1.0F, 0.2F).getRGB() : DrawHelper.getColorWithOpacity(ColorUtils3.skyRainbow(7, j * 4), 80).getRGB();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderUtils.color(color1);
            GL11.glVertex3d(posX - Math.sin((j * 6.2831855F / 90.0F)) * radius, posY + height - (
                    mc.player.isSneaking() ? 0.23F : 0.0F), posZ +
                    Math.cos((j * 6.2831855F / 90.0F)) * radius);
        }

        //etc ne gey
        GL11.glVertex3d(posX, posY + height + 0.3D - (mc.player.isSneaking() ? 0.23D : 0.0D), posZ);
        GL11.glEnd();


        GL11.glPopMatrix();

        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
    }
}