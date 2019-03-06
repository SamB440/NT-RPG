package cz.neumimto.rpg.commands;

import cz.neumimto.core.localization.Arg;
import cz.neumimto.core.localization.TextHelper;
import cz.neumimto.rpg.NtRpgPlugin;
import cz.neumimto.rpg.configuration.Localizations;
import cz.neumimto.rpg.players.ActionResult;
import cz.neumimto.rpg.players.IActiveCharacter;
import cz.neumimto.rpg.players.groups.ClassDefinition;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static cz.neumimto.rpg.NtRpgPlugin.pluginConfig;

/**
 * Created by NeumimTo on 5.11.2017.
 */
public class AnyClassDefCommandElement extends CommandElement {

	private final boolean validate;

	public AnyClassDefCommandElement(@Nullable Text key) {
		super(key);
		validate = true;
	}

	public AnyClassDefCommandElement(@Nullable Text key, boolean validate) {
		super(key);
		this.validate = validate;
	}

	@Override
	protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
		String clazz = args.next();
		ClassDefinition configClass = NtRpgPlugin.GlobalScope.classService.getClassDefinitionByName(clazz);
		if (configClass == null) {
			throw args.createError(Localizations.UNKNOWN_CLASS.toText(Arg.arg("class", clazz)));
		}


		if (validate && pluginConfig.RESPECT_CLASS_SELECTION_ORDER) {
			IActiveCharacter character = NtRpgPlugin.GlobalScope.characterService.getCharacter((Player) source);
			ActionResult result = NtRpgPlugin.GlobalScope.characterService.canGainClass(character, configClass);
			if (!result.isOk()) {
				throw args.createError(result.getErrorMesage());
			}

		}
		if (!source.hasPermission("ntrpg.class." + configClass.getName().toLowerCase())) {
			throw args.createError(TextHelper.parse("&CNo permission ntrpg.class.%class%",
					Arg.arg("class", configClass.getName().toLowerCase())));
		}
		return configClass;
	}

	@Override
	public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
		if (validate) {
			//todo
			return NtRpgPlugin.GlobalScope.classService.getClassDefinitions().stream()
					.map(ClassDefinition::getName)
					.filter(a -> src.hasPermission("ntrpg.class." + a.toLowerCase()))
					.collect(Collectors.toList());
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public Text getUsage(CommandSource src) {
		return Text.of("<class>");
	}

}