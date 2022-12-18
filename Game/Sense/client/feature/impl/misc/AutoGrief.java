package Game.Sense.client.feature.impl.misc;

import Game.Sense.client.GameSense;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.NumberSetting;

import java.util.Random;

public class AutoGrief extends Feature {
    public static NumberSetting on = new NumberSetting("Grief", 1, 1, 23, 1, () -> true);
    public AutoGrief(){
        super("AutoGrief", FeatureCategory.Misc);
        addSettings(on);
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {



        String[] messages = new String[]{"null","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21","22","23"};

            String finalText = "/grief-" + messages[(int) on.getNumberValue()];
            mc.player.sendChatMessage(finalText);
            GameSense.instance.featureManager.getFeature(AutoGrief.class).setEnabled(false);


    }


    @Override
    public void onEnable() {
        GameSense.instance.featureManager.getFeature(AutoGrief.class).setEnabled(false);
        super.onEnable();
    }
}
