package io.github.jadedchara.ace_lib;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.jadedchara.ace_lib.common.api.common.payload.UpdateFlagsPayload;
import io.github.jadedchara.ace_lib.common.command.FlagSuggestionProvider;
import io.github.jadedchara.ace_lib.common.data.StoredFlagType;
import io.github.jadedchara.ace_lib.common.registry.BlockRegistry;
import io.github.jadedchara.ace_lib.common.registry.DataComponentRegistry;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.command.CommandManager;
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
	public List<ItemStack> prideFlags = new ArrayList<>();
	public static List<String> flagArgs = new ArrayList<>();
	public static final ComponentKey<StoredFlagType> DISPLAYFLAG =
		ComponentRegistry.getOrCreate(Identifier.of("ace_lib", "displayflag"), StoredFlagType.class);


    @Override
    public void onInitialize() {
        LOGGER.info("Hello Quilt world from AceLib! Stay fresh!");
		//flagArgSet.add("classic");
		//Backbone stuff
		BlockRegistry.init();
		DataComponentRegistry.init();
		PayloadTypeRegistry.playS2C().register(UpdateFlagsPayload.ID,UpdateFlagsPayload.CODEC);

		//===============
		//RELOAD HANDLING
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
			new SimpleSynchronousResourceReloadListener() {
				@Override
				public Identifier getFabricId() {
					return Identifier.of(MOD_ID, "flag_type_loader");
				}

				@Override
				public void reload(ResourceManager manager) {
					System.out.println("Reloading flag types...");
					prideFlags.clear();
					//List<ItemStack> prideFlags = new ArrayList<>();
					ItemStack def = BlockRegistry.PRIDE_FLAG.asItem().getDefaultStack();
					def.set(
						DataComponentRegistry.FLAG_TYPE,
						"classic"
					);
					flagArgs.add("classic");
					prideFlags.add(def);

					BlockRegistry.PRIDE_FLAGS.getOrInitTabStacks().clear();
					ItemGroupEvents.modifyEntriesEvent(BlockRegistry.PRIDE_FLAGS_KEY)
						.register(
							(entries -> {entries.getDisplayStacks().clear();})
						);
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
								ItemStack temp = BlockRegistry.PRIDE_FLAG.asItem().getDefaultStack();
								temp.set(
									DataComponentRegistry.FLAG_TYPE,
									j
										.get("type")
										.toString()
										.replaceAll("\"","")
								);
								flagArgs.add(
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
					for (ItemStack flag : prideFlags){

						BlockRegistry.PRIDE_FLAGS.getOrInitTabStacks().add(flag);
						System.out.println(
							"Added a "
								+ flag.getComponents().get(DataComponentRegistry.FLAG_TYPE)
								+ " flag! Enjoy!"
						);
					}
				}
			}
		);
		ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player,listener)->{
			ServerPlayNetworking.send(player,new UpdateFlagsPayload(prideFlags));

		});
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(
				//nicking
				CommandManager.literal("nick")
					.requires(source ->source.hasPermission(2))
					.then(
						CommandManager.argument("nick", StringArgumentType.string())

							.executes(context -> {
								context.getSource().sendFeedback(() -> Text.literal("Called nickname"), false);
								if(
									context.getSource().isPlayer() &&
									StringArgumentType.getString(context,"nick") != null &&
									!StringArgumentType.getString(context,"nick").startsWith(" ")
								){
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
						CommandManager.literal("add")
							.then(CommandManager.argument("type",StringArgumentType.string())
								.suggests(new FlagSuggestionProvider())
								.executes(context -> {

								if(context.getSource().isPlayer()){
									if(flagArgs.contains(StringArgumentType.getString(context,"type"))){
										context.getSource().sendFeedback(
											() -> Text.literal(
												"Added your "+StringArgumentType.getString(context,"type")+" flag."
											), false
										);
										try{
											context
												.getSource()
												.getPlayer()
												.getComponent(AceLib.DISPLAYFLAG)
												.addFlag(
													StringArgumentType.getString(context,"type"),
													context.getSource().getPlayer()
												);
										}catch (Exception e){
											System.out.println(e);
										}
									}else{
										context.getSource().sendFeedback(
											()-> Text.literal(
												"Unable to add a "+StringArgumentType.getString(context,"type")+"flag."
											), false
										);
									}
								}
								return 1;
							}
						))
					).then(
						CommandManager.literal("remove")
							.then(CommandManager.argument("type", StringArgumentType.string())
								.suggests(new FlagSuggestionProvider())
								.executes(context -> {
								context.getSource().sendFeedback(
									() -> Text.literal(
										"Removed your "+StringArgumentType.getString(context,"type")+" flag."
									),
									false
								);
								if(context.getSource().isPlayer()){
									try{
										context
											.getSource()
											.getPlayer()
											.getComponent(AceLib.DISPLAYFLAG)
											.removeFlag(
												StringArgumentType.getString(context,"type"),
												context.getSource().getPlayer()
											);
									}catch (Exception e){
										System.out.println(e);
									}
								}
								return 1;
							}
						))
					).then(
						CommandManager.literal("clear")
							.executes(context -> {
								context.getSource().sendFeedback(
									() -> Text.literal("Cleared all your pride flags."), false
								);
								if(context.getSource().isPlayer()){
									try{
										context
											.getSource()
											.getPlayer()
											.getComponent(AceLib.DISPLAYFLAG)
											.clearFlags(
												context.getSource().getPlayer()
											);
									}catch (Exception e){
										System.out.println(e);
									}
								}
								return 1;
							}
						))
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
										//FakePlayer fp = new FakePlayer()
									}
									return 1;
								}
							)
					)
			);
		});
    }
}
