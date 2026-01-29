package io.github.jadedchara.ace_lib.common.api.common.payload;

import io.github.jadedchara.ace_lib.AceLib;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.util.Identifier;

public record UpdateFlagsPayload(Identifier identifier, ItemStack newItem) implements CustomPayload {
	//definition
	public static final Identifier iden = Identifier.of(AceLib.MOD_ID, "update_flags");
	public static final CustomPayload.Id<UpdateFlagsPayload> ID = new CustomPayload.Id<>(iden);
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	//Utils
	public static final PacketCodec<RegistryByteBuf, UpdateFlagsPayload> CODEC = PacketCodec.tuple(
		Identifier.PACKET_CODEC, UpdateFlagsPayload::identifier,
		ItemStack.PACKET_CODEC, UpdateFlagsPayload::newItem,
		UpdateFlagsPayload::new);
}
