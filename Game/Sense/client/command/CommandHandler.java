package Game.Sense.client.command;


import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.packet.EventMessage;

public class CommandHandler {

    public CommandManager commandManager;

    public CommandHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @EventTarget
    public void onMessage(EventMessage event) {
        String msg = event.getMessage();
        if (msg.length() > 0 && msg.startsWith(".")) {
            event.setCancelled(this.commandManager.execute(msg));
        }
    }
}
