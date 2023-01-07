package views.pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Page extends JPanel {
    public Page() {
        super();
        setBorder(new EmptyBorder(16, 16, 16, 16));
        setLayout(new BorderLayout());
    }
}
