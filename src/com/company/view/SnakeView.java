package com.company.view;

import com.company.controller.SnakeController;
import com.company.model.SnakeModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SnakeView extends JFrame {
    public static final int INTEND = 5;
    private SnakeModel model;
    private SnakeController controller;
    public GameField gameField;
    public Map<String, ImageIcon> hashMapImage;

    public SnakeView(SnakeModel model, SnakeController controller) {
        this.controller = controller;
        this.model = model;
        controller.setView(this);
        try {
            LoadImage("assets");
        } catch (IOException e) {
            e.printStackTrace();
        }
        createInterface();
    }

    private void createInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        gameField = new GameField(model, controller, this);
        panel.add(gameField, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setVisible(true);
    }


    private void LoadImage(String path) throws IOException {
        hashMapImage = new HashMap<String, ImageIcon>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            BufferedImage image = ImageIO.read(files[i]);
            hashMapImage.put(files[i].getName(), new ImageIcon(image));
        }
    }
}
