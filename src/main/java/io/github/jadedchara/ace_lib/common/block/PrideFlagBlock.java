package io.github.jadedchara.ace_lib.common.block;

import com.mojang.serialization.MapCodec;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipConfig;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class PrideFlagBlock extends BlockWithEntity {
	public static final MapCodec<PrideFlagBlock> CODEC = Block.createCodec(PrideFlagBlock::new);
	/*
	Different combinations of shift-placing at angles, etc.

	TO-DO:
	- Vertical-hanging flag staked in ground
	- Horizontal hanging from strings.
	- Horizontal, mounted on wall
	- Vertical, hanging on wall, a la banners.
	- Vertical, hanging from ceiling.
	COVERED:
	- Wall-pole at an angle
	- Pole staked on ground as a classic setup.
	*/
	public PrideFlagBlock(Settings settings) {
		super(settings);
		this.setDefaultState(getDefaultState()
			.with(Properties.HORIZONTAL_FACING, Direction.NORTH)
			.with(Properties.ATTACHED, false)
		);
	}

	@Override
	protected MapCodec<? extends PrideFlagBlock> getCodec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PrideFlagBlockEntity(pos,state);
	}


	@Override
	public void appendTooltip(
		ItemStack stack,
		Item.TooltipContext context,
		List<net.minecraft.text.Text> tooltip,
		TooltipConfig tc
	) {
		String TYPE = stack.getOrDefault(
			DataComponentRegistry.FLAG_TYPE,
			"classic"
		);
		tooltip.add(Text.literal(TYPE).formatted(Formatting.GOLD));

	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING).add(Properties.ATTACHED);

	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		boolean wallmount = ctx.getSide().getAxis().isHorizontal() ;

		return this.getDefaultState()
			.with(
				Properties.HORIZONTAL_FACING,
				ctx.getPlayer().getHorizontalFacing().getOpposite()
			).with(
				Properties.ATTACHED,
				wallmount
			);
	}
	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;

	}
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ANIMATED;
		//this.getoutl
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		//Voxel Equivalents
		// 1/16 - 0.0625F
		//  1/8 - 0.1250F
		// 3/16 - 0.1875F
		//  1/4 - 0.2500F
		// 5/16 - 0.3125F
		//  3/8 - 0.3750F
		// 7/16 - 0.4375F
		//  1/2 - 0.5000F
		// 9/16 - 0.5625F
		//  5/8 - 0.6250F
		//11/16 - 0.6875F
		//  3/4 - 0.7500F
		//13/16 - 0.8125F
		//  7/8 - 0.8750F
		//15/16 - 0.9375F
		//    1 - 1.0000F

		if(state.get(Properties.ATTACHED).equals(true)){
			//wall-hang, with pole
			if(state.get(Properties.HORIZONTAL_FACING).equals(Direction.WEST)){
				return VoxelShapes.cuboid(
						0.0625F,
						0.3750F,
						0.4600F,
						0.8125F,
						0.8750F,
						0.5400F
					);
			}else if(state.get(Properties.HORIZONTAL_FACING).equals(Direction.EAST)){
				return VoxelShapes.cuboid(
						0.1875F,
						0.3750F,
						0.4600F,
						0.9375F,
						0.8750F,
						0.5400F
					);
			}else if(state.get(Properties.HORIZONTAL_FACING).equals(Direction.NORTH)){
				return VoxelShapes.cuboid(
						0.4600F,
						0.3750F,
						0.0625F,
						0.5400F,
						0.8750F,
						0.8125F
					);
			}else if(state.get(Properties.HORIZONTAL_FACING).equals(Direction.SOUTH)){
				return VoxelShapes.cuboid(
						0.4600F,
						0.3750F,
						0.1875F,
						0.5400F,
						0.8750F,
						0.9375F
				);
			}

		}else{
			//floor-mount, with pole
			if(state.get(Properties.HORIZONTAL_FACING).equals(Direction.NORTH)){
				return VoxelShapes.combine(
					VoxelShapes.cuboid(
						0.0625F,
						0.3750F,
						0.4600F,
						0.8125F,
						0.8750F,
						0.5400F
					),
					VoxelShapes.cuboid(
						0.8125F,
						0.0000F,
						0.4375F,
						0.9375F,
						0.8750F,
						0.5625F
					),BooleanBiFunction.OR);
			}else if(state.get(Properties.HORIZONTAL_FACING).equals(Direction.SOUTH)){
				return VoxelShapes.combine(
					VoxelShapes.cuboid(
						0.1875F,
						0.3750F,
						0.4600F,
						0.9375F,
						0.8750F,
						0.5400F
					),
					VoxelShapes.cuboid(
						0.0625F,
						0.0000F,
						0.4375F,
						0.1875F,
						0.8750F,
						0.5625F
					), BooleanBiFunction.OR);
			}else if(state.get(Properties.HORIZONTAL_FACING).equals(Direction.EAST)){
				return VoxelShapes.combine(
					VoxelShapes.cuboid(
						0.4600F,
						0.3750F,
						0.0625F,
						0.5400F,
						0.8750F,
						0.8125F
					),
					VoxelShapes.cuboid(
						0.4375F,
						0.0000F,
						0.8125F,
						0.5625F,
						0.8750F,
						0.9375F
					), BooleanBiFunction.OR);
			}else if(state.get(Properties.HORIZONTAL_FACING).equals(Direction.WEST)){
				return VoxelShapes.combine(
					VoxelShapes.cuboid(
						0.4600F,
						0.3750F,
						0.1875F,
						0.5400F,
						0.8750F,
						0.9375F
					),
					VoxelShapes.cuboid(
						0.4375F,
						0.0000F,
						0.0625F,
						0.5625F,
						0.8750F,
						0.1875F
					), BooleanBiFunction.OR);
			}

		}

		return VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}

}
