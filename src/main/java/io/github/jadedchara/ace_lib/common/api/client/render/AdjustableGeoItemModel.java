package io.github.jadedchara.ace_lib.common.api.client.render;

import io.github.jadedchara.ace_lib.AceLib;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

/**
 * CURRENTLY WIP
 * @param <T>
 */
public class AdjustableGeoItemModel<T extends GeoAnimatable> extends DefaultedItemGeoModel<T> {
	public Identifier MODEL;
	public Identifier TEXTURE;
	public Identifier ANIMATION;

	public AdjustableGeoItemModel(Identifier reference) {
		super(reference);
	}
	@Override
	public Identifier getAnimationResource(T a){
		return ANIMATION;
	}
	@Override
	public Identifier getModelResource(T a){
		return MODEL;
	}
	@Override
	public Identifier getTextureResource(T a){
		return TEXTURE;

		//this.getRenderType()
	}


	public void setTexture(Identifier t){
		this.TEXTURE = t;
	}
	public void setModel(Identifier t){
		this.MODEL = t;
	}
	public void setAnimation(Identifier t){
		this.ANIMATION = t;
	}

	@Override
	public @Nullable RenderLayer getRenderType(T animatable, Identifier texture) {
		return RenderLayer.getEntityCutoutNoCull(texture);
	}

}
