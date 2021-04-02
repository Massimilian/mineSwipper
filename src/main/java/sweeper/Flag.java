package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.CLOSED); // заполняем матрицу пустыми закрытыми полями
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get(Coord coord) {
        return flagMap.get(coord); // возвращает значение поля по координатам
    }

    /**
     * Производит действие по нажатой ЛКМ
     *
     * @param coord
     */
    public void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    public void setFlagedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    public void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    /**
     * Проверка на наличие флага
     *
     * @param coord
     */
    public void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED:
                this.setClosedToBox(coord);
                break;
            case CLOSED:
                this.setFlagedToBox(coord);
                break;
            case OPENED:
                break;
        }
    }

    public int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    public void setBombedToBomb(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    public void setOpenedToClosedBombBox(Coord c) {
        if (flagMap.get(c) == Box.CLOSED) {
            flagMap.set(c, Box.OPENED);
        }
    }

    public void setNoBombToFlagedSafeBox(Coord c) {
        if (flagMap.get(c) == Box.FLAGED) {
            flagMap.set(c, Box.NOBOMB);
        }
    }


    int getCountOfFlaggedBoxedAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (flagMap.get(around) == Box.FLAGED) {
                count++;
            }
        }
        return count;
    }
}
