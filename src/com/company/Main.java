package com.company;

import com.company.controller.SnakeController;
import com.company.model.SnakeModel;
import com.company.view.SnakeView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SnakeModel model = new SnakeModel();
        SnakeController controller = new SnakeController(model);
        JFrame frame = new SnakeView(model, controller);

    }
}
