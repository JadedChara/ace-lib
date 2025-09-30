package io.github.jadedchara.ace_lib.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.client.model.item.FlagItemClothModel;
import io.github.jadedchara.ace_lib.client.model.item.FlagItemModel;
import io.github.jadedchara.ace_lib.client.render.layer.FlagItemLayer;
import io.github.jadedchara.ace_lib.common.api.client.render.AddonItemModelRenderLayer;
import io.github.jadedchara.ace_lib.common.item.PrideFlag;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoItemRenderer;

public class FlagItemRenderer extends DynamicGeoItemRenderer<PrideFlag> implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {
	//????
	public Identifier tex = Identifier.of(AceLib.MOD_ID,"textures/flag/classic.png");

	public FlagItemRenderer() {
		super(new FlagItemClothModel(Identifier.of(AceLib.MOD_ID,"pride_flag")));
		 addRenderLayer(
			new FlagItemLayer(
				new FlagItemModel(
					Identifier.of(
						AceLib.MOD_ID,
						"pride_flag"
					)
				),
				Identifier.of(AceLib.MOD_ID,"textures/flag/classic.png")
			)
		);
		//this.render();
	}
	@Override
	public void render(ItemStack stack, ModelTransformationMode transformType, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay) {
		this.animatable = (PrideFlag) stack.getItem();
		this.currentItemStack = stack;


		//TEMPORARY

		for (GeoRenderLayer<PrideFlag> layer : this.getRenderLayers()) {
			if(layer instanceof AddonItemModelRenderLayer<PrideFlag>){
				//
			}
		}
		Identifier flag = Identifier.tryValidate(
			"ace_lib",
			"textures/flag/"+stack
				.getComponents()
				.getOrDefault(DataComponentRegistry.FLAG_TYPE,"classic")
				.replaceAll("\"","")+".png"

		);

		if(flag == null){
			flag = Identifier.of(AceLib.MOD_ID,"textures/flag/classic.png");
		}

		this.renderPerspective = transformType;
		float partialTick = MinecraftClient.getInstance().method_60646().getTickDelta(true);
		if (transformType == ModelTransformationMode.GUI) {

			this.renderInGui(transformType, poseStack, bufferSource, packedLight, packedOverlay, partialTick);

		} else {
			RenderLayer renderType = this.getRenderType(this.animatable, flag, bufferSource, partialTick);
			VertexConsumer buffer = ItemRenderer.getDirectItemGlintConsumer(bufferSource, renderType, false, this.currentItemStack != null && this.currentItemStack.hasGlint());
			this.defaultRender(
				poseStack, this.animatable, bufferSource,
				renderType, buffer, 0.0F, partialTick,
				packedLight);

		}

		this.animatable = null;

	}
	@Override
	public Identifier getTextureLocation(PrideFlag anim){
		try{
			this.tex = Identifier.tryValidate(
				"ace_lib",
				"textures/flag/"+this.getCurrentItemStack()
					.getComponents()
					.getOrDefault(DataComponentRegistry.FLAG_TYPE,"classic")
					.replaceAll("\"","")+".png"

			);
			if(this.tex == null){
				this.tex = Identifier.of(AceLib.MOD_ID,"textures/flag/classic.png");
			}
		}catch(Exception e){

		}
		return this.tex;
	}


	@Override
	public Identifier getFabricId() {
		return null;
	}
}
