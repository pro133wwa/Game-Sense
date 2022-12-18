package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventReceivePacket;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.UwU.NAXNADO.Friend;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.math.TimerHelper;
import net.minecraft.network.play.server.SPacketChat;


public class AutoTPAccept
        extends Module {
    private final BooleanSetting friendsOnly;
    private final NumberSetting delay;
    private final TimerHelper timerHelper = new TimerHelper();

    public AutoTPAccept() {
        super("AutoTPAccept", "Автоматически принимает телепорт", ModuleCategory.PLAYER);
        friendsOnly = new BooleanSetting("Friends Only", false, () -> true);
        delay = new NumberSetting("Delay", 300.0f, 0.0f, 1000.0f, 100.0f, () -> true);
        addSettings(this.friendsOnly, delay);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket e) {
        SPacketChat message = (SPacketChat) e.getPacket();
        if (message.getChatComponent().getFormattedText().contains("телепортироваться")) {
            if (this.friendsOnly.getBoolValue()) {
                for (Friend friend : GameSense.instance.friendManager.getFriends()) {
                    if (!message.getChatComponent().getFormattedText().contains(friend.getName()) || !this.timerHelper.hasReached(this.delay.getNumberValue()))
                        continue;
                    mc.player.sendChatMessage("/tpaccept");
                    timerHelper.reset();
                }
            } else if (this.timerHelper.hasReached(this.delay.getNumberValue())) {
                mc.player.sendChatMessage("/tpaccept");
                timerHelper.reset();
            }
        }
    }
}
