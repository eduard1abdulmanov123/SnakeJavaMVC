package com.company.view;

import com.company.controller.SnakeController;
import com.company.model.Cell;
import com.company.model.Food;
import com.company.model.SnakeCell;
import com.company.model.SnakeModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class GameField extends JPanel {
    private SnakeModel model;
    private SnakeController controller;
    private SnakeView view;
    private int square;
    private Point sizeCell;

    public GameField(SnakeModel model, SnakeController controller, SnakeView view) {
        this.model = model;
        this.controller = controller;
        this.view = view;
        addKeyListener(controller);
        setFocusable(true);
        FieldInitialization();
    }

    public void FieldInitialization() {
        removeAll();
        ImageIcon icon = view.hashMapImage.get("Grass.png");
        sizeCell = new Point(icon.getIconWidth(), icon.getIconHeight());
        square = model.getSQUARE();
        setPreferredSize(new Dimension(sizeCell.x * square, sizeCell.y * square));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        for (int i = 1; i < square - 1; i++) {
            for (int j = 1; j < square - 1; j++) {
                ImageIcon icon = view.hashMapImage.get("Grass.png");
                g2d.drawImage(icon.getImage(), j * sizeCell.x, i * sizeCell.y, null);
            }
        }

        Vector<SnakeCell> sneakCells = model.getSnake();
        for (int i = sneakCells.size() - 1; i >= 0; i--) {
            SnakeCell c = sneakCells.get(i);
            g2d.drawImage(getImageForSneakCell(c), c.getX() * sizeCell.x, c.getY() * sizeCell.y, null);
        }

        Food food = model.getFood();
        g2d.drawImage(view.hashMapImage.get(food.getState() + ".png").getImage(),
                food.getX() * sizeCell.x, food.getY() * sizeCell.y, null);

        Vector<Cell> blockCells = model.getBlocks();
        for (Cell block : blockCells) {
            ImageIcon icon = view.hashMapImage.get(block.getState() + ".png");
            g2d.drawImage(icon.getImage(), block.getX() * sizeCell.x, block.getY() * sizeCell.y, null);
        }
    }

    public Image getImageForSneakCell(SnakeCell c) {
        BufferedImage image = new BufferedImage(sizeCell.x, sizeCell.y, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        ImageIcon sprite = view.hashMapImage.get(c.getState() + ".png");
        g2d.translate(sprite.getIconWidth() / 2, sprite.getIconHeight() / 2);
        g2d.rotate(c.getDegrees());
        g2d.translate(-sprite.getIconWidth() / 2, -sprite.getIconHeight() / 2);
        g2d.drawImage(sprite.getImage(), 0, 0, null);
        return (new ImageIcon(image)).getImage();
    }
}
