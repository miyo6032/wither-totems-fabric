package net.barribob.totemmod;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class LootingStatusEffect extends StatusEffect {
    protected LootingStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 65506);
    }
}
