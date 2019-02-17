package com.ldtteam.supertools;

import com.ldtteam.supertools.api.util.constant.Constants;
import com.ldtteam.supertools.coremod.network.BlockParticleEffectMessage;
import com.ldtteam.supertools.items.HammerSuperTools;
import com.ldtteam.supertools.items.ModItems;
import com.ldtteam.supertools.items.ShovelSuperTools;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod(Constants.MOD_ID)
public class SuperTools
{
    private static final Logger logger = LogManager.getLogger(Constants.MOD_ID);

    /**
     * Forge created instance of the Mod.
     */
    public static SuperTools instance;

    /**
     * The channel.
     */
    public static final String CHANNEL = Constants.MOD_ID;

    /**
     * The channel protocol version.
     */
    private static final String PROTOCOL_VERSION = "1.0";

    /**
     * Progress in % of breaking something.
     */
    private int curBlockDamageMP = 0;

    /**
     * Creation of the channel.
     */
    public static SimpleChannel channel = NetworkRegistry.ChannelBuilder
                                            .named(new ResourceLocation(Constants.MOD_ID, CHANNEL))
                                            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                                            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                                            .networkProtocolVersion(() -> PROTOCOL_VERSION)
                                            .simpleChannel();

    public SuperTools()
    {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);

        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Getter for the SUPER_TOOLS Logger.
     *
     * @return the logger.
     */
    public static Logger getLogger()
    {
        return logger;
    }

    /**
     * Event to register the items.
     *
     * @param event the event to register it with.
     */
    public void registerItems(final RegistryEvent.Register<Item> event)
    {
        ModItems.init(event.getRegistry());
    }

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
        if (item.getItem() instanceof HammerSuperTools
              || item.getItem() instanceof ShovelSuperTools)
        {
            final ItemStack mainHand = event.getPlayer().getHeldItemMainhand();
            final World world = event.getPlayer().getEntityWorld();
            for (BlockPos pos : getAffectedPos(event.getPlayer()))
            {
                final IBlockState state = world.getBlockState(pos);
                if (ForgeHooks.canToolHarvestBlock(world, pos, item)
                      || item.canHarvestBlock(state)
                      || (item.getItem() instanceof ShovelSuperTools && (state.getMaterial() == Material.SAND || state.getMaterial() == Material.GRASS || state.getMaterial() == Material.GROUND || state.getMaterial() == Material.CLAY)))
                {
                    state.getBlock().harvestBlock(world, event.getPlayer(), pos, state, world.getTileEntity(pos), mainHand);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    /**
     * On right click of an item.
     *
     * @param event the called event.
     */
    @SubscribeEvent
    public void onItemRightClick(@NotNull final PlayerInteractEvent.RightClickBlock event)
    {
        if (!event.getWorld().isRemote)
        {
            final ItemStack item = event.getItemStack();
            if (item.getItem() instanceof ShovelSuperTools)
            {
                final EntityPlayer player = event.getEntityPlayer();
                final World world = event.getWorld();
                if (player.canPlayerEdit(event.getPos(), event.getFace(), event.getItemStack()))
                {
                    for (BlockPos pos : getAffectedPos(event.getEntityPlayer()))
                    {
                        if (event.getFace() != EnumFacing.DOWN && world.getBlockState(pos.up()).getMaterial() == Material.AIR
                              && world.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK)
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
    private List<BlockPos> getAffectedPos(@NotNull final EntityPlayer player)
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

    /**
     * The break speed.
     *
     * @param event the event.
     */
    @SubscribeEvent
    public void breakSpeed(@NotNull final PlayerEvent.BreakSpeed event)
    {
        final ItemStack item = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if (item.getItem() instanceof HammerSuperTools
              || item.getItem() instanceof ShovelSuperTools)
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
                    final BlockParticleEffectMessage pEM = new BlockParticleEffectMessage(pos, theBlock, facing.getIndex());
                    final Chunk chunk = world.getChunk(pos);
                    if (world instanceof WorldServer)
                    {
                        ((WorldServer) world).getPlayerChunkMap()
                          .getEntry(chunk.x, chunk.z)
                          .getWatchingPlayers()
                          .forEach(p -> channel.sendTo(pEM, p.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT));
                        world.sendBlockBreakProgress(player.getEntityId(), pos, (int) (curBlockDamageMP * 10.0F) - 1);
                    }
                }
            }
        }
    }

    /**
     * Event handler for forge pre init event.
     *
     * @param event the forge pre init event.
     */
    public void preInit(@NotNull final FMLCommonSetupEvent event)
    {
        int messageNumber = 0;
        channel.registerMessage(messageNumber++,
          BlockParticleEffectMessage.class,
          BlockParticleEffectMessage::encode,
          BlockParticleEffectMessage::decode,
          BlockParticleEffectMessage::onMessage);
    }

    public static ResourceLocation location(final String path)
    {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}