/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.util.*;


public class Record {
    //outlook, temp, humidity, wind
    int[] attribute = new int[ReadFromFile.attributeList.size()];
    boolean classification;
}
