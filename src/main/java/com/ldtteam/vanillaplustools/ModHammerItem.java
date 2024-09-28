package com.ldtteam.vanillaplustools;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

public class ModHammerItem extends PickaxeItem
{
    /**
     * Setups the tool.
     *
     * @param tier           the tier of it.
     */
    public ModHammerItem(final Tier tier)
    {
        super(tier, new Item.Properties().attributes(PickaxeItem.createAttributes(tier, 1.0F, -2.8F)));
    }

    @Override
    public boolean canPerformAction(@NotNull final ItemStack stack, @NotNull final ItemAbility toolAction)
    {
        return toolAction == ItemAbilities.PICKAXE_DIG;
    }
}
