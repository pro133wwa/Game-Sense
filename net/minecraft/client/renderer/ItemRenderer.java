package net.minecraft.client.renderer;

import Game.Sense.client.Helper.events.Event;
import com.google.common.base.MoreObjects;

import java.util.Objects;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.EventManager;
import Game.Sense.client.Helper.events.impl.player.EventTransformSideFirstPerson;
import Game.Sense.client.module.feature.COMBAT.KillAura;
import Game.Sense.client.module.feature.RENDER.NoOverlay;
import Game.Sense.client.module.feature.RENDER.SwingAnimations;
import Game.Sense.client.module.feature.RENDER.ViewModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import optifine.Config;
import optifine.DynamicLights;
import optifine.Reflector;
import optifine.ReflectorForge;
import shadersmod.client.Shaders;

public class ItemRenderer {
    /*  43 */   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    /*  44 */   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    /*     */
    /*     */
    /*     */   private float spin;
    /*     */
    /*     */   private final Minecraft mc;
    /*     */
    /*  51 */   private ItemStack itemStackMainHand = ItemStack.field_190927_a;
    /*  52 */   private ItemStack itemStackOffHand = ItemStack.field_190927_a;
    /*     */   private float equippedProgressMainHand;
    /*     */   private float prevEquippedProgressMainHand;
    /*     */   private float equippedProgressOffHand;
    /*     */   private float prevEquippedProgressOffHand;
    /*     */   private final RenderManager renderManager;
    /*     */   private final RenderItem itemRenderer;
    /*     */   private float rotate;
    /*     */
    /*     */   public ItemRenderer(Minecraft mcIn) {
        /*  62 */     this.mc = mcIn;
        /*  63 */     this.renderManager = mcIn.getRenderManager();
        /*  64 */     this.itemRenderer = mcIn.getRenderItem();
        /*     */   }
    /*     */
    /*     */   public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        /*  68 */     renderItemSide(entityIn, heldStack, transform, false);
        /*     */   }
    /*     */
    /*     */   public void renderItemSide(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
        /*  72 */     if (!heldStack.func_190926_b()) {
            /*  73 */       Item item = heldStack.getItem();
            /*  74 */       Block block = Block.getBlockFromItem(item);
            /*  75 */       GlStateManager.pushMatrix();
            /*  76 */       boolean flag = (this.itemRenderer.shouldRenderItemIn3D(heldStack) && block.getBlockLayer() == BlockRenderLayer.TRANSLUCENT);
            /*     */
            /*  78 */       if (flag && (!Config.isShaders() || !Shaders.renderItemKeepDepthMask)) {
                /*  79 */         GlStateManager.depthMask(false);
                /*     */       }
            /*     */
            /*  82 */       this.itemRenderer.renderItem(heldStack, entitylivingbaseIn, transform, leftHanded);
            /*     */
            /*  84 */       if (flag) {
                /*  85 */         GlStateManager.depthMask(true);
                /*     */       }
            /*     */
            /*  88 */       GlStateManager.popMatrix();
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */   private void rotateArroundXAndY(float angle, float angleY) {
        /*  96 */     GlStateManager.pushMatrix();
        /*  97 */     GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
        /*  98 */     GlStateManager.rotate(angleY, 0.0F, 1.0F, 0.0F);
        /*  99 */     RenderHelper.enableStandardItemLighting();
        /* 100 */     GlStateManager.popMatrix();
        /*     */   }
    /*     */
    /*     */   private void setLightmap() {
        /* 104 */     EntityPlayerSP entityPlayerSP = this.mc.player;
        /* 105 */     int i = this.mc.world.getCombinedLight(new BlockPos(((AbstractClientPlayer)entityPlayerSP).posX, ((AbstractClientPlayer)entityPlayerSP).posY + entityPlayerSP.getEyeHeight(), ((AbstractClientPlayer)entityPlayerSP).posZ), 0);
        /*     */
        /* 107 */     if (Config.isDynamicLights()) {
            /* 108 */       i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
            /*     */     }
        /*     */
        /* 111 */     float f = (i & 0xFFFF);
        /* 112 */     float f1 = (i >> 16);
        /* 113 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
        /*     */   }
    /*     */
    /*     */   private void rotateArm(float p_187458_1_) {
        /* 117 */     EntityPlayerSP entityplayersp = this.mc.player;
        /* 118 */     float f = entityplayersp.prevRenderArmPitch + (entityplayersp.renderArmPitch - entityplayersp.prevRenderArmPitch) * p_187458_1_;
        /* 119 */     float f1 = entityplayersp.prevRenderArmYaw + (entityplayersp.renderArmYaw - entityplayersp.prevRenderArmYaw) * p_187458_1_;
        /* 120 */     GlStateManager.rotate((entityplayersp.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
        /* 121 */     GlStateManager.rotate((entityplayersp.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */   private float getMapAngleFromPitch(float pitch) {
        /* 128 */     float f = 1.0F - pitch / 45.0F + 0.1F;
        /* 129 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
        /* 130 */     f = -MathHelper.cos(f * 3.1415927F) * 0.5F + 0.5F;
        /* 131 */     return f;
        /*     */   }
    /*     */
    /*     */   private void renderArms() {
        /* 135 */     if (!this.mc.player.isInvisible()) {
            /* 136 */       GlStateManager.disableCull();
            /* 137 */       GlStateManager.pushMatrix();
            /* 138 */       GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            /* 139 */       renderArm(EnumHandSide.RIGHT);
            /* 140 */       renderArm(EnumHandSide.LEFT);
            /* 141 */       GlStateManager.popMatrix();
            /* 142 */       GlStateManager.enableCull();
            /*     */     }
        /*     */   }
    /*     */
    /*     */   private void renderArm(EnumHandSide p_187455_1_) {
        /* 147 */     this.mc.getTextureManager().bindTexture(this.mc.player.getLocationSkin());
        /* 148 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.player);
        /* 149 */     RenderPlayer renderplayer = (RenderPlayer)render;
        /* 150 */     GlStateManager.pushMatrix();
        /* 151 */     float f = (p_187455_1_ == EnumHandSide.RIGHT) ? 1.0F : -1.0F;
        /* 152 */     GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
        /* 153 */     GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        /* 154 */     GlStateManager.rotate(f * -41.0F, 0.0F, 0.0F, 1.0F);
        /* 155 */     GlStateManager.translate(f * 0.3F, -1.1F, 0.45F);
        /*     */
        /* 157 */     if (p_187455_1_ == EnumHandSide.RIGHT) {
            /* 158 */       renderplayer.renderRightArm((AbstractClientPlayer)this.mc.player);
            /*     */     } else {
            /* 160 */       renderplayer.renderLeftArm((AbstractClientPlayer)this.mc.player);
            /*     */     }
        /*     */
        /* 163 */     GlStateManager.popMatrix();
        /*     */   }
    /*     */
    /*     */   private void renderMapFirstPersonSide(float p_187465_1_, EnumHandSide p_187465_2_, float p_187465_3_, ItemStack p_187465_4_) {
        /* 167 */     float f = (p_187465_2_ == EnumHandSide.RIGHT) ? 1.0F : -1.0F;
        /* 168 */     GlStateManager.translate(f * 0.125F, -0.125F, 0.0F);
        /*     */
        /* 170 */     if (!this.mc.player.isInvisible()) {
            /* 171 */       GlStateManager.pushMatrix();
            /* 172 */       GlStateManager.rotate(f * 10.0F, 0.0F, 0.0F, 1.0F);
            /* 173 */       renderArmFirstPerson(p_187465_1_, p_187465_3_, p_187465_2_);
            /* 174 */       GlStateManager.popMatrix();
            /*     */     }
        /*     */
        /* 177 */     GlStateManager.pushMatrix();
        /* 178 */     GlStateManager.translate(f * 0.51F, -0.08F + p_187465_1_ * -1.2F, -0.75F);
        /* 179 */     float f1 = MathHelper.sqrt(p_187465_3_);
        /* 180 */     float f2 = MathHelper.sin(f1 * 3.1415927F);
        /* 181 */     float f3 = -0.5F * f2;
        /* 182 */     float f4 = 0.4F * MathHelper.sin(f1 * 6.2831855F);
        /* 183 */     float f5 = -0.3F * MathHelper.sin(p_187465_3_ * 3.1415927F);
        /* 184 */     GlStateManager.translate(f * f3, f4 - 0.3F * f2, f5);
        /* 185 */     GlStateManager.rotate(f2 * -45.0F, 1.0F, 0.0F, 0.0F);
        /* 186 */     GlStateManager.rotate(f * f2 * -30.0F, 0.0F, 1.0F, 0.0F);
        /* 187 */     renderMapFirstPerson(p_187465_4_);
        /* 188 */     GlStateManager.popMatrix();
        /*     */   }
    /*     */
    /*     */   private void renderMapFirstPerson(float p_187463_1_, float p_187463_2_, float p_187463_3_) {
        /* 192 */     float f = MathHelper.sqrt(p_187463_3_);
        /* 193 */     float f1 = -0.2F * MathHelper.sin(p_187463_3_ * 3.1415927F);
        /* 194 */     float f2 = -0.4F * MathHelper.sin(f * 3.1415927F);
        /* 195 */     GlStateManager.translate(0.0F, -f1 / 2.0F, f2);
        /* 196 */     float f3 = getMapAngleFromPitch(p_187463_1_);
        /* 197 */     GlStateManager.translate(0.0F, 0.04F + p_187463_2_ * -1.2F + f3 * -0.5F, -0.72F);
        /* 198 */     GlStateManager.rotate(f3 * -85.0F, 1.0F, 0.0F, 0.0F);
        /* 199 */     renderArms();
        /* 200 */     float f4 = MathHelper.sin(f * 3.1415927F);
        /* 201 */     GlStateManager.rotate(f4 * 20.0F, 1.0F, 0.0F, 0.0F);
        /* 202 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
        /* 203 */     renderMapFirstPerson(this.itemStackMainHand);
        /*     */   }
    /*     */
    /*     */   private void renderMapFirstPerson(ItemStack stack) {
        /* 207 */     GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        /* 208 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        /* 209 */     GlStateManager.scale(0.38F, 0.38F, 0.38F);
        /* 210 */     GlStateManager.disableLighting();
        /* 211 */     this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
        /* 212 */     Tessellator tessellator = Tessellator.getInstance();
        /* 213 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
        /* 214 */     GlStateManager.translate(-0.5F, -0.5F, 0.0F);
        /* 215 */     GlStateManager.scale(0.0078125F, 0.0078125F, 0.0078125F);
        /* 216 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        /* 217 */     bufferbuilder.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
        /* 218 */     bufferbuilder.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
        /* 219 */     bufferbuilder.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
        /* 220 */     bufferbuilder.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
        /* 221 */     tessellator.draw();
        /* 222 */     MapData mapdata = ReflectorForge.getMapData(Items.FILLED_MAP, stack, (World)this.mc.world);
        /*     */
        /* 224 */     if (mapdata != null) {
            /* 225 */       this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
            /*     */     }
        /*     */
        /* 228 */     GlStateManager.enableLighting();
        /*     */   }
    /*     */
    /*     */   private void renderArmFirstPerson(float p_187456_1_, float p_187456_2_, EnumHandSide p_187456_3_) {
        /* 232 */     EventTransformSideFirstPerson event = new EventTransformSideFirstPerson(p_187456_3_);
        /* 233 */     EventManager.call((Event)event);
        /* 234 */     boolean flag = (p_187456_3_ != EnumHandSide.LEFT);
        /* 235 */     float f = flag ? 1.0F : -1.0F;
        /* 236 */     float f1 = MathHelper.sqrt(p_187456_2_);
        /* 237 */     float f2 = -0.3F * MathHelper.sin(f1 * 3.1415927F);
        /* 238 */     float f3 = 0.4F * MathHelper.sin(f1 * 6.2831855F);
        /* 239 */     float f4 = -0.4F * MathHelper.sin(p_187456_2_ * 3.1415927F);
        /* 240 */     GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_187456_1_ * -0.6F, f4 + -0.71999997F);
        /* 241 */     GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
        /* 242 */     float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * 3.1415927F);
        /* 243 */     float f6 = MathHelper.sin(f1 * 3.1415927F);
        /* 244 */     GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
        /* 245 */     GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
        /* 246 */     EntityPlayerSP entityPlayerSP = this.mc.player;
        /* 247 */     this.mc.getTextureManager().bindTexture(entityPlayerSP.getLocationSkin());
        /* 248 */     GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);
        /* 249 */     GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
        /* 250 */     GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
        /* 251 */     GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
        /* 252 */     GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);
        RenderPlayer renderplayer = (RenderPlayer) this.renderManager.<AbstractClientPlayer>getEntityRenderObject(entityPlayerSP);
        /* 254 */     GlStateManager.disableCull();
        /*     */
        /* 256 */     if (flag) {
            /* 257 */       renderplayer.renderRightArm((AbstractClientPlayer)entityPlayerSP);
            /*     */     } else {
            /* 259 */       renderplayer.renderLeftArm((AbstractClientPlayer)entityPlayerSP);
            /*     */     }
        /*     */
        /* 262 */     GlStateManager.enableCull();
        /*     */   }
    /*     */
    /*     */
    /*     */   private void transformEatFirstPerson(float p_187454_1_, EnumHandSide p_187454_2_, ItemStack p_187454_3_) {
        /* 267 */     EventTransformSideFirstPerson event = new EventTransformSideFirstPerson(p_187454_2_);
        /* 268 */     EventManager.call((Event)event);
        /* 269 */     float f = this.mc.player.getItemInUseCount() - p_187454_1_ + 1.0F;
        /* 270 */     float f1 = f / p_187454_3_.getMaxItemUseDuration();
        /* 271 */     float f3 = 1.0F - (float)Math.pow(f1, 27.0D);
        /* 272 */     int i = (p_187454_2_ == EnumHandSide.RIGHT) ? 1 : -1, n = i;
        /* 273 */     if (f1 < 0.8F) {
            /* 274 */       float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
            /* 275 */       GlStateManager.translate(0.0F, f2, 0.0F);
            /*     */     }
        /* 277 */     GlStateManager.translate(f3 * 0.6F * i, f3 * -0.5F, f3 * 0.0F);
        /* 278 */     if (GameSense.instance.featureManager.getFeature(ViewModel.class).isEnabled()) {
            /* 279 */       GlStateManager.rotate(i * f3 * 20.0F, 0.0F, 1.0F, 0.0F);
            /*     */     } else {
            /* 281 */       GlStateManager.rotate(i * f3 * 90.0F, 0.0F, 1.0F, 0.0F);
            /*     */     }
        /* 283 */     GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
        /* 284 */     GlStateManager.rotate(i * f3 * 30.0F, 0.0F, 0.0F, 1.0F);
        /*     */   }
    /*     */
    /*     */   private void transformFirstPerson(EnumHandSide p_187453_1_, float p_187453_2_) {
        /* 288 */     int i = (p_187453_1_ == EnumHandSide.RIGHT) ? 1 : -1;
        /* 289 */     float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * 3.1415927F);
        /* 290 */     GlStateManager.rotate(i * (45.0F + f * -20.0F), 0.0F, 1.0F, 0.0F);
        /* 291 */     float f1 = MathHelper.sin(MathHelper.sqrt(p_187453_2_) * 3.1415927F);
        /* 292 */     GlStateManager.rotate(i * f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        /* 293 */     GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
        /* 294 */     GlStateManager.rotate(i * -45.0F, 0.0F, 1.0F, 0.0F);
        /* 295 */     GlStateManager.translate(0.0F, 0.02F, 0.0F);
        /*     */   }
    /*     */
    /*     */   private void transformSideFirstPerson(EnumHandSide p_187459_1_, float p_187459_2_) {
        /* 299 */     EventTransformSideFirstPerson event = new EventTransformSideFirstPerson(p_187459_1_);
        /* 300 */     EventManager.call((Event)event);
        /* 301 */     int i = (p_187459_1_ == EnumHandSide.RIGHT) ? 1 : -1;
        /* 302 */     GlStateManager.translate(i * 0.56F, -0.52F + p_187459_2_ * -0.6F, -0.72F);
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */   private void transformFirstPersonItem(float equipProgress, float swingProgress) {
        /* 308 */     GlStateManager.translate(0.56F, -0.44F, -0.71999997F);
        /* 309 */     GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
        /* 310 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        /*     */
        /* 312 */     float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
        /* 313 */     float f2 = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
        /* 314 */     GlStateManager.rotate(f * -20.0F, 0.0F, 0.0F, 0.0F);
        /* 315 */     GlStateManager.rotate(f2 * -20.0F, 0.0F, 0.0F, 1.0F);
        /* 316 */     GlStateManager.rotate(f2 * -80.0F, 0.01F, 0.0F, 0.0F);
        /*     */
        /* 318 */     GlStateManager.translate(0.4F, 0.2F, 0.2F);
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */   public void renderItemInFirstPerson(float partialTicks) {
        /* 325 */     EntityPlayerSP entityPlayerSP = this.mc.player;
        /* 326 */     float f = entityPlayerSP.getSwingProgress(partialTicks);
        /* 327 */     EnumHand enumhand = (EnumHand)MoreObjects.firstNonNull(((AbstractClientPlayer)entityPlayerSP).swingingHand, EnumHand.MAIN_HAND);
        /* 328 */     float f1 = ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch + (((AbstractClientPlayer)entityPlayerSP).rotationPitch - ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch) * partialTicks;
        /* 329 */     float f2 = ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw + (((AbstractClientPlayer)entityPlayerSP).rotationYaw - ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw) * partialTicks;
        /* 330 */     boolean flag = true;
        /* 331 */     boolean flag1 = true;
        /*     */
        /* 333 */     if (entityPlayerSP.isHandActive()) {
            /* 334 */       ItemStack itemstack = entityPlayerSP.getActiveItemStack();
            /*     */
            /* 336 */       if (!itemstack.func_190926_b() && itemstack.getItem() == Items.BOW) {
                /* 337 */         EnumHand enumhand1 = entityPlayerSP.getActiveHand();
                /* 338 */         flag = (enumhand1 == EnumHand.MAIN_HAND);
                /* 339 */         flag1 = !flag;
                /*     */       }
            /*     */     }
        /*     */
        /* 343 */     rotateArroundXAndY(f1, f2);
        /* 344 */     setLightmap();
        /* 345 */     rotateArm(partialTicks);
        /* 346 */     GlStateManager.enableRescaleNormal();
        /*     */
        /* 348 */     if (flag) {
            /* 349 */       float f3 = (enumhand == EnumHand.MAIN_HAND) ? f : 0.0F;
            /* 350 */       float f5 = 1.0F - this.prevEquippedProgressMainHand + (this.equippedProgressMainHand - this.prevEquippedProgressMainHand) * partialTicks;
            /*     */
            /* 352 */       if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[] { EnumHand.MAIN_HAND, Float.valueOf(partialTicks), Float.valueOf(f1), Float.valueOf(f3), Float.valueOf(f5), this.itemStackMainHand })) {
                /* 353 */         renderItemInFirstPerson((AbstractClientPlayer)entityPlayerSP, partialTicks, f1, EnumHand.MAIN_HAND, f3, this.itemStackMainHand, f5);
                /*     */       }
            /*     */     }
        /*     */
        /* 357 */     if (flag1) {
            /* 358 */       float f4 = (enumhand == EnumHand.OFF_HAND) ? f : 0.0F;
            /* 359 */       float f6 = 1.0F - this.prevEquippedProgressOffHand + (this.equippedProgressOffHand - this.prevEquippedProgressOffHand) * partialTicks;
            /*     */
            /* 361 */       if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[] { EnumHand.OFF_HAND, Float.valueOf(partialTicks), Float.valueOf(f1), Float.valueOf(f4), Float.valueOf(f6), this.itemStackOffHand })) {
                /* 362 */         renderItemInFirstPerson((AbstractClientPlayer)entityPlayerSP, partialTicks, f1, EnumHand.OFF_HAND, f4, this.itemStackOffHand, f6);
                /*     */       }
            /*     */     }
        /*     */
        /* 366 */     GlStateManager.disableRescaleNormal();
        /* 367 */     RenderHelper.disableStandardItemLighting();
        /*     */   }
    /*     */
    /*     */   private void translate() {
        /* 371 */     GlStateManager.rotate(20.0F, 0.0F, 1.0F, 0.0F);
        /* 372 */     GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        /* 373 */     GlStateManager.rotate(20.0F, 0.0F, 1.0F, 0.0F);
        /*     */   }
    /*     */   private void translate2() {
        /* 376 */     GlStateManager.rotate(50.0F, 10.0F, 0.0F, 0.0F);
        /*     */   }
    /*     */   public void renderItemInFirstPerson(AbstractClientPlayer p_187457_1_, float p_187457_2_, float p_187457_3_, EnumHand p_187457_4_, float p_187457_5_, ItemStack p_187457_6_, float p_187457_7_) {
        /* 379 */     if (!Config.isShaders() || !Shaders.isSkipRenderHand(p_187457_4_)) {
            /* 380 */       boolean flag = (p_187457_4_ == EnumHand.MAIN_HAND);
            /* 381 */       EnumHandSide enumhandside = flag ? p_187457_1_.getPrimaryHand() : p_187457_1_.getPrimaryHand().opposite();
            /* 382 */       GlStateManager.pushMatrix();
            /*     */
            /* 384 */       if (p_187457_6_.func_190926_b()) {
                /* 385 */         if (flag && !p_187457_1_.isInvisible()) {
                    /* 386 */           renderArmFirstPerson(p_187457_7_, p_187457_5_, enumhandside);
                    /*     */         }
                /* 388 */       } else if (p_187457_6_.getItem() instanceof net.minecraft.item.ItemMap) {
                /* 389 */         if (flag && this.itemStackOffHand.func_190926_b()) {
                    /* 390 */           renderMapFirstPerson(p_187457_3_, p_187457_7_, p_187457_5_);
                    /*     */         } else {
                    /* 392 */           renderMapFirstPersonSide(p_187457_7_, enumhandside, p_187457_5_, p_187457_6_);
                    /*     */         }
                /*     */       } else {
                /* 395 */         boolean flag1 = (enumhandside == EnumHandSide.RIGHT);
                /*     */
                /* 397 */         if (p_187457_1_.isHandActive() && p_187457_1_.getItemInUseCount() > 0 && p_187457_1_.getActiveHand() == p_187457_4_) {
                    /* 398 */           float f5, f6; int j = flag1 ? 1 : -1;
                    /*     */
                    /* 400 */           switch (p_187457_6_.getItemUseAction()) {
                        /*     */             case NONE:
                            /* 402 */               transformSideFirstPerson(enumhandside, p_187457_7_);
                            /*     */               break;
                        /*     */
                        /*     */             case EAT:
                            /*     */             case DRINK:
                            /* 407 */               transformEatFirstPerson(p_187457_2_, enumhandside, p_187457_6_);
                            /* 408 */               transformSideFirstPerson(enumhandside, p_187457_7_);
                            /*     */               break;
                        /*     */
                        /*     */             case BLOCK:
                            /* 412 */               transformSideFirstPerson(enumhandside, p_187457_7_);
                            /*     */               break;
                        /*     */
                        /*     */             case BOW:
                            /* 416 */               transformSideFirstPerson(enumhandside, p_187457_7_);
                            /* 417 */               GlStateManager.translate(j * -0.2785682F, 0.18344387F, 0.15731531F);
                            /* 418 */               GlStateManager.rotate(-13.935F, 1.0F, 0.0F, 0.0F);
                            /* 419 */               GlStateManager.rotate(j * 35.3F, 0.0F, 1.0F, 0.0F);
                            /* 420 */               GlStateManager.rotate(j * -9.785F, 0.0F, 0.0F, 1.0F);
                            /* 421 */               f5 = p_187457_6_.getMaxItemUseDuration() - this.mc.player.getItemInUseCount() - p_187457_2_ + 1.0F;
                            /* 422 */               f6 = f5 / 20.0F;
                            /* 423 */               f6 = (f6 * f6 + f6 * 2.0F) / 3.0F;
                            /*     */
                            /* 425 */               if (f6 > 1.0F) {
                                /* 426 */                 f6 = 1.0F;
                                /*     */               }
                            /*     */
                            /* 429 */               if (f6 > 0.1F) {
                                /* 430 */                 float f7 = MathHelper.sin((f5 - 0.1F) * 1.3F);
                                /* 431 */                 float f3 = f6 - 0.1F;
                                /* 432 */                 float f4 = f7 * f3;
                                /* 433 */                 GlStateManager.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                                /*     */               }
                            /*     */
                            /* 436 */               GlStateManager.translate(f6 * 0.0F, f6 * 0.0F, f6 * 0.04F);
                            /* 437 */               GlStateManager.scale(1.0F, 1.0F, 1.0F + f6 * 0.2F);
                            /* 438 */               GlStateManager.rotate(j * 45.0F, 0.0F, -1.0F, 0.0F); break;
                        /*     */           }
                    /*     */         } else {
                    /* 441 */           float f = -0.4F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * 3.1415927F);
                    /* 442 */           float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * 6.2831855F);
                    /* 443 */           float f2 = -0.2F * MathHelper.sin(p_187457_5_ * 3.1415927F);
                    /* 444 */           int i = flag1 ? 1 : -1;
                    /* 445 */           float equipProgress = 1.0F - this.prevEquippedProgressMainHand + (this.equippedProgressMainHand - this.prevEquippedProgressMainHand) * p_187457_2_;
                    /* 446 */           float swingprogress = this.mc.player.getSwingProgress(p_187457_2_);
                    /* 447 */           String mode = SwingAnimations.swordAnim.getCurrentMode();
                    /* 448 */           if (GameSense.instance.featureManager.getFeature(SwingAnimations.class).isEnabled() && ((this.mc.gameSettings.keyBindAttack.pressed && !SwingAnimations.auraOnly.getBoolValue()) || (GameSense.instance.featureManager.getFeature(KillAura.class).isEnabled() && KillAura.target != null))) {
                        /* 449 */             if (GameSense.instance.featureManager.getFeature(SwingAnimations.class).isEnabled() && ((this.mc.gameSettings.keyBindAttack.pressed && !SwingAnimations.auraOnly.getBoolValue()) || (GameSense.instance.featureManager.getFeature(KillAura.class).isEnabled() && KillAura.target != null))) {
                            /* 450 */               if (enumhandside != (this.mc.gameSettings.mainHand.equals(EnumHandSide.LEFT) ? EnumHandSide.RIGHT : EnumHandSide.LEFT)) {
                                /* 451 */                 if (mode.equalsIgnoreCase("Glide")) {
                                    /* 452 */                   transformFirstPersonItem(equipProgress / 2.0F, 0.0F);
                                    /* 453 */                   translate();
                                    /* 454 */                 } else if (mode.equalsIgnoreCase("Nursultan")) {
                                    /* 455 */                   GlStateManager.translate(0.96F, -0.02F, -0.71999997F);
                                    /* 456 */                   GlStateManager.translate(0.0F, -0.0F, 0.0F);
                                    /* 457 */                   GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                                    /* 458 */                   float var3 = MathHelper.sin(0.0F);
                                    /* 459 */                   float var4 = MathHelper.sin(MathHelper.sqrt(0.0F) * 3.1415927F);
                                    /* 460 */                   GlStateManager.rotate(var3 * -20.0F, 0.0F, 1.0F, 0.0F);
                                    /* 461 */                   GlStateManager.rotate(var4 * -20.0F, 0.0F, 0.0F, 1.0F);
                                    /* 462 */                   GlStateManager.rotate(var4 * -80.0F, 1.0F, 0.0F, 0.0F);
                                    /* 463 */                   GlStateManager.translate(-0.5F, 0.2F, 0.0F);
                                    /* 464 */                   GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
                                    /* 465 */                   GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
                                    /* 466 */                   GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
                                    /* 467 */                   int alpha = (int)Math.min(255L, ((System.currentTimeMillis() % 255L > 127L) ? Math.abs(Math.abs(System.currentTimeMillis()) % 255L - 255L) : (System.currentTimeMillis() % 255L)) * 2L);
                                    /* 468 */                   float f5 = (f1 > 0.5D) ? (1.0F - f1) : f1;
                                    /* 469 */                   GlStateManager.translate(0.3F, -0.0F, 0.4F);
                                    /* 470 */                   GlStateManager.rotate(0.0F, 0.0F, 0.0F, 1.0F);
                                    /* 471 */                   GlStateManager.translate(0.0F, 0.5F, 0.0F);
                                    /* 472 */                   GlStateManager.rotate(90.0F, 1.0F, 0.0F, -1.0F);
                                    /* 473 */                   GlStateManager.translate(0.6F, 0.5F, 0.0F);
                                    /* 474 */                   GlStateManager.rotate(-90.0F, 1.0F, 0.0F, -1.0F);
                                    /* 475 */                   GlStateManager.rotate(-10.0F, 1.0F, 0.0F, -1.0F);
                                    /* 476 */                   GlStateManager.rotate(-f5 * 10.0F, 10.0F, 10.0F, -9.0F);
                                    /* 477 */                   GlStateManager.rotate(10.0F, -1.0F, 0.0F, 0.0F);
                                    /* 478 */                   GlStateManager.translate(0.0D, 0.0D, -0.5D);
                                    /* 479 */                   GlStateManager.rotate(this.mc.player.isSwingInProgress ? (-alpha / SwingAnimations.fapSmooth.getNumberValue()) : 1.0F, 1.0F, -0.0F, 1.0F);
                                    /* 480 */                   GlStateManager.translate(0.0D, 0.0D, 0.5D);
                                    /* 481 */                 } else if (mode.equalsIgnoreCase("GlobalSpin")) {
                                    /* 482 */                   GlStateManager.rotate((float)(System.currentTimeMillis() / 16L * (int)SwingAnimations.spinSpeed.getNumberValue() % 360L), 0.0F, 0.0F, -0.1F);
                                    /* 483 */                   transformFirstPersonItem(0.0F, 0.0F);
                                    /* 484 */                   translate();
                                    /*     */                 }
                                /*     */               } else {
                                /* 487 */                 GlStateManager.translate(i * f, f1, f2);
                                /* 488 */                 transformSideFirstPerson(enumhandside, p_187457_7_);
                                /* 489 */                 transformFirstPerson(enumhandside, p_187457_5_);
                                /*     */               }
                            /*     */             } else {
                            /* 492 */               transformSideFirstPerson(enumhandside, p_187457_7_);
                            /* 493 */               transformFirstPerson(enumhandside, p_187457_5_);
                            /*     */             }
                        /*     */           } else {
                        /* 496 */             GlStateManager.translate(i * f, f1, f2);
                        /* 497 */             transformSideFirstPerson(enumhandside, p_187457_7_);
                        /* 498 */             transformFirstPerson(enumhandside, p_187457_5_);
                        /*     */           }
                    /*     */         }
                /* 501 */         renderItemSide((EntityLivingBase)p_187457_1_, p_187457_6_, flag1 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
                /*     */       }
            /* 503 */       GlStateManager.popMatrix();
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */   public void renderOverlays(float partialTicks) {
        /* 512 */     GlStateManager.disableAlpha();
        /*     */
        /* 514 */     if (this.mc.player.isEntityInsideOpaqueBlock()) {
            /* 515 */       IBlockState iblockstate = this.mc.world.getBlockState(new BlockPos((Entity)this.mc.player));
            /* 516 */       BlockPos blockpos = new BlockPos((Entity)this.mc.player);
            /* 517 */       EntityPlayerSP entityPlayerSP = this.mc.player;
            /*     */
            /* 519 */       for (int i = 0; i < 8; i++) {
                /* 520 */         double d0 = ((EntityPlayer)entityPlayerSP).posX + ((((i >> 0) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
                /* 521 */         double d1 = ((EntityPlayer)entityPlayerSP).posY + ((((i >> 1) % 2) - 0.5F) * 0.1F);
                /* 522 */         double d2 = ((EntityPlayer)entityPlayerSP).posZ + ((((i >> 2) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
                /* 523 */         BlockPos blockpos1 = new BlockPos(d0, d1 + entityPlayerSP.getEyeHeight(), d2);
                /* 524 */         IBlockState iblockstate1 = this.mc.world.getBlockState(blockpos1);
                /*     */
                /* 526 */         if (iblockstate1.func_191058_s()) {
                    /* 527 */           iblockstate = iblockstate1;
                    /* 528 */           blockpos = blockpos1;
                    /*     */         }
                /*     */       }
            /*     */
            /*     */
            /* 533 */       Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
            /*     */
            /* 535 */       if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, new Object[] { this.mc.player, Float.valueOf(partialTicks), object, iblockstate, blockpos })) {
                /* 536 */         renderBlockInHand(this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
                /*     */       }
            /*     */     }
        /*     */
        /*     */
        /* 541 */     if (!this.mc.player.isSpectator()) {
            /* 542 */       if (this.mc.player.isInsideOfMaterial(Material.WATER) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, new Object[] { this.mc.player, Float.valueOf(partialTicks) })) {
                /* 543 */         renderWaterOverlayTexture(partialTicks);
                /*     */       }
            /*     */
            /* 546 */       if (this.mc.player.isBurning() && !NoOverlay.noFire.getBoolValue() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, new Object[] { this.mc.player, Float.valueOf(partialTicks) })) {
                /* 547 */         renderFireInFirstPerson();
                /*     */       }
            /*     */     }
        /*     */
        /* 551 */     GlStateManager.enableAlpha();
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */   private void renderBlockInHand(TextureAtlasSprite partialTicks) {
        /* 558 */     this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        /* 559 */     Tessellator tessellator = Tessellator.getInstance();
        /* 560 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
        /* 561 */     float f = 0.1F;
        /* 562 */     GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
        /* 563 */     GlStateManager.pushMatrix();
        /* 564 */     float f1 = -1.0F;
        /* 565 */     float f2 = 1.0F;
        /* 566 */     float f3 = -1.0F;
        /* 567 */     float f4 = 1.0F;
        /* 568 */     float f5 = -0.5F;
        /* 569 */     float f6 = partialTicks.getMinU();
        /* 570 */     float f7 = partialTicks.getMaxU();
        /* 571 */     float f8 = partialTicks.getMinV();
        /* 572 */     float f9 = partialTicks.getMaxV();
        /* 573 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        /* 574 */     bufferbuilder.pos(-1.0D, -1.0D, -0.5D).tex(f7, f9).endVertex();
        /* 575 */     bufferbuilder.pos(1.0D, -1.0D, -0.5D).tex(f6, f9).endVertex();
        /* 576 */     bufferbuilder.pos(1.0D, 1.0D, -0.5D).tex(f6, f8).endVertex();
        /* 577 */     bufferbuilder.pos(-1.0D, 1.0D, -0.5D).tex(f7, f8).endVertex();
        /* 578 */     tessellator.draw();
        /* 579 */     GlStateManager.popMatrix();
        /* 580 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */   private void renderWaterOverlayTexture(float partialTicks) {
        /* 588 */     if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
            /* 589 */       this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
            /* 590 */       Tessellator tessellator = Tessellator.getInstance();
            /* 591 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
            /* 592 */       float f = this.mc.player.getBrightness();
            /* 593 */       GlStateManager.color(f, f, f, 0.5F);
            /* 594 */       GlStateManager.enableBlend();
            /* 595 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            /* 596 */       GlStateManager.pushMatrix();
            /* 597 */       float f1 = 4.0F;
            /* 598 */       float f2 = -1.0F;
            /* 599 */       float f3 = 1.0F;
            /* 600 */       float f4 = -1.0F;
            /* 601 */       float f5 = 1.0F;
            /* 602 */       float f6 = -0.5F;
            /* 603 */       float f7 = -this.mc.player.rotationYaw / 64.0F;
            /* 604 */       float f8 = this.mc.player.rotationPitch / 64.0F;
            /* 605 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            /* 606 */       bufferbuilder.pos(-1.0D, -1.0D, -0.5D).tex((4.0F + f7), (4.0F + f8)).endVertex();
            /* 607 */       bufferbuilder.pos(1.0D, -1.0D, -0.5D).tex((0.0F + f7), (4.0F + f8)).endVertex();
            /* 608 */       bufferbuilder.pos(1.0D, 1.0D, -0.5D).tex((0.0F + f7), (0.0F + f8)).endVertex();
            /* 609 */       bufferbuilder.pos(-1.0D, 1.0D, -0.5D).tex((4.0F + f7), (0.0F + f8)).endVertex();
            /* 610 */       tessellator.draw();
            /* 611 */       GlStateManager.popMatrix();
            /* 612 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            /* 613 */       GlStateManager.disableBlend();
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */   private void renderFireInFirstPerson() {
        /* 621 */     Tessellator tessellator = Tessellator.getInstance();
        /* 622 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
        /* 623 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
        /* 624 */     GlStateManager.depthFunc(519);
        /* 625 */     GlStateManager.depthMask(false);
        /* 626 */     GlStateManager.enableBlend();
        /* 627 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        /* 628 */     float f = 1.0F;
        /*     */
        /* 630 */     for (int i = 0; i < 2; i++) {
            /* 631 */       GlStateManager.pushMatrix();
            /* 632 */       TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
            /* 633 */       this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            /* 634 */       float f1 = textureatlassprite.getMinU();
            /* 635 */       float f2 = textureatlassprite.getMaxU();
            /* 636 */       float f3 = textureatlassprite.getMinV();
            /* 637 */       float f4 = textureatlassprite.getMaxV();
            /* 638 */       float f5 = -0.5F;
            /* 639 */       float f6 = 0.5F;
            /* 640 */       float f7 = -0.5F;
            /* 641 */       float f8 = 0.5F;
            /* 642 */       float f9 = -0.5F;
            /* 643 */       GlStateManager.translate(-(i * 2 - 1) * 0.24F, -0.3F, 0.0F);
            /* 644 */       GlStateManager.rotate((i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            /* 645 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            /* 646 */       bufferbuilder.pos(-0.5D, -0.5D, -0.5D).tex(f2, f4).endVertex();
            /* 647 */       bufferbuilder.pos(0.5D, -0.5D, -0.5D).tex(f1, f4).endVertex();
            /* 648 */       bufferbuilder.pos(0.5D, 0.5D, -0.5D).tex(f1, f3).endVertex();
            /* 649 */       bufferbuilder.pos(-0.5D, 0.5D, -0.5D).tex(f2, f3).endVertex();
            /* 650 */       tessellator.draw();
            /* 651 */       GlStateManager.popMatrix();
            /*     */     }
        /*     */
        /* 654 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        /* 655 */     GlStateManager.disableBlend();
        /* 656 */     GlStateManager.depthMask(true);
        /* 657 */     GlStateManager.depthFunc(515);
        /*     */   }
    /*     */
    /*     */   public void updateEquippedItem() {
        /* 661 */     this.prevEquippedProgressMainHand = this.equippedProgressMainHand;
        /* 662 */     this.prevEquippedProgressOffHand = this.equippedProgressOffHand;
        /* 663 */     EntityPlayerSP entityplayersp = this.mc.player;
        /* 664 */     ItemStack itemstack = entityplayersp.getHeldItemMainhand();
        /* 665 */     ItemStack itemstack1 = entityplayersp.getHeldItemOffhand();
        /*     */
        /* 667 */     if (entityplayersp.isRowingBoat()) {
            /* 668 */       this.equippedProgressMainHand = MathHelper.clamp(this.equippedProgressMainHand - 0.4F, 0.0F, 1.0F);
            /* 669 */       this.equippedProgressOffHand = MathHelper.clamp(this.equippedProgressOffHand - 0.4F, 0.0F, 1.0F);
            /*     */     } else {
            /* 671 */       float f = entityplayersp.getCooledAttackStrength(1.0F);
            /*     */
            /* 673 */       if (Reflector.ForgeHooksClient_shouldCauseReequipAnimation.exists()) {
                /* 674 */         boolean flag = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, new Object[] { this.itemStackMainHand, itemstack, Integer.valueOf(entityplayersp.inventory.currentItem) });
                /* 675 */         boolean flag1 = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, new Object[] { this.itemStackOffHand, itemstack1, Integer.valueOf(-1) });
                /*     */
                /* 677 */         if (!flag && !Objects.equals(this.itemStackMainHand, itemstack)) {
                    /* 678 */           this.itemStackMainHand = itemstack;
                    /*     */         }
                /*     */
                /* 681 */         if (!flag && !Objects.equals(this.itemStackOffHand, itemstack1)) {
                    /* 682 */           this.itemStackOffHand = itemstack1;
                    /*     */         }
                /*     */
                /* 685 */         this.equippedProgressMainHand += MathHelper.clamp((!flag ? (f * f * f) : 0.0F) - this.equippedProgressMainHand, -0.4F, 0.4F);
                this.equippedProgressOffHand += MathHelper.clamp((float) (!flag1 ? 1 : 0) - this.equippedProgressOffHand, -0.4F, 0.4F);
                /*     */       } else {
                /* 688 */         this.equippedProgressMainHand += MathHelper.clamp((Objects.equals(this.itemStackMainHand, itemstack) ? (f * f * f) : 0.0F) - this.equippedProgressMainHand, -0.4F, 0.4F);
                this.equippedProgressOffHand += MathHelper.clamp((float) (Objects.equals(this.itemStackOffHand, itemstack1) ? 1 : 0) - this.equippedProgressOffHand, -0.4F, 0.4F);
                /*     */       }
            /*     */     }
        /*     */
        /* 693 */     if (this.equippedProgressMainHand < 0.1F) {
            /* 694 */       this.itemStackMainHand = itemstack;
            /*     */
            /* 696 */       if (Config.isShaders()) {
                /* 697 */         Shaders.setItemToRenderMain(this.itemStackMainHand);
                /*     */       }
            /*     */     }
        /*     */
        /* 701 */     if (this.equippedProgressOffHand < 0.1F) {
            /* 702 */       this.itemStackOffHand = itemstack1;
            /*     */
            /* 704 */       if (Config.isShaders()) {
                /* 705 */         Shaders.setItemToRenderOff(this.itemStackOffHand);
                /*     */       }
            /*     */     }
        /*     */   }
    /*     */
    /*     */   public void resetEquippedProgress(EnumHand hand) {
        /* 711 */     if (hand == EnumHand.MAIN_HAND) {
            /* 712 */       this.equippedProgressMainHand = 0.0F;
            /*     */     } else {
            /* 714 */       this.equippedProgressOffHand = 0.0F;
            /*     */     }
        /*     */   }
    /*     */ }