package Game.Sense.client.feature.impl.misc;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.packet.EventReceivePacket;
import Game.Sense.client.event.events.impl.player.EventPreMotion;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.notification.NotificationMode;
import Game.Sense.client.ui.notification.NotificationRenderer;
import Game.Sense.client.ui.settings.impl.NumberSetting;
import Game.Sense.client.utils.math.MathematicHelper;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

public class PlayerTracker extends Feature {
    public NumberSetting radius = new NumberSetting("Radius", 1000, 100, 5000, 10, () -> true);

    public PlayerTracker() {
        super("PlayerTracker", "Поиск игроков.", FeatureCategory.Misc);
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
