package Game.Sense.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.GameSense;
import Game.Sense.client.command.CommandAbstract;
import Game.Sense.client.command.macro.Macro;
import Game.Sense.client.files.impl.MacroConfig;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class MacroCommand extends CommandAbstract {

    public MacroCommand() {
        super("macros", "macro", TextFormatting.GRAY + ".macro" + ChatFormatting.WHITE + " add " + "§3<key> /home_home | §7.macro" + ChatFormatting.WHITE + " remove " + "§3<key> |" +  TextFormatting.GRAY + " .macro" + ChatFormatting.WHITE + " clear " + "§3| §7.macro" + ChatFormatting.WHITE + " list", "§7.macro" + ChatFormatting.WHITE + " add " + "§3<key> </home_home> | §7.macro" + ChatFormatting.WHITE + " remove " + "§3<key> | §7.macro" + ChatFormatting.WHITE + " clear " + "| §7.macro" + ChatFormatting.WHITE + " list", "macro");
    }

    @Override
    public void execute(String... arguments) {
        try {
            if (arguments.length > 1) {
                if (arguments[0].equals("macro")) {
                    if (arguments[1].equals("add")) {
                        StringBuilder command = new StringBuilder();
                        for (int i = 3; i < arguments.length; ++i) {
                            command.append(arguments[i]).append(" ");
                        }
                        GameSense.instance.macroManager.addMacro(new Macro(Keyboard.getKeyIndex(arguments[2].toUpperCase()), command.toString()));
                        GameSense.instance.fileManager.getFile(MacroConfig.class).saveFile();
                        ChatUtils.addChatMessage(ChatFormatting.GREEN + "Added" + " macros for key" + ChatFormatting.RED + " \"" + arguments[2].toUpperCase() + ChatFormatting.RED + "\" " + ChatFormatting.WHITE + "with value " + ChatFormatting.RED + command);
                    }
                    if (arguments[1].equals("clear")) {
                        if (GameSense.instance.macroManager.getMacros().isEmpty()) {
                            ChatUtils.addChatMessage(ChatFormatting.RED + "Your macros list is empty!");
                            return;
                        }
                        GameSense.instance.macroManager.getMacros().clear();
                        ChatUtils.addChatMessage(ChatFormatting.GREEN + "Your macros list " + ChatFormatting.WHITE + " successfully cleared!");
                    }
                    if (arguments[1].equals("remove")) {
                        GameSense.instance.macroManager.deleteMacroByKey(Keyboard.getKeyIndex(arguments[2].toUpperCase()));
                        ChatUtils.addChatMessage(ChatFormatting.GREEN + "Macro " + ChatFormatting.WHITE + "was deleted from key " + ChatFormatting.RED + "\"" + arguments[2].toUpperCase() + "\"");
                    }
                    if (arguments[1].equals("list")) {
                        if (GameSense.instance.macroManager.getMacros().isEmpty()) {
                            ChatUtils.addChatMessage(ChatFormatting.RED + "Your macros list is empty!");
                            return;
                        }
                        GameSense.instance.macroManager.getMacros().forEach(macro -> ChatUtils.addChatMessage(ChatFormatting.GREEN + "Macros list: " + ChatFormatting.WHITE + "Macros Name: " + ChatFormatting.RED + macro.getValue() + ChatFormatting.WHITE + ", Macro Bind: " + ChatFormatting.RED + Keyboard.getKeyName(macro.getKey())));
                    }
                }
            } else {
                ChatUtils.addChatMessage(getUsage());
            }
        } catch (Exception ignored) {

        }
    }
}
