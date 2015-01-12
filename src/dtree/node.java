/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtree;

import java.util.ArrayList;

/**
 *
 * @author deepak
 */
public class node {
    ArrayList<ArrayList<String>> subset=new ArrayList<ArrayList<String>>();
    ArrayList<node> leafnodes=new ArrayList<node>();
    int numleaf=0;
    String label="";
    public void addleaf(node n){
        leafnodes.add(n);
    }
    public void adlabel(String s){
        label=s;
    }
}
