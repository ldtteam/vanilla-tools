package com.ldtteam.supertools.items;

import com.ldtteam.supertools.creativetab.ModCreativeTabs;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.common.ToolType;

import java.util.Locale;

/**
 * Handles simple things that all items need.
 */
public class ShovelSuperTools extends ItemSpade
{
    /**
     * Setups the super tool.
     * @param tier the tier of it.
     * @param attackDamageIn the incoming attack damage.
     * @param attackSpeedIn the attack speed.
     */
    public ShovelSuperTools(final IItemTier tier, final float attackDamageIn, float attackSpeedIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ModCreativeTabs.SUPER_TOOLS).addToolType(ToolType.SHOVEL, tier.getHarvestLevel()));
        this.setRegistryName("shovel" + tier.toString().toLowerCase(Locale.ENGLISH));
    }
}
