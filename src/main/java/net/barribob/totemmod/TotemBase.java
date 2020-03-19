package net.barribob.totemmod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TotemBase extends Block {
    protected static VoxelShape BOTTOM_TOTEM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    protected static VoxelShape TOP_TOTEM_SHAPE = Block.createCuboidShape(4.0D, 2.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static VoxelShape TOTEM_SHAPE = VoxelShapes.union(BOTTOM_TOTEM_SHAPE, TOP_TOTEM_SHAPE);

    public TotemBase(Settings settings) {
		super(settings);
	}

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return TOTEM_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return TOTEM_SHAPE;
    }
    
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		// if (world.getBlockState(pos.up()).getBlock().equals(Main.ModBlocks.totem_top)) {
		// 	world.destroyBlock(pos.up(), true);
		// }
        super.onBreak(world, pos, state, player);
    }
}
