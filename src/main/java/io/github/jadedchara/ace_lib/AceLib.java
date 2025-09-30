package io.github.jadedchara.ace_lib;

import com.google.gson.JsonObject;
import io.github.jadedchara.ace_lib.common.registry.BlockRegistry;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AceLib implements ModInitializer {
	public static final String MOD_ID = "ace_lib";
    public static final Logger LOGGER = LoggerFactory.getLogger("AceLib");


    @Override
    public void onInitialize() {
        LOGGER.info("Hello Quilt world from AceLib! Stay fresh!");
		BlockRegistry.init();
		DataComponentRegistry.init();
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES.SERVER_DATA).registerReloadListener(
			new SimpleSynchronousResourceReloadListener() {
				@Override
				public Identifier getFabricId() {
					return Identifier.of(MOD_ID, "flag_type_loader");
				}

				@Override
				public void reload(ResourceManager manager) {

					List<ItemStack> prideFlags = new ArrayList<>();
					ItemStack def = BlockRegistry.PRIDE_FLAG.asItem().getDefaultStack();
					def.set(
						DataComponentRegistry.FLAG_TYPE,
						"classic"
					);
					prideFlags.add(def);
					for(
						Identifier id :
						manager.findResources("flagtype", path -> path.toString().endsWith(".json")).keySet()
					) {
						try(InputStream stream = manager.getResource(id).get().open()) {
							//retrieve objects
							InputStreamReader r = new InputStreamReader(stream);
							JsonObject j = JsonHelper.deserialize(r);
							r.close();
							//
							try{
								System.out.println(
									"Retrieved: "
										+id.toString()
										+" =\n"
										+j.get("type").toString()
										+"\n"
										+j.get("config").toString()
								);
								ItemStack temp = BlockRegistry.PRIDE_FLAG.asItem().getDefaultStack();
								temp.set(DataComponentRegistry.FLAG_TYPE,j.get("type").toString());
								prideFlags.add(temp);
							}catch(Exception e){
								LOGGER.error("Error occurred while adding Pride Flag Item: " + e);
							}
						} catch(Exception e) {
							LOGGER.error("Error occurred while loading resource json: " + id.toString(), e);
						}
					}
					ItemGroupEvents.modifyEntriesEvent(BlockRegistry.PRIDE_FLAGS_KEY).register(ig->{
							ig.getDisplayStacks().clear();
							for(ItemStack is : prideFlags) {
								ig.getDisplayStacks().add(is);
							}
						}
					);
				}
			}
		);
    }
}
