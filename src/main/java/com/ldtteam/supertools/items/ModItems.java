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
        woodenHammer = new HammerSuperTools(ItemTier.WOOD, 1, -2.8F);
        stoneHammer = new HammerSuperTools(ItemTier.STONE, 1, -2.8F);
        ironHammer = new HammerSuperTools(ItemTier.IRON, 1, -2.8F);
        goldHammer = new HammerSuperTools(ItemTier.GOLD, 1, -2.8F);
        diaHammer = new HammerSuperTools(ItemTier.DIAMOND, 1, -2.8F);

        woodenShovel = new ShovelSuperTools(ItemTier.WOOD, 1.5F, -3.0F);
        stoneShovel = new ShovelSuperTools(ItemTier.STONE, 1.5F, -3.0F);
        ironShovel = new ShovelSuperTools(ItemTier.IRON, 1.5F, -3.0F);
        goldShovel = new ShovelSuperTools(ItemTier.GOLD, 1.5F, -3.0F);
        diaShovel = new ShovelSuperTools(ItemTier.DIAMOND, 1.5F, -3.0F);

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