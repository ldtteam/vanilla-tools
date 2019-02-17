package com.ldtteam.supertools.items;

import com.ldtteam.supertools.api.util.constant.Constants;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;

import java.util.Locale;

/**
 * Handles simple things that all items need.
 */
public class HammerSuperTools extends ItemPickaxe
{
    /**
     * Setups the super tool.
     * @param tier the tier of it.
     * @param attackDamageIn the incoming attack damage.
     * @param attackSpeedIn the attack speed.
     * @param builder the builder.
     */
    public HammerSuperTools(final IItemTier tier, final int attackDamageIn, float attackSpeedIn, final Item.Properties builder)
    {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.setRegistryName(new ResourceLocation(Constants.MOD_ID, "hammer" + tier.toString().toLowerCase(Locale.ENGLISH)));
    }
}
