//лабораторная работа 2, вариант 3 (задания из лабораторной работы 1)
//a. Дан массив чисел. Необходимо определить упорядочен ли он.
//b. Сгенерируйте числовой ряд длиной не более 50, представляющий собой числа Фибоначчи
//   (каждый последующий элемент представляют суммы двух предыдущих, первые два элемента равны 1).
//   Выведите на печать первые 10, отмечая четные числа каким-нибудь символом.
//c. Найдите минимальный элемент в массиве.
//
//классы: NumberArray - массив чисел (подзадачи a и c), FibonacciSeries - ряд Фибоначчи (подзадача b)

//главный класс: создает объекты и вызывает их методы
public class Main {

    //печатает массив и результаты подзадач a и c для одного объекта
    private static void showResults(NumberArray array) {
        //в пустом массиве нет минимума (findMin упал бы с ошибкой), поэтому сначала проверяем
        if (array.isEmpty()) {
            System.out.println("Массив пустой, проверять нечего");
        } else {
            array.print();
            System.out.println("Порядок: " + array.checkOrder());              //подзадача a
            System.out.println("Минимальный элемент: " + array.findMin());     //подзадача c
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //массивы для проверки
        int[] numbers1 = {7, 3, 15, -4, 0, 9, 12}; //не упорядочен
        int[] numbers2 = {-5, 0, 2, 2, 8, 14};     //по возрастанию
        int[] numbers3 = {20, 11, 6, 6, 1, -3};    //по убыванию
        int[] numbers4 = {5};                      //всего один элемент
        int[] numbers5 = {4, 4, 4};                //все элементы одинаковые
        int[] numbers6 = {-1, -8, -3};             //только отрицательные, не упорядочен

        //объект создается конструктором с аргументом - массив задается сразу
        NumberArray array1 = new NumberArray(numbers1); //array1 - первый объект "массив чисел"
        showResults(array1);

        //объект создается конструктором без аргументов, массив передаем методом setNumbers
        NumberArray array2 = new NumberArray(); //array2 - второй объект, пока пустой
        showResults(array2); //проверка пустого массива
        array2.setNumbers(numbers2);
        showResults(array2);

        //тот же объект array2 используем дальше, просто меняем ему массив
        array2.setNumbers(numbers3);
        showResults(array2);

        array2.setNumbers(numbers4);
        showResults(array2);

        array2.setNumbers(numbers5);
        showResults(array2);

        array2.setNumbers(numbers6);
        showResults(array2);

        //ряд Фибоначчи конструктором без аргументов - 50 чисел
        FibonacciSeries fib1 = new FibonacciSeries(); //fib1 - объект "ряд Фибоначчи"
        System.out.println("Ряд из " + fib1.getLength() + " чисел, первые 10 (* - четные):");
        fib1.print(10); //подзадача b

        //ряд конструктором с аргументом - всего 7 чисел, поэтому напечатается только 7
        FibonacciSeries fib2 = new FibonacciSeries(7); //fib2 - второй ряд
        System.out.println("Ряд из " + fib2.getLength() + " чисел, первые 10 (* - четные):");
        fib2.print(10);

        //пробуем задать длину больше 50 - класс сам ограничит ее до 50
        fib2.setLength(100);
        System.out.println("После setLength(100) длина ряда: " + fib2.getLength());
    }
}
