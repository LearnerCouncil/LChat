package rocks.learnercouncil.lchat.proxy.common;

import com.google.common.io.ByteStreams;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class ConfigFile {

    private final BasicLogger logger;
    private final Yaml yaml;
    private Map<String, Object> config;
    private final File file;

    public ConfigFile(String name, File folder, BasicLogger logger) {
        this.logger = logger;
        yaml = new Yaml();
        file = getFile(folder, name);
        config = load();
    }

    @SuppressWarnings("UnstableApiUsage")
    private File getFile(File dataFolder, String name) {
        if(!dataFolder.exists()) {
            logger.info("Created plugin data folder: " + dataFolder.mkdir());
        }

        File file = new File(dataFolder, name);

        if (!file.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
                try (InputStream input = getClass().getClassLoader().getResourceAsStream(name);
                     OutputStream output = Files.newOutputStream(file.toPath())) {
                    assert input != null;
                    ByteStreams.copy(input, output);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
        return file;
    }

    private Map<String, Object> load() {
        Map<String, Object> data = new LinkedHashMap<>();
        try (InputStream input = new FileInputStream(file)) {
            data = yaml.load(input);
        } catch (IOException e) {
            logger.warn("Could not open config file '" + file + "'. Data could not be loaded: " + Arrays.toString(e.getStackTrace()));
        }
        return data;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            yaml.dump(config, writer);
        } catch (IOException e) {
            logger.warn("Could not open config file '" + file + "'. Data could not be saved: " + Arrays.toString(e.getStackTrace()));
        }
    }

    public void reload() {
        config = load();
    }

    public void set(String key, Object value) {
        config.put(key, value);
    }

    public <T> T getOrDefault(String key, Class<T> type, T def) {
        Object element = config.get(key);
        if (!type.isInstance(element)) {
            logger.warn("Configuration element at '" + key + "' is not present/not a '" + type.getSimpleName() + "', returning default.");
            return def;
        }
        return type.cast(element);
    }

    public <T> List<T> getListOrDefault(String key, Class<T> type, List<T> def) {
        List<?> rawList = getOrDefault(key, List.class, null);
        if (rawList == null) {
            return def;
        }
        List<T> list = new ArrayList<>();
        for(Object rawElement : rawList) {
            if (type.isInstance(rawElement)) {
                list.add(type.cast(rawElement));
            } else {
                logger.warn("Element in List at '" + key + "' is not a '" + type.getSimpleName() + "', returning default.");
            }
        }
        return list;
    }
}
