package com.ldtteam.supertools.items;

import com.ldtteam.supertools.creativetab.ModCreativeTabs;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

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
     * @param builder the builder.
     */
    public ShovelSuperTools(final IItemTier tier, final int attackDamageIn, float attackSpeedIn, final Item.Properties builder)
    {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.setRegistryName("hammer" + tier.toString());
        final NonNullList<ItemStack> item = NonNullList.create();
        item.add(new ItemStack(this));
        fillItemGroup(ModCreativeTabs.SUPER_TOOLS, item);
    }
}
