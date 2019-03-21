package org.lrima.laop.settings.option;

import javafx.scene.Node;
import org.lrima.laop.utils.ObjectGetter;

/**
 * Interface that lets us map Types to Swing components.
 *
 * @param <T> The type of the option
 * @author leonard
 */
public interface Option<T> {
    /**
     * @return the value of the option
     */
    T getValue();

    /**
     * @param value value to be change
     * @return true if the value was succesfully set, flase otherwise
     */
    boolean setValue(T value);

    /**
     * Create and generate a new JComponent depending on the type of the option. The component has a listener that automatically change the value of the option when the compoenent's value is changed.
     *
     * @return the generated JComponent
     */
    Node generateComponent();
}
