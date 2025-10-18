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
    public ModHammerItem(Item.Properties props, ToolMaterial tier) {
        // set pickaxe behavior on the *passed* properties (which will already have the ID)
        super(props.pickaxe(tier, 1.0F, -2.8F));
    }
}
