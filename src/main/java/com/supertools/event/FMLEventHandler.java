package com.supertools.event;

import com.supertools.SuperTools;
import com.supertools.coremod.network.BlockParticleEffectMessage;
import com.supertools.items.AbstractHammerSuperTools;
import com.supertools.items.AbstractShovelSuperTools;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Event handler used to catch various forge events.
 */
public class FMLEventHandler
{
    /**
     * Progress in % of breaking the townHall.
     */
    private int curBlockDamageMP = 0;

    /**
     * Event when a block is broken.
     * Event gets cancelled when there no permission to break a hut.
     *
     * @param event {@link net.minecraftforge.event.world.BlockEvent.BreakEvent}
     */
    @SubscribeEvent
    public void onBlockBreak(@NotNull final BlockEvent.BreakEvent event)
    {
        final ItemStack item = event.getPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if (item.getItem() instanceof AbstractHammerSuperTools
              || item.getItem() instanceof AbstractShovelSuperTools)
        {
            final ItemStack mainHand = event.getPlayer().getHeldItemMainhand();
            final World world = event.getPlayer().getEntityWorld();
            for (BlockPos pos : getAffectedPos(event.getPlayer()))
            {
                final IBlockState state = world.getBlockState(pos);
                if (ForgeHooks.canToolHarvestBlock(world, pos, item) || item.canHarvestBlock(state))
                {
                    state.getBlock().harvestBlock(world, event.getPlayer(), pos, state, world.getTileEntity(pos), mainHand);
                    world.setBlockToAir(pos);
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemRightClick(final PlayerInteractEvent.RightClickBlock event)
    {
        if (!event.getWorld().isRemote)
        {
            final ItemStack item = event.getItemStack();
            if (item.getItem() instanceof AbstractShovelSuperTools)
            {
                final EntityPlayer player = event.getEntityPlayer();
                final World world = event.getWorld();
                if (player.canPlayerEdit(event.getPos(), event.getFace(), event.getItemStack()))
                {
                    for (BlockPos pos : getAffectedPos(event.getEntityPlayer()))
                    {
                        if (event.getFace() != EnumFacing.DOWN && world.getBlockState(pos.up()).getMaterial() == Material.AIR && world.getBlockState(pos).getBlock() == Blocks.GRASS)
                        {
                            IBlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
                            world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, iblockstate1, 11);
                        }
                    }
                    item.damageItem(1, player);
                }
            }
        }
    }

    /**
     * Get all affected pos for a player with a tool.
     *
     * @param player the player.
     * @return the list of affected positions.
     */
    public List<BlockPos> getAffectedPos(final EntityPlayer player)
    {
        final List<BlockPos> list = new ArrayList<>();
        final ItemStack stack = player.getHeldItemMainhand();
        final RayTraceResult rayTrace = stack.getItem().rayTrace(player.world, player, false);

        if (rayTrace == null)
        {
            return list;
        }
        final BlockPos center = rayTrace.getBlockPos();
        switch (rayTrace.sideHit)
        {
            case DOWN:
            case UP:
                list.add(center.west());
                list.add(center.east());
                list.add(center.north());
                list.add(center.south());

                list.add(center.west().north());
                list.add(center.west().south());
                list.add(center.east().north());
                list.add(center.east().south());
                break;
            case NORTH:
            case SOUTH:
                list.add(center.up());
                list.add(center.down());
                list.add(center.west());
                list.add(center.east());

                list.add(center.west().up());
                list.add(center.west().down());
                list.add(center.east().up());
                list.add(center.east().down());
                break;
            case EAST:
            case WEST:
                list.add(center.up());
                list.add(center.down());
                list.add(center.north());
                list.add(center.south());

                list.add(center.north().up());
                list.add(center.north().down());
                list.add(center.south().up());
                list.add(center.south().down());
                break;
        }
        return list;
    }

    @SubscribeEvent
    public void on(final PlayerEvent.BreakSpeed event)
    {
        final ItemStack item = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if (item.getItem() instanceof AbstractHammerSuperTools
              || item.getItem() instanceof AbstractShovelSuperTools)
        {
            final EntityPlayer player = event.getEntityPlayer();
            final World world = player.getEntityWorld();
            final BlockPos vector = event.getPos().subtract(player.getPosition());
            final EnumFacing facing = EnumFacing.getFacingFromVector(vector.getX(), vector.getY(), vector.getZ()).getOpposite();
            this.curBlockDamageMP += event.getNewSpeed();

            for (BlockPos pos : getAffectedPos(player))
            {
                final IBlockState theBlock = world.getBlockState(pos);
                if (ForgeHooks.canToolHarvestBlock(world, pos, item) || item.canHarvestBlock(theBlock))
                {
                    SuperTools.getNetwork()
                      .sendToAllAround(new BlockParticleEffectMessage(pos, theBlock, facing.getIndex()),
                        new NetworkRegistry.TargetPoint(player.world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
                    world.sendBlockBreakProgress(player.getEntityId(), pos, (int) (curBlockDamageMP * 10.0F) - 1);
                }
            }
        }
    }
}
