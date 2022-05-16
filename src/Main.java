import java.io.FileInputStream;
import java.io.InputStream;
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
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }


    public static void algo() {
        // how big should it be ?
        result = new char[nb_words*100][nb_words*100];
        for (int i = 0; i<nb_words*100; i++) {
            Arrays.fill(result[i], ' ');
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

                for (int i = 0; i<m.word.length(); i++) {
                    for (int j = 0; j<last_word.word.length(); j++) {
                        if (m.word.charAt(i) == last_word.word.charAt(j)) {
                            // letter in common, see if word fits
                            boolean res = try_fit_word(m, last_word, i, j);
                            if (res) {
                                change = true;
                            }
                        }
                    }
                }

            }
        }
    }

    public static boolean try_fit_word(Mot m, Mot anchor, int m_i, int a_i) {
        if (a_i - m_i < 0) {
            return false; // out of bounds of the grid
        }
        if (anchor.vertical) {
            for (int i = 0; i<a_i; i++) {
                if (result[anchor.x][i] != ' ') { // not empty -> can't put word here
                    return false;
                }
            }

            words_on_grid.add(m);
            words.remove(m);

            m.vertical = false;
            m.x = a_i - m_i;
            m.y = anchor.y;
            System.out.println("x : " + m.x + " y : " + m.y);

            for (int i = 0; i<m.word.length(); i++) {
                result[m.x + i][m.y] = m.word.charAt(i);
            }

        } else {
            for (int i = 0; i<a_i; i++) {
                if (result[i][anchor.y] != ' ') { // not empty -> can't put word here
                    return false;
                }
            }

            words_on_grid.add(m);
            words.remove(m);

            m.vertical = true;
            m.x = anchor.x;
            m.y = a_i - m_i;

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
