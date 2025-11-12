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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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
		List<String> flagarray = this
			.client
			.world
			.getPlayerByUuid(entry.getProfile().getId())
			.getComponent(AceLib.DISPLAYFLAG)
			.getFlags();
		//this.client.getSpriteAtlas().
		//this.client.getGuiAtlasManager().getSprite()
		if(!flagarray.isEmpty()){
			for (String flag : flagarray){
				flag = flag.replaceAll("\"","");
				id = Identifier.of(AceLib.MOD_ID,"textures/flag/"+flag+".png");
				graphics.getMatrices().push();
				graphics.getMatrices().translate(0.0F, 0.0F, 100.0F);
				graphics.drawTexture(id,x+width+1,y,0.0F,0.0F,12,8,12,8);
				//graphics.drawGuiTexture(id, x + width + 1, y, 12, 8);
				graphics.getMatrices().pop();
			}
		}


		/*if (entry.getLatency() < 0) {
			identifier = PING_UNKNOWN;
		} else if (entry.getLatency() < 150) {
			identifier = PING_5;
		} else if (entry.getLatency() < 300)
			identifier = PING_4;
		} else if (entry.getLatency() < 600) {
			identifier = PING_3;
		} else if (entry.getLatency() < 1000) {
			identifier = PING_2;
		} else {
			identifier = PING_1;
		}*/


		//graphics.getMatrices().push();
		//graphics.getMatrices().translate(0.0F, 0.0F, 100.0F);
		//graphics.drawGuiTexture(identifier, x + width - 11+2, y, 10, 8);
		//graphics.getMatrices().pop();
	}
}
