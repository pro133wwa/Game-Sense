package Game.Sense.client.UI.NursultanGui;

import Game.Sense.client.GameSense;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.module.feature.RENDER.ClickGUI;
import Game.Sense.client.UI.NursultanGui.component.AnimationState;
import Game.Sense.client.UI.NursultanGui.component.Component;
import Game.Sense.client.UI.NursultanGui.component.DraggablePanel;
import Game.Sense.client.UI.NursultanGui.component.ExpandableComponent;
import Game.Sense.client.UI.NursultanGui.component.impl.ModuleComponent;
import Game.Sense.client.Helper.Utility.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.List;


public final class Panel extends DraggablePanel {
    Minecraft mc = Minecraft.getMinecraft();
    public static final int HEADER_WIDTH = 107;
    public static final int X_ITEM_OFFSET = 1;
    public static final int ITEM_HEIGHT = 15;
    public static final int HEADER_HEIGHT = 17;
    public List<Module> features;
    public ModuleCategory type;
    public AnimationState state;
    private int prevX;
    private int prevY;
    private boolean dragging;

    public Panel(ModuleCategory category, int x, int y) {
        super(null, category.name(), x, y, HEADER_WIDTH, HEADER_HEIGHT);
        int moduleY = HEADER_HEIGHT;
        this.state = AnimationState.STATIC;
        this.features = GameSense.instance.featureManager.getFeaturesCategory(category);
        for (Module feature : features) {
            this.components.add(new ModuleComponent(this, feature, X_ITEM_OFFSET, moduleY, HEADER_WIDTH - (X_ITEM_OFFSET * 2), ITEM_HEIGHT));
            moduleY += ITEM_HEIGHT;
        }
        this.type = category;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        if (dragging) {
            setX(mouseX - prevX);
            setY(mouseY - prevY);
        }
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        int headerHeight;
        int heightWithExpand = getHeightWithExpand();
        headerHeight = (isExpanded() ? heightWithExpand : height);
        float startAlpha1 = 0.14f;
        int size1 = 25;
        float left1 = x + 1.0f;
        float right1 = x + width;
        float bottom1 = y + headerHeight - 6.0f;
        float top1 = y + headerHeight - 2.0f;
        float top2 = y + 13.0f;

        Color color = new Color(ClickGUI.color.getColorValue());

        float extendedHeight = 2;
        Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
        Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());

       Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0,onecolor,twocolor);
       Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90,onecolor,twocolor );
       Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor,twocolor);
       Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor,twocolor);

       Color threecolor = ColorUtils2.interpolateColorsBackAndForth(15, 0,new Color(ColorUtils.rainbow(10 ,  40, 0.7F, 1.0F, 1.0F).getRGB()),new Color(ColorUtils.rainbow(10 ,  40, 0.7F, 1.0F, 1.0F).getRGB()));

        Color rainbowColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0,threecolor,threecolor);
        Color rainbowColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 0,threecolor,threecolor);
        Color rainbowColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 0,threecolor,threecolor);
        Color rainbowColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 0,threecolor,threecolor);

        //RenderUtils.drawBlurredShadow(x,y+0.5f,width,headerHeight-extendedHeight,18,gradientColor1);

        RoundedUtil.drawGradientRound(x-1f, y - 0.7f,   width+2f, headerHeight - extendedHeight+2f, 6f,gradientColor1.brighter().brighter(),gradientColor2.brighter().brighter(),gradientColor3.brighter().brighter(),gradientColor4.brighter().brighter());
        RoundedUtil.drawGradientRound(x, y + 0.5f,   width, headerHeight - extendedHeight, 6f,gradientColor1,gradientColor2,gradientColor3,gradientColor4);


        mc.rubik_18.drawCenteredString(getName().toUpperCase(), x + 54f, y + HEADER_HEIGHT / 2F - 4, Color.WHITE.getRGB());

        super.drawComponent(scaledResolution, mouseX, mouseY);

        if (isExpanded()) {
            RenderUtils.drawRect2(x,y+16f,width,0.3f,Color.white.getRGB());
            for (Component component : components) {
                component.setY(height+3);
                component.drawComponent(scaledResolution, mouseX, mouseY);
                int cHeight = component.getHeight();
                if (component instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) component;
                    if (expandableComponent.isExpanded()) {
                        cHeight = expandableComponent.getHeightWithExpand() + 8;
                    }
                }
                height += cHeight;
            }
        }
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {
        if (button == 0 && !this.dragging) {
            dragging = true;
            prevX = mouseX - getX();
            prevY = mouseY - getY();
        }
    }


    @Override
    public void onMouseRelease(int button) {
        super.onMouseRelease(button);
        dragging = false;
    }

    @Override
    public boolean canExpand() {
        return !features.isEmpty();
    }

    @Override
    public int getHeightWithExpand() {
        int height = getHeight();
        if (isExpanded()) {
            for (Component component : components) {
                int cHeight = component.getHeight();
                if (component instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) component;
                    if (expandableComponent.isExpanded())
                        cHeight = expandableComponent.getHeightWithExpand() + 5;
                }
                height += cHeight;
            }
        }
        return height;
    }
}
