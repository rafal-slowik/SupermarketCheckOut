package market.property;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * 
 * @author Rafal Slowik
 * @date 17 Mar 2017
 *
 */
public class ConfigProperties {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("configuration");

	private ConfigProperties() {
	}

	public String findByKey(String key) {
		return bundle.getString(key);
	}

	public String formatProperty(String key, Object... argumentsToReplace) {
		String pattern = findByKey(key);
		return MessageFormat.format(pattern, argumentsToReplace);
	}

	public static ConfigProperties getConfigInstance() {
		return SingletonHelper.INSTANCE;
	}

	private static class SingletonHelper {
		private static final ConfigProperties INSTANCE = new ConfigProperties();

		private SingletonHelper() {
		}
	}
}
