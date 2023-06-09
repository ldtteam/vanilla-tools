package com.ldtteam.vanillaplustools;

import com.ldtteam.vanillaplustools.event.LifeCycleEvents;
import com.ldtteam.vanillaplustools.event.ModEvents;
import com.ldtteam.vanillaplustools.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VanillaPlusTools.MOD_ID)
public class VanillaPlusTools
{
    public static final String MOD_ID = "vanillaplustools";
    public static final Logger LOGGER = LogManager.getLogger(VanillaPlusTools.MOD_ID);

    public static final DeferredRegister<CreativeModeTab> TAB_REG       = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VanillaPlusTools.MOD_ID);

    public static final RegistryObject<CreativeModeTab> VANILLA_PLUS_TAB = TAB_REG.register(MOD_ID, () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 1).withTabsBefore(
      CreativeModeTabs.SEARCH).icon(() -> new ItemStack(ModItems.DIAMOND_HAMMER.get())).displayItems((config, builder) -> {
        builder.accept(ModItems.WOODEN_HAMMER.get());
        builder.accept(ModItems.STONE_HAMMER.get());
        builder.accept(ModItems.IRON_HAMMER.get());
        builder.accept(ModItems.GOLD_HAMMER.get());
        builder.accept(ModItems.DIAMOND_HAMMER.get());
        builder.accept(ModItems.NETHERITE_HAMMER.get());

        builder.accept(ModItems.WOODEN_SHOVEL.get());
        builder.accept(ModItems.STONE_SHOVEL.get());
        builder.accept(ModItems.IRON_SHOVEL.get());
        builder.accept(ModItems.GOLD_SHOVEL.get());
        builder.accept(ModItems.DIAMOND_SHOVEL.get());
        builder.accept(ModItems.NETHERITE_SHOVEL.get());
    }).build());

    /**
     * Constructor to initiate this.
     */
    public VanillaPlusTools()
    {
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(ModEvents.class);
        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(LifeCycleEvents.class);
        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(VanillaPlusTools.class);
        TAB_REG.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
