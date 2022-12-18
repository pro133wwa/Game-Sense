package Game.Sense.client.feature.impl.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.packet.EventReceivePacket;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.notification.NotificationMode;
import Game.Sense.client.ui.notification.NotificationRenderer;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerListItem;

public class StaffAlert extends Feature {
    private boolean isJoined;

    public StaffAlert() {
        super("StaffAlert", FeatureCategory.Player);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        SPacketPlayerListItem packetPlayInPlayerListItem;
        if (event.getPacket() instanceof SPacketPlayerListItem && (packetPlayInPlayerListItem = (SPacketPlayerListItem) event.getPacket()).getAction() == SPacketPlayerListItem.Action.UPDATE_LATENCY) {
            this.isJoined = true;
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        for (EntityPlayer staffPlayer : GuiPlayerTabOverlay.getPlayers()) {
            if (staffPlayer == null || staffPlayer == mc.player || !staffPlayer.getDisplayName().getUnformattedText().contains("HELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ST.HELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("MODER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ST.MODER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ADMIN") && !staffPlayer.getDisplayName().getUnformattedText().contains("�����") && !staffPlayer.getDisplayName().getUnformattedText().contains("������") && !staffPlayer.getDisplayName().getUnformattedText().contains("�����") || staffPlayer.ticksExisted >= 10 || !this.isJoined)
                continue;
            ChatUtils.addChatMessage(ChatFormatting.WHITE + "������������� " + ChatFormatting.RESET + staffPlayer.getDisplayName().getUnformattedText() + ChatFormatting.WHITE + " ����� �� ������ / ����� �� ������");
            NotificationRenderer.queue("�6Staff Alert", ChatFormatting.WHITE + "������������� " + ChatFormatting.RESET + staffPlayer.getDisplayName().getUnformattedText() + ChatFormatting.WHITE + " ����� �� ������ / ����� �� ������", 5, NotificationMode.WARNING);
            this.isJoined = false;
        }
    }
}
