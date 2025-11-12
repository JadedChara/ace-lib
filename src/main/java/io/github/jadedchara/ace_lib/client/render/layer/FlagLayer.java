package io.github.jadedchara.ace_lib.client.render.layer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.api.client.render.AddonBlockModelRenderLayer;
import io.github.jadedchara.ace_lib.common.api.client.render.AdjustableGeoBlockModel;
import io.github.jadedchara.ace_lib.common.api.client.render.BlockSubRenderer;
import io.github.jadedchara.ace_lib.common.block.PrideFlagBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;


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
					 Identifier texture, BlockSubRenderer<PrideFlagBlockEntity> g) {
		super(g,model, texture);
		//this.ANIMATABLE = g.getAnimatable();
		this.MODEL = model;
		this.TEXTURE = texture;
		//this.getRenderer().getAnimatable().
	}


	@Override
	protected Identifier getTextureResource(PrideFlagBlockEntity a) {
		String type = a.fetchTYPE().replaceAll("\"","");
		this.TEXTURE = Identifier.of(
			AceLib.MOD_ID,
			"textures/flag/"+type+".png");

		return this.TEXTURE;
	}

	/**
	 * <b>THIS METHOD MAY BE UNUSED.</b>
	 * @param e Base BlockEntity used for retrieving
	 */
	@Deprecated
	@Override
	public void updateTexture(PrideFlagBlockEntity e){
		String type = e.fetchTYPE().replaceAll("\"","");
		//this.ANIMATABLE.getc

		this.TEXTURE = Identifier.of(
			AceLib.MOD_ID,
			"textures/flag/"+type+".png");
		this.MODEL.setTexture(e,this.TEXTURE);
	}


	@Override
	public void render(MatrixStack ps, PrideFlagBlockEntity an, BakedGeoModel bm,
					   RenderLayer rt, VertexConsumerProvider bs, VertexConsumer b, float pt,
					   int pl, int po) {
		//an.markDirty();
		try{
			this.RENDERER.render(this.patchAnimatable,this.partialTick,this.poseStack,this.bufferSource,
				this.packedLight,this.packedOverlay);
		}catch(Exception e){
			super.render(ps, an, bm, rt, bs, b, pt, pl, po);
		}
	}
}
