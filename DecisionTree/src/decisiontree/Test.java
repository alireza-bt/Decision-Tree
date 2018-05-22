/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Test {

    String trainingFile = "train100.txt";
    String testFile = "monks-1.test";

    public static void main(String[] args) {
        Test test = new Test();
        ReadFromFile rff = new ReadFromFile();

        rff.initialize(test.trainingFile, true);
        DecisionTree id3 = new DecisionTree();
        Node root = id3.createTree(ReadFromFile.training, null, 0, "");
        rff.initialize(test.testFile, false);
//        int correctAssessment = checkTestSet(ReadFromFile.testSet, root);
//        System.out.println("correct ones " + correctAssessment + " out of " + ReadFromFile.testSet.size());
        print(root, 0);
//        System.out.println(ReadFromFile.attributeValues.size());
//        for (Entry<String, List<String>> list : ReadFromFile.attributeValues.entrySet()) {
//            System.out.println(list.getKey());
//            list.getValue().stream().forEach(System.out::println);
////            System.out.println(list.toString());
//        }
//        System.out.println(DecisionTree.entropy(1, 2));
    }

    public static int checkTestSet(List<Record> set, Node root) {
        if (set.isEmpty() || root.data == null) {
            return 0;
        }
        int counter = 0;
        for (Record r : set) {
            Node temp = root;
            while (true) {
                if ((temp.data.equals("yes") && r.classification) || (temp.data.equals("no") && !r.classification)) {
                    counter++;
                    break;
                } else if ((temp.data.equals("no") && r.classification) || (temp.data.equals("yes") && !r.classification)) {
                    break;
                } else {
                    int value = r.attribute[ReadFromFile.attributeList.indexOf(temp.data)];
                    if (temp.children.containsKey(value)) {
                        temp = temp.children.get(value);
                    } else {
                        break;
                    }
                }
            }
        }
        return counter;
    }

    public static void print(Node node, int deep) {

        if (node.children.entrySet().size() == 0) {
            for (int i = 0; i < deep; i++) {
                System.out.print("\t|");
            }
            System.out.println(node.data);
        }

        for (Entry<Integer, Node> entry : node.children.entrySet()) {
            for (int i = 0; i < deep; i++) {
                System.out.print("\t|");
            }
            System.out.println(node.data + " = " + entry.getKey() + " : ");
            print(entry.getValue(), deep + 1);
        }
    }
}
