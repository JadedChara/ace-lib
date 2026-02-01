package io.github.jadedchara.ace_lib.client;

import io.github.jadedchara.ace_lib.client.render.PrideFlagRenderer;
import io.github.jadedchara.ace_lib.common.api.common.payload.UpdateFlagsPayload;
import io.github.jadedchara.ace_lib.common.registry.BlockRegistry;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.ItemStack;

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


		//FLAG ADDITION PACKETS (Server->Client)
		//Adds the flags to the respective itemgroup
		ClientPlayNetworking.registerGlobalReceiver(UpdateFlagsPayload.ID,(payload, context)->{
			context.client().execute(()->{
				if (MinecraftClient.getInstance().currentScreen instanceof CreativeInventoryScreen) {
					MinecraftClient.getInstance().setScreen(null);
				}
				BlockRegistry.PRIDE_FLAGS.getOrInitTabStacks().clear();
				//Adds in Flags
				for (ItemStack flag : payload.newItems()){
					BlockRegistry.PRIDE_FLAGS.getOrInitTabStacks().add(flag);
					System.out.println(
						"Added a "
							+ flag.getComponents().get(DataComponentRegistry.FLAG_TYPE)
							+ " flag! Enjoy!"
					);
				}
			});
		});

		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.PRIDE_FLAG,RenderLayer.getTranslucent());


	}
}
