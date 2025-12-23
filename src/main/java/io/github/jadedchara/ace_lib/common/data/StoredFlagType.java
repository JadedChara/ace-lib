package io.github.jadedchara.ace_lib.common.data;

import io.github.jadedchara.ace_lib.AceLib;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.HolderLookup;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import org.ladysnake.cca.api.v3.entity.C2SSelfMessagingComponent;

import java.util.List;

public class StoredFlagType implements AutoSyncedComponent, ServerTickingComponent {
	public LivingEntity entity;
	public String flag;

	public String getValue() { return this.getFlag(); }


	public StoredFlagType(LivingEntity ent){
		this.entity = ent;
		this.flag = "classic";
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String f, Object p){
		this.flag = f;
		AceLib.DISPLAYFLAG.sync(p);
	}

	public void clearFlag(String f){
		this.flag = "";
	}

	@Override
	public void serverTick() {
		//
	}


	@Override
	public void readFromNbt(NbtCompound nbtCompound, HolderLookup.Provider provider) {
		this.flag = nbtCompound.getString("prideflag");
	}

	@Override
	public void writeToNbt(NbtCompound nbtCompound, HolderLookup.Provider provider) {
		nbtCompound.putString("prideflag",this.flag);
	}
}
