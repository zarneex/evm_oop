import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//класс текстового меню: показывает пункты, читает ввод пользователя и вызывает методы объектов
//все поля и методы static - объект класса Menu не создается, меню одно на всю программу
public class Menu {

    private static Scanner scanner = new Scanner(System.in); //scanner - чтение ввода с клавиатуры
    private static NumberArray array = new NumberArray();    //array - массив чисел (сначала пустой)
    private static FibonacciSeries fib = null;               //fib - ряд Фибоначчи (null - еще не построен)

    private static final int FIB_PRINT = 10;          //сколько первых чисел ряда выводить (по условию 10)
    private static final String QUIT = "q";           //ввод для выхода из пункта без изменений
    private static final String TAB1 = "    ";        //отступ для вывода внутри пункта меню
    private static final String TAB2 = "        ";    //отступ побольше - для ошибок и подсказок

    //главный цикл меню: показываем пункты, пока пользователь не выберет выход
    public static void run() {
        boolean working = true; //working - продолжать ли работу
        while (working) {
            printMenu();
            int choice = readMenuChoice(); //choice - номер выбранного пункта

            //switch выбирает действие по номеру пункта
            switch (choice) {
                case 1: inputFromKeyboard(); break;
                case 2: loadFromFile(); break;
                case 3: showArray(); break;
                case 4: checkOrder(); break;
                case 5: findMin(); break;
                case 6: fibonacci(); break;
                case 7: saveToFile(); break;
                case 0:
                    working = false;
                    System.out.println(TAB1 + "Программа завершена");
                    break;
                default:
                    //любое другое число - такого пункта нет
                    System.out.println(TAB1 + "Ошибка: пункта " + choice + " нет в меню");
            }
        }
    }

    //выводит пункты меню
    private static void printMenu() {
        System.out.println();
        System.out.println("===== МЕНЮ =====");
        System.out.println("1. Ввести массив с клавиатуры");
        System.out.println("2. Загрузить массив из файла");
        System.out.println("3. Показать массив");
        System.out.println("4. Проверить упорядоченность массива (a)");
        System.out.println("5. Найти минимальный элемент (c)");
        System.out.println("6. Ряд Фибоначчи: ввести длину, вывести первые " + FIB_PRINT + " (b)");
        System.out.println("7. Сохранить результаты в файл");
        System.out.println("0. Выход");
    }

