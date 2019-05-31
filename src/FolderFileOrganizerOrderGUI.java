import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FolderFileOrganizerOrderGUI extends JPanel implements ActionListener {
    public FolderFileOrganizerOrderGUI(){
        JLabel filler = new JLabel("Coming Soon!");
        filler.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new GridLayout(3,1));
        add(filler);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
