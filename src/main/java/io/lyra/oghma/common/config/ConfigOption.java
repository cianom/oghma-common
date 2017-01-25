package io.lyra.oghma.common.config;

/**
 * Config option identity.
 *
 * @author Cian.
 */
public interface ConfigOption {

    /**
     * @return the name of the option, used to retrieve the value.
     */
    String getOptionName();

    /**
     * @return the default value for the option, or null if absent.
     */
    String getDefaultValue();

}
