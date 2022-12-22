package Game.Sense.client.UI.NursultanGui.component.impl;

import Game.Sense.client.Helper.Utility.render.ClientHelper;
import Game.Sense.client.Helper.Utility.render.RoundedUtil;
import Game.Sense.client.module.feature.RENDER.ClickGUI;
import Game.Sense.client.UI.NursultanGui.component.Component;
import Game.Sense.client.UI.NursultanGui.component.PropertyComponent;
import Game.Sense.client.UI.Settings.Setting;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.Helper.Utility.math.AnimationHelper;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;

import java.awt.*;


public class BooleanSettingComponent extends Component implements PropertyComponent {
    public float textHoverAnimate = 10.0F;
    public float leftRectAnimation = 0.0F;
    public float rightRectAnimation = 0.0F;
    public BooleanSetting booleanSetting;
    Minecraft mc = Minecraft.getMinecraft();

    public BooleanSettingComponent(Component parent, BooleanSetting booleanSetting, int x, int y, int width, int height) {
        super(parent, booleanSetting.getName(), x, y, width, height);
        this.booleanSetting = booleanSetting;
    }

    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        if (this.booleanSetting.isVisible()) {
            int x = this.getX();
            int y = this.getY();
            int width = this.getWidth();
            int height = this.getHeight();
            int middleHeight = (int) (this.getHeight() / 1.9F);
            boolean hovered = this.isHovered((double)mouseX, (double)mouseY);
            if (ClickGUI.theme.currentMode.equals("Light")) {
                this.mc.rubik_16.drawString(this.getName(), (float)(x + 3), (float)(y +  middleHeight - 2), Color.BLACK.getRGB());
            }

            if (ClickGUI.theme.currentMode.equals("Dark")) {
                if (!hovered) {
                    this.mc.rubik_16.drawString(this.getName().toLowerCase(), (float) (x + 3), (float) (y + middleHeight - 2), Color.LIGHT_GRAY.getRGB());
                }
                if (hovered) {
                    this.mc.rubik_16.drawString(this.getName().toLowerCase(), (float) (x + 3), (float) (y + middleHeight - 2), Color.WHITE.getRGB());
                }
            }
            if (ClickGUI.theme.currentMode.equals("Weird")) {
                this.mc.rubik_16.drawString(this.getName(), (float)(x + 3), (float)(y + middleHeight - 3), Color.WHITE.getRGB());
            }


            this.textHoverAnimate = MathHelper.EaseOutBack(this.textHoverAnimate, hovered ? 7.3F : 2.0F, 0.1F);
            this.leftRectAnimation = MathHelper.EaseOutBack(this.leftRectAnimation, booleanSetting.getBoolValue() ? 8.0F : 19.0F, 0.1F);
            this.rightRectAnimation = MathHelper.EaseOutBack(this.rightRectAnimation, (float)(booleanSetting.getBoolValue() ? 3 : 10), 0.1F);
            RenderUtils.drawGradientRound(x + width - 22,(y + 8F), 17, 10, 4, (booleanSetting.getBoolValue() ? RenderUtils.injectAlpha(ClientHelper.getClientColor(), 55):  new Color(40, 40, 40, 40)),(booleanSetting.getBoolValue() ? RenderUtils.injectAlpha(ClientHelper.getClientColor(), 55):   new Color(40, 40, 40, 55)), (booleanSetting.getBoolValue() ? RenderUtils.injectAlpha(ClientHelper.getClientColor(), 55):   new Color(40, 40, 40, 55)), (booleanSetting.getBoolValue() ? RenderUtils.injectAlpha(ClientHelper.getClientColor(), 55):  new Color(40, 40, 40, 55)) );
            RenderUtils.drawCircle1(x + width - 1 - leftRectAnimation, (float) (y + 13.5F), 0, 360, 4, 5, true,(booleanSetting.getBoolValue() ? Color.WHITE: new Color(94, 94, 94,255)));
            if (ClickGUI.theme.currentMode.equals("Weird")) {
                RoundedUtil.drawGradientRound(x + width - 11 - leftRectAnimation, (float) (y + 5F), ((float) width - this.rightRectAnimation - 80.0F), (height - 11), 5.0F, Color.WHITE, Color.WHITE, ClientHelper.getClientColor().darker(), ClientHelper.getClientColor().brighter());
            }
            if (!ClickGUI.theme.currentMode.equals("Weird")) {
                if (booleanSetting.getBoolValue()) {
                }
            } if (hovered && this.booleanSetting.getDesc() != null) {
            }
        }

    }

    public void onMouseClick(int mouseX, int mouseY, int button) {
        if (button == 0 && this.isHovered((double)mouseX, (double)mouseY) && this.booleanSetting.isVisible()) {
            this.booleanSetting.setBoolValue(!this.booleanSetting.getBoolValue());
        }

    }

    public Setting getSetting() {
        return this.booleanSetting;
    }
}
