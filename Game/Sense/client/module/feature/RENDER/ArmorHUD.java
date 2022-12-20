package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.render.EventRender2D;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class ArmorHUD extends Module {

    private static final RenderItem itemRender;

    public ArmorHUD() {
        super("ArmorHUD", ModuleCategory.RENDER);
    }
    @EventTarget
    public void onRender2D(EventRender2D event) {
        GlStateManager.enableTexture2D();
        ScaledResolution resolution = new ScaledResolution(mc);
        int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        int y = (int) (resolution.getScaledHeight() - 65 - (mc.player.isInWater() ? 10 : 0) - 14 * GuiIngame.progress);
        for (ItemStack is : mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.func_190926_b()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
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