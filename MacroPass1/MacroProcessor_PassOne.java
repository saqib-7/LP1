package MacroPass1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class MacroProcessor_PassOne {
    static List<String> MDT;
    static Map<String, String> MNT;
    static int mntPtr, mdtPtr;
    static Map<String, Map<String, String>> ALAs;
    static Map<String, String> currentALA;

    public static void main(String[] args) {
        try {
            pass1();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void pass1() throws Exception {
        MDT = new ArrayList<>();
        MNT = new LinkedHashMap<>();
        ALAs = new LinkedHashMap<>();
        mntPtr = 0;
        mdtPtr = 0;

        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("/home/student/Downloads/PASS I MACRO/input.txt")));
        PrintWriter out_pass1 = new PrintWriter(new FileWriter("/home/student/Downloads/PASS I MACRO/output_pass1.txt"), true);
        PrintWriter out_mnt = new PrintWriter(new FileWriter("/home/student/Downloads/PASS I MACRO/MNT.txt"), true);
        PrintWriter out_mdt = new PrintWriter(new FileWriter("/home/student/Downloads/PASS I MACRO/MDT.txt"), true);
        PrintWriter out_ala = new PrintWriter(new FileWriter("/home/student/Downloads/PASS I MACRO/ALA.txt"), true);

        String s;
        boolean processingMacroDefinition = false;
        boolean processMacroName = false;

        System.out.println("============= Pass 1 Output ==============");
        while ((s = input.readLine()) != null) {
            String s_arr[] = tokenizeString(s, " ");
            String curToken = s_arr[0];

            if (curToken.equalsIgnoreCase("MACRO")) {
                processingMacroDefinition = true;
                processMacroName = true;
            } else if (processingMacroDefinition) {
                if (curToken.equalsIgnoreCase("MEND")) {
                    MDT.add(mdtPtr++, s);
                    processingMacroDefinition = false;
                    continue;
                }

                if (processMacroName) {
                    String macroName = curToken;
                    MNT.put(macroName, String.valueOf(mdtPtr));
                    mntPtr++;

                    currentALA = new LinkedHashMap<>();
                    processArgumentList(s_arr[1]);
                    ALAs.put(macroName, currentALA);

                    MDT.add(mdtPtr++, s);
                    processMacroName = false;
                    continue;
                }

                String indexedArgList = processArguments(s_arr[1]);
                MDT.add(mdtPtr++, curToken + " " + indexedArgList);
            } else {
               System.out.println(s);
               out_pass1.println(s);
            }
        }

        input.close();

        System.out.println("============= MNT ==============");
        for (String key : MNT.keySet()) {
            String mntRow = key + " " + MNT.get(key);
            System.out.println(mntRow);
            out_mnt.println(mntRow);
        }

        System.out.println("============= MDT ==============");
        for (int i = 0; i < MDT.size(); i++) {
            String mdtRow = i + "  " + MDT.get(i);
            System.out.println(mdtRow);
            out_mdt.println(mdtRow);
        }

        System.out.println("============= ALA ==============");
        for (Map.Entry<String, Map<String, String>> entry : ALAs.entrySet()) {
            String macroName = entry.getKey();
            Map<String, String> alaTable = entry.getValue();

            System.out.println("Macro: " + macroName);
            out_ala.println("Macro: " + macroName);

            for (Map.Entry<String, String> alaEntry : alaTable.entrySet()) {
                String alaRow = alaEntry.getKey() + " -> " + alaEntry.getValue();
                System.out.println(alaRow);
                out_ala.println(alaRow);
            }

            System.out.println();
            out_ala.println();
        }

        out_pass1.close();
        out_mnt.close();
        out_mdt.close();
        out_ala.close();
    }

    static void processArgumentList(String argList) {
        StringTokenizer st = new StringTokenizer(argList, ",", false);
        int argCount = st.countTokens();
        String curArg;
        for (int i = 1; i <= argCount; i++) {
            curArg = st.nextToken();
            if (curArg.contains("=")) {
                curArg = curArg.substring(0, curArg.indexOf("="));
            }
            currentALA.put(curArg, "#" + i);
        }
    }

    static String processArguments(String argList) {
        StringTokenizer st = new StringTokenizer(argList, ",", false);
        int argCount = st.countTokens();
        String curArg, argIndexed;
        for (int i = 0; i < argCount; i++) {
            curArg = st.nextToken();
            argIndexed = currentALA.get(curArg);
            argList = argList.replace(curArg, argIndexed);
        }
        return argList;
    }

    static String[] tokenizeString(String str, String separator) {
        StringTokenizer st = new StringTokenizer(str, separator, false);
        String s_arr[] = new String[st.countTokens()];
        for (int i = 0; i < s_arr.length; i++) {
            s_arr[i] = st.nextToken();
        }
        return s_arr;
    }
}
