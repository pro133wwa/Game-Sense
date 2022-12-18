package Game.Sense.client.feature.impl.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.GameSense;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.input.EventMouse;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.files.impl.FriendConfig;
import Game.Sense.client.friend.Friend;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.entity.EntityLivingBase;

public class MiddleClickFriend extends Feature {


    public MiddleClickFriend() {
        super("MiddleClickFriend", "Добавляет игрока в френд лист при нажатии на кнопку мыши", FeatureCategory.Misc);
    }

    @EventTarget
    public void onMouseEvent(EventMouse event) {
        if (event.getKey() == 2 && mc.pointedEntity instanceof EntityLivingBase) {
            if (GameSense.instance.friendManager.getFriends().stream().anyMatch(friend -> friend.getName().equals(mc.pointedEntity.getName()))) {
                GameSense.instance.friendManager.getFriends().remove(GameSense.instance.friendManager.getFriend(mc.pointedEntity.getName()));
                ChatUtils.addChatMessage(ChatFormatting.RED + "Removed " + ChatFormatting.RESET + "'" + mc.pointedEntity.getName() + "'" + " as Friend!");
            } else {
                GameSense.instance.friendManager.addFriend(new Friend(mc.pointedEntity.getName()));
                try {
                    GameSense.instance.fileManager.getFile(FriendConfig.class).saveFile();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                ChatUtils.addChatMessage(ChatFormatting.GREEN + "Added " + ChatFormatting.RESET + "'" + mc.pointedEntity.getName() + "'" + " as Friend!");
            }
        }
    }
}