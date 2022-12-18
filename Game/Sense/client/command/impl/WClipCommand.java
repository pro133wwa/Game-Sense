package Game.Sense.client.command.impl;


import Game.Sense.client.command.CommandAbstract;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.concurrent.TimeUnit;


public class WClipCommand
        extends CommandAbstract {
    Minecraft mc = Minecraft.getMinecraft();

    public WClipCommand() {
        super("clip", "", "", "wclip");
    }

    @Override
    public void execute(String ... args2) {
        if (args2.length > 1) {
            double b;
            double a;
            float y;
            float oldPos;
            if (args2.length > 1) {
                if (args2[0].equalsIgnoreCase("wclip")) {
                    try {

                        //ChatUtils.addChatMessage("vclipped " + Double.valueOf(args2[1]) + " blocks.");
                        for (int i = 0; i < 10; i++) {
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));

                        }
                        for (int i = 0; i < 10; i++) {
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + (double) (Double.parseDouble(args2[1])), mc.player.posZ, false));
                        }
                        mc.player.setPosition(mc.player.posX, mc.player.posY + Double.parseDouble(args2[1]), mc.player.posZ);

                    } catch (Exception ignored) {

                    }
                }
            }
            if (args2[0].equalsIgnoreCase("VertClip")) {
                oldPos = this.mc.player.getPosition().getY();
                y = 0.0f;
                try {
                    if (args2[1].equals("downn")) {
                        this.mc.player.setPositionAndUpdate(this.mc.player.posX, -2.0, this.mc.player.posZ);

                    }
                    if (args2[1].equals("+")) {
                        int i = 0;
                        while ((float)i < Math.max(y / 1000.0f, 3.0f)) {
                            this.mc.player.connection.sendPacket(new CPacketPlayer(this.mc.player.onGround));
                            ++i;
                        }
                        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + Double.parseDouble(args2[2]), this.mc.player.posZ, false));
                        this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + Double.parseDouble(args2[2]), this.mc.player.posZ);
                    }
                    if (args2[1].equals("-")) {
                        int i = 0;
                        while ((float)i < Math.max(y / 1000.0f, 3.0f)) {
                            this.mc.player.connection.sendPacket(new CPacketPlayer(this.mc.player.onGround));
                            ++i;
                        }
                        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + Double.parseDouble(args2[2]), this.mc.player.posZ, false));
                        this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY - Double.parseDouble(args2[2]), this.mc.player.posZ);
                    }
                    if (NumberUtils.isNumber(args2[1])) {
                        int i = 0;
                        while ((double)i < (double)oldPos + Double.parseDouble(args2[1])) {
                            this.mc.player.connection.sendPacket(new CPacketPlayer(this.mc.player.onGround));
                            this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, oldPos + (float)i, this.mc.player.posZ, false));
                            this.mc.player.setPosition(this.mc.player.posX, oldPos + (float)i, this.mc.player.posZ);
                            ++i;
                        }
                    }
                    if (args2[1].equals("down")) {
                        for (int i = 0; i < 255; ++i) {
                            if (this.mc.world.getBlockState(new BlockPos(this.mc.player).add(0, -i, 0)) == Blocks.AIR.getDefaultState()) {
                                y = -i - 1;
                                break;
                            }
                            if (this.mc.world.getBlockState(new BlockPos(this.mc.player).add(0, -i, 0)) != Blocks.BEDROCK.getDefaultState()) continue;
                            ChatUtils.addChatMessage((Object)((Object)TextFormatting.RED) + "\u0422\u0443\u0442 \u043c\u043e\u0436\u043d\u043e \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u0442\u043e\u043b\u044c\u043a\u043e \u043f\u043e\u0434 \u0431\u0435\u0434\u0440\u043e\u043a.");
                            return;
                        }
                    }
                    if (args2[1].equals("up")) {
                        for (int i = 3; i < 50; ++i) {
                            if (this.mc.world.getBlockState(new BlockPos(this.mc.player).add(0, i, 0)) != Blocks.AIR.getDefaultState()) continue;
                            y = i + 1;
                            break;
                        }
                        int ii = 0;
                        while ((double)ii < 0.5) {
                            int finalIi = ii++;
                            new Thread(() -> {
                                try {
                                    TimeUnit.MILLISECONDS.sleep(1L);
                                    this.mc.player.setPosition(this.mc.player.getPosition().getX(), this.mc.player.getPosition().getY() + finalIi, this.mc.player.getPosition().getZ());
                                }
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                    }
                    if (args2[1].equals("down") || args2[1].equals("up")) {
                        // empty if block
                    }
                    if (args2[1].equals("bedrock")) {
                        int i = 0;
                        while ((float)i < Math.max(y / 1000.0f, 3.0f)) {
                            this.mc.player.connection.sendPacket(new CPacketPlayer(this.mc.player.onGround));
                            ++i;
                        }
                        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, -4.0, this.mc.player.posZ, false));
                        this.mc.player.setPosition(this.mc.player.posX, -4.0, this.mc.player.posZ);
                    }
                }
                catch (Exception i) {
                    // empty catch block
                }
            }
            if (args2[0].equalsIgnoreCase("hclip")) {
                double x = this.mc.player.posX;
                double y2 = this.mc.player.posY;
                double z = this.mc.player.posZ;
                double yaw = (double)this.mc.player.rotationYaw * 0.017453292;
                try {

                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.mc.player.capabilities.setFlySpeed(0.05f);
        } else {
            ChatUtils.addChatMessage(this.getUsage());
        }
    }
}


