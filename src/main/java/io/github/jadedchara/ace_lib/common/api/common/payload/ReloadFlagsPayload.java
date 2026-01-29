package io.github.jadedchara.ace_lib.common.api.common.payload;

import io.github.jadedchara.ace_lib.AceLib;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.util.Identifier;

public record ReloadFlagsPayload(boolean reload) implements CustomPayload {

	//definition
	public static final Identifier iden = Identifier.of(AceLib.MOD_ID, "reload_flags");
	public static final Id<ReloadFlagsPayload> ID = new Id<>(iden);
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	//utils
	public static final PacketCodec<RegistryByteBuf, ReloadFlagsPayload> CODEC = PacketCodec.tuple(
		PacketCodecs.BOOL, ReloadFlagsPayload::reload,
		ReloadFlagsPayload::new);

}
