package com.ldtteam.vanillaplustools.items;

import com.ldtteam.vanillaplustools.creativetab.ModCreativeTabs;
import com.ldtteam.vanillaplustools.event.ModEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

public class HookItem extends Item
{
    public HookItem()
    {
        super(new Properties().tab(ModCreativeTabs.VANILLA_PLUS_TOOLS));
    }

    //todo we need to check if placement is valid (need to come up with rules) (can't merge two)
    //todo we need a block, block has to act like a ladder block.
    //todo on shift-right clicking the block it will retrieve all blocks beneath.
    //todo I want a rendering that goes from the highest to the lowest (around corners too, re-use leash)
}
