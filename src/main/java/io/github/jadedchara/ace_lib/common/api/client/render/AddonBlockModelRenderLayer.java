package io.github.jadedchara.ace_lib.common.api.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;


/**
 * Implementation of {@link GeoRenderLayer} for bundling a new model and texture using {@link BlockSubRenderer}
 * @Author Nightstrike
 * @Version 1.0.0
 */
public class AddonBlockModelRenderLayer<T extends BlockEntity & GeoAnimatable> extends GeoRenderLayer<T> {
	public Identifier TEXTURE;
	public AdjustableGeoBlockModel<T> MODEL;
	public AddonBlockModelRenderLayer(AdjustableGeoBlockModel<T> model, Identifier texture) {

		super(new BlockSubRenderer<T>(model));
		this.MODEL = model;
		this.TEXTURE = texture;
		//this.getGeoModel()
		//this.render();

	}
	@Override
	public GeoModel<T> getGeoModel() {
		return this.MODEL;
	}
	@Override
	protected Identifier getTextureResource(T animatable) {
		return this.TEXTURE;
	}

	@Deprecated
	public void updateTexture(T animatable){}
	@Deprecated
	public void updateModel(T animatable){}
	@Deprecated
	public void updateAnimations(T animatable){}

	@Override
	public void render(MatrixStack poseStack,
					   T animatable,
					   BakedGeoModel bakedModel,
					   RenderLayer renderType,
					   VertexConsumerProvider bufferSource,
					   VertexConsumer buffer,
					   float partialTick,
					   int packedLight,
					   int packedOverlay)
	{
		RenderLayer rl = RenderLayer.getEntityCutout(this.getTextureResource(animatable));

		updateTexture(animatable);

		getRenderer()
			.reRender(
				getDefaultBakedModel(animatable),
				poseStack,
				bufferSource,
				animatable,
				rl,
				bufferSource.getBuffer(rl),
				partialTick,
				packedLight,
				OverlayTexture.DEFAULT_UV,
				Color.WHITE.getColor()
			);
	}

}
