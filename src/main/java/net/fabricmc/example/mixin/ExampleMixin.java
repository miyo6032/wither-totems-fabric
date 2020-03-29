package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;

@Mixin(EnchantmentHelper.class)
public class ExampleMixin {
	@Inject(at = @At("TAIL"), method = "getLooting")
	private static void init(LivingEntity entity, CallbackInfoReturnable<Integer> info) {
		if (entity.getAttacking() != null) {
			System.out.println(entity.getAttacker().getDisplayName());
		}
	}
}
