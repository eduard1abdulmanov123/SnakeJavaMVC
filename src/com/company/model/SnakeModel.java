package com.company.model;
import java.awt.*;
import java.util.Vector;
//Класс описывает Модель игры змейка
public class SnakeModel {
    private static final int SQUARE = 20;//Площадь игрового поля
    private static final int POINT = 50;//Одна дополнительная ячейка змейки дает 50 очков
    private Snake snake;
    private Blocks blocks;
    private Food food;
    private boolean gameOver;//конец игры
    private boolean firstgame;//Это первая игра?

    //Конструктор
    public SnakeModel() {
        snake = new Snake();
        blocks = new Blocks(SQUARE);
        food = new Food(SQUARE);
        snake.addObserver(blocks);
        snake.addObserver(food);
        food.geterateFood(blocks.getBlockCells(), snake.getSnakeCells());
        firstgame = true;
        gameOver = true;
    }

    //Начало игры
    public void StartGame() {
        if (!firstgame) {
            gameOver = false;
            snake = new Snake();
            snake.addObserver(blocks);
            snake.addObserver(food);
            blocks.InitialistionBlock();
            food.geterateFood(blocks.getBlockCells(), snake.getSnakeCells());
        } else {
            gameOver = false;
            firstgame = false;
        }
    }

    //Обновление игровой модели
    public void Update() {
        snake.update();
        if (snake.isKill())
            gameOver = true;
        food.update(blocks.getBlockCells(), snake.getSnakeCells());

    }

    //Изменяет направление змейки
    public void changeDirection(int direction) {
        snake.changeDirection(direction);
    }

    //Модель выдает змейку для Представления
    public Vector<SnakeCell> getSnake() {
        return snake.getSnakeCells();
    }

    //Модель выдает преграды для Представления
    public Vector<Cell> getBlocks() {
        return blocks.getBlockCells();
    }

    //Модель выдает еду для Представления
    public Food getFood() {
        return food;
    }

    //Модель выдает площадь игрового поля
    public static int getSQUARE() {
        return SQUARE;
    }

    //Модель выдает скорость движения змейки(то есть задержку перед обновлением)
    public int getSpeed() {
        return snake.getSpeed();
    }

    //Модель выдает Закончена ли игра?
    public boolean isGameOver() {
        return gameOver;
    }

    //Выдает количество очков, заработанное игроком
    public int howManyPoints() {
        return snake.getSnakeCells().size() * POINT - 3 * POINT;
    }

    //Метод сопровождает метод ускорения змейки
    public void changeAcceleration(boolean flag) {
        snake.changeAcceleration(flag);
    }
}
