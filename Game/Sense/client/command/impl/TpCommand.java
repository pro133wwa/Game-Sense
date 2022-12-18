package Game.Sense.client.command.impl;

import Game.Sense.client.command.CommandAbstract;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class TpCommand
        extends CommandAbstract {
    /* synthetic */ Minecraft mc;

    public /* synthetic */ TpCommand() {
        super("tp", "tp", "\u00a76.tp x y z", "tp");
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public /* synthetic */ void execute(String ... args) {
        if (args.length > 1) {
            float endX = (float)Double.parseDouble(args[1]);
            float endY = (float)Double.parseDouble(args[2]);
            float endZ = (float)Double.parseDouble(args[3]);
            if (this.mc.player.ticksExisted % 1 == 0) {
                ChatUtils.addChatMessage("\u041f\u044b\u0442\u0430\u044e\u0441\u044c \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u043d\u0430 " + endX + " " + endY + " " + endZ);
                this.mc.player.motionY += (double)0.2f;
                this.mc.player.connection.sendPacket(new CPacketPlayer.Position((double)endX + 1.5, (double)endY + 0.1, (double)endZ - 0.5, true));
                this.mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY, endZ, false));
                this.mc.player.connection.sendPacket(new CPacketPlayer.Position((double)endX + 1.5, (double)endY + 0.1, (double)endZ - 0.5, true));
                return;
            }
            if (this.mc.player.posX == (double)endX && this.mc.player.posZ == (double)endZ) {
                ChatUtils.addChatMessage("\u00a7a\u0423\u0441\u043f\u0435\u0448\u043d\u043e\u0435 \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435!");
            }
        } else {
            ChatUtils.addChatMessage(this.getUsage());
        }
    }
}