package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import Game.Sense.client.Helper.Utility.render.RoundedUtil;
import Game.Sense.client.Helper.events.impl.render.EventRender2D;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ArmorHUD extends Module {

    private static final RenderItem itemRender;
    public static ListSetting mode = new ListSetting("Armor Mode", "Type_1", () -> true, "Type_1", "Type_2", "Type_3", "Type_4");
    public ArmorHUD() {
        super("ArmorHUD", ModuleCategory.RENDER);
        addSettings(mode);
    }
    @EventTarget
    public void onRender2D(EventRender2D event) {
        GlStateManager.enableTexture2D();
        ScaledResolution resolution = new ScaledResolution(mc);
        int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        int y = (int) (resolution.getScaledHeight() - 65 - (mc.player.isInWater() ? 10 : 0) - 14 * GuiIngame.progress);
        if(mode.currentMode.equals("Type_1")){
            RenderUtils.drawBlurredShadow(i, y - 5,100,30,5,new Color(40, 40, 40,255));
            RoundedUtil.drawRound(i + 5, y - 3,90,1,0,new Color(255, 255, 255,255));
        }


        for (ItemStack is : mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.func_190926_b()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
            if(mode.currentMode.equals("Type_2")){
                RoundedUtil.drawRound(x, y,16.5f,25,5,new Color(0,0,0, 255));
            }
            if(mode.currentMode.equals("Type_3")){
                RenderUtils.drawBlurredShadow(x, y,17f,25,10,new Color(0,0,0, 255));
            }
            GlStateManager.enableDepth();
            itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(is, x, y);
            itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, is, x, y, "");
            itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = is.func_190916_E() > 1 ? is.func_190916_E() + "" : "";
            mc.neverlose500_14.drawStringWithShadow(s, x + 19 - 2 - mc.neverlose500_14.getStringWidth(s), y + 20, 0xFFFFFF);
            int green = Math.abs(is.getMaxDamage() - is.getItemDamage());
            mc.neverlose500_14.drawStringWithShadow(green + "", x + 8 - mc.neverlose500_14.getStringWidth(green + "") / 2, y - -18, -1);
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    static {
        itemRender = mc.getMinecraft().getRenderItem();
    }
}