package Scheduling;

import java.util.Scanner;

public class RoundRobinPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Number of processes: ");
        int n = sc.nextInt();

        int[] at = new int[n], bt = new int[n], rt = new int[n];
        int[] ct = new int[n], tat = new int[n], wt = new int[n];
        boolean[] completed = new boolean[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Arrival time for P" + (i + 1) + ": ");
            at[i] = sc.nextInt();
            System.out.print("Burst time for P" + (i + 1) + ": ");
            bt[i] = sc.nextInt();
            rt[i] = bt[i];
        }

        System.out.print("Enter time quantum: ");
        int tq = sc.nextInt();

        int currentTime = 0, completedCount = 0;
        boolean done;

        while (completedCount < n) {
            done = true;
            for (int i = 0; i < n; i++) {
                if (at[i] <= currentTime && rt[i] > 0) {
                    done = false;
                    if (rt[i] > tq) {
                        rt[i] -= tq;
                        currentTime += tq;
                    } else {
                        currentTime += rt[i];
                        rt[i] = 0;
                        completedCount++;
                        ct[i] = currentTime;
                        tat[i] = ct[i] - at[i];
                        wt[i] = tat[i] - bt[i];
                    }
                } else if (at[i] > currentTime) {
                    if (done) currentTime = at[i];
                }
            }
        }

        System.out.println("\nP\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n", i + 1, at[i], bt[i], ct[i], tat[i], wt[i]);
        }

        sc.close();
    }
}
