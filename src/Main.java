import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int nbmots;
    static char[][] result;
    static int nbmotresult;
    static ArrayList<Mot> mots;
    static Mot first;
    static String[] entre={"1.in","2.in","3.in","4.in","5.in","6.in","7.in","8.in","9.in","10.in","12.in","13.in","11.in"};
    static String path="src/input/";
    public static void main(String[] args) {
        mots=new ArrayList<Mot>();

        first=new Mot(0,5,"AA");
        first.x=0;
        first.y=0;
        first.grid=true;
        first.vertical=false;
        Mot second=new Mot(1, 10, "TEST");
        
        second.grid=true;
        second.x=10;
        second.y=10;
        second.vertical=true;

        Mot third=new Mot(1, 15, "TED");
        mots.add(first);
        mots.add(second);
        mots.add(third);
        sortie("test");

    }
    public static void parse(Scanner sc) {
        nbmots=Integer.parseInt(sc.nextLine());
        for(int i=0;i<nbmots;i++){
            String[] tmp=sc.nextLine().split(" ");
            mots.add(new Mot(i,Integer.parseInt(tmp[1]),tmp[0]));
        }
    }
    public static void sortie(String filePath){
        try{

            PrintWriter writer = new PrintWriter(filePath, StandardCharsets.US_ASCII);
            writer.println(nbmotresult);
            writer.println(first);
            for(int i=0;i<mots.size();i++){
                if(mots.get(i).i!=first.i && mots.get(i).grid){
                    writer.println(mots.get(i));
                }
            }
            writer.flush();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
