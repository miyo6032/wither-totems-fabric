package net.barribob.totemmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TotemMod implements ModInitializer {

    public static final Block TOTEM_BASE = new TotemBase(FabricBlockSettings.of(Material.STONE).build());

	@Override
	public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("totemmod", "totem_base"), TOTEM_BASE);
        Registry.register(Registry.ITEM, new Identifier("totemmod", "totem_base"), new BlockItem(TOTEM_BASE, new Item.Settings().group(ItemGroup.MISC)));
    }
}