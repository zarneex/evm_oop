package L53GUI;

import model.FibonacciSeries;
import model.FileManager;
import model.NumberArray;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//главное окно программы (вид + обработчики событий)
//вся логика задания - в классах пакета model, окно только вызывает их методы и показывает результат
public class MainWindow extends JFrame {

    private static final int FIB_PRINT = 10; //сколько первых чисел ряда показывать (по условию 10)

    //объекты модели (классы из лабораторной работы 3)
    private NumberArray array = new NumberArray(); //array - массив чисел (сначала пустой)
    private FibonacciSeries fib = null;            //fib - ряд Фибоначчи (null - еще не построен)

    //компоненты для массива
    private JTextField arrayField = new JTextField();     //поле ввода чисел
    private JButton applyButton = new JButton("Принять");  //принять числа из поля
    private JButton loadButton = new JButton("Загрузить из файла...");
    private DefaultTableModel arrayModel = new DefaultTableModel(new String[]{"№", "Число"}, 0); //данные таблицы массива
    private JButton orderButton = new JButton("Проверить порядок (a)");
    private JButton minButton = new JButton("Найти минимум (c)");
    private JLabel orderLabel = new JLabel("Порядок: -");
    private JLabel minLabel = new JLabel("Минимальный элемент: -");

    //компоненты для ряда Фибоначчи
    private JSpinner lengthSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 50, 1)); //длина ряда, только 1..50
    private JButton fibButton = new JButton("Построить ряд (b)");
    private DefaultTableModel fibModel = new DefaultTableModel(new String[]{"№", "Число (* - четное)"}, 0);
    private JLabel fibLabel = new JLabel("Ряд еще не построен");

    //нижняя панель
    private JButton saveButton = new JButton("Сохранить результаты...");
    private JLabel statusLabel = new JLabel(" "); //строка сообщений (ошибки - красным)

    public MainWindow() {
        super("Лабораторная работа 5, вариант 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //BorderLayout - раскладка по сторонам окна: центр и низ
        setLayout(new BorderLayout(5, 5));

        //GridLayout(1, 2) - две равные колонки: слева массив, справа ряд
        JPanel center = new JPanel(new GridLayout(1, 2, 10, 0)); //center - средняя часть окна
        center.add(createArrayPanel());
        center.add(createFibPanel());
        add(center, BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        //пока данных нет, кнопки, которым нечего делать, выключены
        updateButtons();

        setSize(900, 520);
        setMinimumSize(new Dimension(760, 420));
        setLocationRelativeTo(null); //окно по центру экрана
    }

    //левая часть: ввод массива, таблица, подзадачи a и c
    private JPanel createArrayPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5)); //panel - вся левая часть
        panel.setBorder(new TitledBorder("Массив (подзадачи a и c)"));

        //сверху - строка ввода и кнопки; BoxLayout кладет компоненты друг под другом
        JPanel top = new JPanel(); //top - верх левой части
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        //строка ввода: подпись слева, кнопка справа, поле растягивается на все оставшееся место
        JPanel inputRow = new JPanel(new BorderLayout(5, 0));
        inputRow.add(new JLabel("Числа через пробел:"), BorderLayout.WEST);
        inputRow.add(arrayField, BorderLayout.CENTER);
        inputRow.add(applyButton, BorderLayout.EAST);
        JPanel fileRow = new JPanel(new FlowLayout(FlowLayout.LEFT)); //FlowLayout - компоненты в строку
        fileRow.add(loadButton);
        top.add(inputRow);
        top.add(fileRow);
        panel.add(top, BorderLayout.NORTH);

        //в центре - таблица с массивом
        panel.add(createTable(arrayModel), BorderLayout.CENTER);

        //снизу - кнопки подзадач и результаты; GridLayout(2, 2) - сетка 2 x 2
        JPanel bottom = new JPanel(new GridLayout(2, 2, 5, 5));
        bottom.add(orderButton);
        bottom.add(orderLabel);
        bottom.add(minButton);
        bottom.add(minLabel);
        panel.add(bottom, BorderLayout.SOUTH);

        //в поле ввода можно напечатать только цифры, пробел и минус - остальные символы не появятся
        arrayField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar(); //c - нажатый символ
                //isISOControl - служебные клавиши (Backspace, Delete), их не трогаем
                if (!Character.isDigit(c) && c != ' ' && c != '-' && !Character.isISOControl(c))
                    e.consume(); //consume - "съесть" символ, он не попадет в поле
            }
        });

        //обработчики нажатий: лямбда e -> ... это короткая запись реализации интерфейса ActionListener
        applyButton.addActionListener(e -> applyArray());
        arrayField.addActionListener(e -> applyArray()); //Enter в поле - то же, что кнопка "Принять"
        loadButton.addActionListener(e -> loadFromFile());
        orderButton.addActionListener(e -> orderLabel.setText("Порядок: " + array.checkOrder()));
        minButton.addActionListener(e -> minLabel.setText("Минимальный элемент: " + array.findMin()));
        return panel;
    }

    //правая часть: длина ряда, таблица с первыми числами, подзадача b
    private JPanel createFibPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new TitledBorder("Ряд Фибоначчи (подзадача b)"));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Длина ряда (1-50):"));
        top.add(lengthSpinner);
        top.add(fibButton);
        panel.add(top, BorderLayout.NORTH);
        panel.add(createTable(fibModel), BorderLayout.CENTER);
        panel.add(fibLabel, BorderLayout.SOUTH);

        fibButton.addActionListener(e -> buildFibonacci());
        return panel;
    }

    //нижняя часть окна: кнопка сохранения и строка сообщений
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(saveButton);
        panel.add(statusLabel);
        saveButton.addActionListener(e -> saveToFile());
        return panel;
    }

    //создает таблицу без редактирования ячеек, с прокруткой
    private JScrollPane createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //null - ячейки нельзя редактировать
        table.getColumnModel().getColumn(0).setMaxWidth(50); //колонка "№" узкая
        return new JScrollPane(table);              //JScrollPane добавляет полосу прокрутки
    }

    //включает и выключает кнопки в зависимости от того, есть ли данные
    private void updateButtons() {
        orderButton.setEnabled(!array.isEmpty());
        minButton.setEnabled(!array.isEmpty());
        saveButton.setEnabled(!array.isEmpty() || fib != null);
    }

    //сообщение в строке внизу: ошибка - красным, обычное - черным
    private void showMessage(String text, boolean error) {
        statusLabel.setForeground(error ? Color.RED : Color.BLACK); //? : - короткий if-else
        statusLabel.setText(text);
    }

    //после нового массива: заполняем таблицу заново, старые результаты стираем
    private void showArray() {
        arrayModel.setRowCount(0); //очищаем таблицу
        for (int i = 0; i < array.size(); i++) {
            arrayModel.addRow(new Object[]{i + 1, array.get(i)});
        }
        orderLabel.setText("Порядок: -");
        minLabel.setText("Минимальный элемент: -");
        updateButtons();
    }

    //кнопка "Принять": берем числа из поля ввода
    private void applyArray() {
        try {
            array.setFromText(arrayField.getText());
            showArray();
            showMessage("Массив принят: " + array.size() + " чисел", false);
        } catch (NumberFormatException e) {
            //строка с ошибкой - массив не изменился
            showMessage("Ошибка: " + e.getMessage(), true);
        }
    }

    //открывает стандартное окно Windows для выбора файла (FileDialog из библиотеки AWT)
    //mode - открыть (LOAD) или сохранить (SAVE); возвращает файл или null, если нажали "Отмена"
    private File chooseFile(String title, int mode, String fileName) {
        FileDialog dialog = new FileDialog(this, title, mode); //dialog - окно выбора файла
        dialog.setDirectory(System.getProperty("user.dir")); //user.dir - папка проекта
        dialog.setFile(fileName);  //"*.txt" - Windows покажет только текстовые файлы
        dialog.setVisible(true);   //окно модальное: программа ждет, пока его закроют
        if (dialog.getFile() == null)
            return null;
        return new File(dialog.getDirectory(), dialog.getFile());
    }

    //кнопка "Загрузить из файла"
    private void loadFromFile() {
        File file = chooseFile("Загрузить массив из файла", FileDialog.LOAD, "*.txt"); //file - выбранный файл
        if (file == null)
            return; //нажали "Отмена"

        //в окне можно вручную выбрать и не txt-файл - такие не принимаем
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            showMessage("Ошибка: можно загружать только файлы .txt", true);
            return;
        }

        try {
            String text = FileManager.readText(file.getPath());
            array.setFromText(text);
            arrayField.setText(array.toText()); //показываем загруженные числа и в поле ввода
            showArray();
            showMessage("Массив загружен из файла " + file.getName(), false);
        } catch (FileNotFoundException e) {
            showMessage("Ошибка: файл " + file.getName() + " не найден", true);
        } catch (IOException e) {
            //например, пустой файл
            showMessage("Ошибка чтения файла " + file.getName() + ": " + e.getMessage(), true);
        } catch (NumberFormatException e) {
            //в файле не числа
            showMessage("Ошибка в файле " + file.getName() + ": " + e.getMessage(), true);
        }
    }

    //кнопка "Построить ряд"
    private void buildFibonacci() {
        int length = (Integer) lengthSpinner.getValue(); //length - длина из счетчика (всегда 1..50)
        fib = new FibonacciSeries(length);

        fibModel.setRowCount(0);
        int count = fib.countToShow(FIB_PRINT); //count - сколько чисел показать (не больше 10)
        for (int i = 0; i < count; i++) {
            fibModel.addRow(new Object[]{i + 1, fib.getMarked(i)});
        }
        fibLabel.setText("Построен ряд из " + length + " чисел, показаны первые " + count);
        updateButtons();
        showMessage("Ряд Фибоначчи построен", false);
    }

    //кнопка "Сохранить результаты"
    private void saveToFile() {
        File file = chooseFile("Сохранить результаты", FileDialog.SAVE, "output.txt"); //output.txt - имя по умолчанию
        if (file == null)
            return; //нажали "Отмена"

        String path = file.getPath(); //path - путь к файлу
        //если расширение не написали - дописываем .txt
        if (!path.toLowerCase().endsWith(".txt"))
            path = path + ".txt";

        //собираем результаты, которые уже есть
        String text = ""; //text - то, что запишем в файл
        if (!array.isEmpty())
            text = text + array.resultsText();
        if (fib != null)
            text = text + fib.resultsText(FIB_PRINT);

        try {
            FileManager.writeText(path, text);
            showMessage("Результаты сохранены в файл " + new File(path).getName(), false);
        } catch (IOException e) {
            showMessage("Ошибка: не удалось записать файл (" + e.getMessage() + ")", true);
        }
    }
}
