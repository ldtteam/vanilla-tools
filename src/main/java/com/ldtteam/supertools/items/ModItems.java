package com.ldtteam.supertools.items;

import com.ldtteam.supertools.api.util.constant.Constants;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

/**
 * Class handling the registering of the mod items.
 * <p>
 * We disabled the following finals since we are neither able to mark the items as final, nor do we want to provide public accessors.
 */
@SuppressWarnings({"squid:ClassVariableVisibilityCheck", "squid:S2444", "squid:S1444"})
@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModItems
{
    public static Item woodenHammer;

    public static Item stoneHammer;

    public static Item ironHammer;

    public static Item goldHammer;

    public static Item diaHammer;

    public static Item nethHammer;

    public static Item woodenShovel;

    public static Item stoneShovel;

    public static Item ironShovel;

    public static Item goldShovel;

    public static Item diaShovel;

    public static Item nethShovel;

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModItems()
    {
        /**
         * Intentionally left empty.
         */
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        ModItems.init(event.getRegistry());
    }

    /**
     * Initiates all the blocks at the correct time.
     * @param registry the registry!
     */
    public static void init(final IForgeRegistry<Item> registry)
    {
        woodenHammer = new HammerSuperTools(ItemTier.WOOD, 1, -2.8F);
        stoneHammer = new HammerSuperTools(ItemTier.STONE, 1, -2.8F);
        ironHammer = new HammerSuperTools(ItemTier.IRON, 1, -2.8F);
        goldHammer = new HammerSuperTools(ItemTier.GOLD, 1, -2.8F);
        diaHammer = new HammerSuperTools(ItemTier.DIAMOND, 1, -2.8F);
        nethHammer = new HammerSuperTools(ItemTier.NETHERITE, 1, -2.8F);

        woodenShovel = new ShovelSuperTools(ItemTier.WOOD, 1.5F, -3.0F);
        stoneShovel = new ShovelSuperTools(ItemTier.STONE, 1.5F, -3.0F);
        ironShovel = new ShovelSuperTools(ItemTier.IRON, 1.5F, -3.0F);
        goldShovel = new ShovelSuperTools(ItemTier.GOLD, 1.5F, -3.0F);
        diaShovel = new ShovelSuperTools(ItemTier.DIAMOND, 1.5F, -3.0F);
        nethShovel = new ShovelSuperTools(ItemTier.NETHERITE, 1.5F, -3.0F);

        registry.register(woodenHammer);
        registry.register(stoneHammer);
        registry.register(ironHammer);
        registry.register(goldHammer);
        registry.register(diaHammer);
        registry.register(nethHammer);

        registry.register(woodenShovel);
        registry.register(stoneShovel);
        registry.register(ironShovel);
        registry.register(goldShovel);
        registry.register(diaShovel);
        registry.register(nethShovel);

    }
}