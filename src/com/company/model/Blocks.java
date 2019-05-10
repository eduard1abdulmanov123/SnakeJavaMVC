package com.company.model;

import java.util.Random;
import java.util.Vector;
//Класс блоков, который играет роль препядствий
public class Blocks implements Observer {
    private Vector<Cell> blockCells;//Блоки
    private int square;//так как поле квадратное,то это максимально дальняя ячейка по x и y

    //Конструктор
    public Blocks(int square) {
        this.square = square;
        blockCells = new Vector<>();
        //Добавление блоков вокруг поля
        for (int i = 0; i < square; i++) {
            blockCells.add(new Cell(i, 0, "Block"));
            blockCells.add(new Cell(i, square - 1, "Block"));
        }
        for (int i = 1; i < square - 1; i++) {
            blockCells.add(new Cell(0, i, "Block"));
            blockCells.add(new Cell(square - 1, i, "Block"));
        }
        //Генерация блоков внутри поля
        generateRandomBlock();
    }

    //Инициализация блоков внутри поля
    public void InitialistionBlock() {
        removeBlocksRandom();
        for (Cell block : blockCells)
            if (block.getState() == "BlockBroken")
                block.setState("Block");
        generateRandomBlock();
    }

    //Рандомная генерация блоков внутри поля
    private void generateRandomBlock() {
        Random random = new Random();
        for (int count = 0; count < square; count++) {
            while (true) {
                int x = (random.nextInt(square - 1) + 1);
                int y = (random.nextInt(square - 1) + 1);
                if (CorrectBlockPosition(x, y)) {
                    blockCells.add(new Cell(x, y, "Block"));
                    break;
                }
            }
        }
    }

    //Проверяет можно ли в этой позиции создать блок
    private boolean CorrectBlockPosition(int x, int y) {
        if (x == 1 && y < 10)
            return false;
        for (Cell block : blockCells) {
            if (x == block.getX() && y == block.getY())
                return false;
        }

        return true;
    }

    //Удаляет все блоки внутри поля
    public void removeBlocksRandom() {
        for (int count = blockCells.size() - 1; count >= 2 * square + 2 * (square - 2); count--) {
            blockCells.remove(count);
        }
    }

    //Получает событие когда змейка двинулась на одну ячейку(является по подписчиком наблюдаемого объекта-змейка)
    @Override
    public boolean handleEvent(int x, int y) {
        for (Cell block : blockCells) {
            if (block.getX() == x && block.getY() == y) {
                block.setState("BlockBroken");
                return true;
            }
        }
        return false;
    }

    //Выдает все преграды на поле
    public Vector<Cell> getBlockCells() {
        return blockCells;
    }
}
