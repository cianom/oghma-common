package io.lyra.oghma.common.config;

/**
 * Standard configuration options shared across modules.
 *
 * @author Cian.
 */
public enum StandardConfigOption implements ConfigOption {

    INSTALL_UUID(false, "installUUID", null),
    FULLSCREEN(false, "fullscreen", Boolean.toString(true)),
    RESOLUTION(false, "resolution", null),
    TRAVEL_SLOT_ONE(false, "travelSlot1", null),
    TRAVEL_SLOT_TWO(false, "travelSlot2", null),
    TRAVEL_SLOT_THREE(false, "travelSlot3", null),;

    private final boolean configurable;
    private final String  optionName;
    private final String  defaultValue;

    StandardConfigOption(final boolean configurable, final String optionName, final String defaultValue) {
        this.configurable = configurable;
        this.optionName = optionName;
        this.defaultValue = defaultValue;
    }

    public boolean isConfigurable() {
        return configurable;
    }

    @Override
    public String getOptionName() {
        return optionName;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

}
