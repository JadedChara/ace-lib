package io.github.jadedchara.ace_lib.common.block;

import io.github.jadedchara.ace_lib.common.registry.BlockRegistry;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.HolderLookup;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;


public class PrideFlagBlockEntity extends BlockEntity implements GeoBlockEntity {
	String TYPE = "classic";
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public PrideFlagBlockEntity(BlockPos pos, BlockState state) {
		super(BlockRegistry.PRIDE_FLAG_BLOCKENTITY, pos, state);
		//this.getWorld().getChunkManager()..markForUpdate(this.getPos());
	}

	// Data Management
	@Override
	public void writeNbt(NbtCompound nbt, HolderLookup.Provider r) {
		nbt.putString("flag_type", TYPE);
		super.writeNbt(nbt, r);
	}

	@Override
	public void readNbtImpl(NbtCompound nbt, HolderLookup.Provider r) {
		super.readNbtImpl(nbt, r);
		TYPE = nbt.getString("flag_type");
	}
	@Override
	public void markDirty() {
		super.markDirty();
		if (world != null) {
			world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
		}
	}

	public String fetchTYPE() {
		this.markDirty();
		return this
			.getComponents()
			.getOrDefault(
				DataComponentRegistry.FLAG_TYPE,
				TYPE
			);
	}

	@Override
	protected void readComponents(ComponentAccess c) {
		super.readComponents(c);
		this.TYPE = c.getOrDefault(DataComponentRegistry.FLAG_TYPE, "classic");
	}

	@Override
	protected void addComponents(DataComponentMap.Builder cmb) {
		super.addComponents(cmb);
		cmb.put(DataComponentRegistry.FLAG_TYPE, TYPE);
	}

	@Override
	public void removeComponentData(NbtCompound nbt) {
		nbt.remove("flag_type");

	}

	@Override
	public NbtCompound toSyncedNbt(HolderLookup.Provider lp) {
		//NbtCompound nbt = new NbtCompound();
		//this.writeNbt(nbt, lp);
		return toNbt(lp);
	}

	//GECKOLIB
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	//Packet Syncing

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this,(e,r)->e.toSyncedNbt(r));
		//this.
	}
}
