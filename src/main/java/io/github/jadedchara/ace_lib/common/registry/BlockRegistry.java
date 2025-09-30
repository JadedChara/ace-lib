package io.github.jadedchara.ace_lib.common.registry;

import com.mojang.serialization.Codec;
import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.block.PrideFlagBlock;
import io.github.jadedchara.ace_lib.common.block.PrideFlagBlockEntity;
import io.github.jadedchara.ace_lib.common.item.PrideFlag;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.PaintingTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BlockRegistry {

	public static Block registerBlockItem(Block block, String name) {
		Identifier id = Identifier.of(AceLib.MOD_ID, name);
		BlockItem blockItem = new BlockItem(
			block,
			new Item.Settings()
		);
		Registry.register(Registries.ITEM, id, blockItem);
		return Registry.register(Registries.BLOCK, id, block);
	}
	public static Block registerPrideFlag(Block block, String name) {
		Identifier id = Identifier.of(AceLib.MOD_ID, name);
		BlockItem blockItem = new PrideFlag(block,
			new Item.Settings()
				.component(
					DataComponentRegistry.FLAG_TYPE,"classic"
				)//.equipmentSlot()
		);
		Registry.register(Registries.ITEM, id, blockItem);
		return Registry.register(Registries.BLOCK, id, block);
	}
	//public static final BlockItem PRIDE_FLAG_ITEM = Registry.register()
	public static final Block PRIDE_FLAG =  registerPrideFlag(
		new PrideFlagBlock(
			AbstractBlock.Settings.create()
				.sounds(BlockSoundGroup.WOOL)
				.dynamicBounds()
				.nonOpaque()

		),
		"pride_flag"
	);
	public static BlockEntityType<PrideFlagBlockEntity> PRIDE_FLAG_BLOCKENTITY = Registry.register(
		Registries.BLOCK_ENTITY_TYPE,
		Identifier.of(AceLib.MOD_ID, "pride_flag_blockentity"),
		BlockEntityType.Builder.create(
			PrideFlagBlockEntity::new,
			BlockRegistry.PRIDE_FLAG
		).build()
	);

	public static final RegistryKey<ItemGroup> PRIDE_FLAGS_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),
		Identifier.of(AceLib.MOD_ID, "pride_flags"));
	public static ItemGroup PRIDE_FLAGS = FabricItemGroup.builder()
		.icon(() -> new ItemStack(PRIDE_FLAG))
		.name(Text.translatable("itemGroup.ace_lib.pride_flags"))
		.entries((dP, entries)->{
			ItemStack is = new ItemStack(PRIDE_FLAG.asItem());
			is.set(DataComponentRegistry.FLAG_TYPE,"classic");
			entries.addStack(is);

		})
		.build();

	//
	public static void init(){
		AceLib.LOGGER.info("Registering Blocks!");
		Registry.register(Registries.ITEM_GROUP,PRIDE_FLAGS_KEY,PRIDE_FLAGS);
	}

}
