import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int nbmots;
    static char[][] result;
    static int nbmotresult;
    static ArrayList<Mot> mots;
    public static void main(String[] args) {
        
    }
    public static void parse(Scanner sc) {
        nbmots=Integer.parseInt(sc.nextLine());
        for(int i=0;i<nbmots;i++){
            String[] tmp=sc.nextLine().split(" ");
            mots.add(new Mot(i,Integer.parseInt(tmp[1]),tmp[0]));
        }
    }
    

}
