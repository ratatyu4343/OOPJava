import apartment.*;
import electricalappliance.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myInput = new Scanner( System.in );
        //створення нової квартири
        Apartment apartment = new Apartment();
        //ініціалізація параметрів через файл
        apartment.getInitialization("src/initialization.txt");
        //меню
        int n = 0;
        try {
            while (n != 8)
            {
                System.out.print("1 - Список електроприборів (відсортований)\n" +
                                 "2 - Знайти електроприбор\n" +
                                 "3 - Додати електроприбор\n" +
                                 "4 - Видалити електроприбор\n" +
                                 "5 - Отримати показники лічильника\n" +
                                 "6 - Увімкнути пристрій в розетку\n" +
                                 "7 - Вимкнути пристрій з розетки\n" +
                                 "8 - Вийти\n>>>");
                switch (n = myInput.nextInt()){
                    case 1:
                        List<ElectricalAppliance> list =  apartment.sortAppliances();
                        System.out.println("Назва\tПотужність(Ват/с)");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i).getName()+"\t"+list.get(i).getPowerAll()
                                    +"\t"+(apartment.isWork(list.get(i))?"Працює":"Не працює"));
                        }
                        break;
                    case 2:
                        System.out.print("Назва: ");
                        myInput.nextLine();
                        String name = myInput.nextLine();
                        System.out.print("Потужність: ");
                        float power = myInput.nextFloat();
                        ElectricalAppliance appliance = apartment.getAppliance(name, power);
                        if(appliance != null) {
                            System.out.println(appliance.getName()+"\t"+ appliance.getPowerAll()
                                    +"\t"+(apartment.isWork(appliance)?"Працює":"Не працює"));
                        }else {
                            System.out.println("Такого прибору не існує...");
                        }
                        break;
                    case 3:
                        System.out.print("Назва: ");
                        myInput.nextLine();
                        String name2 = myInput.nextLine();
                        System.out.print("Потужність: ");
                        float power2 = myInput.nextFloat();
                        System.out.print("1 - Постійний\n" +
                                         "2 - З таймером\n" +
                                         "3 - З батареєю\n>>>");
                        int type = myInput.nextInt();
                        switch (type){
                            case 1:
                                apartment.addAppliance(new ConstantlyAppliance(name2, power2));
                                break;
                            case 2:
                                System.out.print("Таймер: ");
                                int timer = myInput.nextInt();
                                apartment.addAppliance(new EconomyAppliance(name2, power2, timer));
                                break;
                            case 3:
                                System.out.print("Батарея: ");
                                int battery = myInput.nextInt();
                                apartment.addAppliance(new EconomyAppliance(name2, power2, battery));
                                break;
                            default:
                                break;
                        }
                        break;
                    case 4:
                        System.out.print("Назва: ");
                        myInput.nextLine();
                        String name3 = myInput.nextLine();
                        System.out.print("Потужність: ");
                        float power3 = myInput.nextFloat();
                        apartment.deleteAppliance(apartment.getAppliance(name3, power3));
                        break;
                    case 5:
                        float [] arr = apartment.getCounterReadings();
                        System.out.println("Поточний показник: "+arr[1]+" +("+arr[0]+")");
                        break;
                    case 6:
                        System.out.print("Назва: ");
                        myInput.nextLine();
                        String name4 = myInput.nextLine();
                        System.out.print("Потужність: ");
                        float power4 = myInput.nextFloat();
                        if (apartment.getAppliance(name4, power4) == null)
                            break;
                        if (!apartment.connect(apartment.getAppliance(name4, power4)))
                            System.out.println("Усі розетки зайняті!");
                        break;
                    case 7:
                        System.out.print("Назва: ");
                        myInput.nextLine();
                        String name5 = myInput.nextLine();
                        System.out.print("Потужність: ");
                        float power5 = myInput.nextFloat();
                        apartment.disconnect(apartment.getAppliance(name5, power5));
                        break;
                    default:
                        break;
                }

            }
        }catch (Exception e){
            System.out.println("Щось пішло не так....");
        }
        apartment.stopSimulation();
    }
}