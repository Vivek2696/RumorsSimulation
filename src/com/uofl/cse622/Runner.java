package com.uofl.cse622;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of student: ");
        int N = scanner.nextInt();
        System.out.println("Enter the time for simulation: ");
        int T = scanner.nextInt();
        System.out.println("Enter the percent limit: ");
        int P = scanner.nextInt();

        Simulation simulation = new Simulation(N);
        simulation.startSimulation(N,P);
    }
}
