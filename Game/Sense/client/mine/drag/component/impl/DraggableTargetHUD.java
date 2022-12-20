package Game.Sense.client.mine.drag.component.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.drag.component.DraggableComponent;
import Game.Sense.client.module.feature.COMBAT.KillAura;
import Game.Sense.client.module.feature.RENDER.TargetHUD;
import net.minecraft.client.gui.GuiChat;

import static Game.Sense.client.Helper.Utility.Helper.mc;

public class DraggableTargetHUD extends DraggableComponent {

    public DraggableTargetHUD() {
        super("TargetHUD", 350, 25, 1, 1);
    }


    @Override
    public boolean allowDraw() {
        return GameSense.instance.featureManager.getFeature(TargetHUD.class).isEnabled() && mc.currentScreen instanceof GuiChat;
    }
}
