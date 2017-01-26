package io.lyra.oghma.common.config;

import java.io.IOException;
import java.util.UUID;

/**
 * Oghma property configuration contract.
 *
 * @author Cian.
 */
public interface OghmaConfig {

    /**
     * Retrieve a config option value.
     *
     * @param option the config option to retrieve.
     * @return the value if existing, otherwise the default config value if existing, otherwise null.
     */
    String get(final ConfigOption option);

    /**
     * Store a config option value.
     *
     * @param option config option to store.
     * @param value  value to store.
     * @throws IOException if the value could not be persisted to disk.
     */
    void put(final ConfigOption option, final String value) throws IOException;

    /**
     * Store a config option value if no value currently exists. If the option
     * has a default value, this counts as an existing value and nothing will be stored.
     *
     * @param option config option to store.
     * @param value  value to store.
     * @throws IOException if the value could not be persisted to disk.
     */
    default void putIfAbsent(final ConfigOption option, final String value) throws IOException {
        final String existingValue = get(option);
        if (existingValue == null) {
            put(option, value);
        }
    }

    default boolean getBoolean(final ConfigOption option, final boolean defaultValue) {
        final String value = get(option);
        return (value == null) ? defaultValue : Boolean.valueOf(value);
    }

    default UUID getInstallUUID() {
        final String installUUID = get(StandardConfigOption.INSTALL_UUID);
        return (installUUID == null) ? null : UUID.fromString(installUUID);
    }

}
