package com.ldtteam.vanillaplustools.coremod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
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
     * The enumside.
     */
    private int side;

    /**
     * Default constructor.
     */
    public BlockParticleEffectMessage()
    {
        super();
    }

    /**
     * Sends a message for particle effect.
     *
     * @param pos   Coordinates
     * @param side  Side of the block causing effect
     */
    public BlockParticleEffectMessage(final BlockPos pos, final int side)
    {
        this.pos = pos;
        this.side = side;
    }

    @Override
    public void fromBytes(@NotNull final FriendlyByteBuf buf)
    {
        final int x = buf.readInt();
        final int y = buf.readInt();
        final int z = buf.readInt();
        pos = new BlockPos(x, y, z);
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
        Minecraft.getInstance().particleEngine.crack(pos, Direction.values()[side]);
    }

    @Override
    public void toBytes(@NotNull final FriendlyByteBuf buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(side);
    }
}
