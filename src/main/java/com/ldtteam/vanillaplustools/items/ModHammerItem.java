package com.ldtteam.vanillaplustools.items;

import com.ldtteam.vanillaplustools.creativetab.ModCreativeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ToolType;

import java.util.Locale;

public class ModHammerItem extends PickaxeItem
{
    /**
     * Setups the tool.
     * @param tier the tier of it.
     * @param attackDamageIn the incoming attack damage.
     * @param attackSpeedIn the attack speed.
     */
    public ModHammerItem(final Tier tier, final int attackDamageIn, float attackSpeedIn)
    {
        super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(ModCreativeTabs.VANILLA_PLUS_TOOLS).addToolType(ToolType.PICKAXE, tier.getLevel()));
        this.setRegistryName("hammer" + tier.toString().toLowerCase(Locale.ENGLISH));
    }
}
