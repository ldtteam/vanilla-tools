package com.ldtteam.vanillaplustools;

import net.minecraft.world.item.ToolMaterial;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems
{
    public final static DeferredRegister.Items ITEMS = DeferredRegister.createItems(VanillaPlusTools.MOD_ID);
    // Hammers
    public static final DeferredItem<ModHammerItem> WOODEN_HAMMER = ITEMS.registerItem("wooden_hammer", props -> new ModHammerItem(props, ToolMaterial.WOOD));
    public static final DeferredItem<ModHammerItem> STONE_HAMMER = ITEMS.registerItem("stone_hammer", props -> new ModHammerItem(props, ToolMaterial.STONE));
    public static final DeferredItem<ModHammerItem> IRON_HAMMER = ITEMS.registerItem("iron_hammer", props -> new ModHammerItem(props, ToolMaterial.IRON));
    public static final DeferredItem<ModHammerItem> GOLD_HAMMER = ITEMS.registerItem("gold_hammer", props -> new ModHammerItem(props, ToolMaterial.GOLD));
    public static final DeferredItem<ModHammerItem> DIAMOND_HAMMER = ITEMS.registerItem("diamond_hammer", props -> new ModHammerItem(props, ToolMaterial.DIAMOND));
    public static final DeferredItem<ModHammerItem> NETHERITE_HAMMER = ITEMS.registerItem("netherite_hammer", props -> new ModHammerItem(props, ToolMaterial.NETHERITE));

    // Shovels
    public static final DeferredItem<ModShovelItem> WOODEN_SHOVEL = ITEMS.registerItem("wooden_shovel", props -> new ModShovelItem(props, ToolMaterial.WOOD));
    public static final DeferredItem<ModShovelItem> STONE_SHOVEL = ITEMS.registerItem("stone_shovel", props -> new ModShovelItem(props, ToolMaterial.STONE));
    public static final DeferredItem<ModShovelItem> IRON_SHOVEL = ITEMS.registerItem("iron_shovel", props -> new ModShovelItem(props, ToolMaterial.IRON));
    public static final DeferredItem<ModShovelItem> GOLD_SHOVEL = ITEMS.registerItem("gold_shovel", props -> new ModShovelItem(props, ToolMaterial.GOLD));
    public static final DeferredItem<ModShovelItem> DIAMOND_SHOVEL = ITEMS.registerItem("diamond_shovel", props -> new ModShovelItem(props, ToolMaterial.DIAMOND));
    public static final DeferredItem<ModShovelItem> NETHERITE_SHOVEL = ITEMS.registerItem("netherite_shovel", props -> new ModShovelItem(props, ToolMaterial.NETHERITE));
}
