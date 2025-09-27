package io.github.jadedchara.ace_lib.common.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.jadedchara.ace_lib.AceLib;
import net.minecraft.component.DataComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DataComponentRegistry {

	public static final DataComponentType<String> FLAG_TYPE = Registry.register(
		Registries.DATA_COMPONENT_TYPE,
		Identifier.of(AceLib.MOD_ID, "flag_type"),
		DataComponentType.<String>builder().codec(Codec.STRING).build()
	);

	public static void init(){
		AceLib.LOGGER.info("Registering Data Component Types!");
		//Registry.register(Registries.DATA_COMPONENT_TYPE,FLAG_TYPE_KEY,FLAG_TYPES);

	}
}
