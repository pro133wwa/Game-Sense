package Game.Sense.client.module;

import Game.Sense.client.module.feature.AAA.DeathCoordinates;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.module.feature.COMBAT.*;
import Game.Sense.client.module.feature.MOVEMENT.*;
import Game.Sense.client.module.feature.PLAYER.*;
import Game.Sense.client.module.feature.RENDER.*;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {

    public CopyOnWriteArrayList<Module> features = new CopyOnWriteArrayList<>();

    public ModuleManager() {

        /* HUD */
        features.add(new ModuleList());
        features.add(new Notifications());
        features.add(new TargetHUD());
        features.add(new MatrixExploitFly());
        features.add(new ClickTP());
        features.add(new NoOverlay());
        features.add(new ShulkerViewer());
        features.add(new PenisESP());
        features.add(new TimerSmart());
        features.add(new GappleSlot());
        features.add(new ClickGUI());
        features.add(new Hud());
        features.add(new ContainerBlur());
        features.add(new BackTrack());
        //features.add(new StaffList());
        /* MISC */
        features.add(new MiddleClickFriend());
        //features.add(new AirDrop());
        features.add(new ModuleSoundAlert());
        //features.add(new PlayerTracker());
        //features.add(new DeathSound());
        //features.add(new WaterDisabler());
        features.add(new ChatHistory());
        features.add(new BabyBoy());
        features.add(new NameProtect());
        features.add(new BetterChat());
        features.add(new Disabler());
        features.add(new AutoGrief());
        features.add(new DoubleJump());
        features.add(new Hotbar());
        /* COMBAT */
        features.add(new AppleGoldenTimer());
        features.add(new TargetStrafe());
        features.add(new NoFriendDamage());
        features.add(new TriggerBot());
        //features.add(new PushAttack());
        //features.add(new WinBulling());
        //features.add(new AntiCrystal());
        features.add(new AutoPotion());
        features.add(new HitSounds());
        //features.add(new AutoArmor());
        //features.add(new SuperItems());
        //features.add(new FastBow());
        //features.add(new AutoGapple());
        features.add(new AutoTotem());
        features.add(new KillAura());
        features.add(new KeepSprint());


        features.add(new Velocity());
        features.add(new AntiBot());
        features.add(new HitBox());
        /* MOVEMENT */
        //features.add(new AutoParkour());
        features.add(new KTLeave());
        //features.add(new ReallyWorldElytra());
        //features.add(new DamageFly());
        //features.add(new ElytraStrafe());
        //features.add(new Phase());
        //features.add(new WaterSpeed());
        //features.add(new HighJump());
        //features.add(new LongJump());
        features.add(new Spider());
        features.add(new AirJump());
        features.add(new Flight());
        features.add(new Button());
        features.add(new Radar());
        features.add(new Sprint());
        features.add(new Speed());
        features.add(new Jesus());
        features.add(new Strafe());
        features.add(new Timer());
        //features.add(new WaterDisabler());
        //features.add(new NoWeb());
        //features.add(new ElytraFly());


        //scam

        //features.add(new ElytraFix());
        //features.add(new XCarry());
        features.add(new FastWorldLoading());
        //features.add(new ElytraHighJump());
        //features.add(new TeleportBack());
        //features.add(new AutoCrystal());
        //features.add(new Criticals());
        //features.add(new GlideFly());
        //features.add(new FastPlace());

        /* PLAYER */
        features.add(new NoServerRotations());
        features.add(new MiddleClickPearl());
        features.add(new DeathCoordinates());
        features.add(new ItemScroller());
        //features.add(new BedrockClip());
        //features.add(new AntiAFK());
        //features.add(new AutoFarm());
        features.add(new GPS());
        features.add(new AutoTPAccept());
        features.add(new NoJumpDelay());
        features.add(new NoInteract());
        features.add(new NoSlowDown());
        //features.add(new AntiAim());
        features.add(new StaffAlert());
        features.add(new FreeCam());
        features.add(new AutoTool());
        features.add(new GuiWalk());
        features.add(new NoPush());
        features.add(new NoClip());
        //features.add(new NoFall());
        /* VISUALS */
        features.add(new Cosmetics());
        features.add(new ScoreboardFeatures());
        features.add(new DamageParticles());
        features.add(new SwingAnimations());
        features.add(new WorldFeatures());
        features.add(new JumpCircles());
        features.add(new ArmorHUD());
        //features.add(new EntityESP());
        features.add(new Chams());
        features.add(new FullBright());
        features.add(new CustomModel());
        features.add(new ItemPhysics());
        features.add(new TargetESP());
        features.add(new FogColor());

        //scam
        features.add(new Crosshair());


        features.add(new ChinaHat());
        features.add(new PearlESP());
        features.add(new NameTags());
        features.add(new Trails());
        //features.add(new ItemESP());
        //features.add(new BlockESP());
        features.add(new Tracers());
        features.add(new ViewModel());
        //features.add(new XRay());
        features.sort(Comparator.comparingInt(m -> Minecraft.getMinecraft().rubik_17.getStringWidth(((Module) m).getLabel())).reversed());

    }

    public List<Module> getAllFeatures() {
        return this.features;
    }

    public List<Module> getFeaturesCategory(ModuleCategory category) {
        List<Module> features = new ArrayList<>();
        for (Module feature : getAllFeatures()) {
            if (feature.getCategory() == category) {
                features.add(feature);
            }
        }
        return features;
    }

    public Module getFeature(Class<? extends Module> classFeature) {
        for (Module feature : getAllFeatures()) {
            if (feature != null) {
                if (feature.getClass() == classFeature) {
                    return feature;
                }
            }
        }
        return null;
    }

    public Module getFeature(String name) {
        for (Module feature : getAllFeatures()) {
            if (feature.getLabel().equals(name)) {
                return feature;
            }
        }
        return null;
    }

    public List<Module> getFeatureList() {
        return this.features;
    }
}