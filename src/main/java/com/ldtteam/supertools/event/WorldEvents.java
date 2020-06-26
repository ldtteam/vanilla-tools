package com.ldtteam.supertools.event;

import com.ldtteam.supertools.SuperTools;
import com.ldtteam.supertools.coremod.network.BlockParticleEffectMessage;
import com.ldtteam.supertools.items.HammerSuperTools;
import com.ldtteam.supertools.items.ShovelSuperTools;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
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
     * List of tools to test blocks against, used for finding right tool.
     */
    public static List<Tuple<ToolType, ItemStack>> tools;

    /**
     * Tags.
     */
    private static final ResourceLocation CAN_HAMMER = new ResourceLocation("supertools","can_hammer");
    private static final ResourceLocation CAN_SHOVEL = new ResourceLocation("supertools", "can_shovel");

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
                if (isBestTool(state, world, pos, item, event.getPlayer()))
                {
                    state.getBlock().harvestBlock(world, event.getPlayer(), pos, state, world.getTileEntity(pos), mainHand);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
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
        if ((stack.getItem() instanceof HammerSuperTools && BlockTags.getCollection().getOrCreate(CAN_HAMMER).func_230235_a_(target.getBlock()))
        || (stack.getItem() instanceof ShovelSuperTools && BlockTags.getCollection().getOrCreate(CAN_SHOVEL).func_230235_a_(target.getBlock())))
        {
            return true;
        }

        final net.minecraftforge.common.ToolType forgeTool = target.getHarvestTool();

        if (forgeTool == null)
        {
            if (target.getBlockHardness(world, pos) > 0f)
            {
                for (final Tuple<ToolType, ItemStack> tool : getOrInitTestTools())
                {
                    if (tool.getB() != null && tool.getB().getItem() instanceof ToolItem)
                    {
                        final ToolItem toolItem = (ToolItem) tool.getB().getItem();
                        if (tool.getB().getDestroySpeed(target) >= toolItem.getTier().getEfficiency()
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
        float pitch = playerEntity.rotationPitch;
        float yaw = playerEntity.rotationYaw;
        Vector3d vector3d = playerEntity.getEyePosition(1.0F);
        float cosYaw = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float sinYaw = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        float sinPitch = MathHelper.sin(-pitch * 0.017453292F);
        float product = sinYaw * cosPitch;
        float product2 = cosYaw * cosPitch;
        double reachDistance = playerEntity.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
        Vector3d vector3d1 = vector3d.add((double)product * reachDistance, (double)sinPitch * reachDistance, (double)product2 * reachDistance);
        return world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, mode, playerEntity));
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
        final RayTraceResult rayTrace = rayTrace(player.world, player, RayTraceContext.FluidMode.NONE);

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
        final ItemStack item = event.getPlayer().getHeldItem(Hand.MAIN_HAND);
        if (item.getItem() instanceof HammerSuperTools
              || item.getItem() instanceof ShovelSuperTools)
        {
            final PlayerEntity player = event.getPlayer();
            final World world = player.getEntityWorld();
            final BlockPos vector = event.getPos().subtract(new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ()));
            final Direction facing = Direction.getFacingFromVector(vector.getX(), vector.getY(), vector.getZ()).getOpposite();

            for (BlockPos pos : getAffectedPos(player))
            {
                final BlockState theBlock = world.getBlockState(pos);
                if (isBestTool(theBlock, world, pos, item, event.getPlayer()))
                {
                    final BlockParticleEffectMessage pEM = new BlockParticleEffectMessage(pos, theBlock, facing.getIndex());
                    if (world instanceof ServerWorld)
                    {
                        SuperTools.getNetwork().sendToPosition(pEM, new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 10, world.func_234923_W_()));
                    }
                }
            }
        }
    }
}
