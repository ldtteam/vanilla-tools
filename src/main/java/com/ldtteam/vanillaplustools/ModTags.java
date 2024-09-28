package com.ldtteam.vanillaplustools;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.ldtteam.vanillaplustools.VanillaPlusTools.MOD_ID;

public class ModTags
{
    public static TagKey<Block> CAN_HAMMER = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "can_hammer"));
    public static TagKey<Block> CAN_SHOVEL = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "can_shovel"));

    public static TagKey<Item> HAMMERS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "hammers"));
    public static TagKey<Item> SHOVELS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "shovels"));
}
