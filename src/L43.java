//лабораторная работа 4, вариант 3: рабочий, кадры, инженер, администрация
//
//иерархия: Staff (кадры, абстрактный) -> Worker (рабочий), Engineer (инженер), Administrator (администрация)
//класс-группа: Enterprise (предприятие), меню: Menu

//главный класс: создает предприятие, добавляет первых сотрудников и запускает меню
public class L43 {

    public static void main(String[] args) {
        Enterprise enterprise = new Enterprise("Завод"); //enterprise - предприятие (группа сотрудников)

        //добавляем по одному сотруднику каждого вида
        //переменная типа Staff может хранить объект любого наследника
        Staff worker = new Worker(101, "Петров П.П.", 350, 160);                       //рабочий
        Staff engineer = new Engineer(201, "Сидорова А.В.", 60000, 2);                  //инженер
        Staff admin = new Administrator(301, "Иванов И.И.", "директор", 120000, 25);    //администрация
        enterprise.add(worker);
        enterprise.add(engineer);
        enterprise.add(admin);

        System.out.println("Лабораторная работа 4, вариант 3");
        Menu.run(enterprise);
    }
}
