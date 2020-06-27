package net.barribob.totemmod;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class TotemFeature extends Feature<DefaultFeatureConfig> {
    public TotemFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    private boolean isValid(ServerWorldAccess world, BlockPos pos) {
        boolean isSolidBlock = world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
        return isSolidBlock && world.isAir(pos) && world.isAir(pos.up());
    }

    @Override
    public boolean generate(ServerWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig defaultFeatureConfig) {
        pos = new BlockPos(pos.getX(), 255, pos.getZ());

        // Keep moving down until we find a valid position to place the totem
        while (!isValid(world, pos)) {
            pos = pos.down();
            if (pos.getY() < 30) {
                return true;
            }
        }

        // Gets a random direction to face in
        Direction dir = Direction.NORTH;
        int rotations = world.getRandom().nextInt(4);
        for (int i = 0; i < rotations; i++) {
            dir = dir.rotateYClockwise();
        }

        world.setBlockState(pos.up(), TotemMod.TOTEM_TOP.getDefaultState().with(TotemTop.FACING, dir).with(TotemTop.TRIGGERED, Boolean.TRUE), 0);
        world.setBlockState(pos, TotemMod.TOTEM_BASE.getDefaultState(), 0);
        return true;
    }
}
