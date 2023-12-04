package interface_adapter;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.net.FileNameMap;

public abstract class ViewModel {

    public static final Dimension VIEW_DIMENSION = new Dimension(800, 600);


    // COLOR PROPERTIES

    public static final Color TITLE_RED_COLOR = new Color(128, 43, 54);
    public static final Color HEADER_COLOR = new Color(186, 186, 186);
    public static final Color BACKGROUND_COLOR = new Color(245, 245, 245);

    public static final Color TEXT_COLOR = new Color(26, 31, 36);
    public static final Color TEXT_SECONDARY_COLOR = new Color(163, 169, 181);

    public static final Color TEXT_HIGHLIGHT_COLOR = new Color(18, 136, 255);
    // FONTS

    public static final Font TITLE_FONT = new Font("Lato", Font.PLAIN, 24);
    public static final Font HEADING_FONT_BOLD = new Font("Lato", Font.PLAIN, 12);

    private String viewName;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }
    public String getViewName() {
        return this.viewName;
    }

    public abstract void firePropertyChanged();
    public abstract void addPropertyChangeListener(PropertyChangeListener listener);


}
