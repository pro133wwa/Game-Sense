package Game.Sense.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.GameSense;
import Game.Sense.client.command.CommandAbstract;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.ui.notification.NotificationMode;
import Game.Sense.client.ui.notification.NotificationRenderer;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class BindCommand extends CommandAbstract {

    public BindCommand() {
        super("bind", "bind", "§6.bind" + ChatFormatting.RED + " add " + "§7<name> §7<key> " + TextFormatting.RED + "\n" + "[" + TextFormatting.WHITE + "SOKOL CLIENT" + TextFormatting.GRAY + "] " + "§6.bind " + ChatFormatting.RED + "remove " + "§7<name> §7<key> " + "\n" + "[" + TextFormatting.WHITE + "SOKOL CLIENT" + TextFormatting.GRAY + "] " + "§6.bind " + ChatFormatting.RED + "list ", "bind");
    }

    @Override
    public void execute(String... arguments) {
        try {
            if (arguments.length == 4) {
                String moduleName = arguments[2];
                String bind = arguments[3].toUpperCase();
                Feature feature = GameSense.instance.featureManager.getFeature(moduleName);
                if (arguments[0].equalsIgnoreCase("bind") && arguments[1].equalsIgnoreCase("add")) {
                    feature.setBind(Keyboard.getKeyIndex(bind));
                    ChatUtils.addChatMessage(ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " was set on key " + ChatFormatting.RED + "\"" + bind + "\"");
                    NotificationRenderer.queue("Bind Manager", ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " was set on key " + ChatFormatting.RED + "\"" + bind + "\"", 1, NotificationMode.SUCCESS);

                } else if (arguments[0].equalsIgnoreCase("bind") && arguments[1].equalsIgnoreCase("remove")) {
                    feature.setBind(0);
                    ChatUtils.addChatMessage(ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " bind was deleted from key " + ChatFormatting.RED + "\"" + bind + "\"");
                    NotificationRenderer.queue("Bind Manager", ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " bind was deleted from key " + ChatFormatting.RED + "\"" + bind + "\"", 1, NotificationMode.SUCCESS);
                }
            } else if (arguments.length == 2) {
                if (arguments[0].equalsIgnoreCase("bind") && arguments[1].equalsIgnoreCase("list")) {
                    for (Feature f : GameSense.instance.featureManager.getAllFeatures()) {
                        if (f.getBind() != 0) {
                            ChatUtils.addChatMessage(f.getLabel() + " : " + Keyboard.getKeyName(f.getBind()));
                        }

                    }
                } else {
                    ChatUtils.addChatMessage(this.getUsage());
                }

            } else if (arguments[0].equalsIgnoreCase("bind")) {
                ChatUtils.addChatMessage(this.getUsage());
            }

        } catch (Exception ignored) {

        }
    }
}