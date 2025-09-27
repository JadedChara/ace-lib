package io.github.jadedchara.ace_lib.client.model.item;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoItemModel;
import io.github.jadedchara.ace_lib.common.item.PrideFlag;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.data.client.model.Models;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoRenderer;

public class FlagItemClothModel extends AdjustableGeoItemModel<PrideFlag> {
	public Identifier MODEL = buildFormattedModelPath(Identifier.of(AceLib.MOD_ID,"pride_flag_cloth"));
	public Identifier TEXTURE = Identifier.of(AceLib.MOD_ID,"textures/flag/classic.png");
	public Identifier ANIMATION = buildFormattedAnimationPath(Identifier.of(AceLib.MOD_ID,"pride_flag"));
	public FlagItemClothModel(Identifier reference) {
		super(reference);
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

	}
}
