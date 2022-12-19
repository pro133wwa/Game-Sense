package Game.Sense.client.UI.UwU;

import com.google.common.base.Strings;
import net.minecraft.network.play.server.SPacketCombatEvent;

public class ClientChatEvent  {
    private String message;
    private final String originalMessage;

    public ClientChatEvent(String message) {
        this.setMessage(message);
        this.originalMessage = Strings.nullToEmpty(message);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = Strings.nullToEmpty(message);
    }

    public String getOriginalMessage() {
        return this.originalMessage;
    }
}
