package com.Mizeress.SongsToTranscribe.GUI;

import com.Mizeress.SongsToTranscribe.Song;
import com.Mizeress.SongsToTranscribe.SongListFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUI {

    private final JFrame frame;
    private final DefaultTableModel model;
    private final SongListFile songListFile;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 800;

    public GUI(SongListFile songListFile) {
        this.songListFile = songListFile;

        frame = new JFrame();

        //Frame settings
        frame.setTitle("Songs to Transcribe");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        //Setup the panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.darkGray);

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

        //Panel used for controlling table layout on page
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.darkGray);
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablePanel.add(scrollPane);

        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);

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
