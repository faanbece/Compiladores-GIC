/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author faanbece
 */
public class CompiLab2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<String> gram=new ArrayList<>();
         /*        para probar Factorizacion     */  
        //gram.add("E->ECHA|ECHE|ECHI|NOJODA");
        // gram.add("P->iEtS|iEtSeS|iEtSe|iE|a");
        //gram.add("P->EiSt|EiStS|EiStSeS|a|Ei");
        //gram.add("P->EiSt|EiStS|EiStSeS|a|Ei");
        gram.add("P->Ei|Eia|Eib");
        //gram.add("E->E+E|E*E|i");
        //gram.add("S->iEtS|iEtSeS|a");
        
        //gram.add("E->b");
        
        /*gram.add("E->E+T|T");     
        //gram.add("E->T+E|T");
        gram.add("T->T*F|F");
        gram.add("F->(E)|id");
        */
        
        /*ArrayList<String> sol=new ArrayList<>();
        sol.add("S->iEtSS'|a");
        sol.add("S'->eS|€");
        sol.add("E->b");
        */
        /*        para probar Factorizacion       
        gram.add("S->iEtS|iEtSeS|a");
        gram.add("E->b");
        
        ArrayList<String> sol=new ArrayList<>();
        sol.add("S->iEtSS'|a");
        sol.add("S'->eS|€");
        sol.add("E->b");*/
        /*
        Para probar recursividad
        gram.add("E->E+T|T");
        //gram.add("E->T+E|T");
        gram.add("T->T*F|F");
        gram.add("F->(E)|id");
        
        sol.add("E->TE'");
        sol.add("E'->+TE'|€");
        sol.add("E->T");
        sol.add("T->FT'|F");
        sol.add("T'->*FT'|€");
        sol.add("F->(E)|id");
        */
        
       // gram=Divider(gram);/*toca crear una funcion que verifique que fue bien escrita la gramatica*/
        System.out.println("Original : ");
        showArray(gram,false);
        
        gram=noRecursive(gram);
        System.out.println("Sin Recursividad (Izq): ");
        showArray(gram,false);
        
        //showArray(gram,false);
        gram=factorize(gram);
        System.out.println("Factorizada (Izq): ");
        showArray(gram,false);

        // noRecursive(gram);
    }
    
    public static void showArray(ArrayList<String> array, boolean oneLine){
        System.out.println("................");
        if (oneLine) {
                System.out.println("{");
            for (String e : array) {
                System.out.print(e+",");
            }
            System.out.print("\b}\n");
        }else{
            for (String e : array) {
                System.out.println(e);
            }
        }
        System.out.println("................");
    }
    /**
     *Resive una gramatica y la separa en producciones independientes.
     * @param gram
     * @return 
     */
    public static ArrayList<String> divider(ArrayList<String> gram){
        String gem, nogem;
        String[] exp;
        ArrayList<String> R= new ArrayList<>();
        for (String prod : gram) {
            gem=prod.split("->")[0];
            nogem=prod.split("->")[1];            
            exp=nogem.split("\\|");
            for (String term : exp) {
                R.add(gem+"->"+term);
                System.out.println(gem+"->"+term);
            }
        }
        return R;
    }    
    public static ArrayList<String> compacter(ArrayList<String> gram){
        String gem, nogem;
        String[] exp;
        ArrayList<String> R= new ArrayList<>();
        for (String prod : gram) {
            gem=prod.split("->")[0];
            nogem=prod.split("->")[1];            
            exp=nogem.split("\\|");
            for (String term : exp) {
                R.add(gem+"->"+term);
                System.out.println(gem+"->"+term);
            }
        }
        return R;
    }
    public static ArrayList<String> noRecursive(ArrayList<String> grama){
        String gem, nogem, singem,prod, alfa, beta;        
        ArrayList<String> gram=(ArrayList<String>) grama.clone();
        String[] subnogem;
        for (int i = 0; i < gram.size(); i++) {
            prod=gram.get(i);
            gem=prod.split("->")[0];
            nogem=prod.split("->")[1]; 
            if (nogem.indexOf(gem)==0 && (nogem.indexOf(gem+"'") != nogem.indexOf(gem) )) {
                subnogem=nogem.split("\\|");
                alfa="";
                beta="";
                for (String p : subnogem) {
                    if (p.indexOf(gem)==0 && (p.indexOf(gem+"'") != p.indexOf(gem))) { 
                        /*
                        //beta+=element.replaceFirst(f, "")+"|";//Para usar CON singos de agrupacion
                        beta+=element.replaceFirst(f, "")+gem+"'|";//Para usar SIN singos de agrupacion
                        */
                        //p=p.replaceFirst(gem, "").replace(gem,gem+"'");                        
                        alfa+=p.replaceFirst(gem, "").replace(gem,gem+"'")+"|";                        
                    }else{
                        beta+=p+gem+"'|";
                    }
                }
                gram.remove(prod);                
                gram.add(i,   gem+"->"+beta+"\b");               
                gram.add(i+1, gem+"'"+"->"+alfa+"€");
            }
        }
        return gram;
    }
    public static ArrayList<String> factorize(ArrayList<String> grama){
        String gem, nogem, eval,compare, beta, alfa, gamma, element;        
        ArrayList<String> gram=(ArrayList<String>) grama.clone();
        String[] subnogem,prod;
        
        for (int h = 0; h < gram.size(); h++) {
            gem=gram.get(h).split("->")[0];
            nogem=gram.get(h).split("->")[1]; 
            
            //gram=factor(gram,gem,nogem);
           // ArrayList<String> gram=(ArrayList<String>) gramatic.clone();
            prod=nogem.split("\\|");
            ArrayList<String> production=vectToArray(prod);
            Stack<String> factor= new Stack<String>();
            Stack<Integer> numFactor= new Stack<Integer>();
            eval="";
            compare=""; 
            gamma="";
            beta="";
            int count=0, index=0;
           // production.sort(null);
            
           // production=reverseArray(production);
            production=sortByLength(production);
            production=reverseArray(production);

            for (int k = 0; k < production.size(); k++) {            
                compare=production.get(k);
                //System.out.println("Production : "+production);
                //System.out.println("compare : "+compare);
                for (int i = 0; i < compare.length(); i++) {
                    eval=compare.substring(0,i+1);
                    count=0;
                    element="";
                    for (int j = 0; j < production.size(); j++) {
                        element=production.get(j);
                        if (element.indexOf(eval)==0) { 
                            count++;   
                        }
                    }
                    if (count>1) {
                        factor.push(eval);
                        numFactor.push(count);
                    }
                }
                if (!factor.isEmpty()) {
                    System.out.println("Factor : "+factor.peek() );
                    //System.out.println("NumFactor : "+numFactor.peek() );
                    String f=factor.peek();
                    //System.out.println("Se quita : "+gem+"->"+nogem);//+"   en : "+gram.indexOf(gem+"->"+nogem));
                    index=gram.indexOf(gem+"->"+nogem);
                    gram.remove(gem+"->"+nogem);
                    beta="";
                    gamma="";
                    element="";
                    for (int i=0; i<production.size(); i++) {
                        element= production.get(i);
                        if (element.indexOf(f)==0) { 
                            alfa=element.replaceFirst(f, "");
                            if (alfa.length()>0) {
                                i--;
                                production.remove(element);
                                beta+=alfa+"|";
                            }else{
                                beta+="€ "; 
                            }
                        }else{
                            gamma+="|"+element;
                        }
                    }

                    gram.add(index,gem+"->"+f+gem+"'"+gamma);
                    gram.add(index+1,gem+"'"+"->"+beta+"\b");

                    gem=gram.get(h).split("->")[0];
                    nogem=gram.get(h).split("->")[1]; 
                    factor.clear();
                    numFactor.clear();
                    
                    h--;
                    k=production.size();
                    
                    showArray(gram, false);                    
                }
            }     
        }
              
        return gram;
    }  
    public static ArrayList<String> factor(ArrayList<String> gramatic,String gem,String nogem){
        ArrayList<String> gram=(ArrayList<String>) gramatic.clone();
        String[] prod=nogem.split("\\|");
        ArrayList<String> production=vectToArray(prod);
        Stack<String> factor= new Stack<String>();
        Stack<Integer> numFactor= new Stack<Integer>();
        String eval="", compare="", gamma="",beta="";
        int count=0, index=0;
        production.sort(null);
        production=reverseArray(production);
        production=sortByLength(production);
        production=reverseArray(production);
        
        
        
        
        for (int k = 0; k < production.size(); k++) {            
            compare=production.get(k);
            //System.out.println("Production : "+production);
            //System.out.println("compare : "+compare);
            for (int i = 0; i < compare.length(); i++) {
                eval=compare.substring(0,i+1);
                count=0;
                beta="";
                gamma="";
                for (String element: production) {
                    if (element.indexOf(eval)==0) { 
                        count++;   
                    }
                }
                if (count>1) {
                    factor.push(eval);
                    numFactor.push(count);
                }
            }
            if (!factor.isEmpty()) {
                k--;
                System.out.println("Factor : "+factor.peek() );
                System.out.println("NumFactor : "+numFactor.peek() );
                String f=factor.peek();
                System.out.println("Se quita : "+gem+"->"+nogem);//+"   en : "+gram.indexOf(gem+"->"+nogem));
                index=gram.indexOf(gem+"->"+nogem);
                gram.remove(gem+"->"+nogem);
                beta="";
                gamma="";
                for (int i=0; i<production.size(); i++) {
                    String element= production.get(i);
                    if (element.indexOf(f)==0) { 
                        count++;     
                        if (element.replaceFirst(f, "").length()>0) {
                            production.remove(element);
                            beta+=element.replaceFirst(f,"")+"|";
                        }
                        if (element.replaceFirst(f, "").length()==0) {
                            beta+="€ "; 
                        }
                    }else{
                        gamma+="|"+element;
                    }
                }
                
                gram.add(index,gem+"->"+f+gem+"'"+gamma);
                gram.add(index+1,gem+"'"+"->"+beta+"\b");
                
                
                System.out.println("Para poner : ");
                System.out.println("   "+gem+"->"+f.replaceAll(gem, gem+"'")+gem+"'"+gamma);
                System.out.println("   "+gem+"'"+"->"+beta+"\b");
            }
        }       
        return gram;
    }
    /*
    ----------- ZONA DE ALTA EMPANADA  -------------------------
    */
    public static ArrayList<String> sortByLength(ArrayList<String> ToSort){
         ArrayList<String> toSort=(ArrayList<String>) ToSort.clone();
         
         class StringLengthListSort implements Comparator<String>{
            @Override
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
         }
        StringLengthListSort ss = new StringLengthListSort();
        toSort.sort(ss);
        return  toSort;
    }   
    public static ArrayList<String> vectToArray(String[] vect){
        ArrayList<String> array= new ArrayList<>();
        array.addAll(Arrays.asList(vect));
        return array;
    }    
    public static ArrayList<String> reverseArray(ArrayList<String> toReverse){
        ArrayList<String> reverse=new ArrayList<String>();
        int size=toReverse.size();
        for (int i = size-1; i >=0 ; i--) {
            reverse.add(toReverse.get(i));
        }
        return reverse;
    }    
    public static String reverse(String source) {
        int i, len = source.length();
        StringBuilder dest = new StringBuilder(len);
        for (i = (len - 1); i >= 0; i--){
            dest.append(source.charAt(i));
        }
        return dest.toString();
    }
}
