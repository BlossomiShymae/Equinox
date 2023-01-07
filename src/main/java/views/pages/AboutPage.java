package views.pages;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import viewmodels.pages.AboutViewModel;

import javax.swing.*;

@Singleton
public class AboutPage extends Page {
    private final AboutViewModel viewModel;
    private JPanel rootPanel;
    private JLabel wordmarkLabel;
    private JLabel versionNumberLabel;
    private JTextArea legalTextArea;

    @Inject
    public AboutPage(AboutViewModel aboutViewModel) {
        super();
        viewModel = aboutViewModel;
        wordmarkLabel.setIcon(viewModel.getWordmarkImageIcon());
        legalTextArea.setFocusable(false);
        add(rootPanel);
    }
}
