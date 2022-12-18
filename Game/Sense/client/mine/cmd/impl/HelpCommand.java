package Game.Sense.client.mine.cmd.impl;


import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.mine.cmd.CommandAbstract;
import Game.Sense.client.Helper.Utility.other.ChatUtils;

public class HelpCommand
        extends CommandAbstract {
    public HelpCommand() {
        super("help", "help", ".help", "help");
    }

    @Override
    public void execute(String... args) {
        if (args.length == 1) {
            if (args[0].equals("help")) {
                ChatUtils.addChatMessage(ChatFormatting.RED + "All Commands:");
                ChatUtils.addChatMessage(ChatFormatting.WHITE + "§6.bind");
                ChatUtils.addChatMessage(ChatFormatting.WHITE + "§6.macro");
                ChatUtils.addChatMessage(ChatFormatting.WHITE + "§6.gps §7<x> , <y> , <z>");
                ChatUtils.addChatMessage(ChatFormatting.WHITE + "§6.parser");
                ChatUtils.addChatMessage(ChatFormatting.WHITE + "&6 .vclip | .hclip &7 <10>");
                ChatUtils.addChatMessage(ChatFormatting.WHITE + "§6.fakename &7 <name>");
                ChatUtils.addChatMessage(ChatFormatting.WHITE + "§6.friend");
                ChatUtils.addChatMessage(ChatFormatting.WHITE + "§6.cfg");
                ChatUtils.addChatMessage(ChatFormatting.RED + "§6.tp §7<x> , <y> , <z>");
            }
        } else {
            ChatUtils.addChatMessage(this.getUsage());
        }
    }
}
