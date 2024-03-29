import java.io.FileInputStream;
import java.io.InputStream;
import java.io.LineNumberInputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    static int nb_words;
    static char[][] result;
    static ArrayList<Mot> words;
    static ArrayList<Mot> words_on_grid = new ArrayList<>();
    static Mot first_word;
    static String[] entre={"1.in","2.in","3.in","4.in","5.in","6.in","7.in","8.in","9.in","10.in","12.in","13.in","11.in"};
    static String path="src/input/";

    public static void main(String[] args) {
        try{
            for(int i=0;i<entre.length;i++){
                words=new ArrayList<>();
                words_on_grid = new ArrayList<>();
                InputStream ins = new FileInputStream(path+entre[i]);
                Scanner sc=new Scanner(ins);
                parse(sc);
                algo();
                sortie("sortie"+entre[i]);
                /*for(int j=0;j<result.length;j++){
                    for(int k=0;k<result[j].length;k++){
                        System.out.print(result[j][k]);
                    }
                    System.out.println();
                }*/
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }


    public static void algo() {
        // how big should it be ?
        result = new char[nb_words*15][nb_words*15];
        for (int i = 0; i<nb_words*15; i++) {
            Arrays.fill(result[i], '-');
        }


        words.sort((o1, o2) -> o1.word.length() > o2.word.length()?1:0);

        first_word = words.get(0);
        first_word.x = 0;
        first_word.y = 0;
        first_word.vertical = false;
        words_on_grid.add(first_word);
        words.remove(first_word);
        for (int i = 0; i<first_word.word.length(); i++) {
            result[0][i] = first_word.word.charAt(i);
        }

        boolean change = true;
        while(change) {
            change = false;
            for(int k = 0; k<words.size(); k++) {
                Mot m = words.get(k);
                Mot last_word = words_on_grid.get(words_on_grid.size()-1);
                boolean ok=true;
                for (int i = 0; i<m.word.length()&&ok; i++) {
                    for (int j = 0; j<last_word.word.length(); j++) {
                        if (m.word.charAt(i) == last_word.word.charAt(j)) {
                            // letter in common, see if word fits
                            boolean res = try_fit2(m, last_word, i/* position dans mot a add */, j/* position dans last_word*/);
                            if (res) {
                                change = true; // si ya changement nouveau tour de boucle pour tester avec le dernier ajouté
                                ok=false;
                                break;
                                
                            }
                        }
                    }
                }

            }
        }
    }
    public static boolean try_fit2(Mot add, Mot last, int chari, int charj){
        add.vertical=!last.vertical;
        if(last.vertical){
            //last.x;//colone de depart du dernier mot
            //last.y;//ligne de depart du dernier mot
            int xcommun=last.x;
            int ycommun=last.y+charj;
            add.x=xcommun-chari;
            add.y=ycommun;
            if(add.x<0 || add.y<0){
                return false;
            }
            for(int i=0;i<add.word.length();i++){
                if(i!=chari){
                    if(result[add.y][add.x+i]=='-'&& (add.y-1<0|| result[add.y-1][add.x+i]=='-')&& result[add.y+1][add.x+i]=='-'){
                        result[add.y][add.x+i]=add.word.charAt(i);
                    }else{
                        return false;

                    }
                }
            }
            words_on_grid.add(add);
            words.remove(add);

            return true;
        }else{
            //last.x;//colone de depart du dernier mot
            //last.y;//ligne de depart du dernier mot
            int xcommun=last.x+charj;
            int ycommun=last.y;
            add.x=xcommun;
            add.y=ycommun-chari;
            if(add.x<0 || add.y<0){
                return false;
            }
            for(int i=0;i<add.word.length();i++){
                if(i!=chari){
                    if(result[add.y+i][add.x]=='-' && (add.x-1<0||result[add.y+i][add.x-1]=='-') && result[add.y+i][add.x+1]=='-'){
                        result[add.y+i][add.x]=add.word.charAt(i);
                    }else{
                        return false;
                    }
                }
            }
            words_on_grid.add(add);
            words.remove(add);
            return true;
        }
    }
    public static boolean try_fit_word(Mot m, Mot anchor, int m_i, int a_i) {
        if (anchor.vertical) {
            if (anchor.x - a_i < 0) {
                return false;
            }
            for (int i = 0; i<a_i; i++) {
                if (result[anchor.x - i][anchor.y] != ' ') { // not empty -> can't put word here
                    return false;
                }
            }

            words_on_grid.add(m);
            words.remove(m);

            m.vertical = false;
            m.x = anchor.x - m_i;
            m.y = anchor.y + a_i;
            System.out.println("x : " + m.x + " y : " + m.y);

            for (int i = 0; i<m.word.length(); i++) {
                result[m.x + i][m.y] = m.word.charAt(i);
            }

        } else {
            if (anchor.y - m_i < 0) {
                return false;
            }
            for (int i = 0; i<a_i; i++) {
                if (result[anchor.x][anchor.y - m_i] != ' ') { // not empty -> can't put word here
                    return false;
                }
            }

            words_on_grid.add(m);
            words.remove(m);

            m.vertical = true;
            m.x = anchor.x + a_i;
            m.y = anchor.y - m_i;
            System.out.println("x : " + m.x + " y : " + m.y);


            for (int i = 0; i<m.word.length(); i++) {
                result[m.x][m.y + i] = m.word.charAt(i);
            }
        }
        return true;
    }


    public static void parse(Scanner sc) {
        nb_words=Integer.parseInt(sc.nextLine());
        for(int i=0;i<nb_words;i++){
            String[] tmp=sc.nextLine().split(" ");
            words.add(new Mot(i,Integer.parseInt(tmp[1]),tmp[0]));
        }
    }
    public static void sortie(String filePath){
        try{
            PrintWriter writer = new PrintWriter(filePath, StandardCharsets.US_ASCII);
            writer.println(words_on_grid.size());
            writer.println(first_word);
            for(int i=0;i<words_on_grid.size();i++){
                if(words_on_grid.get(i).i!=first_word.i){
                    writer.println(words_on_grid.get(i));
                }
            }
            writer.flush();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
