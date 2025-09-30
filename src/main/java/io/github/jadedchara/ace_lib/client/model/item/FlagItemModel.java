package io.github.jadedchara.ace_lib.client.model.item;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoItemModel;
import io.github.jadedchara.ace_lib.common.item.PrideFlag;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.data.client.model.Models;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoRenderer;

public class FlagItemModel extends AdjustableGeoItemModel<PrideFlag> {
	public Identifier MODEL = buildFormattedModelPath(Identifier.of(AceLib.MOD_ID,"pride_flag"));
	public Identifier TEXTURE = buildFormattedTexturePath(Identifier.of(AceLib.MOD_ID,"flag_base"));
	public Identifier ANIMATION = buildFormattedAnimationPath(Identifier.of(AceLib.MOD_ID,"pride_flag"));
	public FlagItemModel(Identifier reference) {
		super(reference);
		//this.TEXTURE = texPass;
	}
	@Override
	public Identifier getAnimationResource(PrideFlag a){
		return this.ANIMATION;
	}
	@Override
	public Identifier getModelResource(PrideFlag a){
		return this.MODEL;
	}
	@Override
	public Identifier getTextureResource(PrideFlag a, @Nullable GeoRenderer renderer){
		return this.TEXTURE;

		//return this.TEXTURE;
	}
}
