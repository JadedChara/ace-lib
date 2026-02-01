package io.github.jadedchara.ace_lib.common.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.jadedchara.ace_lib.AceLib;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;

public class FlagSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
	@Override
	public CompletableFuture<Suggestions> getSuggestions(
		CommandContext<ServerCommandSource> c,
		SuggestionsBuilder b
	) throws CommandSyntaxException {
		for(String arg: AceLib.flagArgs){
			b.suggest(arg);
		}
		return b.buildFuture();
	}
}
