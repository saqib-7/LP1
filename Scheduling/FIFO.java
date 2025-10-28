package Scheduling;

import java.util.*;

public class FIFO
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

        Queue<Integer> frameQueue = new LinkedList<>();
        Set<Integer> frameSet = new HashSet<>();
        int pageFaults = 0;

        System.out.println("\nPage Replacement Process:");
        for (int i = 0; i < pagesCount; i++) 
        {
            int page = pages[i];

            if (!frameSet.contains(page)) 
            {
                pageFaults++;

                if (frameQueue.size() == framesCount) 
                {
                    int removed = frameQueue.poll();
                    frameSet.remove(removed);
                }

                frameQueue.add(page);
                frameSet.add(page);
            }

            System.out.print("Step " + (i + 1) + " (Page " + page + "): ");
            for (int f : frameQueue) 
            {
                System.out.print(f + " ");
            }
            System.out.println();
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
        sc.close();
    }
}
