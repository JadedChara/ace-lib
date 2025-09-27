package io.github.jadedchara.ace_lib.client.render.layer;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.api.client.render.AddonBlockModelRenderLayer;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoBlockModel;
import io.github.jadedchara.ace_lib.common.block.PrideFlagBlockEntity;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.util.Identifier;


/**
 * Implementation of {@link AddonBlockModelRenderLayer} for use with {@link PrideFlagBlockEntity}
 * <br></br> Renders the actual flag's fabric with the corresponding pride flag.
 * @Author Nightstrike
 * @Version 1.0.0
 */
public class FlagLayer extends AddonBlockModelRenderLayer<PrideFlagBlockEntity> {

	/**
	 *
	 * @param model Utilized for actually shaping the model addon.
	 * @param texture Utilized as a safety, in case the built-in renderer should fail to retrieve the model texture.
	 */
	public FlagLayer(AdjustableGeoBlockModel<PrideFlagBlockEntity> model, Identifier texture) {
		super(model, texture);
		this.MODEL = model;
		this.TEXTURE = texture;
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
