import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class FFOGraphicInterface extends JPanel {


    public FFOGraphicInterface(){
        super(new GridLayout(1,1));

        JTabbedPane jTabbedPane = new JTabbedPane();

        JComponent searchPanel = new FolderFileOrganizerSearchGUI();
        jTabbedPane.addTab("Search", searchPanel);
        jTabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent orderPanel = new FolderFileOrganizerOrderGUI();
        jTabbedPane.addTab("Order", orderPanel);
        jTabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

        add(jTabbedPane);
//        jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FFO (Files and Folders Organizer)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new FFOGraphicInterface(), BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
