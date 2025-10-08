package io.github.jadedchara.ace_lib.common.api.client.render;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.block.PrideFlagBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;


public class AdjustableGeoBlockModel<T extends GeoAnimatable> extends DefaultedBlockGeoModel<T> {
	public Identifier MODEL;
	public Identifier TEXTURE;
	public Identifier ANIMATION;


	public AdjustableGeoBlockModel(Identifier reference) {
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

		public void setTexture(T a, Identifier t){
			this.TEXTURE = t;
		}
		public void setModel(T a, Identifier t){
			this.MODEL = t;
		}
		public void setAnimation(T a, Identifier t){
			this.ANIMATION = t;
		}



	}

