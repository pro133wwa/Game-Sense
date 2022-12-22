package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.Utility.Helper;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.UI.Settings.Setting;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.dop.Notif.NotificationMode;
import Game.Sense.client.UI.dop.Notif.NotificationRenderer;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.MultipleBoolSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.inventory.InvenotryUtil;
import Game.Sense.client.Helper.Utility.math.TimerHelper;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

public class Disabler extends Module {
    public ListSetting disablerMode = new ListSetting("Disabler Mode", "Storm", () -> {
        return true;
    }, new String[]{"Storm"});
    public static MultipleBoolSetting disablerSettings = new MultipleBoolSetting("Addons", new BooleanSetting[]{new BooleanSetting("Flight", true), new BooleanSetting("Strafe"), new BooleanSetting("HighJump", true)});
    public final NumberSetting disablerspeed = new NumberSetting("Disabler Speed", 0.2F, 0.01F, 0.3F, 0.01F, () -> {
        return this.disablerMode.currentMode.equals("Elytra");
    });
    public BooleanSetting resetMotion = new BooleanSetting("Reset Motion", "???? ??? ?????? ?? ?? , ??? ?????? ????? ??????, ?? ??? ????????", true, () -> {
        return this.disablerMode.currentMode.equals("Elytra");
    });
    public TimerHelper timerHelper = new TimerHelper();
    static boolean oa = false;

    public Disabler() {
        super("Disabler", "????????? ??????????? ????????? ?? ???", ModuleCategory.MOVEMENT);
        this.addSettings(new Setting[]{this.disablerMode,  this.disablerspeed, this.resetMotion});
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (this.disablerMode.currentMode.equals("Matrix Old")) {
            if (Helper.mc.player.ticksExisted % 3 == 0) {
                Helper.mc.player.connection.sendPacket(new CPacketEntityAction(Helper.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
        } else if (this.disablerMode.currentMode.equals("Elytra")) {
            InvenotryUtil.swapElytraToChestplate();
            if (Helper.mc.player.ticksExisted % 6 == 0) {
                InvenotryUtil.disabler(InvenotryUtil.getSlotWithElytra());
            }

            EntityPlayerSP var10000;
            if (disablerSettings.getSetting("Flight").getBoolValue()) {
                MovementUtils.setMotion((double)(MovementUtils.getSpeed() * 1.0F + this.disablerspeed.getNumberValue()));
                Helper.mc.player.motionY = 0.38999998569488525D;
                if (Helper.mc.player.ticksExisted % 5 == 0 && !Helper.mc.player.onGround && this.resetMotion.getBoolValue()) {
                    var10000 = Helper.mc.player;
                    var10000.motionY -= 0.5D;
                }
            }

            if (disablerSettings.getSetting("HighJump").getBoolValue()) {
                var10000 = Helper.mc.player;
                var10000.motionY += 0.5D;
                if (Helper.mc.player.ticksExisted % 5 == 0 && !Helper.mc.player.onGround) {
                    var10000 = Helper.mc.player;
                    var10000.motionY -= 0.5D;
                }
            }

            if (disablerSettings.getSetting("Strafe").getBoolValue()) {
                MovementUtils.setMotion((double)(MovementUtils.getSpeed() * 1.0F + this.disablerspeed.getNumberValue()));
                if (Helper.mc.player.onGround && !Helper.mc.gameSettings.keyBindJump.pressed) {
                    Helper.mc.player.jump();
                    MovementUtils.strafe();
                }
            }

            int eIndex = -1;
            int elytraCount = 0;

            for(int i = 0; i < 45; ++i) {
                if (Helper.mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                    eIndex = i;
                }

                if (Helper.mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA) {
                    ++elytraCount;
                }
            }

            if (elytraCount == 0 && eIndex == -1 && Helper.mc.player.getHeldItemOffhand().getItem() != Items.ELYTRA) {
                NotificationRenderer.queue("§6Disabler Exploit", "§c???????? ?????? ? ?????????!", 6, NotificationMode.WARNING);
                this.toggle();
            }
        }

    }

    @EventTarget
    public void on(EventSendPacket event) {
        if (this.disablerMode.currentMode.equals("Elytra") && event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer cPacketPlayer = (CPacketPlayer)event.getPacket();
            cPacketPlayer.onGround = false;
        }

    }

    @EventTarget
    public void onPreUpdate(EventPreMotion event) {
        if (this.disablerMode.currentMode.equals("Storm Movement")) {
            event.setOnGround(false);
        }

    }
}
