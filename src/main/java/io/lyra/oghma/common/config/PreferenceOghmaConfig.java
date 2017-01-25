package io.lyra.oghma.common.config;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Oghma config that uses preferences to persist across plays.
 *
 * @author Cian.
 */
public class PreferenceOghmaConfig implements OghmaConfig {

    private static final String PREFERENCE_NODE = "io.lyra.oghma";

    private static Preferences getOghmaPreferences() {
        return Preferences.userRoot().node(PREFERENCE_NODE);
    }

    @Override
    public String get(final ConfigOption option) {
        final Preferences pref = getOghmaPreferences();
        final String property = pref.get(option.getOptionName(), null);
        return (property == null || property.isEmpty()) ? option.getDefaultValue() : property;
    }

    @Override
    public void put(final ConfigOption option, final String value) throws IOException {
        final Preferences pref = getOghmaPreferences();
        pref.put(option.getOptionName(), value);
        try {
            pref.flush();
        }
        catch (final BackingStoreException e) {
            throw new IOException(e);
        }
    }


}
