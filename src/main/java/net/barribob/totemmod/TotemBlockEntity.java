package net.barribob.totemmod;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class TotemBlockEntity extends BlockEntity {

    int tickCounter = 0;

    public TotemBlockEntity(BlockPos pos, BlockState state) {
        super(TotemMod.TOTEM_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, TotemBlockEntity entity) {
        if(world.isClient) return;

        if (entity.tickCounter % 20 != 0) {
            entity.tickCounter--;
            return;
        }

        entity.tickCounter = 19;

        Box box = new Box(pos).expand(15);
        List<Entity> mobs = entity.world.getOtherEntities(null, box);
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
