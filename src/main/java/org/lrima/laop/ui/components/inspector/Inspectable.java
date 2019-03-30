package org.lrima.laop.ui.components.inspector;

import java.util.Map;

public interface Inspectable {
	Map<String, String> getInformationHashmap();
	String[] getCategories();
}
