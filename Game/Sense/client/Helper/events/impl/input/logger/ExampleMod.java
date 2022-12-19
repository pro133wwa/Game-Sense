package Game.Sense.client.Helper.events.impl.input.logger;

import Game.Sense.client.Helper.EventTarget;
import net.minecraft.init.Blocks;

import org.apache.logging.log4j.Logger;
import Game.Sense.client.Helper.events.impl.input.logger.config.cfg;
import static optifine.Reflector.MinecraftForge;


public class ExampleMod
{
    public static final String MODID = "loggerMod";
    public static final String NAME = "Logger";
    public static final String VERSION = "1.0";

    private static Logger logger;

//    @EventTarget
//    public void preInit(FMLPreInitializationEvent event)
//    {
//        logger = event.getModLog();
//    }

    @EventTarget
    public void init() {

    }
//    public void init(FMLInitializationEvent event) {
//        Connection.startup();
//        MinecraftForge.EVENT_BUS.register(new log());
//        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
//    }
}
