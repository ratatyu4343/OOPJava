package tariff;

import java.util.Comparator;

//статичний метод, що повертає об'єкт Comparator за заданими параметрами
public class TariffCompare {
    public static Comparator<Tariff> getComparator(String [] parameters) {
        Comparator<Tariff> comparator = new TariffComparator("");

        for(var param : parameters) {
            comparator = comparator.thenComparing(new TariffComparator(param));
        }

        return comparator;
    }
}

//клас, що наслідується від Comparator, перевизначений
//метод compare для порівняння тарифів по різним полям
class TariffComparator implements Comparator<Tariff> {
    final private String type;
    public TariffComparator (String type) {
        this.type = type;
    }
    @Override
    public int compare(Tariff o1, Tariff o2) {
        return switch (type) {
            case "" -> 0;
            case "tName" -> o1.gettName().compareTo(o2.gettName());
            case "oName" -> o1.getoName().compareTo(o2.getoName());
            default -> (int)(o1.getAtribute(type) - o2.getAtribute(type));
        };
    }
}

