package io.github.jadedchara.ace_lib.client.model.block;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoBlockModel;
import io.github.jadedchara.ace_lib.common.block.PrideFlagBlockEntity;
import net.minecraft.util.Identifier;


/**
 * Implementation of {@link AdjustableGeoBlockModel} for use with {@link PrideFlagBlockEntity}.
 * <br></br>
 * As this iteration does not require any dynamic changes, it's otherwise verbatim with normal models.
 * @Author Nightstrike
 * @Version 1.0.0
 */
public class FlagPoleModel extends AdjustableGeoBlockModel<PrideFlagBlockEntity> {
	public Identifier MODEL = buildFormattedModelPath(Identifier.of(AceLib.MOD_ID,"flag_pole"));
	public Identifier TEXTURE = buildFormattedTexturePath(Identifier.of(AceLib.MOD_ID,"flag_pole"));
	public Identifier ANIMATION = buildFormattedAnimationPath(Identifier.of(AceLib.MOD_ID,"flag_pole"));


	public FlagPoleModel(Identifier reference) {
		super(reference);

	}
	@Override
	public Identifier getAnimationResource(PrideFlagBlockEntity a){
		return this.ANIMATION;
	}
	@Override
	public Identifier getModelResource(PrideFlagBlockEntity a){
		return this.MODEL;
	}
	@Override
	public Identifier getTextureResource(PrideFlagBlockEntity a){
		return this.TEXTURE;
	}
}
