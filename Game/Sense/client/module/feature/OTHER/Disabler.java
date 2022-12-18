package Game.Sense.client.module.feature.OTHER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
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
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

public class Disabler extends Module {
    public ListSetting disablerMode = new ListSetting("Disabler Mode", "Old Matrix", () -> true, "Old Matrix", "Storm Movement");
    public static MultipleBoolSetting disablerSettings = new MultipleBoolSetting("Addons", new BooleanSetting("Flight", true), new BooleanSetting("Strafe"), new BooleanSetting("HighJump", true));
    public final NumberSetting disablerspeed = new NumberSetting("Disabler Speed", 0.2F, 0.01f, 0.3f, 0.01F, () -> disablerMode.currentMode.equals("Elytra"));
    public BooleanSetting resetMotion = new BooleanSetting("Reset Motion", "���� ��� ������ �� �� , ��� ������ ����� ������, �� ��� ��������", true, () -> disablerMode.currentMode.equals("Elytra"));
    public TimerHelper timerHelper = new TimerHelper();
    static boolean oa = false;

    public Disabler() {
        super("Disabler", "��������� ����������� ��������� �� ���", ModuleCategory.OTHER);
        addSettings();
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setSuffix(disablerMode.currentMode);
        if (disablerMode.currentMode.equals("Matrix Old")) {
            if (mc.player.ticksExisted % 3 == 0) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
        } else if (disablerMode.currentMode.equals("Elytra")) {

            InvenotryUtil.swapElytraToChestplate();
            if (mc.player.ticksExisted % 6 == 0)
                InvenotryUtil.disabler(InvenotryUtil.getSlotWithElytra());

            if (disablerSettings.getSetting("Flight").getBoolValue()) {
                MovementUtils.setMotion(MovementUtils.getSpeed() * 1 + (disablerspeed.getNumberValue()));

                mc.player.motionY = 0.39f;
                if (mc.player.ticksExisted % 5 == 0 && !mc.player.onGround && resetMotion.getBoolValue()) {
                    mc.player.motionY -= 0.5f;
                }
            }
            if (disablerSettings.getSetting("HighJump").getBoolValue()) {
                mc.player.motionY += 0.5f;
                if (mc.player.ticksExisted % 5 == 0 && !mc.player.onGround) {
                    mc.player.motionY -= 0.5f;
                }
            }
            if (disablerSettings.getSetting("Strafe").getBoolValue()) {
                MovementUtils.setMotion(MovementUtils.getSpeed() * 1 + (disablerspeed.getNumberValue()));

                if (mc.player.onGround && !mc.gameSettings.keyBindJump.pressed) {
                    mc.player.jump();
                    MovementUtils.strafe();
                }
            }
            int eIndex = -1;
            int elytraCount = 0;

            for (int i = 0; i < 45; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                    eIndex = i;
                }

                if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA) {
                    elytraCount++;
                }
            }
            if (elytraCount == 0 && eIndex == -1) {
                if (mc.player.getHeldItemOffhand().getItem() != Items.ELYTRA) {
                    NotificationRenderer.queue("�6Disabler Exploit", "�c�������� ������ � ���������!", 6, NotificationMode.WARNING);
                    toggle();
                }
            }
        }
    }

    @EventTarget
    public void on(EventSendPacket event) {
        if (disablerMode.currentMode.equals("Elytra")) {
            if (event.getPacket() instanceof CPacketPlayer) {
                CPacketPlayer cPacketPlayer = (CPacketPlayer) event.getPacket();
                cPacketPlayer.onGround = false;
            }
        }
    }

    @EventTarget
    public void onPreUpdate(EventPreMotion event) {
        if (disablerMode.currentMode.equals("Storm Movement")) {
            event.setOnGround(false);
        }
    }
}
