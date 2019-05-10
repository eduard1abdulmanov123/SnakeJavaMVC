package com.company.controller;

import com.company.model.SnakeModel;
import com.company.view.SnakeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class SnakeController extends KeyAdapter implements ActionListener {
    private SnakeModel model;
    private SnakeView view;
    private Timer timer;
    private boolean pressedShift = false;

    public SnakeController(SnakeModel model) {
        this.model = model;
    }

    public void setView(SnakeView view) {
        this.view = view;
    }

    public void StartGame() {
        model.StartGame();
        timer = new Timer(model.getSpeed(), this);
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!model.isGameOver()) {
            if (e.getKeyCode() >= 37 && e.getKeyCode() <= 40)
                model.changeDirection(e.getKeyCode());
            else if (e.getKeyCode() == 16 && !pressedShift) {
                pressedShift = true;
                model.changeAcceleration(true);
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                StartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 16) {
            model.changeAcceleration(false);
            pressedShift = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!model.isGameOver()) {
            model.Update();
            timer.setDelay(model.getSpeed());
            view.repaint();
        } else {
            timer.stop();
        }
    }
}
