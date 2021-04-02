package sweeper;

/**
 * класс для хранения матрицы элементов Box,
 * причём в данном случае будет два слоя: нижний слой, где хранятся бомбы,
 * и верхний слой, где хранятся флаги.
 * Примечательно, что этот класс не является публичным
 */
class Matrix {
    private Box[][] matrix; // массив для хранения значений Box

    // в конструкторе создаём двумерный массив, размер которогоо мы берём из размера Ranges
    Matrix(Box defaultBox) {
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coord coord : Ranges.getAllCoords()) {
            matrix[coord.x][coord.y] = defaultBox; // заполняем всю матрицу дефолтовыми значениями
        }
    }

    Box get(Coord coord) {
        Box box = null;
        if (Ranges.inRange(coord)) {
            box = matrix[coord.x][coord.y];
        }
        return box;
    }

    void set(Coord coord, Box box) {
        if (Ranges.inRange(coord)) {
            matrix[coord.x][coord.y] = box;
        }
    }
}
