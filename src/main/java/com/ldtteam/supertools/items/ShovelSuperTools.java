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
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ModCreativeTabs.SUPER_TOOLS).addToolType(ToolType.SHOVEL, tier.getHarvestLevel()));
        this.setRegistryName("shovel" + tier.toString().toLowerCase(Locale.ENGLISH));
    }

    @NotNull
    @Override
    public ActionResultType onItemUse(final ItemUseContext context)
    {
        if (!context.getWorld().isRemote)
        {
            final ItemStack item = context.getItem();
            if (item.getItem() instanceof ShovelSuperTools)
            {
                final PlayerEntity player = context.getPlayer();
                final World world = context.getWorld();
                if(super.onItemUse(context) == ActionResultType.CONSUME && player != null)
                {
                    if (player.canPlayerEdit(context.getPos(), context.getFace(), context.getItem()))
                    {
                        for (final BlockPos pos : WorldEvents.getAffectedPos(player))
                        {
                            BlockState blockstate = SHOVEL_LOOKUP.get(world.getBlockState(pos).getBlock());
                            if (world.getBlockState(pos.up()).isAir(world, pos.up()) && blockstate != null)
                            {
                                BlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
                                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                world.setBlockState(pos, iblockstate1, 11);
                            }
                        }
                        context.getItem().damageItem(1, player, (p) -> {
                            p.sendBreakAnimation(context.getHand());
                        });
                        return ActionResultType.CONSUME;
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }
}
