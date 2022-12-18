package Game.Sense.client.feature.impl.misc;

import Game.Sense.client.GameSense;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;

import java.util.Random;

import static java.lang.Thread.sleep;

public class WaterDisabler extends Feature {

    public WaterDisabler(){
        super("WaterDisabler", FeatureCategory.Misc);
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
