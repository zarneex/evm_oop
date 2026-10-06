import java.util.ArrayList;

//класс-"группа" - предприятие, в котором работают сотрудники (объекты Worker, Engineer, Administrator)
//все они хранятся в одном списке типа Staff - это возможно, т.к. они наследники Staff
public class Enterprise {

    private String name;                                 //name - название предприятия
    private ArrayList<Staff> staffList = new ArrayList<>(); //staffList - список сотрудников (коллекция, сама растет)

    private static final String TAB = "    "; //отступ при выводе (как в меню)

    public Enterprise(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //возвращает количество сотрудников
    public int size() {
        return staffList.size();
    }

    //ищет сотрудника по табельному номеру; если не нашли - возвращает null
    public Staff find(int id) {
        //проходим по всем сотрудникам списка
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getId() == id)
                return staffList.get(i);
        }
        return null;
    }

    //добавляет сотрудника; если такой табельный номер уже есть - не добавляет и возвращает false
    public boolean add(Staff staff) {
        if (find(staff.getId()) != null)
            return false;
        staffList.add(staff);
        return true;
    }

    //удаляет сотрудника по табельному номеру; если не нашли - возвращает false
    public boolean remove(int id) {
        Staff staff = find(id); //staff - найденный сотрудник
        if (staff == null)
            return false;
        staffList.remove(staff);
        return true;
    }

    //выводит на экран всех сотрудников
    public void printAll() {
        if (staffList.isEmpty()) {
            System.out.println(TAB + "На предприятии нет сотрудников");
            return;
        }
        System.out.println(TAB + "Сотрудники предприятия \"" + name + "\" (" + staffList.size() + "):");
        for (int i = 0; i < staffList.size(); i++) {
            //getInfo вызывается у каждого объекта свой - рабочего, инженера или администрации
            System.out.println(TAB + (i + 1) + ". " + staffList.get(i).getInfo());
        }
    }

    //вызывает общий метод calcSalary() для каждого сотрудника и выводит зарплаты и общую сумму
    public void printSalaries() {
        if (staffList.isEmpty()) {
            System.out.println(TAB + "На предприятии нет сотрудников");
            return;
        }
        double total = 0; //total - сумма всех зарплат
        for (int i = 0; i < staffList.size(); i++) {
            Staff staff = staffList.get(i); //staff - очередной сотрудник
            //полиморфизм: метод один и тот же, а расчет у рабочего, инженера и администрации разный
            double salary = staff.calcSalary(); //salary - зарплата этого сотрудника
            total = total + salary;
            System.out.println(TAB + staff.getName() + " (таб. № " + staff.getId() + "): "
                    + String.format("%.2f", salary) + " руб");
        }
        System.out.println(TAB + "Всего к выплате: " + String.format("%.2f", total) + " руб");
    }
}
