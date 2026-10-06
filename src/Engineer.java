//класс "инженер" - наследник Staff, зарплата = оклад + надбавка за категорию
public class Engineer extends Staff {

    private double salary; //salary - оклад, руб
    private int category;  //category - категория инженера (1, 2 или 3)

    private static final double CATEGORY_BONUS = 5000; //надбавка за одну категорию, руб

    public Engineer(int id, String name, double salary, int category) {
        super(id, name);
        this.salary = salary;
        this.category = category;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    //у инженера зарплата = оклад + категория * 5000
    @Override
    public double calcSalary() {
        return salary + category * CATEGORY_BONUS;
    }

    @Override
    public String getInfo() {
        return "Инженер: " + super.getInfo() + ", оклад " + String.format("%.2f", salary) + " руб, категория "
                + category;
    }
}
