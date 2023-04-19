import java.util.Scanner;
import java.lang.Math;
import java.io.*;

class Main {
  	public static void main(String[] args)  throws NumberFormatException, IOException, InterruptedException {
		Scanner scanner = new Scanner(System.in);
		ATC controller = new ATC();
		
		System.out.print("Insira a quantidade de aviões que irão decolar: ");
        int departureAmount = scanner.nextInt();

		System.out.println("Insira a o tempo de saída de cada avião:");
		Plane[] departures = new Plane[departureAmount];
        for (int i = 0; i < departureAmount; i++) {
            int departureTime = scanner.nextInt();
            departures[i] = new Plane(departureTime, true, controller);
        }

		System.out.print("Insira a quantidade de aviões que irão pousar: ");
        int arrivalAmount = scanner.nextInt();
		
        System.out.println("Insira o tempo de pouso de cada avião:");
		Plane[] arrivals = new Plane[arrivalAmount];
        for (int i = 0; i < arrivalAmount; i++) {
            int arrivalTime = scanner.nextInt();
		 	arrivals[i] = new Plane(arrivalTime, false, controller);
        }

		System.out.print("Insira a quantidade de pistas disponíveis: ");
		int runwaysAmount = scanner.nextInt();
		controller.setRunways(runwaysAmount);

		int threadsAmount = departureAmount + arrivalAmount;
		Thread[] threads = new Thread[threadsAmount];
		for (int i = 0; i < departureAmount; i++) {
            threads[i] = new Thread(departures[i]);
			threads[i].start();
        }
        for (int i = departureAmount; i < threadsAmount; i++) {
            threads[i] = new Thread(arrivals[i - departureAmount]);
            threads[i].start();
        }
		
		for (int i = 0; i < threadsAmount; i++) {
            threads[i].join();
        }
  	}
}