/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadFromFile {
    
    public static List<Record> training;
    public static List<String> attributeList;
    public static LinkedHashMap<String, List<Integer>> attributeValues;
    public static List<Record> testSet;
//    public static List<String> outlookVal;
//    public static List<String> humidityVal;
//    public static List<String> windVal;
//    public static List<String> tempVal;
//    String[][] Values = {outlookVal, tempVal, HumidityVal, WindVal};
    //FileInputStream in = null;
    File file = null;
    FileReader fr = null;
    BufferedReader br = null;
    
    public ReadFromFile() {
        attributeValues = new LinkedHashMap<>();
        attributeList = new ArrayList<>();
        String[] attrib = {"monk1", "monk2", "monk3", "monk4", "monk5", "monk6"};
        attributeList.addAll(Arrays.asList(attrib));
        for (String temp : attrib) {
            attributeValues.put(temp, new ArrayList<>());
        }

//        outlookVal = new ArrayList<>();
//        tempVal = new ArrayList<>();
//        windVal = new ArrayList<>();
//        humidityVal = new ArrayList<>();
        training = new ArrayList<>();
        testSet = new ArrayList<>();
    }
    
    public void initialize(String fileName, boolean train) {
        try {
            file = new File(fileName);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                Record r = new Record();
                for (int i = 0; i < attributeList.size(); i++) {
                    r.attribute[i] = Integer.parseInt(values[i + 2]);
                    if (!attributeValues.get(attributeList.get(i)).contains(Integer.parseInt(values[i + 2]))) {
                        attributeValues.get(attributeList.get(i)).add(Integer.parseInt(values[i + 2]));
                    }
                }
                if (values[1].equals("1")) {
                    r.classification = true;
                } else {
                    r.classification = false;
                }
                if (train) {
                    training.add(r);
                } else {
                    testSet.add(r);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ReadFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    String[] result;

//    boolean takeNextValue(TrainingData ex) {
//        try {
//            String str = br.readLine();
//            if (str == null) {
//                return false;
//            }
//            result = str.split(",");
//            int i;
//            for (i = 0; i < (result.length) - 1; i++) {
//                ex.hypothesis.AttributeList[i] = result[i];
//            }
//            if (result[i].contains("Not")) {
//                ex.EnjoySport = false;
//            } else {
//                ex.EnjoySport = true;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
}
