package Scheduling;

import java.util.*;

public class LRU
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of frames: ");
        int framesCount = sc.nextInt();

        System.out.print("Enter number of pages: ");
        int pagesCount = sc.nextInt();

        int pages[] = new int[pagesCount];
        System.out.println("Enter page reference string:");
        for (int i = 0; i < pagesCount; i++) 
        {
            pages[i] = sc.nextInt();
        }

        int[] frames = new int[framesCount];
        Arrays.fill(frames, -1);
        int pageFaults = 0;

        System.out.println("\nPage Replacement Process:");
        for (int i = 0; i < pagesCount; i++) 
        {
            int page = pages[i];
            boolean found = false;

            for (int f : frames) 
            {
                if (f == page) 
                {
                    found = true;
                    break;
                }
            }

            if (!found) 
            {
                pageFaults++;

                int replaceIndex = -1;

                for (int j = 0; j < framesCount; j++) 
                {
                    if (frames[j] == -1) 
                    {
                        replaceIndex = j;
                        break;
                    }
                }

                if (replaceIndex == -1) {
                    int leastRecent = i, idx = -1;

                    for (int j = 0; j < framesCount; j++) 
                    {
                        int lastUsed = -1;
                        for (int k = i - 1; k >= 0; k--) 
                        {
                            if (pages[k] == frames[j]) 
                            {
                                lastUsed = k;
                                break;
                            }
                        }
                        if (lastUsed < leastRecent) 
                        {
                            leastRecent = lastUsed;
                            idx = j;
                        }
                    }
                    replaceIndex = idx;
                }

                frames[replaceIndex] = page;
            }

            System.out.print("Step " + (i + 1) + " (Page " + page + "): ");
            for (int f : frames) 
            {
                if (f != -1) System.out.print(f + " ");
            }
            System.out.println();
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
        sc.close();
    }
}

