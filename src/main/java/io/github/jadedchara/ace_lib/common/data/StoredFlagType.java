package io.github.jadedchara.ace_lib.common.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.jadedchara.ace_lib.AceLib;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.*;
import net.minecraft.registry.HolderLookup;
import org.ladysnake.cca.api.v3.component.ComponentV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import org.spongepowered.include.com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class StoredFlagType implements AutoSyncedComponent, ServerTickingComponent {
	public LivingEntity entity;
	public Set<String> flags;


	public Set<String> getValue() { return this.getFlags(); }
	public StoredFlagType(LivingEntity ent){
		this.entity = ent;
		this.flags = Sets.newHashSet();
		//this.flag = "classic";
	}
	public Set<String> getFlags() {
		return flags;
	}
	public void addFlag(String f, Object p){
		this.flags.add(f);
		AceLib.DISPLAYFLAG.sync(p);
	}
	public void removeFlag(String f, Object p){
		this.flags.remove(f);
		AceLib.DISPLAYFLAG.sync(p);
	}

	public void clearFlags(Object p){
		this.flags.clear();
		AceLib.DISPLAYFLAG.sync(p);
	}

	@Override
	public void serverTick() {
		//Unused currently
	}


	@Override
	public void readFromNbt(NbtCompound nbtCompound, HolderLookup.Provider provider) {
		this.flags.clear();
		nbtCompound.getList("flags",NbtElement.STRING_TYPE).forEach((f)->{
			this.flags.add(f.asString());
		});
		//this.flag = nbtCompound.getString("prideflag");
	}

	@Override
	public void writeToNbt(NbtCompound nbtCompound, HolderLookup.Provider provider) {
		NbtList n = new NbtList();
		this.flags.forEach((f)->{
			n.add(NbtString.of(f));
		});
		nbtCompound.put("flags",n);
		//nbtCompound.putString("prideflag",this.flag);
	}
}
