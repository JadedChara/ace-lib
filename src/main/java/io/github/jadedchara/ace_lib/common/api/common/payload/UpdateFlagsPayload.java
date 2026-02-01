package io.github.jadedchara.ace_lib.common.api.common.payload;

import io.github.jadedchara.ace_lib.AceLib;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.List;

public record UpdateFlagsPayload(List<ItemStack> newItems) implements CustomPayload {
	//definition
	public static final Identifier iden = Identifier.of(AceLib.MOD_ID, "update_flags");
	public static final CustomPayload.Id<UpdateFlagsPayload> ID = new CustomPayload.Id<>(iden);
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	//Utils
	public static final PacketCodec<RegistryByteBuf, UpdateFlagsPayload> CODEC = PacketCodec.tuple(
		ItemStack.field_48350, UpdateFlagsPayload::newItems,
		UpdateFlagsPayload::new);
}
