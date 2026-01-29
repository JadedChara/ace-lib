package io.github.jadedchara.ace_lib.mixin;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemGroups.class)
public interface ItemGroupsAccess {

	@Accessor
	public static ItemGroup.DisplayParameters getCachedParameters(){
		throw new AssertionError();
	}
}
