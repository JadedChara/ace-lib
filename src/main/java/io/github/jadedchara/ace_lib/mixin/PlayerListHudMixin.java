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
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin implements PlayerListHudAccessor {

	//Initial carry-overs
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

	@Shadow
	protected abstract List<PlayerListEntry> getVisibleEntries();

	//Adjusts width based on flag count. Still experimental.

	@ModifyArg(
		method = {"render"},
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;min(II)I"
		),
		index = 0
	)
	private int modifywidth(int a){
		int maxFlagWidth = 0;
		List<PlayerListEntry> entries = this.getVisibleEntries();
		for(PlayerListEntry entry : entries){
			int numb = this
				.client
				.world
				.getPlayerByUuid(
					entry.getProfile().getId()
				)
				.getComponent(AceLib.DISPLAYFLAG)
				.getFlags()
				.size();
			if(numb > maxFlagWidth){
				maxFlagWidth=numb;
			}
		}
		return a+5+(maxFlagWidth*14);
	}

	//Adjusts player name for tab-list!
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

	//Adds in the redirect for flags!
	@Inject(method="renderLatencyIcon",at=@At("HEAD"),cancellable = true)
	public void insertFlags(GuiGraphics graphics, int width, int x, int y, PlayerListEntry entry, CallbackInfo ci){
		renderFlagIcon(graphics,width,x,y,entry);
	}

	//Handles flag rendering, ignoring if there aren't any.
	@Unique
	public void renderFlagIcon(GuiGraphics graphics, int width, int x, int y, @NotNull PlayerListEntry entry) {
		Set<String> flagSet = this
			.client
			.world
			.getPlayerByUuid(entry.getProfile().getId())
			.getComponent(AceLib.DISPLAYFLAG)
			.getFlags();
		List<String> flagList = new ArrayList<>();
		flagList.addAll(flagSet);
		int indexing = 1;
		for(String f : flagList){
			graphics.getMatrices().push();
			graphics.getMatrices().translate(0.0F, 0.0F, 100.0F);
			graphics.drawTexture(
				Identifier.of(
					AceLib.MOD_ID,
					"textures/flag/"+f.replaceAll("\"","")+".png"
				),
				x+width-(13+(indexing*13)),
				y,
				0.0F,
				0.0F,
				12,
				8,
				12,
				8
			);
			graphics.getMatrices().pop();
			indexing++;
		}

	}
}
