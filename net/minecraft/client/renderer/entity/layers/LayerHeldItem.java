// Decompiled with: CFR 0.152
// Class Version: 8
package net.minecraft.client.renderer.entity.layers;

import Game.Sense.client.GameSense;
import Game.Sense.client.module.feature.RENDER.CustomModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class LayerHeldItem
        implements LayerRenderer<EntityLivingBase> {
    protected final RenderLivingBase<?> livingEntityRenderer;

    public LayerHeldItem(RenderLivingBase<?> livingEntityRendererIn) {
        this.livingEntityRenderer = livingEntityRendererIn;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled() && CustomModel.modelMode.currentMode.equals("Jeff Killer")) {
            if (!(!CustomModel.onlyMe.getBoolValue() || entitylivingbaseIn == Minecraft.getMinecraft().player || GameSense.instance.friendManager.isFriend(entitylivingbaseIn.getName()) && CustomModel.friends.getBoolValue())) {
                boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
                ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
                ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
                ItemStack itemStack = itemstack1;
                if (!itemstack.func_190926_b() || !itemstack1.func_190926_b()) {
                    GlStateManager.pushMatrix();
                    if (this.livingEntityRenderer.getMainModel().isChild) {
                        float f = 0.5f;
                        GlStateManager.translate(0.0f, 0.75f, 0.0f);
                        GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    }
                    this.renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
                    this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
                    GlStateManager.popMatrix();
                }
            } else {
                boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.LEFT;
                ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
                ItemStack itemStack = itemstack;
                if (!itemstack.func_190926_b()) {
                    GlStateManager.pushMatrix();
                    if (this.livingEntityRenderer.getMainModel().isChild) {
                        float f = 0.5f;
                        GlStateManager.translate(0.0f, 0.75f, 0.0f);
                        GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    }
                    this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
                    GlStateManager.popMatrix();
                }
            }
        } else {
            boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
            ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
            ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
            ItemStack itemStack = itemstack1;
            if (!itemstack.func_190926_b() || !itemstack1.func_190926_b()) {
                GlStateManager.pushMatrix();
                if (this.livingEntityRenderer.getMainModel().isChild) {
                    float f = 0.5f;
                    GlStateManager.translate(0.0f, 0.75f, 0.0f);
                    GlStateManager.scale(0.5f, 0.5f, 0.5f);
                }
                this.renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
                this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
                GlStateManager.popMatrix();
            }
        }
    }

    private void renderHeldItem(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
        if (GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled() && (CustomModel.modelMode.currentMode.equals("Crab") || CustomModel.modelMode.currentMode.equals("Chinchilla"))) {
            if (!p_188358_2_.func_190926_b()) {
                GlStateManager.pushMatrix();
                this.func_191361_a(handSide);
                if (p_188358_1_.isSneaking()) {
                    GlStateManager.translate(0.0f, 0.2f, 0.0f);
                }
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                boolean flag = handSide == EnumHandSide.LEFT;
                boolean bl = flag;
                if (!(!CustomModel.onlyMe.getBoolValue() || p_188358_1_ == Minecraft.getMinecraft().player || GameSense.instance.friendManager.isFriend(p_188358_1_.getName()) && CustomModel.friends.getBoolValue())) {
                    GlStateManager.translate((float)(flag ? -1 : 1) / 16.0f, 0.125f, -0.625f);
                } else {
                    GlStateManager.translate((double)((float)(flag ? -1 : 1) / 16.0f), (double)0.525f, -1.05);
                }
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
                GlStateManager.popMatrix();
            }
        } else if (GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled() && CustomModel.modelMode.currentMode.equals("Freddy Bear")) {
            if (!p_188358_2_.func_190926_b()) {
                GlStateManager.pushMatrix();
                this.func_191361_a(handSide);
                if (p_188358_1_.isSneaking()) {
                    GlStateManager.translate(0.0f, 0.2f, 0.0f);
                }
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                boolean flag = handSide == EnumHandSide.LEFT;
                boolean bl = flag;
                if (!(!CustomModel.onlyMe.getBoolValue() || p_188358_1_ == Minecraft.getMinecraft().player || GameSense.instance.friendManager.isFriend(p_188358_1_.getName()) && CustomModel.friends.getBoolValue())) {
                    GlStateManager.translate((float)(flag ? -1 : 1) / 16.0f, 0.125f, -0.625f);
                } else if (p_188358_1_.isSneaking()) {
                    GlStateManager.translate((double)((float)(flag ? -1 : 1) / 16.0f), (double)0.2f, -0.3);
                } else {
                    GlStateManager.translate((double)((float)(flag ? -1 : 1) / 16.0f), (double)0.3f, -0.5);
                }
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
                GlStateManager.popMatrix();
            }
        } else if (GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled() && CustomModel.modelMode.currentMode.equals("Sonic")) {
            if (!p_188358_2_.func_190926_b()) {
                GlStateManager.pushMatrix();
                this.func_191361_a(handSide);
                if (p_188358_1_.isSneaking()) {
                    GlStateManager.translate(0.0f, 0.2f, 0.0f);
                }
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                boolean flag = handSide == EnumHandSide.LEFT;
                boolean bl = flag;
                if (!(!CustomModel.onlyMe.getBoolValue() || p_188358_1_ == Minecraft.getMinecraft().player || GameSense.instance.friendManager.isFriend(p_188358_1_.getName()) && CustomModel.friends.getBoolValue())) {
                    GlStateManager.translate((float)(flag ? -1 : 1) / 16.0f, 0.125f, -0.625f);
                } else {
                    GlStateManager.translate((float)(flag ? -1 : 1) / 16.0f + 0.05f, 0.05f, -0.925f);
                }
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
                GlStateManager.popMatrix();
            }
        } else if (GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled() && CustomModel.modelMode.currentMode.equals("CupHead")) {
            if (!p_188358_2_.func_190926_b()) {
                GlStateManager.pushMatrix();
                this.func_191361_a(handSide);
                if (p_188358_1_.isSneaking()) {
                    GlStateManager.translate(0.0f, 0.2f, 0.0f);
                }
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                boolean flag = handSide == EnumHandSide.LEFT;
                boolean bl = flag;
                if (!(!CustomModel.onlyMe.getBoolValue() || p_188358_1_ == Minecraft.getMinecraft().player || GameSense.instance.friendManager.isFriend(p_188358_1_.getName()) && CustomModel.friends.getBoolValue())) {
                    GlStateManager.translate((float)(flag ? -1 : 1) / 16.0f, 0.125f, -0.625f);
                } else {
                    GlStateManager.translate((float)(flag ? -1 : 1) / 16.0f, -0.325f, -0.625f);
                }
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
                GlStateManager.popMatrix();
            }
        } else if (GameSense.instance.featureManager.getFeature(CustomModel.class).isEnabled() && CustomModel.modelMode.currentMode.equals("Rabbit")) {
            if (!p_188358_2_.func_190926_b()) {
                GlStateManager.pushMatrix();
                this.func_191361_a(handSide);
                if (p_188358_1_.isSneaking()) {
                    GlStateManager.translate(0.0f, 0.2f, 0.0f);
                }
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                boolean flag = handSide == EnumHandSide.LEFT;
                boolean bl = flag;
                if (!(!CustomModel.onlyMe.getBoolValue() || p_188358_1_ == Minecraft.getMinecraft().player || GameSense.instance.friendManager.isFriend(p_188358_1_.getName()) && CustomModel.friends.getBoolValue())) {
                    GlStateManager.translate((float)(flag ? -1 : 1) / 16.0f, 0.125f, -0.625f);
                } else {
                    GlStateManager.translate((float)(flag ? -2 : 2) / 16.0f, 0.15f, -1.0f);
                }
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
                GlStateManager.popMatrix();
            }
        } else if (!p_188358_2_.func_190926_b()) {
            GlStateManager.pushMatrix();
            this.func_191361_a(handSide);
            if (p_188358_1_.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16.0f, 0.125f, -0.625f);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
            GlStateManager.popMatrix();
        }
    }

    protected void func_191361_a(EnumHandSide p_191361_1_) {
        ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625f, p_191361_1_);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
