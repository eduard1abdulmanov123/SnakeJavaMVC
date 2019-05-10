package com.company.model;

//Класс описывает ячейку змейки
public class SnakeCell extends Cell {
    private int direction;//Направление движения(клавиша нажатия)

    //Конструктор
    public SnakeCell(int x, int y, int direction, String state) {
        super(x, y, state);
        this.direction = direction;
    }

    //Делает шаг в заданом направление ячейки
    public void SnakeCellStep() {
        if ((direction & 255) == 38)
            setY(getY() - 1);
        else if ((direction & 255) == 40)
            setY(getY() + 1);
        else if ((direction & 255) == 37)
            setX(getX() - 1);
        else if ((direction & 255) == 39)
            setX(getX() + 1);
    }

    //Дает градус направления движения(Лево-это 0 градусов)
    public double getDegrees() {
        int degrees = 0;
        if (getState() != "BodyRotate")
            for (int i = 37; i <= 40 && i != direction; i++)
                degrees += 90;
        else {
            if (direction == 9767 || direction == 9512)
                degrees = 90;
            else if (direction == 10024 || direction == 9765)
                degrees = 180;
            else if (direction == 10277 || direction == 10022)
                degrees = 270;
        }
        return Math.toRadians(degrees);
    }

    //Getter и setter
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
