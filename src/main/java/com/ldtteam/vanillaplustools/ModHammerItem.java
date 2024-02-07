package com.ldtteam.vanillaplustools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

public class ModHammerItem extends PickaxeItem
{
    /**
     * Setups the tool.
     *
     * @param tier           the tier of it.
     * @param attackDamageIn the incoming attack damage.
     * @param attackSpeedIn  the attack speed.
     */
    public ModHammerItem(final Tier tier, final int attackDamageIn, float attackSpeedIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties());
    }

    @Override
    public boolean canPerformAction(@NotNull final ItemStack stack, @NotNull final ToolAction toolAction)
    {
        return toolAction == ToolActions.PICKAXE_DIG;
    }
}
