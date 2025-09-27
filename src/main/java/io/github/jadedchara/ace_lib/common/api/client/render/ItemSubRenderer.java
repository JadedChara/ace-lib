package io.github.jadedchara.ace_lib.common.api.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.specialty.DynamicGeoBlockRenderer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;


/**
 * Implementation of {@link DynamicGeoItemRenderer} for use with {@link AddonItemModelRenderLayer}.
 * <br></br>
 * This gets called in the super of AddonItemModelRenderLayer, so it doesn't need to be referenced anywhere else.
 * @Author Nightstrike
 * @Version 1.0.0
 */
public class ItemSubRenderer<T extends Item & GeoAnimatable> extends DynamicGeoItemRenderer<T> {
	public ItemSubRenderer(AdjustableGeoItemModel<T> model) {
		super(model);
		//this.render();

	}

	public ItemStack getCurrentItemStack(){
		return this.currentItemStack;
	}

	@Override
	public GeoModel<T> getGeoModel() {
		return this.model;
	}

	public void tweakModel(ItemStack s){}

	@Override
	public void render(ItemStack stack, ModelTransformationMode transformType, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay) {
		this.animatable = (T) stack.getItem();
		this.currentItemStack = stack;
		tweakModel(this.currentItemStack);
		this.renderPerspective = transformType;
		float partialTick = MinecraftClient.getInstance().method_60646().getTickDelta(true);
		if (transformType == ModelTransformationMode.GUI) {
			this.renderInGui(transformType, poseStack, bufferSource, packedLight, packedOverlay, partialTick);
		} else {
			RenderLayer renderType = this.getRenderType(this.animatable, this.getTextureLocation(this.animatable), bufferSource, partialTick);
			VertexConsumer buffer = ItemRenderer.getDirectItemGlintConsumer(bufferSource, renderType, false, this.currentItemStack != null && this.currentItemStack.hasGlint());
			this.defaultRender(poseStack, this.animatable, bufferSource, renderType, buffer, 0.0F, partialTick, packedLight);
		}

		this.animatable = null;
	}

}
