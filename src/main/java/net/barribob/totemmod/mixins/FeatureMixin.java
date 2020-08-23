package net.barribob.totemmod.mixins;

import net.barribob.totemmod.TotemMod;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class FeatureMixin {
    @Inject(method = "addNetherMineables", at = @At("RETURN"))
    private static void addTotemFeature(GenerationSettings.Builder builder, CallbackInfo ci) {
        builder.feature(GenerationStep.Feature.SURFACE_STRUCTURES, TotemMod.TOTEM_FEATURE);
    }
}
