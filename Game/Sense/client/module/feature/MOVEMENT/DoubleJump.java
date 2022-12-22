package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

public class DoubleJump extends Module
{
    public DoubleJump() {
        super("DoubleJump", "?? ?????? ??????? ??????? ???????", ModuleCategory.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(final EventPreMotion eventPreMotion) {
        final float ex2 = 1.1f;
        final float ex3 = 1.0f;
        if ((this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ)).getBlock()) || this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ)).getBlock()) || this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX - ex2, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ - ex2)).getBlock()) || this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX + ex2, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ + ex2)).getBlock()) || this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX - ex2, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ + ex2)).getBlock()) || this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX + ex2, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ - ex2)).getBlock()) || this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX + ex2, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ)).getBlock()) || this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX - ex2, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ)).getBlock()) || this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ + ex2)).getBlock()) || this.isBlockValid(DoubleJump.mc.world.getBlockState(new BlockPos(DoubleJump.mc.player.posX, DoubleJump.mc.player.posY - ex3, DoubleJump.mc.player.posZ - ex2)).getBlock())) && DoubleJump.mc.player.ticksExisted % 2 == 0 && DoubleJump.mc.gameSettings.keyBindJump.isKeyDown()) {
            DoubleJump.mc.player.jumpTicks = 0;
            DoubleJump.mc.player.fallDistance = 0.08f;
            eventPreMotion.setOnGround(true);
            DoubleJump.mc.player.onGround = true;
        }
    }

    public boolean isBlockValid(final Block block) {
        return block != Blocks.AIR && !Arrays.asList(6, 27, 28, 31, 32, 37, 38, 39, 40, 44, 77, 143, 175).contains(Block.getIdFromBlock(block));
    }
}

