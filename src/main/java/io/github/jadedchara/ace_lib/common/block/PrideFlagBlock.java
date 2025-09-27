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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrideFlagBlock extends BlockWithEntity {
	public static final MapCodec<PrideFlagBlock> CODEC = Block.createCodec(PrideFlagBlock::new);

	public PrideFlagBlock(Settings settings) {
		super(settings);
		this.setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
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
		builder.add(Properties.HORIZONTAL_FACING);
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {

		return this.getDefaultState().with(
			Properties.HORIZONTAL_FACING,
			ctx.getPlayer().getHorizontalFacing().getOpposite()
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
		if(
			state.get(Properties.HORIZONTAL_FACING).equals(Direction.NORTH) ||
				state.get(Properties.HORIZONTAL_FACING).equals(Direction.SOUTH)
		){
			return VoxelShapes.cuboid(0.0f, 0.0f, 0.4f, 1.0f, 1.0f, 0.6f);
		}
		return VoxelShapes.cuboid(0.4f, 0.0f, 0.0f, 0.6f, 1.0f, 1.0f);
	}

}
