package net.barribob.totemmod;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class LootingStatusEffect extends StatusEffect {
	protected LootingStatusEffect() {
		super(StatusEffectType.HARMFUL, 65506);
	}
}
