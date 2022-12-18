package Game.Sense.client.feature.impl.hud;

import Game.Sense.client.GameSense;
import Game.Sense.client.draggable.component.impl.DraggableStaff;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.render.EventRender2D;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;

import Game.Sense.client.utils.render.*;

import java.awt.*;

import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.event.events.impl.packet.EventReceivePacket;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerListItem;


public class StaffList extends Feature {
    public StaffList() {
        super("StaffList", "отображение листа стафов", FeatureCategory.Hud);
    }
    private boolean isJoined;

    public float scale = 2;

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
            if (staffPlayer == null || staffPlayer == mc.player || !staffPlayer.getDisplayName().getUnformattedText().contains("HELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ST.HELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("MODER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ST.MODER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ADMIN") && !staffPlayer.getDisplayName().getUnformattedText().contains("Админ") && !staffPlayer.getDisplayName().getUnformattedText().contains("Хелпер") && !staffPlayer.getDisplayName().getUnformattedText().contains("Модер") || staffPlayer.ticksExisted >= 10 || !this.isJoined)
                continue;
            DraggableStaff dci = (DraggableStaff) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableStaff.class);
            mc.rubik_18.drawCenteredString(ChatFormatting.WHITE + staffPlayer.getDisplayName().getUnformattedText(),dci.getX()+20,dci.getY()+8,Color.WHITE.getRGB());
            this.isJoined = false;
        }
    }
    @EventTarget
    public void onRender(EventRender2D eventRender2D) {
        DraggableStaff dci = (DraggableStaff) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableStaff.class);
        dci.setWidth(60);
        dci.setHeight(60);
        int x = (int) dci.getX();
        int y = (int)dci.getY();
        GLUtils.INSTANCE.rescale(this.scale);
        Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
        Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());
        Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0,onecolor,twocolor);
        Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90,onecolor,twocolor );
        Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor,twocolor);
        Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor,twocolor);


        RoundedUtil.drawGradientRound(x,y,60,60, 6, ColorUtils2.applyOpacity(gradientColor4, .85f), gradientColor1, gradientColor3, gradientColor2);
        RenderUtils.drawRect2(dci.getX(), dci.getY() + 16,dci.getWidth(),0.4f,Color.white.getRGB());
        mc.rubik_18.drawCenteredString("Staff list",dci.getX()  +20, dci.getY() + 6,Color.white.getRGB());

        GLUtils.INSTANCE.rescaleMC();

    }

}
