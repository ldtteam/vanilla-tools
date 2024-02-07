package com.ldtteam.vanillaplustools;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VanillaPlusTools.MOD_ID)
public class VanillaPlusTools
{
    public static final String MOD_ID = "vanillaplustools";
    public static final Logger LOGGER = LogManager.getLogger(VanillaPlusTools.MOD_ID);

    public static final DeferredRegister<CreativeModeTab> TAB_REG       = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VanillaPlusTools.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VANILLA_PLUS_TAB = TAB_REG.register(MOD_ID, () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 1).withTabsBefore(
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
        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(VanillaPlusTools.class);

        TAB_REG.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void onNetworkRegistry(final RegisterPayloadHandlerEvent event)
    {
        final String modVersion = ModList.get().getModContainerById(MOD_ID).get().getModInfo().getVersion().toString();
        final IPayloadRegistrar registry = event.registrar(MOD_ID).versioned(modVersion);

        registry.play(BlockParticleEffectMessage.ID, BlockParticleEffectMessage::new, h -> h.client(BlockParticleEffectMessage::onExecute));
    }
}
