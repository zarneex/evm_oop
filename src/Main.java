//лабораторная работа 1, вариант 3
//a. Дан массив чисел. Необходимо определить упорядочен ли он.
//b. Сгенерируйте числовой ряд длиной не более 50, представляющий собой числа Фибоначчи
//   (каждый последующий элемент представляют суммы двух предыдущих, первые два элемента равны 1).
//   Выведите на печать первые 10, отмечая четные числа каким-нибудь символом.
//c. Найдите минимальный элемент в массиве.

public class Main {

//подзадача a: печатает массив и проверяет, упорядочен ли он (по возрастанию или по убыванию)
    public static void checkOrder(int[] arr) {
        //сначала печатаем сам массив
        System.out.print("Массив: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        boolean up = true;   //up - упорядочен по возрастанию
        boolean down = true; //down - упорядочен по убыванию

        //сравниваем каждый элемент со следующим
        for (int i = 0; i < arr.length - 1; i++) {
            //следующий меньше текущего - значит не по возрастанию
            if (arr[i] > arr[i + 1])
                up = false;
            //следующий больше текущего - значит не по убыванию
            if (arr[i] < arr[i + 1])
                down = false;
        }

        //смотрим, какой порядок остался
        if (up)
            System.out.println("Порядок: по возрастанию");
        else if (down)
            System.out.println("Порядок: по убыванию");
        else
            System.out.println("Порядок: не упорядочен");
    }

//подзадача b: ряд Фибоначчи из 50 чисел, печатаем первые 10, четные помечаем *
    public static void printFibonacci() {
        long[] fib = new long[50]; //fib - массив для чисел ряда, long т.к. большие числа не влезут в int
        fib[0] = 1; //первые два числа равны 1
        fib[1] = 1;

        //каждое следующее число = сумма двух предыдущих
        for (int i = 2; i < fib.length; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }

        //выводим первые 10 чисел
        for (int i = 0; i < 10; i++) {
            //четное число делится на 2 без остатка
            if (fib[i] % 2 == 0)
                System.out.print(fib[i] + "* ");
            else
                System.out.print(fib[i] + " ");
        }
        System.out.println();
    }

//подзадача c: поиск минимального элемента массива
    public static int findMin(int[] arr) {
        int min = arr[0]; //min - минимум, сначала считаем им первый элемент

        //проходим по остальным элементам
        for (int i = 1; i < arr.length; i++) {
            //нашли число меньше - теперь оно минимум
            if (arr[i] < min)
                min = arr[i];
        }
        return min;
    }

    public static void main(String[] args) {
        //массивы для проверки
        int[] numbers1 = {7, 3, 15, -4, 0, 9, 12}; //numbers - числа, этот не упорядочен
        int[] numbers2 = {-5, 0, 2, 2, 8, 14};     //этот по возрастанию
        int[] numbers3 = {20, 11, 6, 6, 1, -3};    //этот по убыванию
        int[] numbers4 = {5};                      //всего один элемент
        int[] numbers5 = {4, 4, 4};                //все элементы одинаковые
        int[] numbers6 = {-1, -8, -3};             //только отрицательные, не упорядочен

        checkOrder(numbers1); //подзадача a - печатает массив и его порядок
        System.out.println("Минимальный элемент: " + findMin(numbers1)); //подзадача c - поиск минимума
        System.out.println();

        checkOrder(numbers2); //подзадача a
        System.out.println("Минимальный элемент: " + findMin(numbers2)); //подзадача c
        System.out.println();

        checkOrder(numbers3); //подзадача a
        System.out.println("Минимальный элемент: " + findMin(numbers3)); //подзадача c
        System.out.println();

        checkOrder(numbers4); //подзадача a
        System.out.println("Минимальный элемент: " + findMin(numbers4)); //подзадача c
        System.out.println();

        checkOrder(numbers5); //подзадача a
        System.out.println("Минимальный элемент: " + findMin(numbers5)); //подзадача c
        System.out.println();

        checkOrder(numbers6); //подзадача a
        System.out.println("Минимальный элемент: " + findMin(numbers6)); //подзадача c
        System.out.println();

        System.out.println("Первые 10 чисел Фибоначчи (* - четные):");
        printFibonacci(); //подзадача b - строит ряд и сама печатает первые 10 чисел
    }
}
