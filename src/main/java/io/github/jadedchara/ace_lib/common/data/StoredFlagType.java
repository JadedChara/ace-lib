package io.github.jadedchara.ace_lib.common.data;

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
	public List<String> flags;

	public List<String> getValue() { return this.getFlags(); }


	public StoredFlagType(LivingEntity ent){
		this.entity = ent;
		this.flags = List.of("transgender");
	}

	public List<String> getFlags() {
		return flags;
	}

	public void addFlag(String f) {
		this.flags.add(f);
	}

	public void removeFlag(String f){
		try{
			this.flags.remove(f);
		}catch(Exception e){
			//flag either doesn't exist, or, y'know...
		}
	}

	public void clearFlags(){
		try{
			this.flags.clear();
		}catch(Exception e){
			//
		}
	}

	@Override
	public void serverTick() {

	}



	@Override
	public void readFromNbt(NbtCompound nbtCompound, HolderLookup.Provider provider) {
		try{
			this.flags.clear();
		}catch(Exception e){
			//probably means flags is already clear lol.
		}
		for (NbtElement elem : nbtCompound.getList("prideflags",8)){
			this.flags.add(elem.asString());
		}
		//this.flags.add(nbtCompound.getList("prideflags", 8));
	}

	@Override
	public void writeToNbt(NbtCompound nbtCompound, HolderLookup.Provider provider) {
		for (String elem: this.flags){
			nbtCompound.putString("prideflags",elem);
		}
	}
}
