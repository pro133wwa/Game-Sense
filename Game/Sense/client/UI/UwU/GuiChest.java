package Game.Sense.client.UI.UwU;

import Game.Sense.client.GameSense;
import Game.Sense.client.module.feature.RENDER.ContainerBlur;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

public class GuiChest
        extends GuiContainer {
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    private final IInventory upperChestInventory;
    public final IInventory lowerChestInventory;
    protected ArrayList<ImageButton> imageButtons = new ArrayList();
    private final int inventoryRows;
    private int blur;

    public GuiChest(IInventory upperInv, IInventory lowerInv) {
        super(new ContainerChest(upperInv, lowerInv, Minecraft.getMinecraft().player));
        this.upperChestInventory = upperInv;
        this.lowerChestInventory = lowerInv;
        this.allowUserInput = false;
        int i = 222;
        int j = 114;
        this.inventoryRows = lowerInv.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 18;
    }

    @Override
    public void initGui() {
        int posY = (this.height - this.ySize) / 2 + 5;
        this.imageButtons.clear();
        this.imageButtons.add(new ImageButton(new ResourceLocation("celestial/stealer.png"), this.width / 2 + 35, posY - 30, 20, 20, "", 30));
        this.imageButtons.add(new ImageButton(new ResourceLocation("celestial/put.png"), this.width / 2 + 60, posY - 30, 20, 20, "", 31));
        this.blur = 0;
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        ScaledResolution sr = new ScaledResolution(this.mc);
        ++this.blur;
        this.blur = (int) MathHelper.clamp(this.blur, 0, ContainerBlur.blurStrength.getNumberValue());
        if (GameSense.instance.featureManager.getFeature(ContainerBlur.class).isEnabled()) {
            if (this.mc.gameSettings.ofFastRender) {
                this.mc.gameSettings.ofFastRender = false;
            }
            RenderHelper.renderBlur(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), this.blur / 2);
        }
        for (ImageButton imageButton : this.imageButtons) {
            imageButton.draw(mouseX, mouseY, Color.WHITE);
            if (!Mouse.isButtonDown(0)) continue;
            imageButton.onClick(mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8.0f, 6.0f, 0x404040);
        this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8.0f, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

    public int getInventoryRows() {
        return this.inventoryRows;
    }
}

