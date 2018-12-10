package com.supertools.items;

/**
 * Class handling the buildTool item.
 */
public class ItemHammer extends AbstractHammerSuperTools
{
    protected ItemHammer(final ToolMaterial material)
    {
        super("hammer" + material.name(), material);
    }
}
