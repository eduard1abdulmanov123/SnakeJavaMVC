package com.company.model;

import java.awt.*;
import java.util.Random;
import java.util.Vector;
//Класс,который описывает Еду для змейки
public class Food extends Cell implements Observer {
    private int square;//так как поле квадратное,то это максимально дальняя ячейка по x и y
    private int amountOfLife;//Количество жизней еды(через сколько шагов змейки еда поменяет позицию)

    //Конструктор
    public Food(int square) {
        super(-1, -1, "Rabbit");
        this.square = square;
        amountOfLife = 0;
    }

    //Обновление еды на карте(если жизний ноль, обновляем позицию,иначе уменьшаем количество жизней)
    public void update(Vector<Cell> blocks, Vector<SnakeCell> snake) {
        if (amountOfLife == 0) {
            geterateFood(blocks, snake);
        }
        amountOfLife--;
    }

    //Генерирует позицию еды на поле и определяет количество жизней
    public void geterateFood(Vector<Cell> blocks, Vector<SnakeCell> snake) {
        Random random = new Random();
        int x = (random.nextInt(square - 1) + 1);
        int y = (random.nextInt(square - 1) + 1);
        while (!CorrectFoodPosition(x, y, blocks, snake)) {
            x = (random.nextInt(square - 1) + 1);
            y = (random.nextInt(square - 1) + 1);
        }
        this.x = x;
        this.y = y;
        amountOfLife = defineAmountOfLife(blocks, snake) + 5;
    }

    //Определяет количество жизней для Еды
    private int defineAmountOfLife(Vector<Cell> blocks, Vector<SnakeCell> snake) {
        byte[][] matrix = new byte[square][square];
        for (int i = 0; i < square; i++)
            for (int j = 0; j < square; j++)
                matrix[i][j] = 0;
        for (Cell block : blocks)
            matrix[block.getY()][block.getX()] = -1;
        for (SnakeCell snakeCell : snake)
            matrix[snakeCell.getY()][snakeCell.getX()] = -1;
        return WaveAlgoritm(snake.get(0), matrix);
    }

    //Волновой алгоритм Ли,который выдает минимальное количество шагов до точки
    private int WaveAlgoritm(SnakeCell head, byte[][] matrix) {
        Vector<Point> bufferList = new Vector<>();
        bufferList.add(new Point(head.getX(), head.getY()));
        matrix[head.getY()][head.getX()] = 1;
        for (int i = 0; i < bufferList.size(); i++) {
            Point point = bufferList.get(i);
            if (point.x == this.x && point.y == this.y)
                return matrix[this.y][this.x];

            for (int y = point.y - 1; y <= point.y + 1; y++) {
                for (int x = point.x - 1; x <= point.x + 1; x++) {
                    if (((x == point.x && y != point.y) || (y == point.y && x != point.x)) && matrix[y][x] == 0) {
                        matrix[y][x] = (byte) (matrix[point.y][point.x] + 1);
                        bufferList.add(new Point(x, y));
                    }
                }
            }
        }
        return 0;
    }

    //Определяет можно ли поставить в данном месте (x and y) Еду
    private boolean CorrectFoodPosition(int x, int y, Vector<Cell> blocks, Vector<SnakeCell> snake) {
        for (int i = 0; i < blocks.size(); i++)
            if (x == blocks.get(i).getX() && y == blocks.get(i).getY())
                return false;
        for (int i = 0; i < snake.size(); i++)
            if (x == snake.get(i).getX() && y == snake.get(i).getY())
                return false;
        return true;
    }

    //Является подписчиком для наблюдаемого объекта(змеи)
    @Override
    public boolean handleEvent(int x, int y) {
        if (this.x == x && this.y == y) {
            amountOfLife = 0;
            this.x = -1;
            this.y = -1;
            return true;
        }
        return false;
    }
}
