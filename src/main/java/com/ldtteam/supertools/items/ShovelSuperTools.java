package com.ldtteam.supertools.items;

import com.ldtteam.supertools.creativetab.ModCreativeTabs;
import com.ldtteam.supertools.event.WorldEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Handles simple things that all items need.
 */
public class ShovelSuperTools extends ShovelItem
{
    /**
     * Setups the super tool.
     * @param tier the tier of it.
     * @param attackDamageIn the incoming attack damage.
     * @param attackSpeedIn the attack speed.
     */
    public ShovelSuperTools(final IItemTier tier, final float attackDamageIn, float attackSpeedIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(ModCreativeTabs.SUPER_TOOLS).addToolType(ToolType.SHOVEL, tier.getLevel()));
        this.setRegistryName("shovel" + tier.toString().toLowerCase(Locale.ENGLISH));
    }

    @NotNull
    @Override
    public ActionResultType useOn(final ItemUseContext context)
    {
        if (!context.getLevel().isClientSide)
        {
            final ItemStack item = context.getItemInHand();
            if (item.getItem() instanceof ShovelSuperTools)
            {
                final PlayerEntity player = context.getPlayer();
                final World world = context.getLevel();
                if (super.useOn(context) == ActionResultType.CONSUME && player != null)
                {
                    if (player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), context.getItemInHand()))
                    {
                        for (final BlockPos pos : WorldEvents.getAffectedPos(player))
                        {
                            BlockState blockstate = FLATTENABLES.get(world.getBlockState(pos).getBlock());
                            if (world.getBlockState(pos.above()).isAir(world, pos.above()) && blockstate != null)
                            {
                                BlockState iblockstate1 = Blocks.GRASS_PATH.defaultBlockState();
                                world.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                world.setBlock(pos, iblockstate1, 11);
                            }
                        }
                        context.getItemInHand().hurtAndBreak(1, player, (p) ->
                        {
                            p.broadcastBreakEvent(context.getHand());
                        });
                        return ActionResultType.CONSUME;
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }
}
