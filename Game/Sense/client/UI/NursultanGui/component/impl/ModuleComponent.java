package Game.Sense.client.UI.NursultanGui.component.impl;

import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.RENDER.ClickGUI;
import Game.Sense.client.UI.NursultanGui.ClickGuiScreen;
import Game.Sense.client.UI.NursultanGui.Panel;
import Game.Sense.client.UI.NursultanGui.component.Component;
import Game.Sense.client.UI.NursultanGui.component.ExpandableComponent;
import Game.Sense.client.UI.UwU.Compont.SorterHelper;
import Game.Sense.client.UI.Settings.Setting;
import Game.Sense.client.UI.Settings.impl.*;
import Game.Sense.client.Helper.Utility.math.TimerHelper;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;


public final class ModuleComponent extends ExpandableComponent {
    Minecraft mc = Minecraft.getMinecraft();
    private final Module module;
    public static TimerHelper timerHelper = new TimerHelper();
    private boolean binding;
    int alpha = 0;
    private final TimerHelper descTimer = new TimerHelper();

    public ModuleComponent(Game.Sense.client.UI.NursultanGui.component.Component parent, Module module, int x, int y, int width, int height) {
        super(parent, module.getLabel(), x, y, width, height);
        this.module = module;
        int propertyX = Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET;
        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                components.add(new BooleanSettingComponent(this, (BooleanSetting) setting, propertyX, height, width - (Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET * 2), Game.Sense.client.UI.NursultanGui.Panel.ITEM_HEIGHT + 6));
            } else if (setting instanceof ColorSetting) {
                components.add(new ColorPickerComponent(this, (ColorSetting) setting, propertyX, height, width - (Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET * 2), Game.Sense.client.UI.NursultanGui.Panel.ITEM_HEIGHT));
            } else if (setting instanceof NumberSetting) {
                components.add(new NumberSettingComponent(this, (NumberSetting) setting, propertyX, height, width - (Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET * 2), Game.Sense.client.UI.NursultanGui.Panel.ITEM_HEIGHT + 5));
            } else if (setting instanceof ListSetting) {
                components.add(new ListSettingComponent(this, (ListSetting) setting, propertyX, height, width - (Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET * 2), Game.Sense.client.UI.NursultanGui.Panel.ITEM_HEIGHT + 2));
            } else if (setting instanceof MultipleBoolSetting) {
                components.add(new MultipleBoolSettingComponent(this, (MultipleBoolSetting) setting, propertyX, height, width - (Game.Sense.client.UI.NursultanGui.Panel.X_ITEM_OFFSET * 2), Game.Sense.client.UI.NursultanGui.Panel.ITEM_HEIGHT + 1));
            }

        }
    }

    public boolean ready = false;
    static String i = " ";

    String getI(String s) {
        if (!timerHelper.hasReached(5)) {
            return i;
        } else {
            timerHelper.reset();
        }
        if (i.length() < s.length()) {
            ready = false;
            return i += s.charAt(i.length());
        }
        ready = true;
        return i;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        components.sort(new SorterHelper());
        float x = getX();
        float y = getY() - 2;
        int width = getWidth();
        int height = getHeight();
        if (isExpanded()) {
            int childY = Panel.ITEM_HEIGHT;
            for (Game.Sense.client.UI.NursultanGui.component.Component child : components) {
                int cHeight = child.getHeight();
                if (child instanceof BooleanSettingComponent) {
                    BooleanSettingComponent booleanSettingComponent = (BooleanSettingComponent) child;
                    if (!booleanSettingComponent.booleanSetting.isVisible()) {
                        continue;
                    }
                }
                if (child instanceof NumberSettingComponent) {
                    NumberSettingComponent numberSettingComponent = (NumberSettingComponent) child;
                    if (!numberSettingComponent.numberSetting.isVisible()) {
                        continue;
                    }
                }

                if (child instanceof ColorPickerComponent) {
                    ColorPickerComponent colorPickerComponent = (ColorPickerComponent) child;
                    if (!colorPickerComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ListSettingComponent) {
                    ListSettingComponent listSettingComponent = (ListSettingComponent) child;
                    if (!listSettingComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) child;
                    if (expandableComponent.isExpanded()) cHeight = expandableComponent.getHeightWithExpand();
                }
                child.setY(childY);
                child.drawComponent(scaledResolution, mouseX, mouseY);
                childY += cHeight;
            }
        }
        if (!ClickGuiScreen.search.getText().isEmpty() && !module.getLabel().toLowerCase().contains(ClickGuiScreen.search.getText().toLowerCase()))
            return;
        Color color = new Color(ClickGUI.color.getColorValue());
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 140);
        boolean hovered = isHovered(mouseX, mouseY);


        if (components.size() > 0.5) {
            mc.rubik_16.drawStringWithShadow(isExpanded() ? "" : "", x + width - 8.5f, y + height / 2F - 3.5, Color.white.getRGB());
        }

        components.sort(new SorterHelper());
//        if (hovered && module.getDesc() != null) {
//            RenderUtils.drawShadow(5, 1, () -> {
//
//                ScaledResolution sr = new ScaledResolution(mc);
//
//                if (!hovered) {
//                    i = " ";
//                }
//                RenderUtils.drawRect(sr.getScaledWidth() / 2 - mc.sfui16.getStringWidth(module.getDesc()) / 2 - 10, sr.getScaledHeight() - 25, sr.getScaledWidth() / 2 + mc.sfui16.getStringWidth(module.getDesc()) / 2 + 10, sr.getScaledHeight(), new Color(0, 0, 0, 150).getRGB());
//                mc.sfui16.drawCenteredStringWithShadow(module == null ? "null pointer :(" : getI(module.getDesc()), sr.getScaledWidth() / 2f, sr.getScaledHeight() - 10, -1);
//
//
//                RenderUtils.drawRect(sr.getScaledWidth() / 2 - mc.sfui16.getStringWidth(module.getDesc()) / 2 - 10, sr.getScaledHeight() - 25, sr.getScaledWidth() / 2 + mc.sfui16.getStringWidth(module.getDesc()) / 2 + 10, sr.getScaledHeight() - 26, color2.getRGB());
//                if (!ClickGUI.potato_mode.getBoolValue()) {
//                    mc.rubik_16.drawCenteredString(module.getLabel(), sr.getScaledWidth() / 2, sr.getScaledHeight() - 21, Color.white.getRGB());
//
//                } else {
//                    mc.rubik_16.drawCenteredString(module.getLabel(), sr.getScaledWidth() / 2, sr.getScaledHeight() - 21, Color.white.getRGB());
//
//                }
//            });
//
//            ScaledResolution sr = new ScaledResolution(mc);
//
//            if (!hovered) {
//                i = " ";
//            }
//            RenderUtils.drawRect(sr.getScaledWidth() / 2 - mc.sfui16.getStringWidth(module.getDesc()) / 2 - 10, sr.getScaledHeight() - 25, sr.getScaledWidth() / 2 + mc.sfui16.getStringWidth(module.getDesc()) / 2 + 10, sr.getScaledHeight(), new Color(0, 0, 0, 150).getRGB());
//            mc.sfui16.drawCenteredStringWithShadow(module == null ? "null pointer :(" : getI(module.getDesc()), sr.getScaledWidth() / 2f, sr.getScaledHeight() - 10, Color.white.getRGB());
//
//
//            RenderUtils.drawRect(sr.getScaledWidth() / 2 - mc.sfui16.getStringWidth(module.getDesc()) / 2 - 10, sr.getScaledHeight() - 25, sr.getScaledWidth() / 2 + mc.sfui16.getStringWidth(module.getDesc()) / 2 + 10, sr.getScaledHeight() - 26, color2.getRGB());
//            if (!ClickGUI.potato_mode.getBoolValue()) {
//                mc.rubik_16.drawCenteredString(module.getLabel(), sr.getScaledWidth() / 2, sr.getScaledHeight() - 21,  Color.white.getRGB());
//
//            } else {
//                mc.rubik_16.drawCenteredString(module.getLabel(), sr.getScaledWidth() / 2, sr.getScaledHeight() - 21, Color.white.getRGB());
//
//            }
//            if (module == null) i = "";
//            else {
//                if (ready && !i.equals(module.getDesc())) i = "";
//            }
//        } else {
//            ready = false;
//        }

        if (module.isEnabled()) {

            if (!ClickGUI.potato_mode.getBoolValue() && ClickGUI.glow.getBoolValue()) {
                mc.rubik_16.drawCenteredBlurredString(binding ? "��� ������... " + Keyboard.getKeyName(module.getBind()) : getName(), x + 53.5f, y + height / 2F - 3, (int) ClickGUI.glowRadius2.getNumberValue(), module.isEnabled() ? RenderUtils.injectAlpha(new Color(new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB()), 100) : Color.WHITE, module.isEnabled() ? ClickGUI.color.getColorValue() : Color.WHITE.getRGB());
            } else {
                mc.rubik_16.drawCenteredString(binding ? "��� ������... " + Keyboard.getKeyName(module.getBind()) : getName(), x + 53.5f, y + height / 2F - 3, module.isEnabled() ? color.getRGB() : Color.WHITE.getRGB());
            }
        } else {
            mc.rubik_16.drawCenteredString(binding ? "��� ������... " + Keyboard.getKeyName(module.getBind()) : getName(), x + 53.5f, y + height / 2F - 3, module.isEnabled() ? new Color(color.getRGB()).getRGB() : Color.WHITE.getRGB());
        }
    }

    @Override
    public boolean canExpand() {
        return !components.isEmpty();
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {
        switch (button) {
            case 0:
                module.toggle();
                break;
            case 2:
                binding = !binding;
                break;
        }
    }

    @Override
    public void onKeyPress(int keyCode) {
        if (binding) {
            ClickGuiScreen.escapeKeyInUse = true;
            module.setBind(keyCode == Keyboard.KEY_DELETE ? Keyboard.KEY_NONE : keyCode);
            binding = false;
        }
    }

    @Override
    public int getHeightWithExpand() {
        int height = getHeight();
        if (isExpanded()) {
            for (Component child : components) {
                int cHeight = child.getHeight();
                if (child instanceof BooleanSettingComponent) {
                    BooleanSettingComponent booleanSettingComponent = (BooleanSettingComponent) child;
                    if (!booleanSettingComponent.booleanSetting.isVisible()) {
                        continue;
                    }
                }
                if (child instanceof NumberSettingComponent) {
                    NumberSettingComponent numberSettingComponent = (NumberSettingComponent) child;
                    if (!numberSettingComponent.numberSetting.isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ColorPickerComponent) {
                    ColorPickerComponent colorPickerComponent = (ColorPickerComponent) child;
                    if (!colorPickerComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ListSettingComponent) {
                    ListSettingComponent listSettingComponent = (ListSettingComponent) child;
                    if (!listSettingComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) child;
                    if (expandableComponent.isExpanded()) cHeight = expandableComponent.getHeightWithExpand();
                }
                height += cHeight;
            }
        }
        return height;
    }

}
