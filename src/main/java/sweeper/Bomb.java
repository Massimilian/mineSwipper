package sweeper;

/**
 * Класс для хранения информации о бомбах
 */
class Bomb {
    private Matrix bombMap; // карта нахождения бомб
    private int totalBombs;

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombCount(); // корректируем количество бомб
    }

    public int getTotalBombs() {
        return totalBombs;
    }

    // инициализация поля - размещение пустого поля и помещение туда бомб с сопутствующим окружением числами
    void start() {
        this.bombMap = new Matrix(Box.ZERO);// инициализация начальной карты одними пустыми полями (можно любое иное значение)
        for (int i = 0; i < totalBombs; i++) { // заполнение пространства бомбами (количество бомб = totalBombs
            placeBomb();
        }

    }

    Box get(Coord coord) {
        return bombMap.get(coord);
    }

    /**
     * метод для корректировки неправильного количества бомб
     */
    private void fixBombCount() {
        int max = Ranges.getSize().x * Ranges.getSize().y / 2;
        totalBombs = Math.min(totalBombs, max);
    }

    /**
     * Метод по установке одной бомбы на карте
     */
    private void placeBomb() {
        //bombMap.set(new Coord(3, 3), Box.BOMB); // эксперимента ради размещаем одну бомбы по адресу 3.3
        boolean isPut = false;
        while (!isPut) {
            Coord coord = Ranges.getRandomCoord(); // получаем рандомное место для бомбы
            if (bombMap.get(coord) != Box.BOMB) {
                isPut = true;
                bombMap.set(coord, Box.BOMB); // устанавливаем бомбу
                incrNumbersAroundBomb(coord);
            }
        }
    }

    /**
     * Увеличиваем счётчики вокруг бомбы
     *
     * @param coord
     */
    private void incrNumbersAroundBomb(Coord coord) {
        for (Coord c : Ranges.getCoordsAround(coord)) {
            if (bombMap.get(c) != Box.BOMB) {
                bombMap.set(c, bombMap.get(c).nextNumberBox()); // устанавливаем следующий номер в клетку
            }
        }
    }
}
