package io.github.jadedchara.ace_lib.client.render.layer;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.client.render.PrideFlagRenderer;
import io.github.jadedchara.ace_lib.common.api.client.render.AddonBlockModelRenderLayer;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoBlockModel;
import io.github.jadedchara.ace_lib.common.block.PrideFlagBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;


/**
 * Implementation of {@link AddonBlockModelRenderLayer} for use with {@link PrideFlagBlockEntity}
 * <br></br> Renders the actual flag's fabric with the corresponding pride flag.
 * @author Nightstrike
 * @version 1.0.0
 */
public class FlagLayer extends AddonBlockModelRenderLayer<PrideFlagBlockEntity> {

	/**
	 * @param model   Utilized for actually shaping the model addon.
	 * @param texture Utilized as a safety, in case the built-in renderer should fail to retrieve the model texture.
	 */
	public FlagLayer(AdjustableGeoBlockModel<PrideFlagBlockEntity> model,
					 Identifier texture, PrideFlagRenderer g) {
		super(model,g.getAnimatable(), texture);
		this.MODEL = model;
		this.TEXTURE = texture;
	}

	@Override
	protected Identifier getTextureResource(PrideFlagBlockEntity a) {
		String type = a.fetchTYPE().replaceAll("\"","");

		this.TEXTURE = Identifier.of(
			AceLib.MOD_ID,
			"textures/flag/"+type+".png");

		//this.getRenderer().getAnimatable().triggerAnim("flag","hang");
		return this.TEXTURE;
	}

	/**
	 * <b>THIS METHOD IS UNUSED.</b>
	 * @param e Base BlockEntity used for retrieving
	 */
	@Deprecated
	@Override
	public void updateTexture(PrideFlagBlockEntity e){
		String type = e.fetchTYPE().replaceAll("\"","");

		this.TEXTURE = Identifier.of(
			AceLib.MOD_ID,
			"textures/flag/"+type+".png");

		this.MODEL.setTexture(e,this.TEXTURE);
	}

}
