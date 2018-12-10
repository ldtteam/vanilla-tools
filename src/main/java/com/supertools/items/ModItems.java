package com.supertools.items;

import net.minecraft.item.Item;
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
     */
    public static void init(final IForgeRegistry<Item> registry)
    {
        woodenHammer = new ItemHammer(Item.ToolMaterial.WOOD);
        stoneHammer = new ItemHammer(Item.ToolMaterial.STONE);
        ironHammer = new ItemHammer(Item.ToolMaterial.IRON);
        goldHammer = new ItemHammer(Item.ToolMaterial.GOLD);
        diaHammer = new ItemHammer(Item.ToolMaterial.DIAMOND);

        woodenShovel = new ItemShovel(Item.ToolMaterial.WOOD);
        stoneShovel = new ItemShovel(Item.ToolMaterial.STONE);
        ironShovel = new ItemShovel(Item.ToolMaterial.IRON);
        goldShovel = new ItemShovel(Item.ToolMaterial.GOLD);
        diaShovel = new ItemShovel(Item.ToolMaterial.DIAMOND);

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