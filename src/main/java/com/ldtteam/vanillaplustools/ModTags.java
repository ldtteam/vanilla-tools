package com.ldtteam.vanillaplustools;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags
{
    public static TagKey<Block> CAN_HAMMER = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("vanillaplustools", "can_hammer"));
    public static TagKey<Block> CAN_SHOVEL = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("vanillaplustools", "can_shovel"));
}
