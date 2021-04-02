package sweeper;

/**
 * "фасадный", который будет решать все вопросы, связанные именно с игровой деятельностью
 */
public class Game {
    private GameState state; // состояние игрока (играем, взорвались или выиграли
    private Bomb bomb;
    private Flag flag;

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows)); // установка размера внутри конструктора
        bomb = new Bomb(bombs); // установка поля с бомбами (bombs - количество бомб)
        flag = new Flag(); // установка поля с закрытыми блоками
    }

    public Box getBox(Coord coord) {
        return flag.get(coord) == Box.OPENED ? bomb.get(coord) : flag.get(coord);
    }

    public GameState getState() {
        return state;
    }

    // инициализируем начальное состояние карты бомб
    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED; // устанавливаем состояние "играем"
    }

    /**
     * производит некое действие после нажатия ЛКМ
     *
     * @param coord
     */
    public void pressLeftButton(Coord coord) {
        if (gameOver()) {
            return;
        }
        openBox(coord);
        checkWinner();
    }

    private void checkWinner() {
        if (state == GameState.PLAYED) {
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs()) {
                state = GameState.WINNER;
            }
        }
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED:
                setOpenedToClosedBoxesAroundNumber(coord);
            case FLAGED:
                return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxesAround(coord);
                        return;
                    case BOMB:
                        openBombs(coord);
                        return;
                    default:
                        flag.setOpenedToBox(coord);
                        return;
                }
        }
    }

    private void openBombs(Coord coord) {
        state = GameState.BOMBED;
        flag.setBombedToBomb(coord);
        for (Coord c : Ranges.getAllCoords()) {
            if (bomb.get(c) == Box.BOMB) {
                flag.setOpenedToClosedBombBox(c);
            } else {
                flag.setNoBombToFlagedSafeBox(c);
            }
        }
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) {
            return;
        }
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED) {
            return false;
        }
        start();
        return true;
    }

    void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (bomb.get(coord) != Box.BOMB) {
            if (flag.getCountOfFlaggedBoxedAround(coord) == bomb.get(coord).getNumber()) {
                for (Coord around : Ranges.getCoordsAround(coord)) {
                    if (flag.get(around) == Box.CLOSED) {
                        openBox(around);
                    }
                }
            }
        }
    }
}
