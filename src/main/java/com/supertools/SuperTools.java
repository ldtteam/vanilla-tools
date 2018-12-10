package com.supertools;

import com.supertools.api.util.constant.Constants;
import com.supertools.coremod.network.BlockParticleEffectMessage;
import com.supertools.event.FMLEventHandler;
import com.supertools.proxy.IProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION, dependencies="after:gbook",
  /*dependencies = Constants.FORGE_VERSION,*/ acceptedMinecraftVersions = Constants.MC_VERSION)
public class SuperTools
{
    private static final Logger     logger = LogManager.getLogger(Constants.MOD_ID);
    /**
     * Forge created instance of the Mod.
     */
    @Mod.Instance(Constants.MOD_ID)
    public static        SuperTools instance;
    /**
     * Access to the proxy associated with your current side. Variable updated
     * by forge.
     */
    @SidedProxy(clientSide = Constants.CLIENT_PROXY_LOCATION, serverSide = Constants.SERVER_PROXY_LOCATION)

    public static        IProxy     proxy;

    private static SimpleNetworkWrapper network;

    static
    {
        MinecraftForge.EVENT_BUS.register(new FMLEventHandler());
    }

    /**
     * Returns whether the side is client or not
     *
     * @return True when client, otherwise false
     */
    public static boolean isClient()
    {
        return proxy.isClient() && FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    /**
     * Returns whether the side is server or not
     *
     * @return True when server, otherwise false
     */
    public static boolean isServer()
    {
        return !proxy.isClient() && FMLCommonHandler.instance().getEffectiveSide().isServer();
    }

    /**
     * Getter for the SUPER_TOOLS Logger.
     *
     * @return the logger.
     */
    public static Logger getLogger()
    {
        return logger;
    }

    /**
     * Event handler for forge pre init event.
     *
     * @param event the forge pre init event.
     */
    @Mod.EventHandler
    public void preInit(@NotNull final FMLPreInitializationEvent event)
    {
        proxy.registerEntities();
        proxy.registerEntityRendering();
        proxy.registerEvents();

        @NotNull final Configuration configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    /**
     * Event handler for forge init event.
     *
     * @param event the forge init event.
     */
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event)
    {
        initializeNetwork();

        proxy.registerEvents();

        proxy.registerRenderer();
    }

    private static synchronized void initializeNetwork()
    {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MOD_NAME);
        getNetwork().registerMessage(BlockParticleEffectMessage.class, BlockParticleEffectMessage.class, 0, Side.CLIENT);

    }

    public static SimpleNetworkWrapper getNetwork()
    {
        return network;
    }
}