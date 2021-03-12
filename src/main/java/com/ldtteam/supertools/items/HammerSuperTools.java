package com.ldtteam.supertools.items;

import com.ldtteam.supertools.creativetab.ModCreativeTabs;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraftforge.common.ToolType;

import java.util.Locale;

/**
 * Handles simple things that all items need.
 */
public class HammerSuperTools extends PickaxeItem
{
    /**
     * Setups the super tool.
     * @param tier the tier of it.
     * @param attackDamageIn the incoming attack damage.
     * @param attackSpeedIn the attack speed.
     */
    public HammerSuperTools(final IItemTier tier, final int attackDamageIn, float attackSpeedIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(ModCreativeTabs.SUPER_TOOLS).addToolType(ToolType.PICKAXE, tier.getLevel()));
        this.setRegistryName("hammer" + tier.toString().toLowerCase(Locale.ENGLISH));
    }
}
