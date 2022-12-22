package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.Utility.render.ClientHelper;
import Game.Sense.client.Helper.Utility.render.ColorUtils2;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import Game.Sense.client.Helper.Utility.render.RoundedUtil;
import Game.Sense.client.Helper.events.impl.render.EventRender2D;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.UI.UwU.PaletteHelper;
import Game.Sense.client.UI.UwU.RectHelper;
import Game.Sense.client.mine.drag.component.impl.DraggableRadar;
import Game.Sense.client.mine.drag.component.impl.DraggableWaterMark;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class Radar extends Module {
    private final NumberSetting size;

    public int scale;

    public Radar(){
        super("Radar", ModuleCategory.RENDER);
        this.size = new NumberSetting("Size", 100.0f, 30.0f, 300.0f, 1.0f, () -> true);
        addSettings();
    }


    @EventTarget
    public void onRender2D(EventRender2D event) {
        DraggableRadar dwm = (DraggableRadar) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableRadar.class);
        dwm.setWidth(100);
        dwm.setHeight(100);
        double psx = dwm.getX();
        double psy = dwm.getY();
        ScaledResolution sr = new ScaledResolution(mc);
        this.scale = 2;
        int sizeRect = (int)this.size.getNumberValue();
        float xOffset = (float) psx;
        float yOffset = (float)psy;
        double playerPosX = Radar.mc.player.posX;
        double playerPosZ = Radar.mc.player.posZ;
        Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
        Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());
        Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0, onecolor, twocolor);
        Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90, onecolor, twocolor);
        Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor, twocolor);
        Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor, twocolor);
        RoundedUtil.drawGradientRound(xOffset + 3.0f, yOffset + 3.0f, dwm.getWidth(), dwm.getHeight(),4,ColorUtils2.applyOpacity(gradientColor4, 10.85f).brighter(), gradientColor1.brighter(), gradientColor3.brighter(), gradientColor2.brighter());
        mc.rubik_18.drawCenteredString("Radar",dwm.getX()+25,dwm.getY()+10,Color.WHITE.getRGB());
        RenderUtils.drawRect2(dwm.getX()+2,dwm.getY()+50,dwm.getWidth()+2,0.5f, Color.white.getRGB());
        RenderUtils.drawRect2(dwm.getX()+52,dwm.getY()+2,0.5f,dwm.getHeight()+2, Color.white.getRGB());
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

