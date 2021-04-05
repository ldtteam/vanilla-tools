package com.ldtteam.supertools.coremod.network;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Handles the server telling nearby clients to render a particle effect.
 */
public class BlockParticleEffectMessage implements IMessage
{
    /**
     * The position.
     */
    private BlockPos pos;

    /**
     * The blockstate.
     */
    private BlockState block;

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
    public BlockParticleEffectMessage(final BlockPos pos, @NotNull final BlockState state, final int side)
    {
        this.pos = pos;
        this.block = state;
        this.side = side;
    }

    @Override
    public void fromBytes(@NotNull final PacketBuffer buf)
    {
        final int x = buf.readInt();
        final int y = buf.readInt();
        final int z = buf.readInt();
        pos = new BlockPos(x, y, z);
        block = Block.getStateById(buf.readInt());
        side = buf.readInt();
    }

    @Nullable
    @Override
    public LogicalSide getExecutionSide()
    {
        return LogicalSide.CLIENT;
    }

    @Override
    public void onExecute(final NetworkEvent.Context ctxIn, final boolean isLogicalServer)
    {
        Minecraft.getInstance().particles.addBlockHitEffects(pos, Direction.byIndex(side));
    }

    @Override
    public void toBytes(@NotNull final PacketBuffer buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(Block.BLOCK_STATE_IDS.getId(block));
        buf.writeInt(side);
    }
}
