package sweeper;

/**
 * Класс, в котором перечислены все возможные состояния поля.
 * Здесь же будут храниться отображения картинок (точнее - ссылки на сами картинки).
 * Внимание! Порядок имеет значение!
 */
public enum Box {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;

    public Object image; // объект, в котором мы будем хранить собственно картинку; Object, а не Image - так как внутри пакета sweeper и не можем привязываться к конкретной реализации

    public Box nextNumberBox() {
        return Box.values()[this.ordinal() + 1]; // возвращаем следующий по порядку номер
    }

    int getNumber() {
        return this.ordinal();
    }
}
