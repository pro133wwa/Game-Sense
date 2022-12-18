package Game.Sense.client.ui.altmanager;



import java.awt.Color;
import java.io.IOException;
import java.security.SecureRandom;


import Game.Sense.client.GameSense;
import Game.Sense.client.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public final class GuiAlt extends GuiScreen {
   private PasswordField password;
   private GuiScreen previousScreen = null;
   private AltLoginThread thread;
   private GuiTextField username;
   private final long initTime = System.currentTimeMillis();
   private static String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
   private static final SecureRandom secureRandom = new SecureRandom();

   public GuiAlt() {
      this.previousScreen = this.previousScreen;
   }

   public static String randomString(int strLength) {
      StringBuilder stringBuilder = new StringBuilder(strLength);

      for(int i = 0; i < strLength; ++i) {
         stringBuilder.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
      }

      return stringBuilder.toString();
   }

   protected void actionPerformed(GuiButton button) {
      switch (button.id) {
         case 0:
            this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
            this.thread.start();
            break;
         case 2:
            this.thread = new AltLoginThread("GameUser" + randomString(5), "");
            this.thread.start();
      }

   }

   public void drawScreen(int x2, int y2, float z2) {
      new ScaledResolution(this.mc);
      int lol = this.height / 4 + 24;
      new ScaledResolution(this.mc);
      //Gui.drawRect(0.0, 0.0, 960.0, 540.0, (new Color(14, 14, 14)).getRGB());
      //Gui.drawRect(0.0, 0.0, 1500.0, 1500.0, (new Color(45, 45, 45)).getRGB());
      RenderUtils.drawBorderedRect((double)(this.width / 2 - 70), (double)(lol - 21), (double)(this.width / 2 + 60), (double)(lol + 110), 0.5, (new Color(16, 16, 16, 255)).getRGB(), (new Color(60, 60, 60, 255)).getRGB(), true);
      RenderUtils.drawGradientSideways((double)(this.width / 2 - 69), (double)(lol - 20), (double)(this.width / 2 + 59), (double)(lol - 19), GameSense.getClientColor().getRGB(), GameSense.getClientColor().getRGB());
      this.username.drawTextBox();
      this.password.drawTextBox();
      this.mc.neverlose500_18.drawCenteredString("AltManager", (float)(this.width / 2 + -6), (float)lol, -1);
      this.mc.neverlose500_15.drawCenteredString(this.thread == null ? "" + TextFormatting.GRAY : this.thread.getStatus(), (float)(this.width / 2 - 5), (float)(lol + 98), -1);
      if (this.username.getText().isEmpty() && !this.username.isFocused()) {
         this.mc.neverlose500_18.drawStringWithShadow("Type email...", (double)(this.width / 2 - 52), (double)(lol + 23), -7829368);
      }

      if (this.password.getText().isEmpty() && !this.password.isFocused()) {
         this.mc.neverlose500_18.drawStringWithShadow("Type pass...", (double)(this.width / 2 - 52), (double)(lol + 43), -7829368);
      }

      super.drawScreen(x2, y2, z2);
   }

   public void initGui() {
      int lol = this.height / 4 + 24;
      this.buttonList.add(new GuiButton(0, this.width / 2 - 50, lol + 60, 90, 13, "Login"));
      this.buttonList.add(new GuiButton(2, this.width / 2 - 50, lol + 64 + 12, 90, 13, "Random name"));
      this.username = new GuiTextField(lol, Minecraft.fontRendererObj, this.width / 2 - 55, lol + 20, 100, 13);
      this.password = new PasswordField(Minecraft.fontRendererObj, this.width / 2 - 55, lol + 40, 100, 13);
      this.username.setFocused(true);
      Keyboard.enableRepeatEvents(true);
   }

   protected void keyTyped(char character, int key) {
      try {
         super.keyTyped(character, key);
      } catch (IOException var4) {
      }

      if (character == '\t') {
         if (!this.username.isFocused() && !this.password.isFocused()) {
            this.username.setFocused(true);
         } else {
            this.username.setFocused(this.password.isFocused());
            this.password.setFocused(!this.username.isFocused());
         }
      }

      if (character == '\r') {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }

      this.username.textboxKeyTyped(character, key);
      this.password.textboxKeyTyped(character, key);
   }

   public void mouseClicked(int x2, int y2, int button) {
      try {
         super.mouseClicked(x2, y2, button);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      this.username.mouseClicked(x2, y2, button);
      this.password.mouseClicked(x2, y2, button);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void updateScreen() {
      this.username.updateCursorCounter();
      this.password.updateCursorCounter();
   }

   static {
      String var10000 = String.valueOf(alphabet);
      alphabet = var10000 + alphabet.toLowerCase();
   }
}
