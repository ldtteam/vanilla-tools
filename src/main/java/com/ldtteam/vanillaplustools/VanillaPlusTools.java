package com.ldtteam.vanillaplustools;

import com.ldtteam.vanillaplustools.event.LifeCycleEvents;
import com.ldtteam.vanillaplustools.event.ModEvents;
import com.ldtteam.vanillaplustools.items.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VanillaPlusTools.MOD_ID)
public class VanillaPlusTools
{
    public static final String MOD_ID = "vanillaplustools";
    public static final Logger LOGGER = LogManager.getLogger(VanillaPlusTools.MOD_ID);

    private static final ResourceLocation CREATIVE_TAB = new ResourceLocation(MOD_ID, "racks");

    /**
     * Constructor to initiate this.
     */
    public VanillaPlusTools()
    {
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(ModEvents.class);
        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(LifeCycleEvents.class);
        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(VanillaPlusTools.class);

        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void creativeTabEvent(final CreativeModeTabEvent.Register event)
    {
        event.registerCreativeModeTab(CREATIVE_TAB, (cf) -> cf.icon(() -> new ItemStack(ModItems.DIAMOND_HAMMER.get())).displayItems((flagSet, output) -> {
            output.accept(ModItems.WOODEN_HAMMER.get());
            output.accept(ModItems.STONE_HAMMER.get());
            output.accept(ModItems.IRON_HAMMER.get());
            output.accept(ModItems.GOLD_HAMMER.get());
            output.accept(ModItems.DIAMOND_HAMMER.get());
            output.accept(ModItems.NETHERITE_HAMMER.get());

            output.accept(ModItems.WOODEN_SHOVEL.get());
            output.accept(ModItems.STONE_SHOVEL.get());
            output.accept(ModItems.IRON_SHOVEL.get());
            output.accept(ModItems.GOLD_SHOVEL.get());
            output.accept(ModItems.DIAMOND_SHOVEL.get());
            output.accept(ModItems.NETHERITE_SHOVEL.get());
        }));
    }
}
