import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class AddToTheEnd_biggest_chain {
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

    public static Mot get_first_of_longest_chain() {
        int[] nb = new int[words.size()];
        for (int k = 0; k < words.size(); k++) {
            Mot m = words.get(k);
            nb[k] = 0;

            ArrayList<Mot> words_copy = new ArrayList<>();
            words_copy.addAll(words);
            words_copy.remove(m);

            boolean change;
            do {
                change = false;
                for (int i = 0; i < words_copy.size(); i++) {
                    Mot word = words_copy.get(i);
                    char first = word.word.charAt(0);
                    char last = m.word.charAt(m.word.length() - 1);

                    if (first == last) {
                        words_copy.remove(word);
                        m = word;
                        nb[i]+=1;
                        change = true;
                    }
                }
            } while (change);

        }

        // find the max
        int max = 0;
        int index = 0;
        for (int i = 0; i<nb.length; i++) {
            if (max < nb[i]) {
                max = nb[i];
                index = i;
            }
        }
        return words.get(index);
    }

    public static void algo() {
        // sort by longest word
        //words.sort((o1, o2) -> o1.word.length() > o2.word.length() ? 1 : 0);

        // sort by biggest value
        words.sort((o1, o2) -> o1.val > o2.val ? 1 : 0);

        // add first word
        first_word = get_first_of_longest_chain();
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