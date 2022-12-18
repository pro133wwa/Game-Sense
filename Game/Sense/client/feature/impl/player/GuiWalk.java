package Game.Sense.client.feature.impl.player;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import org.lwjgl.input.Keyboard;

public class GuiWalk extends Feature {
    public GuiWalk() {
        super("GuiWalk", "��������� ������ � �������� ����������", FeatureCategory.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!(mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
            mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
            mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
            mc.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());
            mc.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
            mc.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
            mc.gameSettings.keyBindSprint.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode());
        }
    }

    public void onDisable() {
        mc.gameSettings.keyBindJump.pressed = false;
        mc.gameSettings.keyBindForward.pressed = false;
        mc.gameSettings.keyBindBack.pressed = false;
        mc.gameSettings.keyBindLeft.pressed = false;
        mc.gameSettings.keyBindRight.pressed = false;
        mc.gameSettings.keyBindSprint.pressed = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}