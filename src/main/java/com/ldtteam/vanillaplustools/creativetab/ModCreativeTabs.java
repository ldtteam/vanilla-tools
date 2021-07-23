package com.ldtteam.vanillaplustools.creativetab;

import com.ldtteam.vanillaplustools.VanillaPlusTools;
import com.ldtteam.vanillaplustools.items.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Class used to handle the creativeTab of VANILLA_PLUS_TOOLS.
 */
public final class ModCreativeTabs
{
    public static final CreativeModeTab VANILLA_PLUS_TOOLS = new CreativeModeTab(VanillaPlusTools.MOD_ID)
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.DIAMOND_HAMMER.get());
        }

        @Override
        public boolean hasSearchBar()
        {
            return false;
        }
    };
}
