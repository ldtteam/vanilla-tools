package com.ldtteam.supertools.event;

import com.ldtteam.supertools.SuperTools;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Class with methods for receiving various forge events.
 * Methods are sorted according to time of execution.
 */
public class LifecycleSubscriber
{
    /**
     * Private constructor to hide implicit public one.
     */
    private LifecycleSubscriber()
    {
        /*
         * Intentionally left empty
         */
    }

    @SubscribeEvent
    public static void onModInit(final FMLCommonSetupEvent event)
    {
        SuperTools.getLogger().warn("FMLCommonSetupEvent");
        SuperTools.getNetwork().registerCommonMessages();
    }
}
