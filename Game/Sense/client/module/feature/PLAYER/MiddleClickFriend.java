package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.input.EventMouse;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.mine.SRC.impl.FriendConfig;
import Game.Sense.client.UI.UwU.NAXNADO.Friend;
import Game.Sense.client.Helper.Utility.other.ChatUtils;
import net.minecraft.entity.EntityLivingBase;

public class MiddleClickFriend extends Module {


    public MiddleClickFriend() {
        super("MiddleClickFriend", "Добавляет игрока в френд лист при нажатии на кнопку мыши", ModuleCategory.PLAYER);
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