    //читает строку с клавиатуры
    private static String readLine(String prompt) {
        System.out.print(prompt);
        //если ввод закрыт (например нажали Ctrl+D), читать больше нечего - завершаем программу
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Ввод закрыт, программа завершена");
            System.exit(0);
        }
        return scanner.nextLine().trim(); //trim убирает пробелы по краям
    }

    //проверяет, ввел ли пользователь q (выход из пункта); регистр не важен - подойдет и Q
    private static boolean isQuit(String line) {
        if (line.equalsIgnoreCase(QUIT)) {
            System.out.println(TAB1 + "Отмена, данные не изменены");
            return true;
        }
        return false;
    }

    //читает номер пункта меню, пока не введут целое число
    private static int readMenuChoice() {
        while (true) {
            String line = readLine("Ваш выбор: "); //line - то, что ввел пользователь
            try {
                return Integer.parseInt(line); //если это не число - будет NumberFormatException
            } catch (NumberFormatException e) {
                if (line.isEmpty())
                    System.out.println(TAB1 + "Ошибка: ничего не введено, введите номер пункта");
                else
                    System.out.println(TAB1 + "Ошибка: \"" + line + "\" - не номер пункта, введите число");
            }
        }
    }

    //переводит строку с числами через пробел в массив; при ошибке выводит сообщение и возвращает null
    private static int[] parseNumbers(String text) {
        if (text.isEmpty()) {
            System.out.println(TAB2 + "Ошибка: числа не введены");
            return null;
        }

        String[] parts = text.split("\\s+"); //parts - отдельные числа ("\\s+" - один или несколько пробелов)
        int[] numbers = new int[parts.length]; //numbers - получившийся массив

        //переводим каждую часть в число
        for (int i = 0; i < parts.length; i++) {
            try {
                numbers[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                //не число или слишком большое число для int
                System.out.println(TAB2 + "Ошибка: \"" + parts[i] + "\" - не целое число (или слишком большое)");
                System.out.println(TAB2 + "Числа нужно разделять пробелами, например: 7 3 15 -4");
                return null;
            }
        }
        return numbers;
    }

    //спрашивает имя txt-файла; возвращает null, если пользователь ввел q
    private static String readFileName(String defaultName) {
        while (true) {
            String name = readLine(TAB1 + "Имя файла (Enter - " + defaultName + ", q - назад): ");

            //ровно "q" - выход; а "q.txt" - это уже обычное имя файла
            if (isQuit(name))
                return null;

            //ничего не ввели - берем имя по умолчанию
            if (name.isEmpty())
                return defaultName;

            //имя уже заканчивается на .txt - берем как есть
            if (name.endsWith(".txt"))
                return name;

            //точки в имени нет - значит расширение не написали, дописываем .txt сами
            if (!name.contains("."))
                return name + ".txt";

            //есть точка, но расширение другое - программа работает только с файлами .txt
            System.out.println(TAB2 + "Ошибка: файл \"" + name + "\" не .txt, поддерживаются только текстовые файлы .txt");
        }
    }

    //пункт 1: ввод массива с клавиатуры (спрашиваем, пока не введут правильно или q)
    private static void inputFromKeyboard() {
        while (true) {
            String line = readLine(TAB1 + "Введите целые числа через пробел (q - назад): ");
            if (isQuit(line))
                return;

            int[] numbers = parseNumbers(line);
            //numbers == null - была ошибка, спрашиваем еще раз
            if (numbers != null) {
                array.setNumbers(numbers);
                System.out.println(TAB1 + "Массив сохранен: " + array.toText());
                return;
            }
        }
    }

    //пункт 2: загрузка массива из файла (спрашиваем имя, пока файл не прочитается или q)
    private static void loadFromFile() {
        while (true) {
            String fileName = readFileName("input.txt");
            if (fileName == null)
                return; //пользователь ввел q

            try {
                String text = FileManager.readText(fileName); //text - содержимое файла
                int[] numbers = parseNumbers(text);
                if (numbers != null) {
                    array.setNumbers(numbers);
                    System.out.println(TAB1 + "Массив загружен из файла " + fileName + ": " + array.toText());
                    return;
                }
                //в файле не числа - сообщение уже выведено, спрашиваем другой файл
            } catch (FileNotFoundException e) {
                //файла нет (или это папка)
                System.out.println(TAB2 + "Ошибка: файл \"" + fileName + "\" не найден");
            } catch (IOException e) {
                //любая другая ошибка чтения, например пустой файл
                System.out.println(TAB2 + "Ошибка чтения файла \"" + fileName + "\": " + e.getMessage());
            }
        }
    }

    //проверяет, задан ли массив; если нет - выводит подсказку
    private static boolean arrayReady() {
        if (array.isEmpty()) {
            System.out.println(TAB1 + "Массив еще не задан - сначала выберите пункт 1 или 2");
            return false;
        }
        return true;
    }

    //пункт 3: показать массив
    private static void showArray() {
        if (arrayReady())
            System.out.println(TAB1 + "Массив: " + array.toText());
    }

    //пункт 4: подзадача a
    private static void checkOrder() {
        if (arrayReady())
            System.out.println(TAB1 + "Порядок: " + array.checkOrder());
    }

    //пункт 5: подзадача c
    private static void findMin() {
        if (arrayReady())
            System.out.println(TAB1 + "Минимальный элемент: " + array.findMin());
    }

    //пункт 6: подзадача b (спрашиваем длину, пока не введут число от 1 до 50 или q)
    private static void fibonacci() {
        while (true) {
            String line = readLine(TAB1 + "Длина ряда от 1 до 50 (q - назад): ");
            if (isQuit(line))
                return;

            try {
                int length = Integer.parseInt(line); //length - длина ряда
                //по условию длина не больше 50
                if (length >= 1 && length <= 50) {
                    fib = new FibonacciSeries(length);
                    System.out.println(TAB1 + fibText());
                    return;
                }
                System.out.println(TAB2 + "Ошибка: длина должна быть от 1 до 50");
            } catch (NumberFormatException e) {
                System.out.println(TAB2 + "Ошибка: \"" + line + "\" - не целое число");
            }
        }
    }

    //текст про ряд Фибоначчи: если ряд короче 10 чисел, выводим сколько есть
    private static String fibText() {
        int count = Math.min(FIB_PRINT, fib.getLength()); //count - сколько чисел выводим (меньшее из двух)
        return "Ряд Фибоначчи из " + fib.getLength() + " чисел, первые " + count
                + " (* - четные): " + fib.firstToText(count);
    }

    //пункт 7: сохранение результатов в файл (спрашиваем имя, пока запись не получится или q)
    private static void saveToFile() {
        //если нет ни массива, ни ряда - сохранять нечего
        if (array.isEmpty() && fib == null) {
            System.out.println(TAB1 + "Сохранять нечего - задайте массив (пункт 1 или 2) или постройте ряд (пункт 6)");
            return;
        }

        //собираем текст результатов
        String text = ""; //text - то, что запишем в файл
        if (!array.isEmpty()) {
            text = text + "Массив: " + array.toText() + "\n";
            text = text + "Порядок: " + array.checkOrder() + "\n";
            text = text + "Минимальный элемент: " + array.findMin() + "\n";
        }
        if (fib != null)
            text = text + fibText() + "\n";

        while (true) {
            String fileName = readFileName("output.txt");
            if (fileName == null)
                return; //пользователь ввел q

            try {
                FileManager.writeText(fileName, text);
                System.out.println(TAB1 + "Результаты сохранены в файл " + fileName);
                return;
            } catch (IOException e) {
                //например недопустимые символы в имени файла
                System.out.println(TAB2 + "Ошибка: не удалось записать файл \"" + fileName + "\" (" + e.getMessage() + ")");
            }
        }
    }
}
