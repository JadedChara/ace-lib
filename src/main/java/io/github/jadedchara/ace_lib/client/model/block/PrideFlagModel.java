package io.github.jadedchara.ace_lib.client.model.block;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoBlockModel;
import io.github.jadedchara.ace_lib.common.block.PrideFlagBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoRenderer;


/**
 * Implementation of {@link AdjustableGeoBlockModel} for use with {@link PrideFlagBlockEntity}.
 * <br></br>
 * To access the stored component textures, it references the components, and if that fails, returns a default.
 * @Author Nightstrike
 * @Version 1.0.0
 */
public class PrideFlagModel extends AdjustableGeoBlockModel<PrideFlagBlockEntity> {
	public Identifier MODEL = buildFormattedModelPath(Identifier.of(AceLib.MOD_ID,"pride_flag"));
	public Identifier TEXTURE = Identifier.of(AceLib.MOD_ID, "textures/flag/classic.png");
	public Identifier ANIMATION = buildFormattedAnimationPath(Identifier.of(AceLib.MOD_ID,"pride_flag"));


	public PrideFlagModel() {
		super(Identifier.of(AceLib.MOD_ID,"pride_flag"));

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
	public Identifier getTextureResource(PrideFlagBlockEntity a, @Nullable GeoRenderer renderer){
		try{

			return Identifier.of(AceLib.MOD_ID,
				"textures/flag/" + a.fetchTYPE().replaceAll("\"","")+".png");
		}catch(Exception e){
			//System.out.println(e);
			return this.TEXTURE;
		}
		//return this.TEXTURE;
	}

	@Override
	public RenderLayer getRenderType(PrideFlagBlockEntity a, Identifier t) {
		return RenderLayer.getEntityTranslucent(getTextureResource(a));
	}

}
