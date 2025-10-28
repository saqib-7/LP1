package Scheduling;

import java.util.*;

public class Optimal 
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

        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        System.out.println("\nPage Replacement Process:");
        for (int i = 0; i < pagesCount; i++) 
        {
            int page = pages[i];

            if (frames.contains(page)) 
            {
            
                System.out.print("Step " + (i + 1) + " (Page " + page + "): ");
                printFrames(frames);
                continue;
            }

            pageFaults++;

            if (frames.size() < framesCount) 
            {
            
                frames.add(page);
            } else {
                int indexToReplace = findOptimal(frames, pages, i + 1);
                frames.set(indexToReplace, page);
            }

            System.out.print("Step " + (i + 1) + " (Page " + page + "): ");
            printFrames(frames);
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
        sc.close();
    }

    private static int findOptimal(List<Integer> frames, int[] pages, int start) 
    {
        int farthest = start, indexToReplace = -1;

        for (int i = 0; i < frames.size(); i++) 
        {
            int page = frames.get(i);
            int j;
            for (j = start; j < pages.length; j++) 
            {
                if (pages[j] == page) 
                {
                    if (j > farthest) 
                    {
                        farthest = j;
                        indexToReplace = i;
                    }
                    break;
                }
            }
            if (j == pages.length) 
            {
                return i;
            }
        }

        return (indexToReplace == -1) ? 0 : indexToReplace;
    }

    private static void printFrames(List<Integer> frames) 
    {
        for (int f : frames) {
            System.out.print(f + " ");
        }
        System.out.println();
    }
}
