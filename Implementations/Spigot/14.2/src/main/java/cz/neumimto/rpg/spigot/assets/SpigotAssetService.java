package cz.neumimto.rpg.spigot.assets;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import cz.neumimto.rpg.api.logging.Log;
import cz.neumimto.rpg.common.assets.AssetService;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Singleton
public class SpigotAssetService implements AssetService {

    @Override
    public String getAssetAsString(String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("assets/nt-rpg/"+path)){
            return CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
        } catch (IOException e) {
            Log.error("Could not read file " + path + e);
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void copyToFile(String s, Path toPath) {
        String assetAsString = getAssetAsString(s);
        try {
            Files.write(toPath, assetAsString.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            Log.error("Could not create file " + toPath, e);
        }
    }
}
