package net.barribob.totemmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class TotemMod implements ModInitializer {
    public static final Block TOTEM_BASE = new TotemBase(FabricBlockSettings.of(Material.STONE).hardness(1.5f).resistance(10f));
    public static final Block TOTEM_TOP = new TotemTop(FabricBlockSettings.of(Material.STONE).hardness(1.5f).resistance(10f));
    private static final Feature<DefaultFeatureConfig> TOTEM_FEATURE = new TotemFeature(DefaultFeatureConfig.CODEC);

    public static BlockEntityType<TotemBlockEntity> TOTEM_BLOCK_ENTITY;
    public static StatusEffect LOOTING;

    @Override
    public void onInitialize() {
        RegistryKey<ConfiguredFeature<?, ?>> totemFeatureRegistryKey = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("totemmod", "wither_skeleton_totem"));
        RegistryKey<PlacedFeature> totemPlacedFeatureRegistryKey = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("totemmod", "wither_skeleton_totem"));
        Registry.register(Registry.FEATURE, new Identifier("totemmod", "totem"), TOTEM_FEATURE);
        RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> TOTEM_FEATURE_CONFIGURED = ConfiguredFeatures.register(totemFeatureRegistryKey.getValue().toString(), TOTEM_FEATURE);
        PlacedFeatures.register(totemPlacedFeatureRegistryKey.getValue().toString(), TOTEM_FEATURE_CONFIGURED, RarityFilterPlacementModifier.of(100), SquarePlacementModifier.of(), BiomePlacementModifier.of());
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.SURFACE_STRUCTURES, totemPlacedFeatureRegistryKey);

        Registry.register(Registry.BLOCK, new Identifier("totemmod", "totem_base"), TOTEM_BASE);
        Registry.register(Registry.BLOCK, new Identifier("totemmod", "totem_top"), TOTEM_TOP);

        Registry.register(Registry.ITEM, new Identifier("totemmod", "totem_top"), new BlockItem(TOTEM_TOP, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier("totemmod", "totem_base"), new BlockItem(TOTEM_BASE, new Item.Settings().group(ItemGroup.MISC)));

        TOTEM_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "totemmod:totem", FabricBlockEntityTypeBuilder.create(TotemBlockEntity::new, TOTEM_TOP).build(null));

        LOOTING = Registry.register(Registry.STATUS_EFFECT, "totemmod:looting", new LootingStatusEffect());
    }
}