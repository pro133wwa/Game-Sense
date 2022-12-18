package Game.Sense.client.feature.impl.misc;

import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;

public class ChatHistory
        extends Feature {
    public ChatHistory() {
        super("ChatHistory", "Ќе удал€ет историю чата при перезаходе на сервер", FeatureCategory.Misc);
    }
}
