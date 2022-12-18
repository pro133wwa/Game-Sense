package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventReceivePacket;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.dop.Notif.NotificationMode;
import Game.Sense.client.UI.dop.Notif.NotificationRenderer;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.math.MathematicHelper;
import Game.Sense.client.Helper.Utility.other.ChatUtils;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

public class PlayerTracker extends Module {
    public NumberSetting radius = new NumberSetting("Radius", 1000, 100, 5000, 10, () -> true);

    public PlayerTracker() {
        super("PlayerTracker", "Поиск игроков.", ModuleCategory.OTHER);
        addSettings(radius);
    }

    @EventTarget
    public void onFind(EventPreMotion eventPreMotionUpdate) {
        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK,
                new BlockPos(MathematicHelper.getRandomInRange(-radius.getNumberValue(), radius.getNumberValue()), 0,
                        MathematicHelper.getRandomInRange(-radius.getNumberValue(), radius.getNumberValue())), EnumFacing.DOWN));
    }

    @EventTarget
    public void onFindReceive(EventReceivePacket eventReceivePacket) {
        SPacketBlockChange packetBlockChange = (SPacketBlockChange) eventReceivePacket.getPacket();
        if (eventReceivePacket.getPacket() instanceof SPacketBlockChange) {
            ChatUtils.addChatMessage(TextFormatting.WHITE + "Игрок замечен на кординатах > " + TextFormatting.RED +
                    packetBlockChange.getBlockPosition().getX() + " " + packetBlockChange.getBlockPosition().getZ());
            NotificationRenderer.queue("Player Tracker", "Игрок замечен на кординатах > " + TextFormatting.RED +
                    packetBlockChange.getBlockPosition().getX() + " " + packetBlockChange.getBlockPosition().getZ(), 2, NotificationMode.INFO);

        }
    }
}
