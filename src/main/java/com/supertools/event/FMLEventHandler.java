package com.supertools.event;

import com.supertools.SuperTools;
import com.supertools.coremod.network.BlockParticleEffectMessage;
import com.supertools.items.AbstractHammerSuperTools;
import com.supertools.items.AbstractItemSuperTools;
import com.supertools.items.AbstractShovelSuperTools;
import com.supertools.items.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
        final Item item = event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem();
        if (item instanceof AbstractHammerSuperTools
              || item instanceof AbstractShovelSuperTools)
        {
            final ItemStack mainHand = event.getPlayer().getHeldItemMainhand();
            final World world = event.getPlayer().getEntityWorld();
            for (BlockPos pos : getAffectedPos(event.getPlayer()))
            {
                final IBlockState state = world.getBlockState(pos);
                if (item.getToolClasses(mainHand).stream().filter(tool -> tool.equals(state.getBlock().getHarvestTool(state))).anyMatch(tool -> state.getBlock().isToolEffective(tool, state)))
                {
                    state.getBlock().harvestBlock(world, event.getPlayer(), pos, state, world.getTileEntity(pos), mainHand);
                    world.setBlockToAir(pos);
                }
            }
        }
    }

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
        final Item item = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem();
        if (item instanceof AbstractHammerSuperTools
              || item instanceof AbstractShovelSuperTools)
        {
            final EntityPlayer player = event.getEntityPlayer();
            final World world = player.getEntityWorld();
            final BlockPos vector = event.getPos().subtract(player.getPosition());
            final EnumFacing facing = EnumFacing.getFacingFromVector(vector.getX(), vector.getY(), vector.getZ()).getOpposite();
            this.curBlockDamageMP += event.getNewSpeed();
            final ItemStack mainHand = event.getEntityPlayer().getHeldItemMainhand();

            for (BlockPos pos : getAffectedPos(player))
            {
                final IBlockState theState = world.getBlockState(pos);
                if (item.getToolClasses(mainHand).stream().filter(tool -> tool.equals(theState.getBlock().getHarvestTool(theState))).anyMatch(tool -> theState.getBlock().isToolEffective(tool, theState)))
                {
                    SuperTools.getNetwork()
                      .sendToAllAround(new BlockParticleEffectMessage(pos, world.getBlockState(pos), facing.getIndex()),
                        new NetworkRegistry.TargetPoint(player.world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
                    world.sendBlockBreakProgress(player.getEntityId(), pos, (int) (curBlockDamageMP * 10.0F) - 1);
                }
            }
        }
    }
}
