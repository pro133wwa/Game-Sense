package Game.Sense.client.mine.cmd.impl;

import Game.Sense.client.mine.cmd.CommandAbstract;
import Game.Sense.client.Helper.Utility.other.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.concurrent.TimeUnit;


public class ClipCommand
        extends CommandAbstract {
    Minecraft mc = Minecraft.getMinecraft();

    public ClipCommand() {
        super("clip", "clip | vclip | hclip | random", "\u00a76.clip | hclip | vclip | eclip | random + | - | bedrock  | <value> | up  | down", "clip", "vclip", "eclip", "hclip", "random", "VertClip");
    }

    @Override
    public void execute(String... args) {
        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("vclip")) {
                try {

                    ChatUtils.addChatMessage("vclipped " + Double.valueOf(args[1]) + " blocks.");
                    for (int i = 0; i < 10; i++) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));

                    }
                    for (int i = 0; i < 10; i++) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + (double) (Double.parseDouble(args[1])), mc.player.posZ, false));
                    }
                    mc.player.setPosition(mc.player.posX, mc.player.posY + Double.parseDouble(args[1]), mc.player.posZ);

                } catch (Exception ignored) {
                }
            }
            if (args[0].equalsIgnoreCase("hclip")) {
                try {
                    ChatUtils.addChatMessage("hclipped " + Double.valueOf(args[1]) + " blocks.");
                    float f = mc.player.rotationYaw * 0.017453292F;
                    double speed = Double.valueOf(args[1]);
                    double x = -(MathHelper.sin(f) * speed);
                    double z = MathHelper.cos(f) * speed;
                    mc.player.setPosition(mc.player.posX + x, mc.player.posY, mc.player.posZ + z);
                } catch (Exception ignored) {
                }
            }
        } else {
            ChatUtils.addChatMessage(getUsage());
        }
    }
}


