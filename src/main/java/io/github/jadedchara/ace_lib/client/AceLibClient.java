package io.github.jadedchara.ace_lib.client;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.client.render.PrideFlagRenderer;
import io.github.jadedchara.ace_lib.common.api.common.payload.ReloadFlagsPayload;
import io.github.jadedchara.ace_lib.common.api.common.payload.UpdateFlagsPayload;
import io.github.jadedchara.ace_lib.common.registry.BlockRegistry;
import io.github.jadedchara.ace_lib.common.api.PlayerListHudAccessor;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

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
		ClientPlayNetworking.registerGlobalReceiver(ReloadFlagsPayload.ID,(payload,context)->{
			context.client().execute(()->{
				if (MinecraftClient.getInstance().currentScreen instanceof CreativeInventoryScreen) {
					MinecraftClient.getInstance().setScreen(null);
					BlockRegistry.PRIDE_FLAGS.getOrInitTabStacks().clear();
				}
			});
		});
		ClientPlayNetworking.registerGlobalReceiver(UpdateFlagsPayload.ID,(payload, context)->{
			context.client().execute(()->{
				/*if (
					payload.identifier() == Identifier.of(AceLib.MOD_ID,"new_flag") &&
					payload.newItem().isOf(BlockRegistry.PRIDE_FLAG.asItem())
				){*/
					//BlockRegistry.PRIDE_FLAGS.getOrInitTabStacks().clear();
					BlockRegistry.PRIDE_FLAGS.getOrInitTabStacks().add(payload.newItem());
					System.out.println(
						"Added a "
							+ payload.newItem().getComponents().get(DataComponentRegistry.FLAG_TYPE)
							+ " flag! Enjoy!"
					);
				//}
			});
		});

		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.PRIDE_FLAG,RenderLayer.getTranslucent());


	}
}
