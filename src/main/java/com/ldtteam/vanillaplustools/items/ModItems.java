package com.ldtteam.vanillaplustools.items;

import com.ldtteam.vanillaplustools.VanillaPlusTools;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(modid = VanillaPlusTools.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VanillaPlusTools.MOD_ID);

    public static final RegistryObject<ModHammerItem> WOODEN_HAMMER = ITEMS.register("wooden_hammer", () -> new ModHammerItem(Tiers.WOOD, 1, -2.8F));
    public static final RegistryObject<ModHammerItem> STONE_HAMMER = ITEMS.register("stone_hammer", () -> new ModHammerItem(Tiers.STONE, 1, -2.8F));
    public static final RegistryObject<ModHammerItem> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new ModHammerItem(Tiers.IRON, 1, -2.8F));
    public static final RegistryObject<ModHammerItem> GOLD_HAMMER = ITEMS.register("gold_hammer", () -> new ModHammerItem(Tiers.GOLD, 1, -2.8F));
    public static final RegistryObject<ModHammerItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new ModHammerItem(Tiers.DIAMOND, 1, -2.8F));
    public static final RegistryObject<ModHammerItem> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new ModHammerItem(Tiers.NETHERITE, 1, -2.8F));

    public static final RegistryObject<ModShovelItem> WOODEN_SHOVEL = ITEMS.register("wooden_shovel", () -> new ModShovelItem(Tiers.WOOD, 1.5F, -3.0F));
    public static final RegistryObject<ModShovelItem> STONE_SHOVEL = ITEMS.register("stone_shovel", () -> new ModShovelItem(Tiers.STONE, 1.5F, -3.0F));
    public static final RegistryObject<ModShovelItem> IRON_SHOVEL = ITEMS.register("iron_shovel", () -> new ModShovelItem(Tiers.IRON, 1.5F, -3.0F));
    public static final RegistryObject<ModShovelItem> GOLD_SHOVEL = ITEMS.register("gold_shovel", () -> new ModShovelItem(Tiers.GOLD, 1.5F, -3.0F));
    public static final RegistryObject<ModShovelItem> DIAMOND_SHOVEL = ITEMS.register("diamond_shovel", () -> new ModShovelItem(Tiers.DIAMOND, 1.5F, -3.0F));
    public static final RegistryObject<ModShovelItem> NETHERITE_SHOVEL = ITEMS.register("netherite_shovel", () -> new ModShovelItem(Tiers.NETHERITE, 1.5F, -3.0F));
}
