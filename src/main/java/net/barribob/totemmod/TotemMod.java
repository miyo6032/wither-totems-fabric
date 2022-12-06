package net.barribob.totemmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class TotemMod implements ModInitializer {
    public static final Block TOTEM_BASE = new TotemBase(FabricBlockSettings.of(Material.STONE).hardness(1.5f).resistance(10f));
    public static final Block TOTEM_TOP = new TotemTop(FabricBlockSettings.of(Material.STONE).hardness(1.5f).resistance(10f));
    public static final Feature<DefaultFeatureConfig> TOTEM_FEATURE = new TotemFeature(DefaultFeatureConfig.CODEC);

    public static BlockEntityType<TotemBlockEntity> TOTEM_BLOCK_ENTITY;
    public static StatusEffect LOOTING;

    @Override
    public void onInitialize() {
        Registry.register(Registries.FEATURE, new Identifier("totemmod", "totem"), TOTEM_FEATURE);

        RegistryKey<PlacedFeature> totemPlacedFeatureRegistryKey = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("totemmod", "wither_skeleton_totem"));
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.SURFACE_STRUCTURES, totemPlacedFeatureRegistryKey);

        Registry.register(Registries.BLOCK, new Identifier("totemmod", "totem_base"), TOTEM_BASE);
        Registry.register(Registries.BLOCK, new Identifier("totemmod", "totem_top"), TOTEM_TOP);

        Registry.register(Registries.ITEM, new Identifier("totemmod", "totem_top"), new BlockItem(TOTEM_TOP, new Item.Settings()));
        Registry.register(Registries.ITEM, new Identifier("totemmod", "totem_base"), new BlockItem(TOTEM_BASE, new Item.Settings()));
        
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(TOTEM_TOP));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(TOTEM_BASE));

        TOTEM_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "totemmod:totem", FabricBlockEntityTypeBuilder.create(TotemBlockEntity::new, TOTEM_TOP).build(null));

        LOOTING = Registry.register(Registries.STATUS_EFFECT, "totemmod:looting", new LootingStatusEffect());
    }
}