package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.module.feature.COMBAT.KillAura;
import Game.Sense.client.Helper.Utility.math.TimerHelper;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;

public class AutoWin extends Module {
    public TimerHelper timerHelper = new TimerHelper();

    public AutoWin() {
        super("AutoWin", "При килле пишет что-то в чат", ModuleCategory.COMBAT);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if ((KillAura.target.getHealth() <= 0.0f || KillAura.target.isDead) && KillAura.target instanceof EntityPlayer) {
            String[] messages = new String[]{"не позорься , ты пролузал фри софту - SokolClient","Хуесос,ну как? Тебя отымели с SokolClient!"};
            String finalText = messages[new Random().nextInt(messages.length)];
            if (timerHelper.hasReached(200)) {
                mc.player.sendChatMessage("/tell " + KillAura.target.getName() + " ," + " " + finalText);
                timerHelper.reset();
            }
        }
    }
}
