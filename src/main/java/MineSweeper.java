import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.*;
import sweeper.Box;

public class MineSweeper extends JFrame { // JFrame помогает программе запуститься в отдельном окне
    private Game game; // создание плоскости, в которой мы будем играть
    private JLabel label; // лейбл для сохранения информации о состоянии игры (играем, проиграли, выиграли)
    private JPanel panel; // панель, в которой мы будем работать
    private final int COLS = 9; // количество столбцов в игре
    private final int ROWS = 9; // количество строк в каждом столбце
    private final int IMAGE_SIZE = 50; // размер картинки (50/50)
    private final int BOMBS = 10; // количество бомб на карте

    public static void main(String[] args) {
        new MineSweeper();
    }

    /**
     * инициализация программы через приватный конструктор
     */
    private MineSweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start(); // стартуем игру
        setImages(); // устанавливаем картинки
        initLabel(); // инициализация лейбла
        initPanel(); // инициализируем панель
        initFrame(); // инициализируем Frame
    }

    /**
     * инициализация лейбла состояния (добавление на экран)
     */
    private void initLabel() {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH);
    }

    /**
     * инициализация панели через приватный конструктор
     */
    private void initPanel() {
        this.panel = new JPanel() {  // создаём новую панель
            @Override
            protected void paintComponent(Graphics g) { // переопределяем метод по отрисовке компонентов
                super.paintComponent(g);
//                вариант для вывода всех картинок в одну строку (COLS = 15, ROWS = 1)
//                for (Box box : Box.values()) {
//                    Coord coord = new Coord(box.ordinal() * IMAGE_SIZE, 0); // создаём координату, по которой будет находиться рисунок
//                    // теперь добавляем отрисовку всех изображений, заявленных внутри enum Box
//                    // изображения передаём по порядку, указанному в самом enum, при помощи метода ordinal()
//                    // порядок начинается с ноля (изначальная позиция координат);
//                    // далее мы инкрементируем это значение, увеличивая его на IMAGE_SIZE
//                    // таким образом заполняется вся строка
//                    g.drawImage((Image) box.image, coord.x, coord.y, this);
//                }

//                вариант для вывода изображений бомб в формате 9/9 (COLS = 9, ROWS = 9).
//                В данном варианте мы обращаемся к конкретному Box, который обладает данными координатами.
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {  // добавляем мышечный адаптор (т.е. возможность работы с программой через мышку)
            @Override
            public void mousePressed(MouseEvent e) { // обрабатываем результат нажатия мышки
                int x = e.getX() / IMAGE_SIZE; // получаем координату x, где мы находимся
                int y = e.getY() / IMAGE_SIZE; // получаем координату y, где мы находимся
                Coord coord = new Coord(x, y); // создаём координату с полученными значениями
                if (e.getButton() == MouseEvent.BUTTON1) { // если была нажата левая кнопка мыши
                    game.pressLeftButton(coord); // тогда в игре вызываем метод, который будет отображать реакцию на нажатие ЛКМ
                }
                if (e.getButton() == MouseEvent.BUTTON3) { // если была нажата правая кнопка мыши
                    game.pressRightButton(coord); // тогда в игре вызываем метод, который будет отображать реакцию на нажатия ПКМ
                }
                if (e.getButton() == MouseEvent.BUTTON2) { //если была нажата средняя кнопка мыши
                    game.start();
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        // создаём размеры данной панели через вспомогательный класс Dimension с размером,
        // напрямую зависящим от максимального размера поля и размера картинок
        add(panel); // добавляем панель
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED:
                return "Think twice!";
            case BOMBED:
                return "BOOOOOM!!! You lose...";
            case WINNER:
                return "You're winner! Good job!";
            default:
                return "Welcome!";
        }
    }

    /**
     * метод для инициализации Frame
     */
    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // завершает работу программы, в данном случае - при закрытии программы
        setTitle("Java Sweeper"); // устанавливаем заголовок
        setResizable(false); // устанавливаем возможность изменения размера в false
        setVisible(true); // делаем программу видимой для пользователя в отдельном окне
        pack(); // устанавливает минимальный размер контейнера, достаточный для отображения всех компонентов
        setLocationRelativeTo(null); // устанавливаем программу по центру
        setIconImage(getImage("icon")); // устанавливаем иконку самой программы

    }

    /**
     * метод по установке всех картинок
     */
    private void setImages() {
        for (Box box : Box.values()) {
            // каждому экземпляру внутри enum box мы присваиваем картинку внутрь экземпляра Object image
            // теперь все картинки подгружены внутрь enum Box
            box.image = getImage(box.name());
        }
    }

    /**
     * метод для получения картинок
     */
    private Image getImage(String name) {
        String filename = "img/" + name.toLowerCase() + ".png"; // название картинки с адресом и расширением
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(filename)); // передаём ресурс из папки, которую ранее пометили как ресурсную (через Mark Directory as...)
        return imageIcon.getImage(); // возвращаем картинку
    }
}
