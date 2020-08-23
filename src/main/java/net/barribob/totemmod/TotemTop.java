package net.barribob.totemmod;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class TotemTop extends BlockWithEntity {
    public static final BooleanProperty TRIGGERED = Properties.TRIGGERED;
    public static DirectionProperty FACING = HorizontalFacingBlock.FACING;
    protected static VoxelShape TOTEM_HEAD = Block.createCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static VoxelShape TOTEM_NECK = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);
    protected static VoxelShape TOTEM_SHAPE = VoxelShapes.union(TOTEM_HEAD, TOTEM_NECK);

    public TotemTop(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TRIGGERED, Boolean.FALSE));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return TOTEM_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return TOTEM_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getPlayerFacing().getOpposite()).with(TRIGGERED, Boolean.FALSE);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.getBlockState(pos).get(TRIGGERED)) {
            world.addParticle(new DustParticleEffect(0.65f + 0.25f * random.nextFloat(), 0, 0, 1.0F),
                    pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(),
                    0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos,
                               boolean moved) {
        if (world.getBlockState(pos.down()).getBlock() == TotemMod.TOTEM_BASE) {
            world.setBlockState(pos, state.with(TRIGGERED, Boolean.TRUE));
        } else {
            world.setBlockState(pos, state.with(TRIGGERED, Boolean.FALSE));
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        neighborUpdate(state, world, pos, null, null, false);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new TotemBlockEntity();
    }

    @Override
    public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(new TranslatableText("block.totemmod.tooltip_1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("block.totemmod.tooltip_2").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("block.totemmod.tooltip_3").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }
}
