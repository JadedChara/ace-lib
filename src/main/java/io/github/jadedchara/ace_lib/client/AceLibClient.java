package io.github.jadedchara.ace_lib.client;

import io.github.jadedchara.ace_lib.client.render.PrideFlagRenderer;
import io.github.jadedchara.ace_lib.common.registry.BlockRegistry;
import io.github.jadedchara.ace_lib.common.api.PlayerListHudAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class AceLibClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(
			BlockRegistry.PRIDE_FLAG_BLOCKENTITY,
			PrideFlagRenderer::new
		);

		/*HudRenderCallback.EVENT.register((drawContext, tickDeltaManager)->{
			PlayerListHud hud = MinecraftClient.getInstance().inGameHud.getPlayerListHud();
			if(((PlayerListHudAccessor)hud).getVisibility()){
				//render logic for flag displays.
			}
		});*/

		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.PRIDE_FLAG,RenderLayer.getTranslucent());


	}
}
