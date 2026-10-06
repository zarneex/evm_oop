//абстрактный класс "кадры" - общий родитель для всех сотрудников предприятия
//abstract - объект этого класса создать нельзя, только объекты наследников
public abstract class Staff {

    private int id;      //id - табельный номер (у каждого сотрудника свой)
    private String name; //name - ФИО сотрудника

    //конструктор - вызывается из конструкторов наследников через super(...)
    public Staff(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //методы доступа к полям (get - получить, set - изменить)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //абстрактный метод - расчет зарплаты; у каждого наследника он считается по-своему
    public abstract double calcSalary();

    //общая информация о сотруднике; наследники дополняют ее своими полями (переопределяют метод)
    public String getInfo() {
        return "таб. № " + id + ", " + name;
    }
}
