package net.barribob.totemmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TotemMod implements ModInitializer {

    public static final Block TOTEM_BASE = new TotemBase(FabricBlockSettings.of(Material.STONE).build());
    public static final Block TOTEM_TOP = new TotemTop(FabricBlockSettings.of(Material.STONE).build());
	public static BlockEntityType<TotemBlockEntity> TOTEM_BLOCK_ENTITY;
	public static StatusEffect LOOTING;

	@Override
	public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("totemmod", "totem_base"), TOTEM_BASE);
        Registry.register(Registry.BLOCK, new Identifier("totemmod", "totem_top"), TOTEM_TOP);

        Registry.register(Registry.ITEM, new Identifier("totemmod", "totem_top"), new BlockItem(TOTEM_TOP, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("totemmod", "totem_base"), new BlockItem(TOTEM_BASE, new Item.Settings().group(ItemGroup.MISC)));

		TOTEM_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "totemmod:totem",
				BlockEntityType.Builder.create(TotemBlockEntity::new, TOTEM_TOP).build(null));

		LOOTING = Registry.register(Registry.STATUS_EFFECT, "totemmod:effect", new LootingStatusEffect());
	}
}