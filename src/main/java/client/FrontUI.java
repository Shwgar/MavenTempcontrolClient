
package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FrontUI {
    
    public static void main(String[] args) throws IOException {
        TempClient client = new TempClient();
        while(true)
        {
            System.out.println("[1] Visa aktuell temperatur");
            System.out.println("[2] Visa aktuell förbrukning");
            System.out.println("[3] Visa aktuell elkostnad");
            System.out.println("[4] Visa aktuell serverhallsstatus");
            System.out.println("[5] Ändra temperatur");
            System.out.println("[6] Ändra elkostnad");
            System.out.println("[7] Dygnsrapport temperatur");
            System.out.println("[8] Dygnsrapport elkostnad");
            System.out.println("[9] Förbrukningsrapport alla serverhallar");
            System.out.print("Val : ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            
            switch (choice)
            {
                case 1:
                    System.out.println("Serverhall nr : 1 " + client.getTemp(1) + " grader celsius");
                    System.out.println("Serverhall nr : 2 " + client.getTemp(2) + " grader celsius");
                    System.out.println("Serverhall nr : 3 " + client.getTemp(3) + " grader celsius");
                    System.out.println("Tryck valfri knapp för att återgå till menyn");
                    System.in.read();
                    break;
                case 2:
                    System.out.println("Serverhall nr : 1 " + client.getConsumption(1) + " watt");
                    System.out.println("Serverhall nr : 2 " + client.getConsumption(2) + " watt");
                    System.out.println("Serverhall nr : 3 " + client.getConsumption(3) + " watt");
                    System.out.println("Tryck valfri knapp för att återgå till menyn");
                    System.in.read();
                    break;
                case 3:
                    System.out.println(client.getCost() + " kr/KWH");
                    System.out.println("Tryck valfri knapp för att återgå till menyn");
                    System.in.read();
                    break;
                case 4:
                    List<sqlObject> datapoints = new ArrayList();
                    datapoints = client.getCurrentStatus();
                    for(sqlObject o : datapoints)
                    {
                        String costph = String.format("%.2f", o.getConsumption()/1000*o.getCost());
                        System.out.println("Serverhall : " + o.getHallId() + ", Temperatur : " + o.getTemperature() + ", förbrukning " + o.getConsumption() + " watt" + ", elkostnad : " + o.getCost() + ", kr/kwh" + ", timkostnad : " + costph + " kr");
                    }

                    System.out.println("Tryck valfri knapp för att återgå till menyn");
                    System.in.read();
                    break;
                case 5:
                    System.out.print("Vilken temperatur vill du ändra till? : ");
                    float changeTemp = scanner.nextFloat();
                    System.out.print("Vilken hall vill du ändra i? 1-3 : ");
                    int changeHall = scanner.nextInt();
                    client.changeTemp(changeTemp, changeHall);
                    break;
                case 6:
                    System.out.print("Vilken elkostnad kr/kwh vill du ändra till? : ");
                    float changeCost = scanner.nextFloat();
                    client.changeCost(changeCost);
                    break;
                case 7:
                    List<sqlObject> datapointstemp = new ArrayList();
                    datapointstemp = client.getDayTemperatures();
                    for(sqlObject o : datapointstemp)
                    {
                        System.out.println("serverhall : " + o.getHallId() + " temperatur : " + o.getTemperature() + " grader celsius" + " kl: " + o.getDate());
                    }
                    System.out.println("Serverhall : 1, " + client.getMaxMinAvgTemp(1));
                    System.out.println("Serverhall : 2, " + client.getMaxMinAvgTemp(2));
                    System.out.println("Serverhall : 3, " + client.getMaxMinAvgTemp(3));
                    System.out.println("Tryck valfri knapp för att återgå till menyn");
                    System.in.read();
                    break;
                case 8:
                    List<sqlObject> datapointsCons = new ArrayList();
                    datapointsCons = client.getDayConsumption();
                    for(sqlObject o : datapointsCons)
                    {
                        System.out.println("serverhall : " + o.getHallId() + " förbrukning : " + o.getConsumption()/1000 + " Kwh" + " kl: " + o.getDate());
                    }
                    System.out.println("Serverhall : 1, " + client.getMaxMinAvgCons(1) + " var dyrast");
                    System.out.println("Serverhall : 2, " + client.getMaxMinAvgCons(2) + " var dyrast");
                    System.out.println("Serverhall : 3, " + client.getMaxMinAvgCons(3) + " var dyrast");
                    System.out.println("Tryck valfri knapp för att återgå till menyn");
                    System.in.read();
                    break;
                case 9:
                    List<sqlObject> datapointsTotCons = new ArrayList();
                    datapointsTotCons = client.getTotalConsumption();
                    for(sqlObject o : datapointsTotCons)
                    {
                        System.out.println("serverhall : " + o.getHallId() + " förbrukning : " + o.getConsumption()/1000 + " Kwh" + " per dygn");
                    }
                
                    System.out.println("Tryck valfri knapp för att återgå till menyn");
                    System.in.read();
                    break;
                default:
                    break;
            
            }
        }
    }
}
