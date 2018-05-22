package decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class DecisionTree {

    public Node createTree(List<Record> set, String attribute, int value, String path) {
        Node root = new Node();
        if (attribute == null) {
            String bestAttribute = chooseAttribute(set, null, 0, path, calculateMostCommon(set, attribute));
            root.data = bestAttribute;
//            path += "," + bestAttribute;
//            path.add(bestAttribute);
            for (int branch : ReadFromFile.attributeValues.get(bestAttribute)) {
                List<Record> newList = initSet(set, bestAttribute, branch);
                Node child = createTree(newList, bestAttribute, branch, path + "," + bestAttribute);
                root.children.put(branch, child);
            }
            return root;
        }

        String bestAttribute = chooseAttribute(set, attribute, value, path, calculateMostCommon(set, attribute));
        if (bestAttribute.equals("yes") || bestAttribute.equals("no")) {
            root.data = bestAttribute;
        } else {
            root.data = bestAttribute;
//            path += "," + bestAttribute;
//            path.add(bestAttribute);
            for (int branch : ReadFromFile.attributeValues.get(bestAttribute)) {
                List<Record> newList = initSet(set, bestAttribute, branch);
                Node temp = new Node();
                temp = createTree(newList, bestAttribute, branch, path + "," + bestAttribute);
                root.children.put(branch, temp);
            }
        }
        return root;
    }

    //path is like: ["sky", "temp", ...]
    String chooseAttribute(List<Record> set, String parent, int value, String path, String mostCommon) {
//        set = initSet(set, parent, value);
        List<String> available = new ArrayList<>();
        available.addAll(ReadFromFile.attributeList);
        //choosing root attribute
        if (parent == null) {
            double maxGain = 0;
            String bestAttribute = "";
            for (String temp : ReadFromFile.attributeList) {
                double attributeGain = gain(set, temp);
                if (attributeGain > maxGain) {
                    maxGain = attributeGain;
                    bestAttribute = temp;
                }
            }
            return bestAttribute;
        }

        //Avoid selecting used attributes
        for (String temp : path.split(",")) {
            available.remove(temp);
        }

        //if all values are in the same class
        boolean allEqual = true;
        int pCounter = 0, nCounter = 0;
        for (Record r : set) {
            //chech value or not ?????
//            if (r.attribute[ReadFromFile.attributeList.indexOf(parent)].equals(value)) 
            if (r.classification) {
                pCounter++;
            } else {
                nCounter++;
            }
            if (nCounter > 0 && pCounter > 0) {
                allEqual = false;
            }
        }
        if (allEqual) {
            return pCounter > 0 ? "yes" : "no";
        } else if (available.isEmpty()) {
            return pCounter >= nCounter ? "yes" : "no";
        }

        //There is no examples for this branch
        if (set.isEmpty()) {
            return mostCommon;
        }

//        Iterator itr = set.iterator();
//If there are still unsused attributes
//        for (Record record : set) {
        double maxGain = -1;
        String bestAttribute = "";
        for (String temp : available) {
            double attributeGain = gain(set, temp);
            if (attributeGain > maxGain) {
                maxGain = attributeGain;
                bestAttribute = temp;
            }
        }
        return bestAttribute;
//        }
//        return mostCommon;
    }

    static double entropy(List<Record> set) {
        int p = 0, n = 0;
        for (Record r : set) {
            if (r.classification) {
                p++;
            } else {
                n++;
            }
        }
        return entropy(p, n);
    }

    static double entropy(int p, int n) {
        if (n == 0 || p == 0) {
            return -1;
        } else {
            double pValue = (p * Math.log10((double) p / (p + n)) / Math.log10(2)) / (p + n);
            double nValue = (n * Math.log10((double) n / (p + n)) / Math.log10(2)) / (p + n);
            return -1 * (pValue + nValue);
        }
    }

    static double gain(List<Record> set, String attribute) {
        double mainEntopy = entropy(set);
        for (int value : ReadFromFile.attributeValues.get(attribute)) {
            int counter = 0, p = 0, n = 0;
            for (Record r : set) {
                if (r.attribute[ReadFromFile.attributeList.indexOf(attribute)] == value) {
                    counter++;
                    if (r.classification) {
                        p++;
                    } else {
                        n++;
                    }
                }
            }
            mainEntopy -= counter * entropy(p, n) / set.size();
        }
        return mainEntopy;
    }

    private List<Record> initSet(List<Record> set, String attribute, int value) {
        List<Record> copy = new ArrayList<>();
        for (Record r : set) {
            copy.add(r);
        }
        Iterator itr = copy.iterator();
        System.out.println(copy.size());
        while (itr.hasNext()) {
            Record temp = (Record) itr.next();
            if (temp.attribute[ReadFromFile.attributeList.indexOf(attribute)] != value) {
                itr.remove();
            }
        }
//        System.out.println(set.size());
        return copy;
    }

    private String calculateMostCommon(List<Record> set, String attribute) {
        int pCounter = 0, nCounter = 0;
        for (Record r : set) {
            if (r.classification) {
                pCounter++;
            } else {
                nCounter++;
            }
        }
        return pCounter >= nCounter ? "yes" : "no";
    }
}
