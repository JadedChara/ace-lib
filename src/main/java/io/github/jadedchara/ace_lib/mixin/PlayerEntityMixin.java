package io.github.jadedchara.ace_lib.mixin;

import com.mojang.authlib.GameProfile;
import io.github.jadedchara.ace_lib.AceLib;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements Nameable {


	@Shadow
	public abstract Text getDisplayName();

	@Shadow
	@Final
	private GameProfile gameProfile;

	@Inject(method="getName",at=@At("HEAD"),cancellable = true)
	public void getFixedName(CallbackInfoReturnable<Text> cir){
		if (this.hasCustomName()){
			cir.setReturnValue(this.getCustomName());
		}
		else{
			cir.setReturnValue(Text.literal(this.gameProfile.getName()));
		}
	}

	@Override
	public Text getName() {
		return Text.literal(this.gameProfile.getName());
	}
}
