package Game.Sense.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.GameSense;
import Game.Sense.client.command.CommandAbstract;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.utils.other.ChatUtils;

public class PanicCommand extends CommandAbstract {

    public PanicCommand() {
        super("panic", "Disabled all modules", ".panic", "panic");
    }

    @Override
    public void execute(String... args) {
        if (args[0].equalsIgnoreCase("panic")) {
            for (Feature feature : GameSense.instance.featureManager.getAllFeatures()) {
                if (feature.isEnabled()) {
                    feature.toggle();
                }
            }
            ChatUtils.addChatMessage(ChatFormatting.GREEN + "Успешно " + ChatFormatting.RED + "выключенны " + ChatFormatting.WHITE + "все модули!");
        }
    }
}
