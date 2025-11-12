package io.github.jadedchara.ace_lib;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.jadedchara.ace_lib.common.data.StoredFlagType;
import io.github.jadedchara.ace_lib.common.registry.BlockRegistry;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.KillCommand;
import net.minecraft.server.command.SummonCommand;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AceLib implements ModInitializer {
	public static final String MOD_ID = "ace_lib";
    public static final Logger LOGGER = LoggerFactory.getLogger("AceLib");
	public static TrackedData<String[]> PRIDE_FLAG;

	public static final ComponentKey<StoredFlagType> DISPLAYFLAG =
		ComponentRegistry.getOrCreate(Identifier.of("ace_lib", "displayflag"), StoredFlagType.class);


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
								/*System.out.println(
									"Retrieved: "
										+id.toString()
										+" =\n"
										+j.get("type").toString()
										+"\n"
										+j.get("config").toString()
								);*/
								ItemStack temp = BlockRegistry.PRIDE_FLAG.asItem().getDefaultStack();
								temp.set(
									DataComponentRegistry.FLAG_TYPE,
									j
										.get("type")
										.toString()
										.replaceAll("\"","")
								);

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
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(
				//nicking
				CommandManager.literal("nick")
					.requires(source ->source.hasPermission(2))
					.then(
						CommandManager.argument("nick", StringArgumentType.string())
							.executes(context -> {
								context.getSource().sendFeedback(() -> Text.literal("Called nickname"), false);
								if(context.getSource().isPlayer()){
									context.getSource().getPlayer().setCustomName(
										Text.of(StringArgumentType.getString(context,"nick"))
									);
									context.getSource().getPlayer().setCustomNameVisible(true);
								}
								return 1;
							}
							)
					)
			);

			dispatcher.register(
				//pride flags
				CommandManager.literal("flag")
					.requires(source ->source.hasPermission(2))
					.then(
						CommandManager.argument("type", StringArgumentType.string())
							.executes(context -> {
									context.getSource().sendFeedback(() -> Text.literal("Called flag"), false);
									if(context.getSource().isPlayer()){
										/*context
											.getSource()
											.getPlayer()
											.getComponent(AceLib.DISPLAYFLAG)
											.clearFlags();*/
										try{
											context
												.getSource()
												.getPlayer()
												.getComponent(AceLib.DISPLAYFLAG)
												.addFlag(StringArgumentType.getString(context,"type"));
										}catch (Exception e){
											System.out.println(e);
										}
									}
									return 1;
								}
							)
					)
			);

			dispatcher.register(
				//pride flags
				CommandManager.literal("fakeplayer")
					.requires(source ->source.hasPermission(2))
					.then(
						CommandManager.argument("name", StringArgumentType.string())
							.executes(context -> {
									context.getSource().sendFeedback(() -> Text.literal("Called fakeplayer"), false);
									if(context.getSource().isPlayer()){

									}
									return 1;
								}
							)
					)
			);
		});
    }
}
