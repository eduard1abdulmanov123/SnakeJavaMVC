package com.company.model;
//Класс описывает ячейку игрового поля(используется для змейки, еды, преград)
public class Cell {
    protected int x;//положение по Х
    protected int y;//положение по У
    protected String state;//Состояние

    //Конструктор
    public Cell(int x, int y, String state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    //getter и setter
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
