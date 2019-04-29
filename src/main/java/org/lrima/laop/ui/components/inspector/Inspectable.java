package org.lrima.laop.ui.components.inspector;

import java.util.Map;

/**
 * interface used to define something that can be inspected in the inspector panel
 * @author Clement Bisaillon
 */
public interface Inspectable {
	/**
	 * @return the hashmap of the information to display
	 */
	Map<String, String> getInformationHashmap();

	/**
	 * @return the categories to show in the inspector panel
	 */
	String[] getCategories();
}
