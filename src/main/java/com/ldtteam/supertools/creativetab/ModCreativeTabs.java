package com.ldtteam.supertools.creativetab;

import com.ldtteam.supertools.api.util.constant.Constants;
import com.ldtteam.supertools.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * Class used to handle the creativeTab of SUPER_TOOLS.
 */
public final class ModCreativeTabs
{
    public static final ItemGroup SUPER_TOOLS = new ItemGroup(Constants.MOD_ID)
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.diaHammer);
        }

        @Override
        public boolean hasSearchBar()
        {
            return false;
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
