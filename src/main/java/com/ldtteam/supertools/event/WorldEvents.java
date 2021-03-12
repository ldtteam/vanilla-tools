package com.ldtteam.supertools.event;

import com.ldtteam.supertools.SuperTools;
import com.ldtteam.supertools.coremod.network.BlockParticleEffectMessage;
import com.ldtteam.supertools.items.HammerSuperTools;
import com.ldtteam.supertools.items.ShovelSuperTools;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
     * Tags.
     */
    private static final ResourceLocation CAN_HAMMER = new ResourceLocation("supertools", "can_hammer");
    private static final ResourceLocation CAN_SHOVEL = new ResourceLocation("supertools", "can_shovel");
    /**
     * List of tools to test blocks against, used for finding right tool.
     */
    public static List<Tuple<ToolType, ItemStack>> tools;

    /**
     * Event when a block is broken.
     * Event gets cancelled when there no permission to break a hut.
     *
     * @param event {@link net.minecraftforge.event.world.BlockEvent.BreakEvent}
     */
    @SubscribeEvent
    public static void onBlockBreak(@NotNull final BlockEvent.BreakEvent event)
    {
        if (event.getState().canOcclude())
        {
            final ItemStack item = event.getPlayer().getItemInHand(Hand.MAIN_HAND);
            if (item.getItem() instanceof HammerSuperTools
                || item.getItem() instanceof ShovelSuperTools)
            {
                final ItemStack mainHand = event.getPlayer().getMainHandItem();
                final World world = event.getPlayer().getCommandSenderWorld();
                final double hardness = event.getState().getDestroySpeed(world, event.getPos());
                for (BlockPos pos : getAffectedPos(event.getPlayer()))
                {
                    final BlockState state = world.getBlockState(pos);
                    if (hardness * 2 >= state.getDestroySpeed(world, pos) && isBestTool(state, world, pos, item, event.getPlayer()))
                    {
                        state.getBlock().playerDestroy(world, event.getPlayer(), pos, state, world.getBlockEntity(pos), mainHand);
                        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }
    }

    /**
     * Gets or initializes the test tool list.
     *
     * @return the list of possible tools.
     */
    public static List<Tuple<ToolType, ItemStack>> getOrInitTestTools()
    {
        if (tools == null)
        {
            tools = new ArrayList<>();
            tools.add(new Tuple<>(ToolType.SHOVEL, new ItemStack(Items.WOODEN_SHOVEL)));
            tools.add(new Tuple<>(ToolType.AXE, new ItemStack(Items.WOODEN_AXE)));
            tools.add(new Tuple<>(ToolType.PICKAXE, new ItemStack(Items.WOODEN_PICKAXE)));
        }
        return tools;
    }

    private static boolean isBestTool(final BlockState target, final IWorld world, final BlockPos pos, final ItemStack stack, final PlayerEntity playerEntity)
    {
        if ((stack.getItem() instanceof HammerSuperTools && BlockTags.getAllTags().getTag(CAN_HAMMER).contains(target.getBlock()))
            || (stack.getItem() instanceof ShovelSuperTools && BlockTags.getAllTags().getTag(CAN_SHOVEL).contains(target.getBlock())))
        {
            return true;
        }

        final net.minecraftforge.common.ToolType forgeTool = target.getHarvestTool();

        if (forgeTool == null)
        {
            if (target.getDestroySpeed(world, pos) > 0f)
            {
                for (final Tuple<ToolType, ItemStack> tool : getOrInitTestTools())
                {
                    if (tool.getB() != null && tool.getB().getItem() instanceof ToolItem)
                    {
                        final ToolItem toolItem = (ToolItem) tool.getB().getItem();
                        if (tool.getB().getDestroySpeed(target) >= toolItem.getTier().getSpeed()
                            && stack.getToolTypes().contains(tool.getA())
                            && (target.getHarvestLevel() == 0 || stack.getHarvestLevel(forgeTool, playerEntity, target) >= target.getHarvestLevel()))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        else if (stack.getToolTypes().contains(forgeTool) && stack.getHarvestLevel(forgeTool, playerEntity, target) >= target.getHarvestLevel())
        {
            return true;
        }

        if (target.getMaterial() == Material.WOOD)
        {
            return false;
        }
        return target.getBlock() instanceof GlazedTerracottaBlock && stack.getItem() instanceof HammerSuperTools;
    }

    public static RayTraceResult rayTrace(final World world, final PlayerEntity playerEntity, final RayTraceContext.FluidMode mode)
    {
        float pitch = playerEntity.xRot;
        float yaw = playerEntity.yRot;
        Vector3d vector3d = playerEntity.getEyePosition(1.0F);
        float cosYaw = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float sinYaw = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        float sinPitch = MathHelper.sin(-pitch * 0.017453292F);
        float product = sinYaw * cosPitch;
        float product2 = cosYaw * cosPitch;
        double reachDistance = playerEntity.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
        Vector3d vector3d1 = vector3d.add((double) product * reachDistance, (double) sinPitch * reachDistance, (double) product2 * reachDistance);
        return world.clip(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, mode, playerEntity));
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
        final RayTraceResult rayTrace = rayTrace(player.level, player, RayTraceContext.FluidMode.NONE);

        if (rayTrace instanceof BlockRayTraceResult)
        {
            final BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) rayTrace;
            final BlockPos center = rayTraceResult.getBlockPos();
            switch (rayTraceResult.getDirection())
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
                    list.add(center.above());
                    list.add(center.below());
                    list.add(center.west());
                    list.add(center.east());

                    list.add(center.west().above());
                    list.add(center.west().below());
                    list.add(center.east().above());
                    list.add(center.east().below());
                    break;
                case EAST:
                case WEST:
                    list.add(center.above());
                    list.add(center.below());
                    list.add(center.north());
                    list.add(center.south());

                    list.add(center.north().above());
                    list.add(center.north().below());
                    list.add(center.south().above());
                    list.add(center.south().below());
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
        final ItemStack item = event.getPlayer().getItemInHand(Hand.MAIN_HAND);
        if (event.getPos() != null && (item.getItem() instanceof HammerSuperTools
                                       || item.getItem() instanceof ShovelSuperTools))
        {
            final PlayerEntity player = event.getPlayer();
            final World world = player.getCommandSenderWorld();
            final BlockPos vector = event.getPos().subtract(new BlockPos(player.getX(), player.getY(), player.getZ()));
            final Direction facing = Direction.getNearest(vector.getX(), vector.getY(), vector.getZ()).getOpposite();

            for (BlockPos pos : getAffectedPos(player))
            {
                final BlockState theBlock = world.getBlockState(pos);
                if (isBestTool(theBlock, world, pos, item, event.getPlayer()))
                {
                    final BlockParticleEffectMessage pEM = new BlockParticleEffectMessage(pos, theBlock, facing.get3DDataValue());
                    if (world instanceof ServerWorld)
                    {
                        SuperTools.getNetwork().sendToPosition(pEM, new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 10, world.dimension()));
                    }
                }
            }
        }
    }
}
