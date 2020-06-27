package net.barribob.totemmod;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

import java.util.List;

public class TotemBlockEntity extends BlockEntity implements Tickable {

    int tickCounter = 0;

    public TotemBlockEntity() {
        super(TotemMod.TOTEM_BLOCK_ENTITY);
    }

    @Override
    public void tick() {
        if (!this.getCachedState().get(TotemTop.TRIGGERED)) {
            return;
        }

        if (tickCounter % 20 != 0) {
            tickCounter--;
            return;
        }

        tickCounter = 19;

        Box box = new Box(pos).expand(15);
        List<Entity> mobs = this.world.getEntities(null, box);
        for (Entity mob : mobs) {
            if (mob instanceof Monster && mob instanceof LivingEntity) {
                ((LivingEntity) mob).addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 40, 1));
                ((LivingEntity) mob).addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 40, 0));
            } else if (mob instanceof PlayerEntity) {
                ((PlayerEntity) mob).addStatusEffect(new StatusEffectInstance(TotemMod.LOOTING, 100, 1));
            }
        }
    }
}
