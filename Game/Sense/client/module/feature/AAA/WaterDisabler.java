package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

import static java.lang.Thread.sleep;

public class WaterDisabler extends Module {

    public WaterDisabler(){
        super("WaterDisabler", ModuleCategory.MOVEMENT);
    }


    @EventTarget
    public void onUpdate(EventUpdate event) {
            mc.player.sendChatMessage(".wclip 85");
            mc.player.sendChatMessage("������");
            mc.player.sendChatMessage(".wclip 5");
            GameSense.instance.featureManager.getFeature(WaterDisabler.class).setEnabled(false);
    }

    @Override
    public void onEnable() {

        super.onEnable();
    }
}
