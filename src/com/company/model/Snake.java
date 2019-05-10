package com.company.model;
import java.util.Vector;
//Класс описывает действия змейки
public class Snake implements Observed {
    private Vector<SnakeCell> snakeCells;//Все тело змеи
    private Vector<Observer> observers;
    private boolean kill;//Мертва она или нет
    private int speed;//Скорость змейки

    //Конструктор
    public Snake() {
        observers = new Vector<>();
        snakeCells = new Vector<>();
        snakeCells.add(new SnakeCell(1, 3, 40, "Head"));
        snakeCells.add(new SnakeCell(1, 2, 40, "Body"));
        snakeCells.add(new SnakeCell(1, 1, 40, "Tail"));
        kill = false;
        speed = 300;
    }

    //Обновляет положение змейки и проверяет не съела ли она себя
    public void update() {
        snakeCells.get(0).SnakeCellStep();
        if (notifyObservers()) {
            for (int i = snakeCells.size() - 1; i >= 1; i--)
                changeCells(snakeCells.get(i), snakeCells.get(i - 1));
            cheakAteHimself();
            return;
        }
        snakeGrow();
    }

    //Меняет две ячейки,при обновление положения змеи
    private void changeCells(SnakeCell cellChangeble, SnakeCell cell) {
        cellChangeble.SnakeCellStep();
        if (cell.getState() == "Head") {
            if (cell.getDirection() != (cellChangeble.getDirection() & 255)) {
                cellChangeble.setState("BodyRotate");
                cellChangeble.setDirection(((cellChangeble.getDirection() & 255) << 8) | cell.getDirection());
            } else {
                cellChangeble.setDirection(cell.getDirection());
                cellChangeble.setState("Body");
            }
        } else if (cellChangeble.getState() == "Tail")
            cellChangeble.setDirection((cell.getDirection() & 255));
        else {
            cellChangeble.setState(cell.getState());
            cellChangeble.setDirection(cell.getDirection());
        }

    }

    //Провека на съела ли себя змея
    private void cheakAteHimself() {
        int size = snakeCells.size();
        SnakeCell head = snakeCells.get(0);
        boolean flagAte = false;
        for (int i = 4; i < size; i++) {
            if (flagAte) {
                snakeCells.remove(snakeCells.size() - 1);
                continue;
            }
            SnakeCell snakeCell = snakeCells.get(i);
            if (head.getX() == snakeCell.getX() && head.getY() == snakeCell.getY()) {
                snakeCells.get(i - 1).setState("Tail");
                snakeCells.get(i - 1).setDirection((snakeCells.get(i - 1).getDirection() & 255));
                snakeCells.remove(i);
                flagAte = true;
            }
        }
    }

    //Увеличивает рост змейки, после того,как она съела еду
    private void snakeGrow() {
        SnakeCell beforeHead = snakeCells.get(1);
        SnakeCell newPartSnakeCell = new SnakeCell(beforeHead.getX(), beforeHead.getY(), beforeHead.getDirection(), "Body");
        changeCells(newPartSnakeCell, snakeCells.get(0));
        snakeCells.add(1, newPartSnakeCell);
        speed = (int) (speed * 0.99);
    }

    //Изменяет направление движения змейки при нажатие на клавиши
    public void changeDirection(int direction) {
        SnakeCell head = snakeCells.get(0);
        SnakeCell beforeHead = snakeCells.get(1);
        if (Math.abs(direction - (beforeHead.getDirection() & 255)) != 2) {
            head.setDirection(direction);
        }
    }

    //Добавляет слушателя
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    //Удаляет слушателя
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    //Расслывает уведомление подписчикам о том,что она сделала движение,если она врезалась в блок или не врезалась возвращает true
    //Если съела фрукт возвращает false;
    @Override
    public boolean notifyObservers() {
        SnakeCell head = snakeCells.get(0);
        for (Observer o : observers) {
            if (Blocks.class.isInstance(o) && o.handleEvent(head.getX(), head.getY()))
                kill = true;
            else if (Food.class.isInstance(o) && o.handleEvent(head.getX(), head.getY()))
                return false;
        }
        return true;
    }

    //Возвращает змейку(то есть все ячейки змейки)
    public Vector<SnakeCell> getSnakeCells() {
        return snakeCells;
    }

    //Мертва ли змейка или нет
    public boolean isKill() {
        return kill;
    }

    //Ускоряет змеку
    public void changeAcceleration(boolean flag) {
        if (flag)
            speed /= 2;
        else
            speed *= 2;
    }

    //Дает скорость змейки
    public int getSpeed() {
        return speed;
    }
}
