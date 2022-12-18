package Game.Sense.client.feature.impl.misc;
/*    */
/*    */ import Game.Sense.client.event.EventTarget;
/*    */ import Game.Sense.client.event.events.impl.player.EventPreMotion;
/*    */ import Game.Sense.client.feature.Feature;
/*    */ import Game.Sense.client.feature.impl.FeatureCategory;
/*    */ import Game.Sense.client.ui.settings.Setting;
/*    */ import Game.Sense.client.ui.settings.impl.NumberSetting;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class AirDrop
        /*    */   extends Feature
        /*    */ {
    /* 30 */   private final NumberSetting delay = new NumberSetting("Stealer Speed", 1.0F, 0.0F, 100.0F, 1.0F, () -> Boolean.valueOf(true));
    /*    */
    /*    */
    /* 33 */
    /*    */
    /*    */   public AirDrop() {
        /* 36 */     super("AirStealer", "", FeatureCategory.Misc);
        /* 37 */     addSettings(new Setting[] { (Setting)this.delay });
        /*    */   }
    /*    */
    /*    */   @EventTarget
    /*    */   public void onUpdate(EventPreMotion event) {
        /* 42 */     if (mc.player.openContainer instanceof ContainerChest) {
            /* 43 */       ContainerChest container = (ContainerChest)mc.player.openContainer;
            /*    */
            /* 45 */       for (int index = 0; index < container.inventorySlots.size(); index++) {
                /* 46 */         if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0)) {
                    /* 47 */           mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, (EntityPlayer)mc.player);
                    /* 48 */         } else if (isEmpty((Container)container)) {
                    /* 49 */           mc.player.closeScreen();
                    /*    */         }
                /*    */       }
            /*    */     }
        /*    */   }
    /*    */
    /*    */
    /*    */   public boolean isWhiteItem(ItemStack itemStack) {
        /* 57 */     return (itemStack.getItem() instanceof net.minecraft.item.ItemArmor || itemStack.getItem() instanceof net.minecraft.item.ItemEnderPearl || itemStack.getItem() instanceof net.minecraft.item.ItemSword || itemStack.getItem() instanceof net.minecraft.item.ItemTool || itemStack.getItem() instanceof net.minecraft.item.ItemFood || itemStack.getItem() instanceof net.minecraft.item.ItemPotion || itemStack.getItem() instanceof net.minecraft.item.ItemBlock || itemStack.getItem() instanceof net.minecraft.item.ItemArrow || itemStack.getItem() instanceof net.minecraft.item.ItemCompass);
        /*    */   }
    /*    */
    /*    */   private boolean isEmpty(Container container) {
        /* 61 */     for (int index = 0; index < container.inventorySlots.size(); index++) {
            /* 62 */       if (isWhiteItem(container.getSlot(index).getStack())) {
                /* 63 */         return false;
                /*    */       }
            /*    */     }
        /*    */
        /* 67 */     return true;
        /*    */   }
    /*    */ }
