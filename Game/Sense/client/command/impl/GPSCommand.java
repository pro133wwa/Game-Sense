package Game.Sense.client.command.impl;

import Game.Sense.client.command.CommandAbstract;
import Game.Sense.client.utils.other.ChatUtils;

public class GPSCommand extends CommandAbstract {
    public static int x, z;
    public static String mode;

    public GPSCommand() {
        super("gps", "gps coommand", "�bUsage: �6.gps <x> <z> <off/on>", "gps");
    }

    @Override
    public void execute(String... args) {
        if (args.length < 4) {
            ChatUtils.addChatMessage(this.getUsage());
        } else {
            mode = args[3].toLowerCase();
            if (mode.equalsIgnoreCase("on")) {
                x = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);
            } else if (mode.equalsIgnoreCase("off")) {
                x = 0;
                z = 0;
            }
        }
    }
}
