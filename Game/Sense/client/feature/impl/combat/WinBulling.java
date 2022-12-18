package Game.Sense.client.feature.impl.combat;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.utils.math.TimerHelper;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;

public class WinBulling extends Feature {
    public TimerHelper timerHelper = new TimerHelper();

    public WinBulling() {
        super("WinBulling", "При килле пишет что-то в чат", FeatureCategory.Combat);
    }

    public WinBulling(String label, String desc, FeatureCategory category) {
        super(label, desc, category);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if ((KillAura.target.getHealth() <= 0.0f || KillAura.target.isDead) && KillAura.target instanceof EntityPlayer) {
            String[] messages = new String[]{"Бедный вьебал ","Лузай дальше бездарность"};
            String finalText = messages[new Random().nextInt(messages.length)];
            if (timerHelper.hasReached(200)) {
                mc.player.sendChatMessage("/tell " + KillAura.target.getName() + " ," + " " + finalText);
                timerHelper.reset();
            }
        }
    }
}
