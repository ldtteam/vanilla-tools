package com.ldtteam.supertools.event;

import com.ldtteam.supertools.SuperTools;
import com.ldtteam.supertools.coremod.network.BlockParticleEffectMessage;
import com.ldtteam.supertools.items.HammerSuperTools;
import com.ldtteam.supertools.items.ShovelSuperTools;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class WorldEvents
{
    /**
     * Event when a block is broken.
     * Event gets cancelled when there no permission to break a hut.
     *
     * @param event {@link net.minecraftforge.event.world.BlockEvent.BreakEvent}
     */
    @SubscribeEvent
    public static void onBlockBreak(@NotNull final BlockEvent.BreakEvent event)
    {
        final ItemStack item = event.getPlayer().getHeldItem(Hand.MAIN_HAND);
        if (item.getItem() instanceof HammerSuperTools
              || item.getItem() instanceof ShovelSuperTools)
        {
            final ItemStack mainHand = event.getPlayer().getHeldItemMainhand();
            final World world = event.getPlayer().getEntityWorld();
            for (BlockPos pos : getAffectedPos(event.getPlayer()))
            {
                final BlockState state = world.getBlockState(pos);

                if (ForgeHooks.isToolEffective(world, pos, mainHand))
                {
                    state.getBlock().harvestBlock(world, event.getPlayer(), pos, state, world.getTileEntity(pos), mainHand);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
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
    public static List<BlockPos> getAffectedPos(@NotNull final PlayerEntity player)
    {
        final List<BlockPos> list = new ArrayList<>();
        final RayTraceResult rayTrace = Item.rayTrace(player.world, player, RayTraceContext.FluidMode.NONE);

        if (rayTrace instanceof BlockRayTraceResult)
        {
            final BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) rayTrace;
            final BlockPos center = rayTraceResult.getPos();
            switch (rayTraceResult.getFace())
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
        }

        return list;
    }

    /**
     * The break speed.
     *
     * @param event the event.
     */
    @SubscribeEvent
    public static void breakSpeed(@NotNull final PlayerEvent.BreakSpeed event)
    {
        final ItemStack item = event.getEntityPlayer().getHeldItem(Hand.MAIN_HAND);
        if (item.getItem() instanceof HammerSuperTools
              || item.getItem() instanceof ShovelSuperTools)
        {
            final PlayerEntity player = event.getEntityPlayer();
            final World world = player.getEntityWorld();
            final BlockPos vector = event.getPos().subtract(player.getPosition());
            final Direction facing = Direction.getFacingFromVector(vector.getX(), vector.getY(), vector.getZ()).getOpposite();
            final ItemStack mainHand = event.getEntityPlayer().getHeldItemMainhand();

            for (BlockPos pos : getAffectedPos(player))
            {
                final BlockState theBlock = world.getBlockState(pos);
                if (ForgeHooks.isToolEffective(world, pos, mainHand))
                {
                    final BlockParticleEffectMessage pEM = new BlockParticleEffectMessage(pos, theBlock, facing.getIndex());
                    if (world instanceof ServerWorld)
                    {
                        SuperTools.getNetwork().sendToPosition(pEM, new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 10, world.getDimension().getType()));
                    }
                }
            }
        }
    }
}
