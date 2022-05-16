import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class AddToTheEnd {
    static int nb_words;
    static ArrayList<Mot> words;
    static ArrayList<Mot> words_on_grid = new ArrayList<>();
    static Mot first_word;
    static String[] entre = {"1.in", "2.in", "3.in", "4.in", "5.in", "6.in", "7.in", "8.in", "9.in", "10.in", "12.in", "13.in", "11.in"};
    static String path = "src/input/";

    public static void main(String[] args) {
        try {
            for (int i = 0; i < entre.length; i++) {
                words = new ArrayList<>();
                words_on_grid = new ArrayList<>();
                InputStream ins = new FileInputStream(path + entre[i]);
                Scanner sc = new Scanner(ins);
                parse(sc);
                algo();
                sortie("sortie" + entre[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void algo() {
        words.sort((o1, o2) -> o1.word.length() > o2.word.length() ? 1 : 0);

        // add first word
        first_word = words.get(0);
        first_word.x = 0;
        first_word.y = 0;
        first_word.vertical = false;
        words_on_grid.add(first_word);
        words.remove(first_word);

        // add the next ones
        Mot last_word = first_word;
        boolean change = true;
        while (change) {
            change = false;
            for (int k = 0; k < words.size(); k++) {
                Mot m = words.get(k);

                char m_first = m.word.charAt(0);
                char last_char = last_word.word.charAt(last_word.word.length()-1);

                if (m_first == last_char) {
                    m.vertical = !last_word.vertical;

                    if(last_word.vertical) {
                        m.x = last_word.x;
                        m.y = last_word.y + last_word.word.length();
                    } else {
                        m.x = last_word.x + last_word.word.length();
                        m.y = last_word.y;
                    }

                    words.remove(m);
                    words_on_grid.add(m);
                    last_word = m;
                    change = true;
                }
            }

        }
    }


    public static void parse(Scanner sc) {
        nb_words = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < nb_words; i++) {
            String[] tmp = sc.nextLine().split(" ");
            words.add(new Mot(i, Integer.parseInt(tmp[1]), tmp[0]));
        }
    }

    public static void sortie(String filePath) {
        try {
            PrintWriter writer = new PrintWriter(filePath, StandardCharsets.US_ASCII);
            writer.println(words_on_grid.size());
            writer.println(first_word);
            for (int i = 0; i < words_on_grid.size(); i++) {
                if (words_on_grid.get(i).i != first_word.i) {
                    writer.println(words_on_grid.get(i));
                }
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}