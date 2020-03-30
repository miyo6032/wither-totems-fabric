package net.barribob.totemmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.barribob.totemmod.TotemMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin
{
    @Inject(at = @At("RETURN"), method = "getLooting", cancellable = true)
    private static void onGetLooting(LivingEntity entity, CallbackInfoReturnable<Integer> info)
    {
	StatusEffectInstance instance = entity.getStatusEffect(TotemMod.LOOTING);

	if (instance == null)
	{
	    return;
	}

	System.out.println(info.getReturnValueI());
	info.setReturnValue(info.getReturnValueI() + 2);
    }
}
