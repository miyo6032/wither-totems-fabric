package net.barribob.totemmod;

import com.mojang.serialization.Codec;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class TotemFeature extends Feature<DefaultFeatureConfig> {
    public TotemFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        return generate(context.getWorld(), context.getOrigin());
    }

    private boolean isValid(ServerWorldAccess world, BlockPos pos) {
        boolean isSolidBlock = world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
        return isSolidBlock && world.isAir(pos) && world.isAir(pos.up());
    }

    public boolean generate(StructureWorldAccess world, BlockPos pos) {
        pos = new BlockPos(pos.getX(), 120, pos.getZ());

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

        TallPlantBlock.placeAt(world, TotemMod.TOTEM_TOP.getDefaultState().with(TotemTop.FACING, dir), pos, 2);
        return true;
    }
}
