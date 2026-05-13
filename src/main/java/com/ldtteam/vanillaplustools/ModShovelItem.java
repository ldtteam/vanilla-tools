package com.ldtteam.vanillaplustools;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

public class ModShovelItem extends ShovelItem
{
    /**
     * Setups the tool.
     *
     * @param prop
     * @param tier the tier of it.
     */
    public ModShovelItem(final Properties prop, final ToolMaterial tier)
    {
        super(tier, 1.5F, -3.0F, prop);
    }

    @Override
    public boolean canPerformAction(final ItemInstance stack, final ItemAbility itemAbility)
    {
        return ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility);
    }

    @NotNull
    @Override
    public InteractionResult useOn(final UseOnContext context)
    {
        if (!context.getLevel().isClientSide())
        {
            final ItemStack item = context.getItemInHand();
            if (item.getItem() instanceof ModShovelItem)
            {
                final Player player = context.getPlayer();
                final Level level = context.getLevel();
                if (super.useOn(context) == InteractionResult.CONSUME && player != null)
                {
                    if (player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), context.getItemInHand()))
                    {
                        for (final BlockPos pos : ModEvents.getAffectedPos(player))
                        {
                            BlockState blockstate = FLATTENABLES.get(level.getBlockState(pos).getBlock());
                            if (level.getBlockState(pos.above()).isAir() && blockstate != null)
                            {
                                BlockState state = Blocks.DIRT_PATH.defaultBlockState();
                                level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                                level.setBlock(pos, state, 11);
                            }
                        }
                        context.getItemInHand().hurtAndBreak(1, player, context.getItemInHand().getEquipmentSlot());
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }
}
