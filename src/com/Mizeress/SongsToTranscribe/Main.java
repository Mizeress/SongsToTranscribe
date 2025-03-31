package com.Mizeress.SongsToTranscribe;

import com.Mizeress.SongsToTranscribe.GUI.GUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SongListFile listFile = new SongListFile();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI mainFrame = new GUI(listFile);
                mainFrame.populateTable();
            }
        });
    }
}
