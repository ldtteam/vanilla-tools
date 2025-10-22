package com.ldtteam.vanillaplustools;

import net.minecraft.world.item.Item;

import net.minecraft.world.item.ToolMaterial;

public class ModHammerItem extends Item
{
    /**
     * Setups the tool.
     *
     * @param prop
     * @param tier the tier of it.
     */
    public ModHammerItem(final Properties prop, final ToolMaterial tier)
    {
        super(prop.pickaxe(tier, 1.0F, -2.8F));
    }
}
