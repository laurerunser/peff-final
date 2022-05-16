import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int nbmots;
    static char[][] result;
    static int nbmotresult;
    static ArrayList<Mot> resultM;
    public static void main(String[] args) {
        
    }
    public static void parse(Scanner sc) {
        nbmots=Integer.parseInt(sc.nextLine());
        mots=new String[nbmots];
        vals=new int[nbmots];
        for(int i=0;i<nbmots;i++){
            String[] tmp=sc.nextLine().split(" ");
            mots[i]=tmp[0];
            vals[i]=Integer.parseInt(tmp[1]);

        }
    }
    

}
