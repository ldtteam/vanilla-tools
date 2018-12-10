package com.supertools.creativetab;

import com.supertools.api.util.constant.Constants;
import com.supertools.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Class used to handle the creativeTab of SUPER_TOOLS.
 */
public final class ModCreativeTabs
{
    public static final CreativeTabs SUPER_TOOLS = new CreativeTabs(Constants.MOD_ID)
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.diaHammer);
        }

        @Override
        public boolean hasSearchBar()
        {
            return true;
        }
    };

    /**
     * Private constructor to hide the implicit one.
     */
    private ModCreativeTabs()
    {
        /*
         * Intentionally left empty.
         */
    }
}
