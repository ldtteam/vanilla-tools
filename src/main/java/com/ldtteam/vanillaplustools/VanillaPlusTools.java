package com.ldtteam.vanillaplustools;

import com.ldtteam.vanillaplustools.event.LifeCycleEvents;
import com.ldtteam.vanillaplustools.event.ModEvents;
import com.ldtteam.vanillaplustools.items.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VanillaPlusTools.MOD_ID)
public class VanillaPlusTools
{
    public static final String MOD_ID = "vanillaplustools";
    public static final Logger LOGGER = LogManager.getLogger(VanillaPlusTools.MOD_ID);

    /**
     * Constructor to initiate this.
     */
    public VanillaPlusTools()
    {
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(ModEvents.class);
        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(LifeCycleEvents.class);

        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
