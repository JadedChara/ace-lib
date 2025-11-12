package io.github.jadedchara.ace_lib.client.render.sprite;

import io.github.jadedchara.ace_lib.AceLib;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasHolder;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

public class FlagSpriteManager extends SpriteAtlasHolder implements IdentifiableResourceReloadListener {
	public static Identifier ATLAS_ID = Identifier.of(AceLib.MOD_ID,"textures/atlas/flag.png");
	public static Identifier FLAG_PATH = Identifier.of(AceLib.MOD_ID,"flag");



	public FlagSpriteManager(TextureManager textureManager, Identifier atlasId, Identifier id) {
		super(MinecraftClient.getInstance().getTextureManager(), ATLAS_ID, id);
	}

	@Override
	public Identifier getFabricId() {
		return Identifier.of(AceLib.MOD_ID,"textures/flag");
	}
}
