package io.github.jadedchara.ace_lib.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.client.model.item.FlagItemClothModel;
import io.github.jadedchara.ace_lib.client.model.item.FlagItemModel;
import io.github.jadedchara.ace_lib.client.render.layer.FlagItemLayer;
import io.github.jadedchara.ace_lib.common.item.PrideFlag;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;

public class FlagItemRenderer extends DynamicGeoItemRenderer<PrideFlag> {
	public FlagItemRenderer() {
		super(new FlagItemModel(Identifier.of(AceLib.MOD_ID,"pride_flag")));
		addRenderLayer(
			new FlagItemLayer(
				new FlagItemClothModel(
					Identifier.of(
						AceLib.MOD_ID,
						"pride_flag"
					)
				),
				Identifier.of(AceLib.MOD_ID,"textures/flag/classic.png"),
				this
			)
		);
		//this.render();
	}
	@Override
	public void render(ItemStack stack, ModelTransformationMode transformType, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay) {
		this.animatable = (PrideFlag) stack.getItem();
		this.currentItemStack = stack;

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
