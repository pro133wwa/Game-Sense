package Game.Sense.client.UI.Minecraft.ALT;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.util.text.TextFormatting;

public final class AltLoginThread extends Thread {
   private final String password;
   private String status;
   private final String username;
   private String randomName;
   private final Minecraft mc = Minecraft.getMinecraft();

   public AltLoginThread(String username, String password) {
      super("Alt Login Thread");
      this.username = username;
      this.password = password;
      this.status = TextFormatting.GRAY + "Waiting...";
   }

   private Session createSession(String username, String password) {
      YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
      YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
      auth.setUsername(username);
      auth.setPassword(password);

      try {
         auth.logIn();
         return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
      } catch (AuthenticationException var6) {
         var6.printStackTrace();
         return null;
      }
   }

   public String getStatus() {
      return this.status;
   }

   public void run() {
      Session auth;
      if (this.password.equals("")) {
         this.mc.session = new Session(this.username, "", "", "mojang");
         auth = this.createSession(this.username, this.password);
         this.status = TextFormatting.WHITE + "Your name is: " + ChatFormatting.GRAY + this.username;
      } else {
         this.status = TextFormatting.YELLOW + "Logging in...";
         auth = this.createSession(this.username, this.password);
         if (auth == null) {
            this.status = TextFormatting.RED + "Login failed!";
         } else {
            TextFormatting var10001 = TextFormatting.WHITE;
            this.status = var10001 + "ur nickname - " + ChatFormatting.GRAY + auth.getUsername();
            this.mc.session = auth;
         }

      }
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
