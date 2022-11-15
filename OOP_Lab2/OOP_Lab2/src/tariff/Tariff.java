package tariff;

import java.util.Comparator;

public class Tariff implements Comparable<Tariff> {
    //id
    final String id;
    //name of tariff
    private String tName = "";
    //name of operator
    private String oName = "";
    //cost for month
    private double payroll = 0;
    //call cost in network
    private double callIn = 0;
    //call cost out of network
    private double callOut = 0;
    //call cost to home phone
    private double callHome = 0;
    //sms cost
    private double sms = 0;
    //likest number
    private double likeNum = 0;
    //tarification 12sec
    private double tarif12 = 0;
    //tarification 60sec
    private double tarif60 = 0;
    //cost of starting
    private double startCost = 0;

    public Tariff(String id) {
        this.id = id;
    }

    //setters
    public void setAtribute(String atribute, String value) {
        switch (atribute) {
            case "tName" -> tName = value;
            case "oName" -> oName = value;
        }
    }

    public  void setAtribute(String atribute, double value) {
        if (value < 0) value = 0;
        switch (atribute) {
            case "payroll" -> payroll = value;
            case "callIn" -> callIn = value;
            case "callOut" -> callOut = value;
            case "callHome" -> callHome = value;
            case "sms" -> sms = value;
            case "likeNum" -> likeNum = value;
            case "tarif12" -> tarif12 = value;
            case "tarif60" -> tarif60 = value;
            case "startCost" -> startCost = value;
        }
    }

    //getters
    public String gettName() {
        return tName;
    }

    public String getoName() {
        return oName;
    }

    public double getAtribute(String atribute) {
        return switch (atribute) {
            case "payroll" -> payroll;
            case "callIn" -> callIn;
            case "callOut" -> callOut;
            case "callHome" -> callHome;
            case "sms" -> sms;
            case "likeNum" -> likeNum;
            case "tarif12" -> tarif12;
            case "tarif60" -> tarif60 ;
            case "startCost" -> startCost;
            default -> -1;
        };
    }

    //comparing defenition
    @Override
    public int compareTo(Tariff o) {
        return (int)(payroll - o.getAtribute("payroll"));
    }

    //string convert
    @Override
    public String toString() {
        String str = String.format(
            "ID: %s\n" +
            "Name: %s\n" +
            "Operator: %s\n" +
            "MonthCost: %f\n" +
            "CallInNetwork: %f\n" +
            "CallOutNetwork: %f\n" +
            "CallHomePhone: %f\n" +
            "SMS: %f\n" +
            "LikestNumber: %f\n" +
            "12sec-Tarif: %f\n" +
            "60sec-Tarif: %f\n" +
            "FirstIncluding: %f\n",
             id, tName, oName, payroll,
             callIn, callOut, callHome,
             sms, likeNum, tarif12,
             tarif60, startCost
        );
        return  str;
    }
}
