package views.pages;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import utils.mvvm.PropertyChangedEventArgs;
import viewmodels.pages.SettingsViewModel;

import javax.swing.*;

@Singleton
public class SettingsPage extends Page {
    private final SettingsViewModel viewModel;
    private JPanel rootPanel;
    private JPanel settingsPanel;
    private JScrollPane scrollPanel;
    private JTextField rootLocationField;
    private JButton selectFolderButton;

    @Inject
    public SettingsPage(SettingsViewModel settingsViewModel) {
        super();
        viewModel = settingsViewModel;
        setView();

        selectFolderButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) viewModel.saveSelectedFolder(fileChooser.getSelectedFile());
        });

        viewModel.propertyChanged.subscribe(this::onPropertyChanged);
        add(rootPanel);
    }

    private void onPropertyChanged(PropertyChangedEventArgs e) {
        setView();
    }

    private void setView() {
        rootLocationField.setText(viewModel.getSelectedFolder());
    }
}
