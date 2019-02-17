package com.ldtteam.supertools.items;

import com.ldtteam.supertools.api.util.constant.Constants;
import com.ldtteam.supertools.creativetab.ModCreativeTabs;
import net.minecraft.item.*;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

/**
 * Class handling the registering of the mod items.
 * <p>
 * We disabled the following finals since we are neither able to mark the items as final, nor do we want to provide public accessors.
 */
@SuppressWarnings({"squid:ClassVariableVisibilityCheck", "squid:S2444", "squid:S1444"})
public final class ModItems
{
    @ObjectHolder(Constants.MOD_ID + ":hammerwood")
    public static Item woodenHammer;

    @ObjectHolder(Constants.MOD_ID + ":hammerstone")
    public static Item stoneHammer;

    @ObjectHolder(Constants.MOD_ID + ":hammeriron")
    public static Item ironHammer;

    @ObjectHolder(Constants.MOD_ID + ":hammergold")
    public static Item goldHammer;

    @ObjectHolder(Constants.MOD_ID + ":hammerdiamond")
    public static Item diaHammer;

    @ObjectHolder(Constants.MOD_ID + ":shovelwood")
    public static Item woodenShovel;

    @ObjectHolder(Constants.MOD_ID + ":shovelstone")
    public static Item stoneShovel;

    @ObjectHolder(Constants.MOD_ID + ":shoveliron")
    public static Item ironShovel;

    @ObjectHolder(Constants.MOD_ID + ":shovelgold")
    public static Item goldShovel;

    @ObjectHolder(Constants.MOD_ID + ":shoveldiamond")
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