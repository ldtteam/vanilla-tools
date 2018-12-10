package com.supertools.items;

/**
 * Class handling the buildTool item.
 */
public class ItemShovel extends AbstractShovelSuperTools
{
    protected ItemShovel(final ToolMaterial material)
    {
        super("shovel" + material.name(), material);
    }
}
