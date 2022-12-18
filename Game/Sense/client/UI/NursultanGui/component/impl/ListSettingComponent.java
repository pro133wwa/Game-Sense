package Game.Sense.client.UI.NursultanGui.component.impl;

import Game.Sense.client.module.feature.RENDER.ClickGUI;
import Game.Sense.client.UI.NursultanGui.Panel;
import Game.Sense.client.UI.NursultanGui.component.Component;
import Game.Sense.client.UI.NursultanGui.component.ExpandableComponent;
import Game.Sense.client.UI.NursultanGui.component.PropertyComponent;
import Game.Sense.client.UI.Settings.Setting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;


public class ListSettingComponent extends ExpandableComponent implements PropertyComponent {

    private final ListSetting listSetting;
    Minecraft mc = Minecraft.getMinecraft();

    public ListSettingComponent(Component parent, ListSetting listSetting, int x, int y, int width, int height) {
        super(parent, listSetting.getName(), x, y, width, height);
        this.listSetting = listSetting;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        String selectedText = listSetting.currentMode;
        int dropDownBoxY = y + 4;
        int textColor = 0xFFFFFF;
        Gui.drawRect(x, y, x + width, y + height, new Color(20, 20, 20, 111).getRGB());
        Gui.drawRect(x + 0.5F, dropDownBoxY, x + getWidth() - 0.5F, (int) (dropDownBoxY + 11), new Color(30, 30, 30).getRGB());
        mc.neverlose900_14.drawStringWithOutline(getName(), x + 2 + Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET, dropDownBoxY + 3, new Color(222, 222, 222).getRGB());
        mc.neverlose900_14.drawCenteredBlurredString(selectedText, x + width - 16, dropDownBoxY + 3, 8, RenderUtils.injectAlpha(new Color(ClickGUI.color.getColorValue()), 100), ClickGUI.color.getColorValue());
        if (isExpanded()) {
            Gui.drawRect(x + Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET, y + height, x + width - Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET, y + getHeightWithExpand(), new Color(25, 25, 25, 160).getRGB());
            handleRender(x, y + getHeight() + 2, width, textColor);
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        super.onMouseClick(mouseX, mouseY, button);
        if (isExpanded()) {
            handleClick(mouseX, mouseY, getX(), getY() + getHeight() + 2, getWidth());
        }
    }

    private void handleRender(int x, int y, int width, int textColor) {
        int color = 0;
        Color onecolor = new Color(ClickGUI.color.getColorValue());

        for (String e : listSetting.getModes()) {
            if (listSetting.currentMode.equals(e)) {
                mc.neverlose900_15.drawCenteredBlurredString(e, x + Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET + width / 2 + 0.5f, y + 2.5F, 8, RenderUtils.injectAlpha(new Color(ClickGUI.color.getColorValue()), 60), ClickGUI.color.getColorValue());
            } else {
                mc.neverlose900_15.drawCenteredString(e, x + Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET + width / 2 + 0.5f, y + 2.5F, Color.GRAY.getRGB());

            }
            y += (Game.Sense.client.UI.NursultanGui.Panel.ITEM_HEIGHT - 3);
        }
    }

    private void handleClick(int mouseX, int mouseY, int x, int y, int width) {
        for (String e : this.listSetting.getModes()) {
            if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + Game.Sense.client.UI.NursultanGui.Panel.ITEM_HEIGHT - 3) {
                listSetting.setListMode(e);
            }

            y += Game.Sense.client.UI.NursultanGui.Panel.ITEM_HEIGHT - 3;
        }
    }


    @Override
    public int getHeightWithExpand() {
        return getHeight() + listSetting.getModes().toArray().length * (Panel.ITEM_HEIGHT - 3);
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {
    }

    @Override
    public boolean canExpand() {
        return listSetting.modes.toArray().length > 0;
    }

    @Override
    public Setting getSetting() {
        return listSetting;
    }
}