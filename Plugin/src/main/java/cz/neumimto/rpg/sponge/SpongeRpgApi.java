package cz.neumimto.rpg.sponge;

import cz.neumimto.core.localization.Arg;
import cz.neumimto.core.localization.TextHelper;
import cz.neumimto.rpg.api.RpgApi;
import cz.neumimto.rpg.api.events.effect.EventFactoryService;
import cz.neumimto.rpg.api.items.ItemService;
import cz.neumimto.rpg.api.localization.LocalizationService;
import cz.neumimto.rpg.api.skills.ISkillService;
import cz.neumimto.rpg.common.entity.players.attributes.AttributeConfig;
import cz.neumimto.rpg.common.configuration.PluginConfig;
import cz.neumimto.rpg.sponge.utils.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;

public final class SpongeRpgApi implements RpgApi {

    protected SpongeRpgApi() {
    }

    @Override
    public Collection<AttributeConfig> getAttributes() {
        return Sponge.getRegistry().getAllOf(AttributeConfig.class);
    }

    @Override
    public Optional<AttributeConfig> getAttributeById(String id) {
        return Sponge.getRegistry().getType(AttributeConfig.class, id);
    }

    @Override
    public ItemService getItemService() {
        return NtRpgPlugin.GlobalScope.itemService;
    }

    private void broadcastMessage(Text text) {
        Collection<Player> onlinePlayers = Sponge.getServer().getOnlinePlayers();
        for (Player onlinePlayer : onlinePlayers) {
            onlinePlayer.sendMessage(text);
        }
    }

    @Override
    public void broadcastMessage(String message) {
        broadcastMessage(TextHelper.parse(message));
    }

    @Override
    public void broadcastLocalizableMessage(String message, Arg arg) {
        broadcastMessage(TextHelper.parse(NtRpgPlugin.GlobalScope.localizationService.translate(message, arg)));
    }

    @Override
    public void broadcastLocalizableMessage(String message, String singleKey, String singleArg) {
        broadcastMessage(TextHelper.parse(NtRpgPlugin.GlobalScope.localizationService.translate(message, singleKey, singleArg)));
    }

    @Override
    public String getTextAssetContent(String templateName) {
        try {
            return Sponge.getAssetManager().getAsset(NtRpgPlugin.GlobalScope.plugin, templateName).get().readString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Unknown template " + templateName);
        }
    }

    @Override
    public void executeCommandBatch(Map<String, String> args, List<String> cmd) {
        Utils.executeCommandBatch(args, cmd);
    }

    @Override
    public boolean postEvent(Object event) {
        return Sponge.getEventManager().post((Event) event);
    }

    @Override
    public void unregisterListeners(Object listener) {
        Sponge.getEventManager().unregisterListeners(listener);
    }

    @Override
    public void registerListeners(Object listener) {
        Sponge.getEventManager().registerListeners(NtRpgPlugin.GlobalScope.plugin, listener);
    }

    @Override
    public EventFactoryService getEventFactory() {
        return NtRpgPlugin.GlobalScope.eventFactory;
    }

    @Override
    public ISkillService getSkillService() {
        return NtRpgPlugin.GlobalScope.skillService;
    }

    @Override
    public LocalizationService getLocalizationService() {
        return NtRpgPlugin.GlobalScope.localizationService;
    }

    @Override
    public PluginConfig getPluginConfig() {
        return NtRpgPlugin.pluginConfig;
    }

    @Override
    public Executor getAsyncExecutor() {
        return NtRpgPlugin.asyncExecutor;
    }
}