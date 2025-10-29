package MacroPass2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class MacroProcessor_PassTwo {
    static List<String> MDT;
    static Map<String, String> MNT;
    static Map<String, List<String>> ALA;
    static List<String> actualParams;
    static int mdtPtr;

    public static void main(String[] args) {
        try {
            initiallizeTables();
            pass2();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void pass2() throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("/home/student/Downloads/PASSS II MACRO/output_pass1.txt")));
        PrintWriter out_pass2 = new PrintWriter(new FileWriter("/home/student/Downloads/PASSS II MACRO/output_pass2.txt"), true);

        System.out.println("============= Pass 2 Output ==============");

        String s;
        while ((s = input.readLine()) != null) {
            String[] s_arr = tokenizeString(s, " ");
            if (MNT.containsKey(s_arr[0])) {
                String macroName = s_arr[0];
                actualParams.clear();

                String[] actual_params_arr = tokenizeString(s_arr[1], ",");
                for (String param : actual_params_arr) {
                    if (param.contains("=")) {
                        param = param.substring(param.indexOf("=") + 1);
                    }
                    actualParams.add(param);
                }

                mdtPtr = Integer.parseInt(MNT.get(macroName));
                List<String> formalParamList = ALA.get(macroName);

                String macroLine;
                boolean skipHeader = true;

                while (true) {
                    macroLine = MDT.get(mdtPtr++);
                    String[] tokens = tokenizeString(macroLine, " ");

                    if (tokens[0].equalsIgnoreCase("MEND")) break;
                    if (skipHeader) {
                        skipHeader = false;
                        continue; 
                    }

                    String mnemonic = tokens[0];
                    String params = tokens.length > 1 ? tokens[1] : "";

                    String expandedParams = replaceParamsUsingALA(params, formalParamList);
                    String expandedLine = "+" + mnemonic + " " + expandedParams;

                    System.out.println(expandedLine);
                    out_pass2.println(expandedLine);
                }
            } else {
                System.out.println(s);
                out_pass2.println(s);
            }
        }

        input.close();
        out_pass2.close();
    }

    static String replaceParamsUsingALA(String paramList, List<String> formalParams) {
        if (paramList.isEmpty()) return "";

        StringBuilder result = new StringBuilder();
        String[] paramTokens = tokenizeString(paramList.replace("#", ""), ",");

        for (String token : paramTokens) {
            int index = Integer.parseInt(token);
            String value = (index <= actualParams.size())
                ? actualParams.get(index - 1)
                : formalParams.get(index - 1);
            result.append(value).append(",");
        }

        if (result.length() > 0) {
            result.setLength(result.length() - 1); 
        }

        return result.toString();
    }

    static void initiallizeTables() throws Exception {
        MDT = new ArrayList<>();
        MNT = new LinkedHashMap<>();
        ALA = new LinkedHashMap<>();
        actualParams = new ArrayList<>();

        BufferedReader br;
        String s;

        br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/student/Downloads/PASSS II MACRO/MNT.txt")));
        while ((s = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s, " ");
            MNT.put(st.nextToken(), st.nextToken());
        }
        br.close();

        br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/student/Downloads/PASSS II MACRO/MDT.txt")));
        while ((s = br.readLine()) != null) {
            int index = Integer.parseInt(s.substring(0, s.indexOf(" ")).trim());
            String content = s.substring(s.indexOf(" ") + 1);
            while (MDT.size() <= index) MDT.add(""); 
            MDT.set(index, content);
        }
        br.close();

        br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/student/Downloads/PASSS II MACRO/ALA.txt")));
        String macroName = null;
        List<String> paramList = null;

        while ((s = br.readLine()) != null) {
            if (s.trim().isEmpty()) continue;

            if (s.startsWith("Macro:")) {
                macroName = s.substring(s.indexOf(":") + 1).trim();
                paramList = new ArrayList<>();
                ALA.put(macroName, paramList);
            } else if (macroName != null) {
                String[] tokens = s.split("->");
                if (tokens.length == 2) {
                    String param = tokens[0].trim();          
                    String position = tokens[1].trim();       
                    int index = Integer.parseInt(position.replace("#", ""));
                    while (paramList.size() < index) paramList.add(""); 
                    paramList.set(index - 1, param);
                }
            }
        }
        br.close();
    }

    static String[] tokenizeString(String str, String separator) {
        StringTokenizer st = new StringTokenizer(str, separator, false);
        String[] s_arr = new String[st.countTokens()];
        for (int i = 0; i < s_arr.length; i++) {
            s_arr[i] = st.nextToken();
        }
        return s_arr;
    }
}
