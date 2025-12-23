package io.github.jadedchara.ace_lib.common.entity;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.server.world.ServerWorld;

public class DummyPlayer extends FakePlayer {
	public DummyPlayer(ServerWorld world, GameProfile profile) {
		super(world, profile);
		//this.setPos();
	}
}
