package Game.Sense.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.GameSense;
import Game.Sense.client.command.CommandAbstract;
import Game.Sense.client.ui.config.Config;
import Game.Sense.client.ui.config.ConfigManager;
import Game.Sense.client.utils.other.ChatUtils;
import org.lwjgl.Sys;

import java.io.File;

public class ConfigCommand extends CommandAbstract {

    public ConfigCommand() {
        super("cfg", "configurations", ChatFormatting.RED + ".cfg" + ChatFormatting.WHITE + " save <name> | load <name> | delete <name> | list | create <name> | dir" + ChatFormatting.RED, "<name>", "cfg");
    }

    @Override
    public void execute(String... args) {
        try {
            if (args.length >= 2) {
                String upperCase = args[1].toUpperCase();
                if (args.length == 3) {
                    switch (upperCase) {
                        case "LOAD":
                            if (GameSense.instance.configManager.loadConfig(args[2])) {
                                ChatUtils.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "loaded config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            } else {
                                ChatUtils.addChatMessage(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "load config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            }
                            break;
                        case "SAVE":
                            if (GameSense.instance.configManager.saveConfig(args[2])) {
                                GameSense.instance.fileManager.saveFiles();
                                ChatUtils.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "saved config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                                ConfigManager.getLoadedConfigs().clear();
                                GameSense.instance.configManager.load();
                            } else {
                                ChatUtils.addChatMessage(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to save config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            }
                            break;
                        case "DELETE":
                            if (GameSense.instance.configManager.deleteConfig(args[2])) {
                                ChatUtils.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "deleted config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            } else {
                                ChatUtils.addChatMessage(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to delete config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            }
                            break;
                        case "CREATE":
                            GameSense.instance.configManager.saveConfig(args[2]);
                            ChatUtils.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "created config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            break;

                    }
                } else if (args.length == 2 && upperCase.equalsIgnoreCase("LIST")) {
                    ChatUtils.addChatMessage(ChatFormatting.GREEN + "Configs:");
                    for (Config config : GameSense.instance.configManager.getContents()) {
                        ChatUtils.addChatMessage(ChatFormatting.RED + config.getName());
                    }
                } else if (args.length == 2 && upperCase.equalsIgnoreCase("DIR")) {
                    File file = new File("C:\\RichClient\\game\\configs", "configs");
                    Sys.openURL(file.getAbsolutePath());
                }
            } else {
                ChatUtils.addChatMessage(this.getUsage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}