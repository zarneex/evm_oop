//класс "рабочий" - наследник Staff (extends), зарплата зависит от отработанных часов
public class Worker extends Staff {

    private double hourRate; //hourRate - ставка за час, руб
    private int hours;       //hours - отработано часов за месяц

    public Worker(int id, String name, double hourRate, int hours) {
        super(id, name); //super - вызов конструктора родителя (Staff), он запишет id и name
        this.hourRate = hourRate;
        this.hours = hours;
    }

    public double getHourRate() {
        return hourRate;
    }

    public void setHourRate(double hourRate) {
        this.hourRate = hourRate;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    //@Override - метод родителя переопределяется: у рабочего зарплата = ставка * часы
    @Override
    public double calcSalary() {
        return hourRate * hours;
    }

    @Override
    public String getInfo() {
        //super.getInfo() - берем общую часть из родителя и дописываем свое
        //String.format("%.2f", x) - число с двумя знаками после запятой
        return "Рабочий: " + super.getInfo() + ", ставка " + String.format("%.2f", hourRate) + " руб/ч, "
                + hours + " ч";
    }
}
