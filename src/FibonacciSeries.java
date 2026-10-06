//класс "ряд Фибоначчи": хранит числа ряда и умеет печатать первые из них,
//отмечая четные (подзадача b)
public class FibonacciSeries {

    private long[] series; //series - числа ряда, long т.к. большие числа не влезут в int

    //конструктор без аргументов - ряд максимальной длины (50 чисел)
    public FibonacciSeries() {
        setLength(50);
    }

    //конструктор с аргументом - ряд заданной длины
    public FibonacciSeries(int length) {
        setLength(length);
    }

    //метод для задания длины ряда, после него ряд строится заново
    public void setLength(int length) {
        //по условию длина ряда не больше 50
        if (length > 50)
            length = 50;
        //0 или отрицательная длина не имеет смысла (массив с отрицательной длиной создать нельзя - будет ошибка)
        if (length < 1)
            length = 1;

        series = new long[length];
        generate();
    }

    //возвращает длину ряда
    public int getLength() {
        return series.length;
    }

    //заполняет массив числами Фибоначчи
    //private - этот метод нужен только внутри класса, снаружи его вызвать нельзя
    private void generate() {
        for (int i = 0; i < series.length; i++) {
            //первые два числа равны 1, каждое следующее = сумма двух предыдущих
            if (i < 2)
                series[i] = 1;
            else
                series[i] = series[i - 1] + series[i - 2];
        }
    }

    //подзадача b: печатает первые count чисел ряда, четные помечает *
    public void print(int count) {
        //если просят больше чисел, чем есть в ряду - печатаем сколько есть
        if (count > series.length)
            count = series.length;

        for (int i = 0; i < count; i++) {
            //четное число делится на 2 без остатка
            if (series[i] % 2 == 0)
                System.out.print(series[i] + "* ");
            else
                System.out.print(series[i] + " ");
        }
        System.out.println();
    }
}
