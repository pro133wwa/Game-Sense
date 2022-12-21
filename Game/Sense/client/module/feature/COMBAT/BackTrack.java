package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.Helper.Utility.Helper;
import Game.Sense.client.Helper.Utility.render.RenderUtils;
import Game.Sense.client.Helper.events.impl.packet.EventAttackSilent;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.Helper.events.impl.render.EventRender3D;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BackTrack extends Module {
    private boolean isActive;
    public static NumberSetting health;
    public static List<Vec3d> pastPositions = new ArrayList<>();
    public static List<Vec3d> forwardPositions = new ArrayList<>();
    public static List<Vec3d> positions = new ArrayList<>();
    private final Deque<Packet<?>> packets = new ArrayDeque<>();
    private int ticks;
    private final NumberSetting amount = new NumberSetting("????", 20, 1, 100, 1, () -> true);
    private final NumberSetting forward = new NumberSetting("Forward", 20, 1, 100, 1, () -> true);

    public BackTrack() {
        super("BackTrack", "new", ModuleCategory.COMBAT);
        health = new NumberSetting("????????", 15, 1, 20, 1, () -> true);
        addSettings( amount);
    }

    public void onPreMotion(final EventPreMotion event) {
        if (Helper.mc.player.ticksExisted < 5) {
            onDisable();
            return;
        }

        if (KillAura.target == null) return;

        pastPositions.add(new Vec3d(KillAura.target.posX, KillAura.target.posY, KillAura.target.posZ));

        final double deltaX = (KillAura.target.posX - KillAura.target.lastTickPosX) * 2;
        final double deltaZ = (KillAura.target.posZ - KillAura.target.lastTickPosZ) * 2;

        forwardPositions.clear();
        int i = 0;
        while (forward.getNumberValue() > forwardPositions.size()) {
            i++;
            forwardPositions.add(new Vec3d(KillAura.target.posX + deltaX * i, KillAura.target.posY, KillAura.target.posZ + deltaZ * i));
        }

        while (pastPositions.size() > (int) amount.getNumberValue()) {
            pastPositions.remove(0);
        }

        positions.clear();
        positions.addAll(forwardPositions);
        positions.addAll(pastPositions);

        ticks++;
    }

    public void onRender3DEvent(final EventRender3D event) {
        if (KillAura.target != null && !positions.isEmpty()) RenderUtils.renderBreadCrumbs(positions);
    }

    public void onAttackEvent(final EventAttackSilent event) {
        if (event.getTargetEntity() instanceof EntityPlayer) KillAura.target = (EntityLivingBase) event.getTargetEntity();
        ticks = 0;
    }

    public void onDisable() {
        KillAura.target = null;
        positions.clear();
        pastPositions.clear();
        forwardPositions.clear();
        packets.clear();
    }

    public void onPacketSend(final EventSendPacket event) {

        if (KillAura.target == null) return;

        Packet p = event.getPacket();


        packets.add(p);
        event.setCancelled(true);


        if ((int) amount.getNumberValue() <= pastPositions.size()) {

            for (final Packet thisPacket : packets) Helper.sendPacket(thisPacket);

            pastPositions.clear();
            packets.clear();
        }

    }
}
