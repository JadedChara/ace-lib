package io.github.jadedchara.ace_lib.common.api.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;
import software.bernie.geckolib.util.Color;

public class AddonItemModelRenderLayer<T extends Item & GeoAnimatable> extends GeoRenderLayer<T> {
	public Identifier TEXTURE;
	public AdjustableGeoItemModel<T> MODEL;
	public DynamicGeoItemRenderer<T> RENDERER;

	//pre-built renderer for non-dynamic changes to the layer.
	public AddonItemModelRenderLayer(AdjustableGeoItemModel<T> model, Identifier texture) {
		super(new ItemSubRenderer<>(model));
		this.MODEL = model;
		this.TEXTURE = texture;

	}
	//Input your own implementation of the renderer, to override whatever is needed.
	public AddonItemModelRenderLayer(ItemSubRenderer m,AdjustableGeoItemModel<T> model, Identifier texture) {
		super(m);
		this.MODEL = model;
		this.TEXTURE = texture;

	}
	@Override
	public AdjustableGeoItemModel<T> getGeoModel() {
		return this.MODEL;
	}
	@Override
	public Identifier getTextureResource(T animatable) {
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
					   @Nullable RenderLayer renderType,
					   VertexConsumerProvider bufferSource,
					   @Nullable VertexConsumer buffer,
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
