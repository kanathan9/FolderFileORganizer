import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @author  Administrator
 * @created May 31, 2019
 */
class FolderFileOrganizerSearchGUI extends JPanel implements ActionListener
{
    JButton btChooseFolder;
    JLabel labelFile;
    JTextField tfSearchText;
    JComboBox cmbFileTypeCombo;
    JButton btSearchButton;
    JList lsResultFileList;
    JButton btOpenButton;
    JButton btShowFolderButton;
    JButton btTrashButton;
    JLabel lbLabel0;
    JLabel lbLabel1;

    public final JFileChooser fileChooser = new JFileChooser();
    static File fileChoosed ;
    static DefaultListModel resultListModel = new DefaultListModel();


    /**
     *Constructor for the FolderFileOrganizerSearchGUI object
     */
    public FolderFileOrganizerSearchGUI()
    {
        super();
        setBorder( BorderFactory.createTitledBorder( "" ) );

        GridBagLayout gbFolderFileOrganizerGUI = new GridBagLayout();
        GridBagConstraints gbcFolderFileOrganizerGUI = new GridBagConstraints();
        setLayout( gbFolderFileOrganizerGUI );

        btChooseFolder = new JButton( "Choose a folder..."  );
        btChooseFolder.addActionListener( this );
        gbcFolderFileOrganizerGUI.gridx = 2;
        gbcFolderFileOrganizerGUI.gridy = 2;
        gbcFolderFileOrganizerGUI.gridwidth = 11;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 0;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 10,10,0,10 );
        gbFolderFileOrganizerGUI.setConstraints( btChooseFolder, gbcFolderFileOrganizerGUI );
        add( btChooseFolder );

        labelFile = new JLabel(FolderFilesOrganizer.DEFAULT_FOLDER);
        gbcFolderFileOrganizerGUI.gridx = 14;
        gbcFolderFileOrganizerGUI.gridy = 2;
        gbcFolderFileOrganizerGUI.gridwidth = 7;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 1;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 10,10,0,10 );
        gbFolderFileOrganizerGUI.setConstraints( labelFile, gbcFolderFileOrganizerGUI );
        add( labelFile );

        tfSearchText = new JTextField( );
        gbcFolderFileOrganizerGUI.gridx = 2;
        gbcFolderFileOrganizerGUI.gridy = 8;
        gbcFolderFileOrganizerGUI.gridwidth = 7;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 0;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.CENTER;
        gbcFolderFileOrganizerGUI.insets = new Insets( 0,10,10,10 );
        gbFolderFileOrganizerGUI.setConstraints( tfSearchText, gbcFolderFileOrganizerGUI );
        add( tfSearchText );

        String []dataFileTypeCombo = {"ALL","VIDEOS","DOCUMENTS","IMAGES","MUSIC","EXECUTABLE","COMPRESSED","DISC-MEDIA"};
        cmbFileTypeCombo = new JComboBox( dataFileTypeCombo );
        gbcFolderFileOrganizerGUI.gridx = 10;
        gbcFolderFileOrganizerGUI.gridy = 8;
        gbcFolderFileOrganizerGUI.gridwidth = 5;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 0;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 0,10,10,10 );
        gbFolderFileOrganizerGUI.setConstraints( cmbFileTypeCombo, gbcFolderFileOrganizerGUI );
        add( cmbFileTypeCombo );

        btSearchButton = new JButton( "Search"  );
        btSearchButton.addActionListener( this );
        gbcFolderFileOrganizerGUI.gridx = 16;
        gbcFolderFileOrganizerGUI.gridy = 8;
        gbcFolderFileOrganizerGUI.gridwidth = 4;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 0;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 0,10,10,10 );
        gbFolderFileOrganizerGUI.setConstraints( btSearchButton, gbcFolderFileOrganizerGUI );
        add( btSearchButton );

