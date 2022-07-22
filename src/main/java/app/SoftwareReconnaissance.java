package app;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SoftwareReconnaissance {
    //create this class a this find common methods between two classes
    // For this I will use HashSet collection internally
    //create a configuration for
    public static void main(String[] args){
        new SoftwareReconnaissance(args[0], args[1], args[2]);
    }

    SoftwareReconnaissance(String file1, String file2, String file3) {
        String line;

        ArrayList<String> arrayList1 = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        ArrayList<Integer> millisecondFile = new ArrayList<Integer>();
        ArrayList<String> difference = new ArrayList<String>();
        ArrayList<String> unique = new ArrayList<String>();

        try {
            BufferedReader vinothTryFile1 = new BufferedReader(new FileReader(file1));
            while ((line = vinothTryFile1.readLine()) != null)
            {
                String[] record = line.split(",");
                String[] data = record[0].split(":");
                millisecondFile.add(Integer.valueOf(data[data.length-1]));
                subtractor(millisecondFile,difference);
                arrayList1.addAll(Arrays.asList(record));
                if (difference.size() != 0)
                {
                    arrayList1.add(arrayList1.size()-4,difference.get(difference.size()-1));
                }
            }
            difference.clear();
            millisecondFile.clear();
//            System.out.println(arrayList1);
            vinothTryFile1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Before "+arrayList1.size()/5);


        try {
            BufferedReader vinothTryFile2 = new BufferedReader(new FileReader(file2));
            while ((line = vinothTryFile2.readLine()) != null)
            {
                String[] record = line.split(",");
                String[] data = record[0].split(":");
                millisecondFile.add(Integer.valueOf(data[data.length-1]));
                subtractor(millisecondFile,difference);
                arrayList2.addAll(Arrays.asList(record));
                if (difference.size() != 0)
                {
                    arrayList2.add(arrayList2.size()-4,difference.get(difference.size()-1));
                }
            }
            difference.clear();
            millisecondFile.clear();
//            System.out.println(arrayList2);
            vinothTryFile2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int position = 2;

        for (int i = 0; i <= arrayList2.size()/5; i++) {
            int index = arrayList1.indexOf(arrayList2.get(position));
            if (index == -1)
            {
                try {
                    unique.add(String.valueOf(arrayList1.subList(position-2,position+3)));
//                    arrayList1.subList(index-2,index+3).clear();
                }
                catch (IndexOutOfBoundsException ignored)
                {}

            }
            position += 5;
        }
//        System.out.println(unique);
        System.out.println("After "+arrayList1.size()/5);

        try
        {
            FileWriter writer=new FileWriter(file3+"\\lv.log");
            for(String str: unique) {
                str = str.replace("[","");
                str = str.replace("]","");
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void subtractor(ArrayList<Integer> millisecondFile, ArrayList<String> difference) {
        if (millisecondFile.size() > 1)
        {
            difference.add(String.valueOf(millisecondFile.get(millisecondFile.size() - 1) - millisecondFile.get(millisecondFile.size() - 2)));
        }
    }
}
