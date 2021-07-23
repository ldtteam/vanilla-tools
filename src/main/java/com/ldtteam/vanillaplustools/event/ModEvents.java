package com.ldtteam.vanillaplustools.event;

import com.ldtteam.vanillaplustools.VanillaPlusTools;
import com.ldtteam.vanillaplustools.coremod.network.BlockParticleEffectMessage;
import com.ldtteam.vanillaplustools.items.ModHammerItem;
import com.ldtteam.vanillaplustools.items.ModShovelItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor.TargetPoint;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ModEvents
{
    /**
     * Tags.
     */
    private static final ResourceLocation CAN_HAMMER = new ResourceLocation("vanillaplustools", "can_hammer");
    private static final ResourceLocation CAN_SHOVEL = new ResourceLocation("vanillaplustools", "can_shovel");
    /**
     * List of tools to test blocks against, used for finding right tool.
     */
    public static List<Tuple<ToolType, ItemStack>> tools;

    @SubscribeEvent
    public static void onModInit()
    {
        VanillaPlusTools.NETWORK_CHANNEL.registerCommonMessages();
    }

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
            final ItemStack item = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
            if (item.getItem() instanceof ModHammerItem || item.getItem() instanceof ModShovelItem)
            {
                final ItemStack mainHand = event.getPlayer().getMainHandItem();
                final Level level = event.getPlayer().getCommandSenderWorld();
                final double hardness = event.getState().getDestroySpeed(level, event.getPos());
                for (BlockPos pos : getAffectedPos(event.getPlayer()))
                {
                    final BlockState state = level.getBlockState(pos);
                    if (hardness * 2 >= state.getDestroySpeed(level, pos) && isBestTool(state, level, pos, item, event.getPlayer()))
                    {
                        state.getBlock().playerDestroy(level, event.getPlayer(), pos, state, level.getBlockEntity(pos), mainHand);
                        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
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

    private static boolean isBestTool(final BlockState target, final LevelAccessor level, final BlockPos pos, final ItemStack stack, final Player player)
    {
        if ((stack.getItem() instanceof ModHammerItem && BlockTags.getAllTags().getTag(CAN_HAMMER).contains(target.getBlock()))
            || (stack.getItem() instanceof ModShovelItem && BlockTags.getAllTags().getTag(CAN_SHOVEL).contains(target.getBlock())))
        {
            return true;
        }

        final ToolType forgeTool = target.getHarvestTool();

        if (forgeTool == null)
        {
            if (target.getDestroySpeed(level, pos) > 0f)
            {
                for (final Tuple<ToolType, ItemStack> tool : getOrInitTestTools())
                {
                    if (tool.getB() != null && tool.getB().getItem() instanceof DiggerItem)
                    {
                        final DiggerItem toolItem = (DiggerItem) tool.getB().getItem();
                        if (tool.getB().getDestroySpeed(target) >= toolItem.getTier().getSpeed()
                            && stack.getToolTypes().contains(tool.getA())
                            && (target.getHarvestLevel() == 0 || stack.getHarvestLevel(forgeTool, player, target) >= target.getHarvestLevel()))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        else if (stack.getToolTypes().contains(forgeTool) && stack.getHarvestLevel(forgeTool, player, target) >= target.getHarvestLevel())
        {
            return true;
        }

        if (target.getMaterial() == Material.WOOD)
        {
            return false;
        }
        return target.getBlock() instanceof GlazedTerracottaBlock && stack.getItem() instanceof ModHammerItem;
    }

    public static BlockHitResult rayTrace(final Level level, final Player player, final ClipContext.Fluid mode)
    {
        float pitch = player.getXRot();
        float yaw = player.getYRot();
        Vec3 vec3 = player.getEyePosition(1.0F);
        float cosYaw = Mth.cos(-yaw * 0.017453292F - 3.1415927F);
        float sinYaw = Mth.sin(-yaw * 0.017453292F - 3.1415927F);
        float cosPitch = -Mth.cos(-pitch * 0.017453292F);
        float sinPitch = Mth.sin(-pitch * 0.017453292F);
        float product = sinYaw * cosPitch;
        float product2 = cosYaw * cosPitch;
        double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
        Vec3 vec32 = vec3.add((double) product * reachDistance, (double) sinPitch * reachDistance, (double) product2 * reachDistance);
        return level.clip(new ClipContext(vec3, vec32, ClipContext.Block.OUTLINE, mode, player));
    }

    /**
     * Get all affected pos for a player with a tool.
     *
     * @param player the player.
     * @return the list of affected positions.
     */
    public static List<BlockPos> getAffectedPos(@NotNull final Player player)
    {
        final List<BlockPos> list = new ArrayList<>();
        final HitResult rayTrace = rayTrace(player.level, player, ClipContext.Fluid.NONE);

        if (rayTrace instanceof BlockHitResult)
        {
            final BlockHitResult rayTraceResult = (BlockHitResult) rayTrace;
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
        final ItemStack item = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
        if (event.getPos() != null && (item.getItem() instanceof ModHammerItem || item.getItem() instanceof ModShovelItem))
        {
            final Player player = event.getPlayer();
            final Level level = player.getCommandSenderWorld();
            final BlockPos vector = event.getPos().subtract(new BlockPos(player.getX(), player.getY(), player.getZ()));
            final Direction facing = Direction.getNearest(vector.getX(), vector.getY(), vector.getZ()).getOpposite();

            for (BlockPos pos : getAffectedPos(player))
            {
                final BlockState theBlock = level.getBlockState(pos);
                if (isBestTool(theBlock, level, pos, item, event.getPlayer()))
                {
                    final BlockParticleEffectMessage pEM = new BlockParticleEffectMessage(pos, theBlock, facing.get3DDataValue());
                    if (level instanceof ServerLevel)
                    {
                        VanillaPlusTools.NETWORK_CHANNEL.sendToPosition(pEM, new TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 10, level.dimension()));
                    }
                }
            }
        }
    }
}
