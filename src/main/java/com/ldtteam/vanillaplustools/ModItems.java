package com.ldtteam.vanillaplustools;

import net.minecraft.world.item.Tiers;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems
{
    public final static DeferredRegister.Items ITEMS = DeferredRegister.createItems(VanillaPlusTools.MOD_ID);
    public static final DeferredItem<ModHammerItem> WOODEN_HAMMER = ITEMS.register("wooden_hammer", () -> new ModHammerItem(Tiers.WOOD));
    public static final DeferredItem<ModHammerItem> STONE_HAMMER = ITEMS.register("stone_hammer", () -> new ModHammerItem(Tiers.STONE));
    public static final DeferredItem<ModHammerItem> IRON_HAMMER  = ITEMS.register("iron_hammer", () -> new ModHammerItem(Tiers.IRON));
    public static final DeferredItem<ModHammerItem> GOLD_HAMMER  = ITEMS.register("gold_hammer", () -> new ModHammerItem(Tiers.GOLD));
    public static final DeferredItem<ModHammerItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new ModHammerItem(Tiers.DIAMOND));
    public static final DeferredItem<ModHammerItem> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new ModHammerItem(Tiers.NETHERITE));

    public static final DeferredItem<ModShovelItem> WOODEN_SHOVEL = ITEMS.register("wooden_shovel", () -> new ModShovelItem(Tiers.WOOD));
    public static final DeferredItem<ModShovelItem> STONE_SHOVEL = ITEMS.register("stone_shovel", () -> new ModShovelItem(Tiers.STONE));
    public static final DeferredItem<ModShovelItem> IRON_SHOVEL = ITEMS.register("iron_shovel", () -> new ModShovelItem(Tiers.IRON));
    public static final DeferredItem<ModShovelItem> GOLD_SHOVEL = ITEMS.register("gold_shovel", () -> new ModShovelItem(Tiers.GOLD));
    public static final DeferredItem<ModShovelItem> DIAMOND_SHOVEL = ITEMS.register("diamond_shovel", () -> new ModShovelItem(Tiers.DIAMOND));
    public static final DeferredItem<ModShovelItem> NETHERITE_SHOVEL = ITEMS.register("netherite_shovel", () -> new ModShovelItem(Tiers.NETHERITE));
}
