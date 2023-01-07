package viewmodels.ui;

import javax.swing.*;

public class PageNavigationViewModel {
    private final String title;
    private final Class<? extends JPanel> page;

    public PageNavigationViewModel(String title, Class<? extends JPanel> page) {
        this.title = title;
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public Class<? extends JPanel> getPage() {
        return page;
    }
}
