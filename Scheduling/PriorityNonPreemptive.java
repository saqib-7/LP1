package Scheduling;

import java.util.Scanner;

public class PriorityNonPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Number of processes: ");
        int n = sc.nextInt();

        int[] at = new int[n], bt = new int[n], priority = new int[n];
        int[] ct = new int[n], wt = new int[n], tat = new int[n];
        boolean[] completed = new boolean[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Arrival time for P" + (i + 1) + ": ");
            at[i] = sc.nextInt();
            System.out.print("Burst time for P" + (i + 1) + ": ");
            bt[i] = sc.nextInt();
            System.out.print("Priority for P" + (i + 1) + " (lower number = higher priority): ");
            priority[i] = sc.nextInt();
        }

        int completedCount = 0, currentTime = 0;

        while (completedCount < n) {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!completed[i] && at[i] <= currentTime && priority[i] < highestPriority) {
                    highestPriority = priority[i];
                    idx = i;
                }
            }

            if (idx == -1) {
                currentTime++;
                continue;
            }

            currentTime += bt[idx];
            ct[idx] = currentTime;
            tat[idx] = ct[idx] - at[idx];
            wt[idx] = tat[idx] - bt[idx];
            completed[idx] = true;
            completedCount++;
        }

        System.out.println("\nP\tAT\tBT\tPriority\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.printf("%d\t%d\t%d\t%d\t\t%d\t%d\t%d\n", i + 1, at[i], bt[i], priority[i], ct[i], tat[i], wt[i]);
        }

        sc.close();
    }
}