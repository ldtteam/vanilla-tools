package com.ldtteam.supertools.items;

import com.ldtteam.supertools.creativetab.ModCreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Class handling the registering of the mod items.
 * <p>
 * We disabled the following finals since we are neither able to mark the items as final, nor do we want to provide public accessors.
 */
@SuppressWarnings({"squid:ClassVariableVisibilityCheck", "squid:S2444", "squid:S1444"})
public final class ModItems
{
    public static Item woodenHammer;
    public static Item stoneHammer;
    public static Item ironHammer;
    public static Item goldHammer;
    public static Item diaHammer;

    public static Item woodenShovel;
    public static Item stoneShovel;
    public static Item ironShovel;
    public static Item goldShovel;
    public static Item diaShovel;

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModItems()
    {
        /*
         * Intentionally left empty.
         */
    }

    /**
     * Initates all the blocks. At the correct time.
     * @param registry the registry!
     */
    public static void init(final IForgeRegistry<Item> registry)
    {
        final Item.Properties properties = new Item.Properties().group(ModCreativeTabs.SUPER_TOOLS);
        woodenHammer = new HammerSuperTools(ItemTier.WOOD, (int) ItemTier.WOOD.getAttackDamage(), 0, properties);
        stoneHammer = new HammerSuperTools(ItemTier.STONE, (int) ItemTier.STONE.getAttackDamage(), 0, properties);
        ironHammer = new HammerSuperTools(ItemTier.IRON, (int) ItemTier.IRON.getAttackDamage(), 0, properties);
        goldHammer = new HammerSuperTools(ItemTier.GOLD, (int) ItemTier.GOLD.getAttackDamage(), 0, properties);
        diaHammer = new HammerSuperTools(ItemTier.DIAMOND, (int) ItemTier.DIAMOND.getAttackDamage(), 0, properties);

        woodenShovel = new ShovelSuperTools(ItemTier.WOOD, (int) ItemTier.WOOD.getAttackDamage(), 0, properties);
        stoneShovel = new ShovelSuperTools(ItemTier.STONE, (int) ItemTier.STONE.getAttackDamage(), 0, properties);
        ironShovel = new ShovelSuperTools(ItemTier.IRON, (int) ItemTier.IRON.getAttackDamage(), 0, properties);
        goldShovel = new ShovelSuperTools(ItemTier.GOLD, (int) ItemTier.GOLD.getAttackDamage(), 0, properties);
        diaShovel = new ShovelSuperTools(ItemTier.DIAMOND, (int) ItemTier.DIAMOND.getAttackDamage(), 0, properties);

        registry.register(woodenHammer);
        registry.register(stoneHammer);
        registry.register(ironHammer);
        registry.register(goldHammer);
        registry.register(diaHammer);

        registry.register(woodenShovel);
        registry.register(stoneShovel);
        registry.register(ironShovel);
        registry.register(goldShovel);
        registry.register(diaShovel);

    }
}