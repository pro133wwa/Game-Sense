package Game.Sense.client.mine.cmd.impl;

import Game.Sense.client.mine.cmd.CommandAbstract;
import Game.Sense.client.Helper.Utility.other.ChatUtils;

import com.mojang.realmsclient.gui.ChatFormatting;

public class TpPlayer
        extends CommandAbstract {
    public TpPlayer() {
        super("tp", "tp", "\u00a76.tpplayer player", "tp");
    }

    @Override
    public void execute(String... args) {
        if (args.length == 1) {
            if (args[0].equals("tp")) {
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
