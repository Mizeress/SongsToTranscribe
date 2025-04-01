package com.Mizeress.SongsToTranscribe.GUI;

import com.Mizeress.SongsToTranscribe.Song;
import com.Mizeress.SongsToTranscribe.SongListFile;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class GUI {

    private final JFrame frame;
    private final DefaultTableModel model;
    private final SongListFile songListFile;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 800;

    public GUI(SongListFile songListFile) {
        this.songListFile = songListFile;

        //Apply a theme
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame();

        //Frame settings
        frame.setTitle("Songs to Transcribe");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        //Set up the panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.darkGray);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //Create the buttons
        Button addButton = new Button("Add");
        buttonPanel.add(addButton);

        Button deleteButton = new Button("Delete");
        buttonPanel.add(deleteButton);

        Button sortButton = new Button("Sort");
        buttonPanel.add(sortButton);

        //Create the table and model
        model = new DefaultTableModel(new Object[]{"Name", "Song"}, 0);
        JTable table = new JTable(model);

        //Customize table header
        JTableHeader header = new JTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(102, 102, 102)); //Blue background on header
        header.setForeground(Color.WHITE); // White text

        //addButton clicked action
        addButton.addActionListener(e -> openAddSongForm());

        //Delete Button Action
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1) {
                String name = (String) model.getValueAt(selectedRow, 0);
                String artist = (String) model.getValueAt(selectedRow, 1);

                Song songToDelete = new Song(name, artist);
                songListFile.deleteSong(songToDelete);
                populateTable();
            }
        });

        //Sort Button Action
        sortButton.addActionListener(e -> {
            songListFile.sortList();
            populateTable();
        });

        //Scroll Pane to hold table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 600));
        scrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        //Panel used for controlling table layout on page
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.darkGray);
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablePanel.add(scrollPane);

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void openAddSongForm() {
        //Create a dialog for the form
        JDialog dialog = new JDialog(frame, "Add Song", true);
        dialog.setSize(300,200);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));

        //Add input fields
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel artistLabel = new JLabel("Artist:");
        JTextField artistField = new JTextField();

        //Add buttons
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        // Add action for the add Button
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String artist = artistField.getText();

            if (!name.isEmpty() && !artist.isEmpty()) {
                Song song = new Song(name, artist);
                songListFile.appendSong(song);
                populateTable();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Error, try again");
            }
        });

        //Add action for the cancel button
        cancelButton.addActionListener(e -> dialog.dispose());

        //Add components to the dialog box
        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(artistLabel);
        dialog.add(artistField);
        dialog.add(addButton);
        dialog.add(cancelButton);

        //Display the dialog box
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

    }

    public void populateTable() {
        model.setRowCount(0); //Clear table
        for(Song song : songListFile.getSongList().getSongList()) {
            model.addRow(new Object[]{song.getName(), song.getArtist()});
        }
    }

}
