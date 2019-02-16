package com.ldtteam.supertools.coremod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Handles the server telling nearby clients to render a particle effect.
 */
public class BlockParticleEffectMessage
{
    /**
     * The position.
     */
    private BlockPos pos;

    /**
     * The blockstate.
     */
    private IBlockState block;

    /**
     * The enumside.
     */
    private int side;

    /**
     * Empty constructor used when registering the message.
     */
    public BlockParticleEffectMessage()
    {
        super();
    }

    /**
     * Sends a message for particle effect.
     *
     * @param pos   Coordinates
     * @param state Block State
     * @param side  Side of the block causing effect
     */
    public BlockParticleEffectMessage(final BlockPos pos, @NotNull final IBlockState state, final int side)
    {
        this.pos = pos;
        this.block = state;
        this.side = side;
    }

    public void fromBytes(@NotNull final ByteBuf buf)
    {
        final int x = buf.readInt();
        final int y = buf.readInt();
        final int z = buf.readInt();
        pos = new BlockPos(x, y, z);
        block = Block.getStateById(buf.readInt());
        side = buf.readInt();
    }

    public void toBytes(@NotNull final ByteBuf buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(Block.BLOCK_STATE_IDS.get(block));
        buf.writeInt(side);
    }

    public static void encode(BlockParticleEffectMessage message, PacketBuffer packet)
    {
        message.toBytes(packet);
    }

    public static BlockParticleEffectMessage decode(PacketBuffer packet)
    {
        BlockParticleEffectMessage message = new BlockParticleEffectMessage();
        message.fromBytes(packet);
        return message;
    }

    public static void onMessage(final BlockParticleEffectMessage message, Supplier<NetworkEvent.Context> context)
    {
        Minecraft.getInstance().particles.addBlockHitEffects(message.pos, EnumFacing.byIndex(message.side));
    }
}
