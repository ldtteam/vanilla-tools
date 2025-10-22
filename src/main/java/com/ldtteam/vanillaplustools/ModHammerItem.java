package com.ldtteam.vanillaplustools;

import net.minecraft.world.item.Item;

import net.minecraft.world.item.ToolMaterial;

public class ModHammerItem extends Item
{
    /**
     * Setups the tool.
     *
     * @param tier           the tier of it.
     */
    public ModHammerItem(final ToolMaterial tier)
    {
        super(new Item.Properties().pickaxe(tier, 1.0F, -2.8F));
    }
}
