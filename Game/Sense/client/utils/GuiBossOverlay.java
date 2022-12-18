package Game.Sense.client.utils;

import Game.Sense.client.GameSense;
import Game.Sense.client.event.EventManager;
import Game.Sense.client.event.events.impl.render.EventBossBar;
import Game.Sense.client.feature.impl.player.NoOverlay;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.BossInfoClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;

import java.util.Map;
import java.util.UUID;

public class GuiBossOverlay extends Gui {
    public static boolean pot = false;
    private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
    private final Minecraft client;
    public static final Map<UUID, BossInfoClient> mapBossInfos = Maps.<UUID, BossInfoClient>newLinkedHashMap();

    public GuiBossOverlay(Minecraft clientIn) {
        this.client = clientIn;
    }

    public void renderBossHealth() {
        if (GameSense.instance.featureManager.getFeature(NoOverlay.class).isEnabled() && NoOverlay.noBossBar.getBoolValue()) {
            return;
        }
        if (!this.mapBossInfos.isEmpty()) {
            ScaledResolution scaledresolution = new ScaledResolution(this.client);
            int i = scaledresolution.getScaledWidth();
            int j = 12;

            for (BossInfoClient bossinfoclient : this.mapBossInfos.values()) {
                int k = i / 2 - 91;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.client.getTextureManager().bindTexture(GUI_BARS_TEXTURES);
                this.render(k, j, bossinfoclient);
                String s = bossinfoclient.getName().getFormattedText();

                EventBossBar event = new EventBossBar(s);
                EventManager.call(event);
                this.client.fontRendererObj.drawStringWithShadow(s, (float) (i / 2 - this.client.fontRendererObj.getStringWidth(s) / 2), (float) (j - 9), 16777215);
                j += 10 + this.client.fontRendererObj.FONT_HEIGHT;

                if (j >= scaledresolution.getScaledHeight() / 3) {
                    break;
                }
            }
        }
    }

    private void render(int x, int y, BossInfo info) {
        this.drawTexturedModalRect(x, y, 0, info.getColor().ordinal() * 5 * 2, 182, 5);

        if (info.getOverlay() != BossInfo.Overlay.PROGRESS) {
            this.drawTexturedModalRect(x, y, 0, 80 + (info.getOverlay().ordinal() - 1) * 5 * 2, 182, 5);
        }

        int i = (int) (info.getPercent() * 183.0F);

        if (i > 0) {
            this.drawTexturedModalRect(x, y, 0, info.getColor().ordinal() * 5 * 2 + 5, i, 5);

            if (info.getOverlay() != BossInfo.Overlay.PROGRESS) {
                this.drawTexturedModalRect(x, y, 0, 80 + (info.getOverlay().ordinal() - 1) * 5 * 2 + 5, i, 5);
            }
        }
    }

    public void read(SPacketUpdateBossInfo packetIn) {
        if (packetIn.getOperation() == SPacketUpdateBossInfo.Operation.ADD) {
            this.mapBossInfos.put(packetIn.getUniqueId(), new BossInfoClient(packetIn));
        } else if (packetIn.getOperation() == SPacketUpdateBossInfo.Operation.REMOVE) {
            this.mapBossInfos.remove(packetIn.getUniqueId());
        } else {
            ((BossInfoClient) this.mapBossInfos.get(packetIn.getUniqueId())).updateFromPacket(packetIn);
        }
    }

    public void clearBossInfos() {
        this.mapBossInfos.clear();
    }

    public boolean shouldPlayEndBossMusic() {
        if (!this.mapBossInfos.isEmpty()) {
            for (BossInfo bossinfo : this.mapBossInfos.values()) {
                if (bossinfo.shouldPlayEndBossMusic()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean shouldDarkenSky() {
        if (!this.mapBossInfos.isEmpty()) {
            for (BossInfo bossinfo : this.mapBossInfos.values()) {
                if (bossinfo.shouldDarkenSky()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean shouldCreateFog() {
        if (!this.mapBossInfos.isEmpty()) {
            for (BossInfo bossinfo : this.mapBossInfos.values()) {
                if (bossinfo.shouldCreateFog()) {
                    return true;
                }
            }
        }

        return false;
    }
}

