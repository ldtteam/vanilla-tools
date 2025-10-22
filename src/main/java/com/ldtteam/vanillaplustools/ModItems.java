package com.ldtteam.vanillaplustools;

import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems
{
    public final static DeferredRegister.Items ITEMS = DeferredRegister.createItems(VanillaPlusTools.MOD_ID);
    public static final DeferredItem<ModHammerItem> WOODEN_HAMMER = ITEMS.registerItem("wooden_hammer", prop -> new ModHammerItem(prop, ToolMaterial.WOOD));
    public static final DeferredItem<ModHammerItem> STONE_HAMMER = ITEMS.registerItem("stone_hammer", prop -> new ModHammerItem(prop, ToolMaterial.STONE));
    public static final DeferredItem<ModHammerItem> IRON_HAMMER  = ITEMS.registerItem("iron_hammer", prop -> new ModHammerItem(prop, ToolMaterial.IRON));
    public static final DeferredItem<ModHammerItem> GOLD_HAMMER  = ITEMS.registerItem("gold_hammer", prop -> new ModHammerItem(prop, ToolMaterial.GOLD));
    public static final DeferredItem<ModHammerItem> DIAMOND_HAMMER = ITEMS.registerItem("diamond_hammer", prop -> new ModHammerItem(prop, ToolMaterial.DIAMOND));
    public static final DeferredItem<ModHammerItem> NETHERITE_HAMMER = ITEMS.registerItem("netherite_hammer", prop -> new ModHammerItem(prop, ToolMaterial.NETHERITE));

    public static final DeferredItem<ModShovelItem> WOODEN_SHOVEL = ITEMS.registerItem("wooden_shovel", prop -> new ModShovelItem(prop, ToolMaterial.WOOD));
    public static final DeferredItem<ModShovelItem> STONE_SHOVEL = ITEMS.registerItem("stone_shovel", prop -> new ModShovelItem(prop, ToolMaterial.STONE));
    public static final DeferredItem<ModShovelItem> IRON_SHOVEL = ITEMS.registerItem("iron_shovel", prop -> new ModShovelItem(prop, ToolMaterial.IRON));
    public static final DeferredItem<ModShovelItem> GOLD_SHOVEL = ITEMS.registerItem("gold_shovel", prop -> new ModShovelItem(prop, ToolMaterial.GOLD));
    public static final DeferredItem<ModShovelItem> DIAMOND_SHOVEL = ITEMS.registerItem("diamond_shovel", prop -> new ModShovelItem(prop, ToolMaterial.DIAMOND));
    public static final DeferredItem<ModShovelItem> NETHERITE_SHOVEL = ITEMS.registerItem("netherite_shovel", prop -> new ModShovelItem(prop, ToolMaterial.NETHERITE));
}
