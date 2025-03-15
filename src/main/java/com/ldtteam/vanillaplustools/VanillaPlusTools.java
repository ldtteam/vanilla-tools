package com.ldtteam.vanillaplustools;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
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

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VANILLA_PLUS_TAB = TAB_REG.register(MOD_ID, () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 1).title(Component.literal("Vanilla+ Tools")).icon(() -> new ItemStack(ModItems.DIAMOND_HAMMER.get())).displayItems((config, builder) -> {
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
    public VanillaPlusTools(IEventBus bus)
    {
        NeoForge.EVENT_BUS.register(ModEvents.class);
        bus.register(VanillaPlusTools.class);

        TAB_REG.register(bus);
        ModItems.ITEMS.register(bus);
    }

    @SubscribeEvent
    public static void onNetworkSetup(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(BlockParticleEffectMessage.TYPE,
          StreamCodec.of((RegistryFriendlyByteBuf buf, BlockParticleEffectMessage packet) -> packet.write(buf), BlockParticleEffectMessage::new),
          BlockParticleEffectMessage::onExecute);
    }
}
