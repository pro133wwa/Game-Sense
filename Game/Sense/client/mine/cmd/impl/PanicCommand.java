package Game.Sense.client.mine.cmd.impl;

import Game.Sense.client.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.GameSense;
import Game.Sense.client.mine.cmd.CommandAbstract;
import Game.Sense.client.Helper.Utility.other.ChatUtils;

public class PanicCommand extends CommandAbstract {

    public PanicCommand() {
        super("panic", "Disabled all modules", ".panic", "panic");
    }

    @Override
    public void execute(String... args) {
        if (args[0].equalsIgnoreCase("panic")) {
            for (Module feature : GameSense.instance.featureManager.getAllFeatures()) {
                if (feature.isEnabled()) {
                    feature.toggle();
                }
            }
            ChatUtils.addChatMessage(ChatFormatting.GREEN + "Успешно " + ChatFormatting.RED + "выключенны " + ChatFormatting.WHITE + "все модули!");
        }
    }
}
