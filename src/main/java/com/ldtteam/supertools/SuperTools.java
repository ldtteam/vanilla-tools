package com.ldtteam.supertools;

import com.ldtteam.supertools.api.util.constant.Constants;
import com.ldtteam.supertools.coremod.network.NetworkChannel;
import com.ldtteam.supertools.event.LifecycleSubscriber;
import com.ldtteam.supertools.event.WorldEvents;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.MOD_ID)
public class SuperTools
{
    private static final Logger logger = LogManager.getLogger(Constants.MOD_ID);

    /**
     * Creation of the channel.
     */
    private static NetworkChannel network = new NetworkChannel("net-channel");

    /**
     * Constructor to initiate this.
     */
    public SuperTools()
    {
        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(LifecycleSubscriber.class);
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(WorldEvents.class);
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
     * Get the network handler.
     * @return the network handler.
     */
    public static NetworkChannel getNetwork()
    {
        return network;
    }
}