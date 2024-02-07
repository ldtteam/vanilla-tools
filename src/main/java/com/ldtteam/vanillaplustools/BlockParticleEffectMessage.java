package com.ldtteam.vanillaplustools;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

/**
 * Handles the server telling nearby clients to render a particle effect.
 */
public class BlockParticleEffectMessage implements IClientBoundDistributor
{
    public static final ResourceLocation ID = new ResourceLocation(VanillaPlusTools.MOD_ID, "block_particle_effect_message");

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
    public BlockParticleEffectMessage(@NotNull final FriendlyByteBuf buf)
    {
        super();
        final int x = buf.readInt();
        final int y = buf.readInt();
        final int z = buf.readInt();
        pos = new BlockPos(x, y, z);
        side = buf.readInt();
    }

    /**
     * Sends a message for particle effect.
     *
     * @param pos   Coordinates
     * @param side  Side of the block causing effect
     */
    public BlockParticleEffectMessage(final BlockPos pos, final int side)
    {
        super();
        this.pos = pos;
        this.side = side;
    }

    public void onExecute(@NotNull final PlayPayloadContext ctxIn)
    {
        ctxIn.workHandler().execute(() -> Minecraft.getInstance().particleEngine.crack(pos, Direction.values()[side]));
    }

    @Override
    public void write(final FriendlyByteBuf buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(side);
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
