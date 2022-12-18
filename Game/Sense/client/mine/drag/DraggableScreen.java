package Game.Sense.client.mine.drag;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.DraggableComponent;
import net.minecraft.util.math.MathHelper;

public class DraggableScreen {

    public void draw(int mouseX, int mouseY) {
        for(DraggableComponent draggableComponent : GameSense.instance.draggableHUD.getComponents()) {
            if(draggableComponent.allowDraw()) {
                drawComponent(mouseX, mouseY, draggableComponent);
            }
        }
    }

    private void drawComponent(int mouseX, int mouseY, DraggableComponent draggableComponent) {
        draggableComponent.draw(mouseX, mouseY);

        boolean hover = MathHelper.isMouseHoveringOnRect(draggableComponent.getX(), draggableComponent.getY(), draggableComponent.getWidth(), draggableComponent.getHeight(), mouseX, mouseY);


    }

    public void click(int mouseX, int mouseY) {
        for(DraggableComponent draggableComponent : GameSense.instance.draggableHUD.getComponents()) {
            if(draggableComponent.allowDraw()) {
                draggableComponent.click(mouseX, mouseY);
            }
        }
    }

    public void release() {
        for(DraggableComponent draggableComponent : GameSense.instance.draggableHUD.getComponents()) {
            draggableComponent.release();
        }
    }

}