package com.ldtteam.vanillaplustools.items;

import com.ldtteam.vanillaplustools.creativetab.ModCreativeTabs;
import com.ldtteam.vanillaplustools.event.ModEvents;
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
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ModShovelItem extends ShovelItem
{
    /**
     * Setups the tool.
     * @param tier the tier of it.
     * @param attackDamageIn the incoming attack damage.
     * @param attackSpeedIn the attack speed.
     */
    public ModShovelItem(final Tier tier, final float attackDamageIn, float attackSpeedIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(ModCreativeTabs.VANILLA_PLUS_TOOLS).addToolType(ToolType.SHOVEL, tier.getLevel()));
        this.setRegistryName("shovel" + tier.toString().toLowerCase(Locale.ENGLISH));
    }

    @NotNull
    @Override
    public InteractionResult useOn(final UseOnContext context)
    {
        if (!context.getLevel().isClientSide)
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
                        context.getItemInHand().hurtAndBreak(1, player, (p) ->
                                p.broadcastBreakEvent(context.getHand()));
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }
}
