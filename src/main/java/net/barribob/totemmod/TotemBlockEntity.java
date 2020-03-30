package net.barribob.totemmod;

import java.util.List;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

public class TotemBlockEntity extends BlockEntity implements Tickable
{

    int tickCounter = 0;

    public TotemBlockEntity()
    {
	super(TotemMod.TOTEM_BLOCK_ENTITY);
    }

    @Override
    public void tick()
    {
	if (!this.getCachedState().get(TotemTop.TRIGGERED))
	{
	    return;
	}

	if (tickCounter % 20 != 0)
	{
	    tickCounter--;
	    return;
	}

	tickCounter = 19;

	Box box = new Box(pos).expand(15);
	List<Entity> mobs = this.world.getEntities(null, box);
	for (Entity mob : mobs)
	{
	    if (mob instanceof WitherSkeletonEntity)
	    {
		((WitherSkeletonEntity) mob).addStatusEffect(new StatusEffectInstance(TotemMod.LOOTING, 40, 1));
		((WitherSkeletonEntity) mob).addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 40, 0));
	    }
	}
    }
}
