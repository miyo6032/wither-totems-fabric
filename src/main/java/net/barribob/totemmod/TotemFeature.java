package net.barribob.totemmod;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class TotemFeature extends Feature<DefaultFeatureConfig>
{
    public TotemFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer)
    {
	super(configDeserializer);
    }

    private boolean isValid(IWorld worldIn, BlockPos pos)
    {
	boolean correctBlock = worldIn.getBlockState(pos.down()).getBlock().equals(Blocks.NETHERRACK)
		|| worldIn.getBlockState(pos.down()).getBlock().equals(Blocks.NETHER_BRICKS)
		|| worldIn.getBlockState(pos.down()).getBlock().equals(Blocks.SOUL_SAND);
	return correctBlock && worldIn.isAir(pos) && worldIn.isAir(pos.up());
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config)
    {
	pos = new BlockPos(pos.getX(), 255, pos.getZ());

	// Keep moving down until we find a valid position to place the totem
	while (!isValid(world, pos))
	{
	    pos = pos.down();
	    if (pos.getY() < 30)
	    {
		return true;
	    }
	}

	// Gets a random direction to face in
	Direction dir = Direction.NORTH;
	int rotations = world.getRandom().nextInt(4);
	for (int i = 0; i < rotations; i++)
	{
	    dir = dir.rotateYClockwise();
	}

	world.setBlockState(pos.up(), TotemMod.TOTEM_TOP.getDefaultState().with(TotemTop.FACING, dir).with(TotemTop.TRIGGERED, Boolean.valueOf(true)), 0);
	world.setBlockState(pos, TotemMod.TOTEM_BASE.getDefaultState(), 0);
	return true;
    }
}
