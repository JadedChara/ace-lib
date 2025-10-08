package io.github.jadedchara.ace_lib.client;

import io.github.jadedchara.ace_lib.client.render.PrideFlagRenderer;
import io.github.jadedchara.ace_lib.common.registry.BlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class AceLibClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(
			BlockRegistry.PRIDE_FLAG_BLOCKENTITY,
			PrideFlagRenderer::new
		);



		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.PRIDE_FLAG,RenderLayer.getTranslucent());


	}
}
