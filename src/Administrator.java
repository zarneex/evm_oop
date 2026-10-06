//класс "администрация" (сотрудник администрации) - наследник Staff,
//зарплата = оклад + премия в процентах от оклада
public class Administrator extends Staff {

    private String post;         //post - должность (директор, бухгалтер и т.д.)
    private double salary;       //salary - оклад, руб
    private double bonusPercent; //bonusPercent - премия, % от оклада

    public Administrator(int id, String name, String post, double salary, double bonusPercent) {
        super(id, name);
        this.post = post;
        this.salary = salary;
        this.bonusPercent = bonusPercent;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonusPercent() {
        return bonusPercent;
    }

    public void setBonusPercent(double bonusPercent) {
        this.bonusPercent = bonusPercent;
    }

    //у администрации зарплата = оклад + оклад * процент / 100
    @Override
    public double calcSalary() {
        return salary + salary * bonusPercent / 100;
    }

    @Override
    public String getInfo() {
        return "Администрация: " + super.getInfo() + ", " + post + ", оклад " + String.format("%.2f", salary)
                + " руб, премия " + String.format("%.1f", bonusPercent) + "%";
    }
}
