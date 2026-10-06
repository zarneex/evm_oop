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

    //проверяет, пустой ли массив (true - пустой, в нем нет ни одного числа)
    public boolean isEmpty() {
        return numbers.length == 0;
    }

    //печатает массив в одну строку
    public void print() {
        System.out.print("Массив: ");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
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
