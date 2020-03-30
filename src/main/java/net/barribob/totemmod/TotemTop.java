package net.barribob.totemmod;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TotemTop extends BlockWithEntity
{
    public static final BooleanProperty TRIGGERED = Properties.TRIGGERED;
    public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    protected static VoxelShape TOTEM_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);

    public TotemTop(Settings settings)
    {
	super(settings);
	this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TRIGGERED, Boolean.valueOf(false)));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
	return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context)
    {
	return TOTEM_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context)
    {
	return TOTEM_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
	return this.getDefaultState().with(FACING, context.getPlayerFacing().getOpposite()).with(TRIGGERED, Boolean.valueOf(false));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rot)
    {
	return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirrorIn)
    {
	return state.rotate(mirrorIn.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
	builder.add(FACING, TRIGGERED);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
    {
	if (world.getBlockState(pos).get(TRIGGERED))
	{
	    world.addParticle(new DustParticleEffect(0.65f + 0.25f * random.nextFloat(), 0, 0, 1.0F),
		    pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(),
		    0.0D, 0.0D, 0.0D);
	}
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos,
	    boolean moved)
    {
	if (world.getBlockState(pos.down()).getBlock() == TotemMod.TOTEM_BASE)
	{
	    world.setBlockState(pos, state.with(TRIGGERED, Boolean.valueOf(true)));
	}
	else
	{
	    world.setBlockState(pos, state.with(TRIGGERED, Boolean.valueOf(false)));
	}
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
    {
	neighborUpdate(state, world, pos, null, null, false);
    }

    @Override
    public boolean hasBlockEntity()
    {
	return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view)
    {
	return new TotemBlockEntity();
    }
}
