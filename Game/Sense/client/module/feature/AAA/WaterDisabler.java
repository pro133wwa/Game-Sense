package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;

import static java.lang.Thread.sleep;

public class WaterDisabler extends Module {

    public WaterDisabler(){
        super("WaterDisabler", ModuleCategory.OTHER);
    }


    @EventTarget
    public void onUpdate(EventUpdate event) {

        int i = 0;
        if (timerHelper.hasReached(1000)) {
            mc.player.sendChatMessage(".wclip 85");
            i++;
        }
        if(i==1){

            mc.player.sendChatMessage(".wclip 5");
            GameSense.instance.featureManager.getFeature(WaterDisabler.class).setEnabled(false);

        }


    }

    @Override
    public void onEnable() {

        super.onEnable();
    }
}
