package views.pages;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import viewmodels.pages.AboutViewModel;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Singleton
public class AboutPage extends Page {
    private final AboutViewModel viewModel;
    private JPanel rootPanel;
    private JLabel wordmarkLabel;
    private JLabel versionNumberLabel;
    private JTextArea legalTextArea;

    @Inject
    public AboutPage(AboutViewModel aboutViewModel) throws IOException, XmlPullParserException {
        super();
        viewModel = aboutViewModel;
        wordmarkLabel.setIcon(viewModel.getWordmarkImageIcon());
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        versionNumberLabel.setText(model.getVersion());
        legalTextArea.setFocusable(false);
        add(rootPanel);
    }
}
