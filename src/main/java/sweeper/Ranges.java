package sweeper;

import java.util.ArrayList;
import java.util.Random;

/**
 * специальный класс для работы с полями, находящимися по координатам, прописанным внутри Coord.
 */
public class Ranges {
    private static Coord size; // координата, указывающая на границы нашего экрана.
    // Изначально статическая, чтобы потом можно было обращаться к ней без создания экземпляра класса.
    private static ArrayList<Coord> allCoords; // статическв коллекция, коорая содержит в себе все координаты
    private static Random random = new Random(); // поле для генерации случайных значений внутри метода getRandomCoord()

    static void setSize(Coord _size) { // функция установки размера экрана (адрес "крайнего" поля)
        size = _size;
        allCoords = fillCoords();
    }

    public static Coord getSize() { // функция получения адреса "крайнего" поля
        return size;
    }

    public static ArrayList<Coord> getAllCoords() {
        return allCoords;
    }

    private static ArrayList<Coord> fillCoords() {
        ArrayList<Coord> result = new ArrayList<>();
        for (int i = 0; i < size.y; i++) {
            for (int j = 0; j < size.x; j++) {
                result.add(new Coord(j, i));
            }
        }
        return result;
    }

    public static boolean inRange(Coord coord) {
        return coord.x < size.x && coord.y < size.y && coord.x >= 0 && coord.y >= 0;
    }

    static Coord getRandomCoord() {
        return new Coord((int) (random.nextInt(size.x)), random.nextInt(size.y));
    }

    static ArrayList<Coord> getCoordsAround(Coord coord) {
        Coord around;
        ArrayList<Coord> list = new ArrayList<>();
        for (int x = coord.x - 1; x <= coord.x + 1; x++) { // перебираем все клетки вокруг по оси x
            for (int y = coord.y - 1; y <= coord.y + 1; y++) { // перебираем все клетки вокруг по оси y
                if (inRange(around = new Coord(x, y))) {
                    if (!around.equals(coord)) {
                        list.add(around);
                    }
                }
            }

        }
        return list;
    }
}
