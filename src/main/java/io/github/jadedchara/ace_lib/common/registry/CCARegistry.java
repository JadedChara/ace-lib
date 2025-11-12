package io.github.jadedchara.ace_lib.common.registry;

import io.github.jadedchara.ace_lib.AceLib;
import io.github.jadedchara.ace_lib.common.data.StoredFlagType;
import net.minecraft.entity.LivingEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class CCARegistry implements EntityComponentInitializer {
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
		entityComponentFactoryRegistry.registerFor(LivingEntity.class,AceLib.DISPLAYFLAG, StoredFlagType::new);
	}
}
