package views;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import events.TrayLeftClickEvent;
import services.EventAggregatorService;
import services.PageService;
import services.ResourceLoaderService;
import viewmodels.MainWindowViewModel;

import javax.swing.*;
import java.awt.*;

@Singleton
public class MainWindowView extends JFrame {
    private final MainWindowViewModel viewModel;
    private JPanel rootPanel;
    private JTabbedPane pagePanel;

    @Inject
    public MainWindowView(MainWindowViewModel mainWindowViewModel, PageService pageService, ResourceLoaderService resourceLoaderService, EventAggregatorService eventAggregatorService) {
        super();
        add(rootPanel);
        viewModel = mainWindowViewModel;

        // Setup pages for the page panel
        for (var navigation : viewModel.getPageNavigationList()) {
            pagePanel.add(navigation.getTitle(), pageService.getPage(navigation.getPage()));
        }
        setTitle(viewModel.getTitle());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(resourceLoaderService.getApplicationIcon());

        eventAggregatorService.getEvent(TrayLeftClickEvent.class).subscribe(e -> {
            setVisible(true);
            setExtendedState(JFrame.NORMAL);
        });
        addWindowStateListener(e -> {
            if (e.getNewState() == ICONIFIED) {
                setVisible(false);
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        pagePanel = new JTabbedPane();
        pagePanel.setTabPlacement(1);
        rootPanel.add(pagePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
