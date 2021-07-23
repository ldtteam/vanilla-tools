package com.ldtteam.vanillaplustools;

import com.ldtteam.vanillaplustools.coremod.network.NetworkChannel;
import com.ldtteam.vanillaplustools.event.ModEvents;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VanillaPlusTools.MOD_ID)
public class VanillaPlusTools
{
    public static final String MOD_ID = "vanillaplustools";
    public static final Logger LOGGER = LogManager.getLogger(VanillaPlusTools.MOD_ID);
    public static final NetworkChannel NETWORK_CHANNEL = new NetworkChannel("net-channel");

    /**
     * Constructor to initiate this.
     */
    public VanillaPlusTools()
    {
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(ModEvents.class);
    }
}
