import java.util.Scanner;

//класс текстового меню для работы с предприятием (группой сотрудников)
//все поля и методы static - объект класса Menu не создается
public class Menu {

    private static Scanner scanner = new Scanner(System.in); //scanner - чтение ввода с клавиатуры
    private static Enterprise enterprise;                    //enterprise - предприятие, с которым работает меню

    private static final String QUIT = "q";        //ввод для выхода из пункта без изменений
    private static final int CANCEL = -1;           //это значение возвращают методы чтения, если ввели q
    private static final int MAX_MONEY = 10000000;   //ограничение для денежных сумм (10 млн руб)
    private static final String TAB1 = "    ";     //отступ для вывода внутри пункта меню
    private static final String TAB2 = "        "; //отступ побольше - для ошибок и подсказок

    //главный цикл меню; предприятие передается из главного класса
    public static void run(Enterprise e) {
        enterprise = e;
        boolean working = true; //working - продолжать ли работу
        while (working) {
            printMenu();
            int choice = readMenuChoice(); //choice - номер выбранного пункта

            switch (choice) {
                case 1: addWorker(); break;
                case 2: addEngineer(); break;
                case 3: addAdministrator(); break;
                case 4: enterprise.printAll(); break;
                case 5: findStaff(); break;
                case 6: removeStaff(); break;
                case 7: enterprise.printSalaries(); break;
                case 0:
                    working = false;
                    System.out.println(TAB1 + "Программа завершена");
                    break;
                default:
                    System.out.println(TAB1 + "Ошибка: пункта " + choice + " нет в меню");
            }
        }
    }

