package com.ldtteam.vanillaplustools.coremod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
     * The blockstate.
     */
    private BlockState block;

    /**
     * The target.
     */
    private BlockHitResult target;

    /**
     * The enumside.
     */
    private int side;

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
    public void fromBytes(@NotNull final FriendlyByteBuf buf)
    {
        final int x = buf.readInt();
        final int y = buf.readInt();
        final int z = buf.readInt();
        pos = new BlockPos(x, y, z);
        block = Block.stateById(buf.readInt());
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
        Minecraft.getInstance().particleEngine.addBlockHitEffects(pos, target);
    }

    @Override
    public void toBytes(@NotNull final FriendlyByteBuf buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(Block.BLOCK_STATE_REGISTRY.getId(block));
        buf.writeInt(side);
    }
}
