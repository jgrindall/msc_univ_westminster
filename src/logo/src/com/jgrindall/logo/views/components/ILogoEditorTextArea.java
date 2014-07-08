/**
 * ILogoEditorTextArea.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * interface for all possible text areas, contains three basic methods.
 */
package com.jgrindall.logo.views.components;
import com.jgrindall.logo.utils.TextLocationObject;
public interface ILogoEditorTextArea {
    void highlight(TextLocationObject loc);
    String getText();
    void setText(String s);
}