    //выводит пункты меню
    private static void printMenu() {
        System.out.println();
        System.out.println("===== МЕНЮ (" + enterprise.getName() + ", сотрудников: " + enterprise.size() + ") =====");
        System.out.println("1. Добавить рабочего");
        System.out.println("2. Добавить инженера");
        System.out.println("3. Добавить сотрудника администрации");
        System.out.println("4. Показать всех сотрудников");
        System.out.println("5. Найти по табельному номеру");
        System.out.println("6. Удалить по табельному номеру");
        System.out.println("7. Рассчитать зарплату всех (общий метод)");
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
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                if (line.isEmpty())
                    System.out.println(TAB1 + "Ошибка: ничего не введено, введите номер пункта");
                else
                    System.out.println(TAB1 + "Ошибка: \"" + line + "\" - не номер пункта, введите число");
            }
        }
    }

    //читает целое число от min до max; если ввели q - возвращает CANCEL
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            String line = readLine(TAB1 + prompt + " (q - назад): ");
            if (isQuit(line))
                return CANCEL;
            try {
                int value = Integer.parseInt(line); //value - введенное число
                if (value >= min && value <= max)
                    return value;
                System.out.println(TAB2 + "Ошибка: нужно число от " + min + " до " + max);
            } catch (NumberFormatException e) {
                System.out.println(TAB2 + "Ошибка: \"" + line + "\" - не целое число");
            }
        }
    }

    //читает дробное число от min до max; если ввели q - возвращает CANCEL
    private static double readDouble(String prompt, int min, int max) {
        while (true) {
            String line = readLine(TAB1 + prompt + " (q - назад): ");
            if (isQuit(line))
                return CANCEL;
            try {
                //Java понимает дробь только через точку, поэтому запятую заменяем на точку (300,5 -> 300.5)
                double value = Double.parseDouble(line.replace(',', '.')); //value - введенное число
                //такая проверка заодно не пропустит "NaN" и "Infinity", которые Java тоже считает числами
                if (value >= min && value <= max)
                    return value;
                System.out.println(TAB2 + "Ошибка: нужно число от " + min + " до " + max);
            } catch (NumberFormatException e) {
                System.out.println(TAB2 + "Ошибка: \"" + line + "\" - не число");
            }
        }
    }

    //читает текст из букв (ФИО, должность); если ввели q - возвращает null
    private static String readWords(String prompt) {
        while (true) {
            String line = readLine(TAB1 + prompt + " (q - назад): ");
            if (isQuit(line))
                return null;
            if (line.isEmpty()) {
                System.out.println(TAB2 + "Ошибка: ничего не введено");
                continue;
            }

            //проверяем каждый символ: разрешены буквы, пробел, дефис и точка (например "Иванов И.И.")
            boolean correct = true; //correct - все ли символы подходят
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i); //c - очередной символ
                if (!Character.isLetter(c) && c != ' ' && c != '-' && c != '.')
                    correct = false;
            }
            if (correct)
                return line;
            System.out.println(TAB2 + "Ошибка: можно использовать только буквы, пробел, дефис и точку");
        }
    }

    //читает новый табельный номер, которого еще нет на предприятии; если ввели q - возвращает CANCEL
    private static int readNewId() {
        while (true) {
            int id = readInt("Табельный номер от 1 до 99999", 1, 99999); //id - табельный номер
            if (id == CANCEL)
                return CANCEL;
            if (enterprise.find(id) == null)
                return id;
            System.out.println(TAB2 + "Ошибка: сотрудник с номером " + id + " уже есть");
        }
    }

    //пункт 1: добавить рабочего (на любом шаге можно ввести q и отменить добавление)
    private static void addWorker() {
        int id = readNewId();
        if (id == CANCEL) return;
        String name = readWords("ФИО");                                   //name - ФИО
        if (name == null) return;
        double rate = readDouble("Ставка за час, руб", 1, MAX_MONEY);     //rate - ставка за час
        if (rate == CANCEL) return;
        int hours = readInt("Отработано часов за месяц от 1 до 744", 1, 744); //hours - часы (744 = 31 день * 24 ч)
        if (hours == CANCEL) return;

        enterprise.add(new Worker(id, name, rate, hours));
        System.out.println(TAB1 + "Рабочий добавлен");
    }

    //пункт 2: добавить инженера
    private static void addEngineer() {
        int id = readNewId();
        if (id == CANCEL) return;
        String name = readWords("ФИО");
        if (name == null) return;
        double salary = readDouble("Оклад, руб", 1, MAX_MONEY);    //salary - оклад
        if (salary == CANCEL) return;
        int category = readInt("Категория от 1 до 3", 1, 3);      //category - категория
        if (category == CANCEL) return;

        enterprise.add(new Engineer(id, name, salary, category));
        System.out.println(TAB1 + "Инженер добавлен");
    }

    //пункт 3: добавить сотрудника администрации
    private static void addAdministrator() {
        int id = readNewId();
        if (id == CANCEL) return;
        String name = readWords("ФИО");
        if (name == null) return;
        String post = readWords("Должность");                       //post - должность
        if (post == null) return;
        double salary = readDouble("Оклад, руб", 1, MAX_MONEY);
        if (salary == CANCEL) return;
        double bonus = readDouble("Премия от 0 до 100 %", 0, 100);  //bonus - премия в процентах
        if (bonus == CANCEL) return;

        enterprise.add(new Administrator(id, name, post, salary, bonus));
        System.out.println(TAB1 + "Сотрудник администрации добавлен");
    }

    //пункт 5: поиск сотрудника по табельному номеру
    private static void findStaff() {
        if (enterprise.size() == 0) {
            System.out.println(TAB1 + "На предприятии нет сотрудников");
            return;
        }
        int id = readInt("Табельный номер", 1, 99999);
        if (id == CANCEL) return;

        Staff staff = enterprise.find(id); //staff - найденный сотрудник (null - не найден)
        if (staff == null)
            System.out.println(TAB1 + "Сотрудник с номером " + id + " не найден");
        else
            System.out.println(TAB1 + "Найден: " + staff.getInfo());
    }

    //пункт 6: удаление сотрудника по табельному номеру
    private static void removeStaff() {
        if (enterprise.size() == 0) {
            System.out.println(TAB1 + "На предприятии нет сотрудников");
            return;
        }
        int id = readInt("Табельный номер", 1, 99999);
        if (id == CANCEL) return;

        if (enterprise.remove(id))
            System.out.println(TAB1 + "Сотрудник с номером " + id + " удален");
        else
            System.out.println(TAB1 + "Сотрудник с номером " + id + " не найден");
    }
}
