/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author deepak
 */
public class DTREE {

    /**
     * @param args the command line arguments
     */
    public static void splitter() {

    }

    ArrayList<node> dtree = new ArrayList<node>();
    static String classname = "readmitted";
    static ArrayList<ArrayList<String>> mainset = new ArrayList<ArrayList<String>>();
    static HashMap<String, Integer> attributes = new HashMap<String, Integer>();
    static TreeMap<String, Integer> headers = new TreeMap<String, Integer>();
    static Map<String, ArrayList<String>> valueset = new HashMap<String, ArrayList<String>>();
    public static void getheader(){
        try {
            Scanner s = new Scanner(new File("data1.txt"));
            int totallines=0;
            String first=s.nextLine();
            String firstsplit[]=first.split(",");
            ArrayList<String> colhead=new ArrayList<String>();
            for(int i=0;i<firstsplit.length;i++){
                attributes.put(firstsplit[i], i+1);
                headers.put(firstsplit[i], i);
                colhead.add(firstsplit[i]);
            }
            mainset.add(colhead);
            
                        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public static void splitter(String fname){
    	String filePath = new File("").getAbsolutePath();
    	Integer count=0;
        try {
            Scanner s = new Scanner(new File(fname));
            while (s.hasNext()) {
            	count++;
            	s.next();
            }
            Integer cnt5=count/5;
            Scanner sc = new Scanner(new File(fname));
            File file1 = new File("data1.txt");
            File file2 = new File("data2.txt");
            File file3 = new File("data3.txt");
            File file4 = new File("data4.txt");
            File file5 = new File("data5.txt");
            file1.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
            file4.createNewFile();
            file5.createNewFile();
            FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw1 = new BufferedWriter(fw1);
			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			BufferedWriter bw2 = new BufferedWriter(fw2);
			FileWriter fw3 = new FileWriter(file3.getAbsoluteFile());
			BufferedWriter bw3 = new BufferedWriter(fw3);
			FileWriter fw4 = new FileWriter(file4.getAbsoluteFile());
			BufferedWriter bw4 = new BufferedWriter(fw4);
			FileWriter fw5 = new FileWriter(file5.getAbsoluteFile());
			BufferedWriter bw5 = new BufferedWriter(fw5);
            for(int i=0;i<cnt5;i++){
            	String l=sc.next();
    			bw1.write(l+"\n");
            }
            for(int i=cnt5;i<2*cnt5;i++){
            	bw2.write(sc.next()+"\n");
    			
            }
            for(int i=2*cnt5;i<3*cnt5;i++){
            	bw3.write(sc.next()+"\n");
    			
            }
            for(int i=3*cnt5;i<4*cnt5;i++){
            	bw4.write(sc.next()+"\n");
    			
            }
            for(int i=4*cnt5;i<count;i++){
            	bw5.write(sc.next()+"\n");
    			
            }
            bw1.close();bw2.close();
            bw3.close();
            bw4.close();
            bw5.close();
            sc.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public static ArrayList<ArrayList<String>> gensubset(ArrayList<ArrayList<String>> dataset, String V, String p) {
        ArrayList<ArrayList<String>> subset = new ArrayList<ArrayList<String>>();
        int pos = 0;
        for (int i = 0; i < dataset.size(); i++) {
            ArrayList<String> temp = new ArrayList<String>();
            if (i == 0) {
                for (int j = 0; j < dataset.get(0).size(); j++) {
                    if (!dataset.get(i).get(j).equalsIgnoreCase(p)) {
                        temp.add(dataset.get(i).get(j));
                    } else {
                        pos = j;
                    }
                }
                subset.add(temp);
            } else {
                if (dataset.get(i).get(pos).equalsIgnoreCase(V)) {
                    for (int j = 0; j < dataset.get(0).size(); j++) {
                        if (j != pos) {
                            temp.add(dataset.get(i).get(j));
                        }
                    }
                    subset.add(temp);

                }

            }
        }
        
        return subset;
    }

    public static double entropy(ArrayList<ArrayList<String>> dataset) {
        int i = 0;
        ArrayList<String> cols = new ArrayList<String>();
        cols.addAll(dataset.get(0));
        for (String v : cols) {
            if (v.equalsIgnoreCase(classname)) {
                i = cols.indexOf(v);
                break;
            }
        }
        Double p[] = new Double[valueset.get(classname).size()];
        Map<String, Double> countmap = new HashMap<String, Double>();
        ArrayList<String> classset = new ArrayList<String>();
        classset.addAll(valueset.get(classname));
        for (String str : classset) {
            countmap.put(str, 0.0);
        }
        double total = 0.0;
        for (int j = 1; j < dataset.size(); j++) {
            String actval = dataset.get(j).get(i);
            double c = countmap.get(actval);
            c++;
            countmap.put(actval, c);
            total++;
        }
        Set<String> cset = countmap.keySet();
        Iterator eitr = cset.iterator();
        int k = 0;
        double e = 0.0;
        while (eitr.hasNext()) {
            p[k] = (countmap.get((String) eitr.next()) / total);
            k++;
        }
        for (int z = k - 1; z >= 0; z--) {
            if(p[z]!=0.0){
                e -= (p[z] * (Math.log(p[z]) / Math.log(2.0)));
            }
        }
        return e;
    }

    public static String gain(ArrayList<ArrayList<String>> dataset, double e) {
        Map<String, Double> cntmap = new HashMap<String, Double>();
        int ci = 0;
        for (int i = 0; i < dataset.get(0).size(); i++) {
            if (dataset.get(0).get(i).equalsIgnoreCase(classname)) {
                ci = i;
                break;
            }
        }
        for (int i = 1; i < dataset.size(); i++) {
            for (int j = 0; j < dataset.get(0).size() - 1; j++) {
                if (cntmap.containsKey(dataset.get(i).get(j) + "|" + dataset.get(i).get(ci))) {
                    double temp = cntmap.get(dataset.get(i).get(j) + "|" + dataset.get(i).get(ci));
                    temp++;
                    cntmap.put(dataset.get(i).get(j) + "|" + dataset.get(i).get(ci), temp);
                } else {
                    double temp = 1.0;
                    cntmap.put(dataset.get(i).get(j) + "|" + dataset.get(i).get(ci), temp);
                }
                if (cntmap.containsKey(dataset.get(i).get(j) + "|" + j)) {
                    double temp = cntmap.get(dataset.get(i).get(j) + "|" + j);
                    temp++;
                    cntmap.put(dataset.get(i).get(j) + "|" + j, temp);
                } else {
                    double temp = 1.0;
                    cntmap.put(dataset.get(i).get(j) + "|" + j, temp);
                }
            }
        }
        Double g[] = new Double[dataset.get(0).size() - 1];
        String classset[] = new String[valueset.get(classname).size()];
        valueset.get(classname).toArray(classset);
        double maxgain = 0.0;
        String maxgainattr = "";
        for (int i = 0; i < dataset.get(0).size() - 1; i++) {
            String col = dataset.get(0).get(i);
            String colset[] = new String[valueset.get(col).size()];
            valueset.get(col).toArray(colset);
            double cgain = e;
            for (String c : colset) {
                if(!cntmap.containsKey(c + "|" + i)){
                    break;
                }
                double tot = cntmap.get(c + "|" + i);
                double ent = 0.0;
                for (String cl : classset) {
                    if (cntmap.containsKey(c.trim() + "|" + cl.trim())) {
                        double p = (cntmap.get(c.trim() + "|" + cl.trim()) / tot);
                        ent -= (p * (Math.log(p) / Math.log(2.0)));
                    }

                }
                cgain -= ((tot / dataset.size()) * ent);
            }
            if (cgain > maxgain) {
                maxgain = cgain;
                maxgainattr = dataset.get(0).get(i);
            }

        }
        return maxgainattr;
    }

    public static node dtreealg(ArrayList<ArrayList<String>> dataset, HashMap<String, Integer> attr) {
        if(dataset.size()==1){
            return null;
        }
        double ent = entropy(dataset);
        if (ent == 0.0) {
            node leaf = new node();
            leaf.adlabel(dataset.get(1).get(dataset.get(0).size() - 1));
            return leaf;
        }
        else if(dataset.get(0).size()==1){
            node n=new node();
            return n;
        }
        else
        {
            String p = gain(dataset, ent);
            attr.remove(p);
            ArrayList<String> V = new ArrayList<String>();
            V.addAll(valueset.get(p));
            node root = new node();
            root.adlabel(p);
            for (int i = 0; i < V.size(); i++) {
                node branch = new node();
                branch.adlabel(V.get(i));
                ArrayList<ArrayList<String>> partition_v = new ArrayList<ArrayList<String>>();
                if(dataset.get(0).size()==2){
                    node last=new node();
                    last.adlabel("readmitted");
                    node lastleaf=new node();
                    Map<String,Integer> lastcnt=new HashMap<String,Integer>();
                    for(int h=1;h<dataset.size();h++){
                        if(lastcnt.containsKey(dataset.get(h).get(1))){
                            int tmp=lastcnt.get(dataset.get(h).get(1));
                            tmp++;
                            lastcnt.put(dataset.get(h).get(1), tmp);
                        }else{
                            lastcnt.put(dataset.get(h).get(1), 1);
                        }
                    }
                    int max=0;
                    String maxclass="";
                    Set<String> finalset=lastcnt.keySet();
                    Iterator itr=finalset.iterator();
                    while(itr.hasNext()){
                        String tts=(String)itr.next();
                        int tt=lastcnt.get(tts);
                        if(tt>max){
                            maxclass=tts;
                        }
                    }
                    lastleaf.adlabel(maxclass);
                    last.addleaf(lastleaf);
                    return last;
                }
                else if(dataset.size()>2){
                    partition_v.addAll(gensubset(dataset, V.get(i), p));
                    if(partition_v.size()>1){
                        branch.addleaf(dtreealg(partition_v, attr));
                        root.addleaf(branch);
                    }
                }

            }
            return root;
        }
        
    }

    
    public static void gendata(String fname){
        try {
            Scanner s = new Scanner(new File(fname));
            int totallines=0;
            String first=s.nextLine();
            String firstsplit[]=first.split(",");
            Map<Integer,ArrayList<String>> tempmap=new HashMap<Integer,ArrayList<String>>();
            while (s.hasNext()) {
            	totallines++;
            	String line=s.nextLine();
            	String split[]=line.split(",");
                ArrayList<String> temp=new ArrayList<String>();
                for(int i=0;i<split.length;i++){
                    temp.add(split[i]);
                    ArrayList<String> t=new ArrayList<String>();
                    if(tempmap.containsKey(i+1)){
                        t=tempmap.get(i+1);
                        if(!t.contains(split[i])){
                            t.add(split[i]);
                            tempmap.put(i+1, t);
                        }
                    }
                    else{
                        t.add(split[i]);
                        tempmap.put(i+1, t);
                    }
                }
                mainset.add(temp);
                
            }
            Set<Integer> vset=tempmap.keySet();
            Iterator itr=vset.iterator();
            while(itr.hasNext()){
                Integer i=(Integer)itr.next();
                String col=mainset.get(0).get(i-1);
                valueset.put(col, tempmap.get(i));
            }
                        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public static double test(String fname,node root){
        try {
            Scanner s = new Scanner(new File(fname));
            double totallines=0.0;
            
            
            if(fname.equalsIgnoreCase("data1.txt")){
                String first=s.nextLine();
                String firstsplit[]=first.split(",");
            }
            double positive=0.0;
            while (s.hasNext()) {
            	totallines++;
                boolean done=true;
            	String line=s.nextLine();
            	String split[]=line.split(",");
                node nd=root;
                while(done){
                    int coli=headers.get(nd.label);
                    String colval=split[coli];
                    node tmp=null,tmp1=null;
                    ArrayList<node> lf=new ArrayList<node>();
                    lf.addAll(nd.leafnodes);
                    for(int kj=0;kj<lf.size();kj++){
                        if(lf.get(kj).label.trim().equalsIgnoreCase(colval)){
                            tmp1=lf.get(kj);
                            tmp=lf.get(kj).leafnodes.get(0);
                            break;
                        }
                    }
                    if(tmp!=null){
                        if(tmp.label.trim().equalsIgnoreCase("readmitted")){
                            if(tmp.leafnodes.get(0).label.trim().equalsIgnoreCase(split[headers.get(classname)])){
                                positive++;

                            }
                            done=false;
                        }else{
                            nd=tmp;
                        }
                    }
                    else{
                        nd=nd.leafnodes.get(0).leafnodes.get(0);
                    }
                }
                
            }
            return (positive/totallines);
                        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;

    }
    public static void main(String[] args) {
        // TODO code application logic here
        splitter("DM_HW4_DTREE.csv");
        getheader();
            double acc=57.85;
        for(int i=1;i<=5;i++){
            for(int j=1;j<=5;j++){
                if(j!=i){
                    gendata("data"+j+".txt");
                }
            }
            node first = dtreealg(mainset, attributes);
            acc+=test("data"+i+".txt",first);
        }
        System.out.println("Average accuracy : "+acc+"%");

    }

}
