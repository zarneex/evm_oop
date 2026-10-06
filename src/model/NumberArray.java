package model;

//класс "массив чисел": хранит массив и умеет проверять его порядок (подзадача a)
//и искать минимальный элемент (подзадача c)
public class NumberArray {

    private int[] numbers; //numbers - числа массива, private - поле видно только внутри класса

    //конструктор без аргументов - создает пустой массив, числа потом задаются через setNumbers
    public NumberArray() {
        numbers = new int[0];
    }

    //конструктор с аргументом - сразу задает массив
    public NumberArray(int[] numbers) {
        this.numbers = numbers; //this.numbers - поле класса, numbers - параметр конструктора
    }

    //метод для передачи нового массива в уже созданный объект
    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    //разбирает строку с числами через пробел и сохраняет их как массив
    //при ошибке возбуждает NumberFormatException с понятным сообщением, старый массив не меняется
    //(этот разбор раньше был в меню лабы 3, в лабе 5 перенесен сюда, чтобы окно не содержало логики)
    public void setFromText(String text) {
        text = text.trim();
        if (text.isEmpty())
            throw new NumberFormatException("числа не введены");

        String[] parts = text.split("\\s+"); //parts - отдельные числа ("\\s+" - один или несколько пробелов)
        int[] result = new int[parts.length]; //result - новый массив
        for (int i = 0; i < parts.length; i++) {
            try {
                result[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                //заменяем стандартное сообщение Java на понятное
                throw new NumberFormatException("\"" + parts[i] + "\" - не целое число (или слишком большое)");
            }
        }
        numbers = result; //ошибок не было - запоминаем новый массив
    }

    //проверяет, пустой ли массив (true - пустой, в нем нет ни одного числа)
    public boolean isEmpty() {
        return numbers.length == 0;
    }

    //количество элементов (нужно окну, чтобы заполнить таблицу)
    public int size() {
        return numbers.length;
    }

    //элемент с номером i
    public int get(int i) {
        return numbers[i];
    }

    //результаты подзадач a и c одним текстом (для сохранения в файл)
    public String resultsText() {
        return "Массив: " + toText() + "\n"
                + "Порядок: " + checkOrder() + "\n"
                + "Минимальный элемент: " + findMin() + "\n";
    }

    //возвращает массив в виде строки (числа через пробел), чтобы его можно было
    //и вывести на экран, и записать в файл
    public String toText() {
        String text = ""; //text - сюда собираем числа
        for (int i = 0; i < numbers.length; i++) {
            text = text + numbers[i] + " ";
        }
        return text.trim(); //trim убирает лишний пробел в конце
    }

    //подзадача a: проверяет, упорядочен ли массив (по возрастанию или по убыванию)
    public String checkOrder() {
        boolean up = true;   //up - упорядочен по возрастанию
        boolean down = true; //down - упорядочен по убыванию

        //сравниваем каждый элемент со следующим
        for (int i = 0; i < numbers.length - 1; i++) {
            //следующий меньше текущего - значит не по возрастанию
            if (numbers[i] > numbers[i + 1])
                up = false;
            //следующий больше текущего - значит не по убыванию
            if (numbers[i] < numbers[i + 1])
                down = false;
        }

        //смотрим, какой порядок остался
        if (up)
            return "по возрастанию";
        else if (down)
            return "по убыванию";
        else
            return "не упорядочен";
    }

    //подзадача c: ищет минимальный элемент массива
    public int findMin() {
        int min = numbers[0]; //min - минимум, сначала считаем им первый элемент

        //проходим по остальным элементам
        for (int i = 1; i < numbers.length; i++) {
            //нашли число меньше - теперь оно минимум
            if (numbers[i] < min)
                min = numbers[i];
        }
        return min;
    }
}
