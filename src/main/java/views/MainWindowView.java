package views;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import events.TrayLeftClickEvent;
import services.EventAggregatorService;
import services.PageService;
import services.ResourceLoaderService;
import viewmodels.MainWindowViewModel;

import javax.swing.*;

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
        for (var navigation: viewModel.getPageNavigationList()) {
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
}
