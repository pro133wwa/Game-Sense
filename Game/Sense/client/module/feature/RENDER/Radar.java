package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.Utility.render.ClientHelper;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import Game.Sense.client.Helper.events.impl.render.EventRender2D;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.UI.UwU.PaletteHelper;
import Game.Sense.client.UI.UwU.RectHelper;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class Radar extends Module {
    private final NumberSetting size;
    private final NumberSetting posx = new NumberSetting("PosX", 860.0f, 0.0f, 900.0f, 1.0f, () -> true);
    private final NumberSetting posy = new NumberSetting("PosY", 15.0f, 0.0f, 350.0f, 1.0f, () -> true);
    public int scale;

    public Radar(){
        super("Radar", ModuleCategory.RENDER);
        this.size = new NumberSetting("Size", 100.0f, 30.0f, 300.0f, 1.0f, () -> true);
        addSettings(posy, posx);
    }


    @EventTarget
    public void onRender2D(EventRender2D event) {
        double psx = this.posx.getNumberValue();
        double psy = this.posy.getNumberValue();
        ScaledResolution sr = new ScaledResolution(mc);
        this.scale = 2;
        int sizeRect = (int)this.size.getNumberValue();
        float xOffset = (float)((double)(sr.getScaledWidth() - sizeRect) - psx);
        float yOffset = (float)psy;
        double playerPosX = Radar.mc.player.posX;
        double playerPosZ = Radar.mc.player.posZ;
        //RectHelper.drawBorderedRect((double)xOffset + 2.5, (double)yOffset + 2.5, (double)(xOffset + (float)sizeRect) - 2.5, (double)(yOffset + (float)sizeRect) - 2.5, 0.5, PaletteHelper.getColor(2), PaletteHelper.getColor(11));
        //RectHelper.drawBorderedRect(xOffset + 3.0f, yOffset + 3.0f, xOffset + (float)sizeRect - 3.0f, yOffset + (float)sizeRect - 3.0f, 0.2, PaletteHelper.getColor(2), new Color(0, 0, 0, 150).getRGB());
        //RectHelper.drawRect((double)xOffset + ((double)((float)sizeRect / 2.0f) - 0.5), (double)yOffset + 3.5, (double)xOffset + ((double)((float)sizeRect / 2.0f) + 0.2), (double)(yOffset + (float)sizeRect) - 3.5, PaletteHelper.getColor(155, 100));
        //RectHelper.drawRect((double)xOffset + 48, (double)yOffset + ((double)((float)sizeRect / 2.0f) - 2), (double)(xOffset + (float)sizeRect) - 48, (double)yOffset + ((double)((float)sizeRect / 2.0f) + 2), new Color(255, 255, 255, 255).getRGB());
        //RectHelper.drawRectBetter(xOffset + 3.5f, yOffset + 1f, sizeRect - 7, 2.0, ClientHelper.getClientColor().getRGB());
        RenderUtils.drawSmoothRect(xOffset + 3.0f, yOffset + 3.0f, xOffset + (float)sizeRect - 3.0f, yOffset + (float)sizeRect - 3.0f, new Color(0, 0, 0, 150).getRGB());
        RenderUtils.drawSmoothRect((double)xOffset + 48.5, (double)yOffset + ((double)((float)sizeRect / 2.0f) - 1.5), (double)(xOffset + (float)sizeRect) - 48.5, (double)yOffset + ((double)((float)sizeRect / 2.0f) + 1.5), new Color(255, 255, 255, 255).getRGB());
        for (Entity entity : Radar.mc.world.loadedEntityList) {
            EntityPlayer entityPlayer;
            if (!(entity instanceof EntityPlayer) || (entityPlayer = (EntityPlayer)entity) == Radar.mc.player || entityPlayer.isInvisible()) continue;
            float partialTicks = Radar.mc.timer.renderPartialTicks;
            float posX = (float)(entityPlayer.posX + (entityPlayer.posX - entityPlayer.lastTickPosX) * (double)partialTicks - playerPosX) * (float)this.scale;
            float posZ = (float)(entityPlayer.posZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * (double)partialTicks - playerPosZ) * (float)this.scale;
            int color = Radar.mc.player.canEntityBeSeen(entityPlayer) ? new Color(255, 0, 0).getRGB() : new Color(255, 0, 0).getRGB();
            float cos = (float)Math.cos((double)Radar.mc.player.rotationYaw * (Math.PI / 180));
            float sin = (float)Math.sin((double)Radar.mc.player.rotationYaw * (Math.PI / 180));
            float rotY = -(posZ * cos - posX * sin);
            float rotX = -(posX * cos + posZ * sin);
            if (rotY > (float)sizeRect / 2.0f - 5.0f) {
                rotY = (float)sizeRect / 2.0f - 5.0f;
            } else if (rotY < -((float)sizeRect / 2.0f - 5.0f)) {
                rotY = -((float)sizeRect / 2.0f - 5.0f);
            }
            if (rotX > (float)sizeRect / 2.0f - 5.0f) {
                rotX = (float)sizeRect / 2.0f - 5.0f;
            } else if (rotX < -((float)sizeRect / 2.0f - 5.0f)) {
                rotX = -((float)sizeRect / 2.0f - 5.0f);
            }
            RectHelper.drawBorderedRect((double)(xOffset + (float)sizeRect / 2.0f + rotX) - 1.5, (double)(yOffset + (float)sizeRect / 2.0f + rotY) - 1.5, (double)(xOffset + (float)sizeRect / 2.0f + rotX) + 1.5, (double)(yOffset + (float)sizeRect / 2.0f + rotY) + 1.5, 0.5, new Color(255, 0, 0,255).getRGB(), new Color(255, 0, 0,255).getRGB());
        }
    }
}