//        String []dataResultFileList = { "Chocolate", "Ice Cream", "Apple Pie" };
        lsResultFileList = new JList( resultListModel );
        lsResultFileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsResultFileList.setSelectedIndex(0);
        JScrollPane scpResultFileList = new JScrollPane( lsResultFileList );
        gbcFolderFileOrganizerGUI.gridx = 2;
        gbcFolderFileOrganizerGUI.gridy = 12;
        gbcFolderFileOrganizerGUI.gridwidth = 18;
        gbcFolderFileOrganizerGUI.gridheight = 10;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 1;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 0,10,0,10 );
        gbFolderFileOrganizerGUI.setConstraints( scpResultFileList, gbcFolderFileOrganizerGUI );
        add( scpResultFileList );

        btOpenButton = new JButton( "Open File"  );
        btOpenButton.addActionListener( this );
        gbcFolderFileOrganizerGUI.gridx = 3;
        gbcFolderFileOrganizerGUI.gridy = 24;
        gbcFolderFileOrganizerGUI.gridwidth = 5;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 0;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 10,10,10,10 );
        gbFolderFileOrganizerGUI.setConstraints( btOpenButton, gbcFolderFileOrganizerGUI );
        add( btOpenButton );

        btShowFolderButton = new JButton( "Open folder"  );
        btShowFolderButton.addActionListener( this );
        gbcFolderFileOrganizerGUI.gridx = 10;
        gbcFolderFileOrganizerGUI.gridy = 24;
        gbcFolderFileOrganizerGUI.gridwidth = 5;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 0;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 10,10,10,10 );
        gbFolderFileOrganizerGUI.setConstraints( btShowFolderButton, gbcFolderFileOrganizerGUI );
        add( btShowFolderButton );

        btTrashButton = new JButton( "Trash"  );
        btTrashButton.addActionListener( this );
        gbcFolderFileOrganizerGUI.gridx = 17;
        gbcFolderFileOrganizerGUI.gridy = 24;
        gbcFolderFileOrganizerGUI.gridwidth = 5;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 0;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 10,10,10,10 );
        gbFolderFileOrganizerGUI.setConstraints( btTrashButton, gbcFolderFileOrganizerGUI );
        add( btTrashButton );

        lbLabel0 = new JLabel( "Enter Search text:"  );
        gbcFolderFileOrganizerGUI.gridx = 2;
        gbcFolderFileOrganizerGUI.gridy = 6;
        gbcFolderFileOrganizerGUI.gridwidth = 7;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 1;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 10,10,0,10 );
        gbFolderFileOrganizerGUI.setConstraints( lbLabel0, gbcFolderFileOrganizerGUI );
        add( lbLabel0 );

        lbLabel1 = new JLabel( "Select File type:"  );
        gbcFolderFileOrganizerGUI.gridx = 10;
        gbcFolderFileOrganizerGUI.gridy = 6;
        gbcFolderFileOrganizerGUI.gridwidth = 5;
        gbcFolderFileOrganizerGUI.gridheight = 2;
        gbcFolderFileOrganizerGUI.fill = GridBagConstraints.BOTH;
        gbcFolderFileOrganizerGUI.weightx = 1;
        gbcFolderFileOrganizerGUI.weighty = 1;
        gbcFolderFileOrganizerGUI.anchor = GridBagConstraints.NORTH;
        gbcFolderFileOrganizerGUI.insets = new Insets( 10,10,0,10 );
        gbFolderFileOrganizerGUI.setConstraints( lbLabel1, gbcFolderFileOrganizerGUI );
        add( lbLabel1 );
    }

    /**
     */
    public void actionPerformed( ActionEvent e )
    {
        if ( e.getSource() == btChooseFolder )
        {
            // Action for btChooseFolder
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setApproveButtonText("Select");
            int val = fileChooser.showOpenDialog(this);
            if(val == fileChooser.APPROVE_OPTION){
                fileChoosed = fileChooser.getSelectedFile();
                labelFile.setText(fileChoosed.getAbsolutePath());
            }
        }
        if ( e.getSource() == btSearchButton )
        {
            // Action for btSearchButton
            runSearch(tfSearchText.getText(), (String) cmbFileTypeCombo.getSelectedItem());
        }
        if ( e.getSource() == btOpenButton )
        {
            // Action for btOpenButton
            File file = (File) lsResultFileList.getSelectedValue();
            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                }catch(IOException ex){
                    System.out.println("Could not open file: "+ file.getAbsolutePath());
                }
            }
        }
        if ( e.getSource() == btShowFolderButton )
        {
            // Action for btShowFolderButton
            File file = (File) lsResultFileList.getSelectedValue();
            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file.getParentFile());
                }catch(Exception ex){
                    System.out.println("Could not open folder: "+ file.getParentFile().getAbsolutePath());
                    ex.printStackTrace();
                }
            }
        }

        if ( e.getSource() == btTrashButton )
        {
            // Action for btShowFolderButton
            File file = (File) lsResultFileList.getSelectedValue();
            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.moveToTrash(file);
                }catch(Exception ex){
                    System.out.println("Could not open folder: "+ file.getParentFile().getAbsolutePath());
                    ex.printStackTrace();
                }
            }
        }
    }

    public void runSearch(String searchText, String fileType){
        FolderFilesOrganizer.command = new StringBuilder("SEARCH");
        FolderFilesOrganizer.workingFile = (null != fileChoosed)? new StringBuilder(fileChoosed.getAbsolutePath()) : new StringBuilder("-d");
        FolderFilesOrganizer.fileToSearch = new StringBuilder(searchText.strip());
        FolderFilesOrganizer.fileType = new StringBuilder(fileType);
        FolderFilesOrganizer.runCommand();
        FolderFilesOrganizer.displayResults();
    }
}