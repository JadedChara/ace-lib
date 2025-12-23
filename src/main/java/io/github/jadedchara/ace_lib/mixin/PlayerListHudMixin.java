package io.github.jadedchara.ace_lib.mixin;


import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.api.PlayerListHudAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin implements PlayerListHudAccessor {


	public boolean getVisibility(){
		return this.visible;
	}

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	protected abstract Text applyGameModeFormatting(PlayerListEntry entry, MutableText name);

	//int y, in render needs to be y+14

	/*@ModifyConstant(method="render",constant = @Constant(intValue = 13))
	public int tweakBox(int constant){
		return constant+16;
	}*/
	@ModifyArg(
		method = {"render"},
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;min(II)I"
		),
		index = 0
	)
	private int modifywidth(int a){
		return a+19;
	}


	@Shadow
	private boolean visible;

	@Inject(method="getPlayerName",at=@At("HEAD"),cancellable = true)
	public void patchlistedname(PlayerListEntry entry, CallbackInfoReturnable<Text> cir){
		cir.setReturnValue(this.client.world.getPlayerByUuid(entry.getProfile().getId()).getDisplayName() != null ?
			this.applyGameModeFormatting(
			entry, this.client.world.getPlayerByUuid(entry.getProfile().getId()).getDisplayName().copy()) :
			this.applyGameModeFormatting(
				entry,
				Team.decorateName(
					entry.getScoreboardTeam(),
					Text.literal(String.valueOf(this.client.world.getPlayerByUuid(entry.getProfile().getId()).getName())))));
	}

	@Inject(method="renderLatencyIcon",at=@At("HEAD"),cancellable = true)
	public void insertFlags(GuiGraphics graphics, int width, int x, int y, PlayerListEntry entry, CallbackInfo ci){
		renderFlagIcon(graphics,width,x,y,entry);
	}


	public void renderFlagIcon(GuiGraphics graphics, int width, int x, int y, PlayerListEntry entry) {
		Identifier id;
		String displayflag = this
			.client
			.world
			.getPlayerByUuid(entry.getProfile().getId())
			.getComponent(AceLib.DISPLAYFLAG)
			.getFlag();
		//this.client.getSpriteAtlas().
		//this.client.getGuiAtlasManager().getSprite()
		if(displayflag != null || displayflag != ""){
			displayflag = displayflag.replaceAll("\"","");
			id = Identifier.of(AceLib.MOD_ID,"textures/flag/"+displayflag+".png");
			graphics.getMatrices().push();
			graphics.getMatrices().translate(0.0F, 0.0F, 100.0F);
			graphics.drawTexture(id,x+width-26,y,0.0F,0.0F,12,8,12,8);
			graphics.getMatrices().pop();
		}
	}
}
