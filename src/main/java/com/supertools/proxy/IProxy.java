package com.supertools.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Basic proxy interface.
 */
public interface IProxy
{
    /**
     * Returns whether or not the proxy is client sided or server sided.
     *
     * @return true when client, false when server.
     */
    boolean isClient();

    /**
     * Method to register events in.
     */
    void registerEvents();

    /**
     * Method to register Entities in.
     */
    void registerEntities();

    /**
     * Method to register entity rendering in.
     */
    void registerEntityRendering();

    /**
     * Registers all block and item renderer.
     */
    void registerRenderer();

    /**
     * Method to get a side specific world from a message context anywhere.
     * @param dimension the dimension.
     * @return The world.
     */
    @Nullable
    World getWorld(final int dimension);
}
