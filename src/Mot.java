public class Mot {
    int i;
    int x;
    int y;
    int val;
    boolean vertical;//true pour vertical
    String word;
    public Mot(int i, int val, String word){
        this.i=i;
        this.val=val;
        this.word = word;
    }
    public String toString(){
        String ret="";
        ret+=i;
        ret+=" ";
        ret+=x;
        ret+=" ";
        ret+=y;
        ret+=" ";
        if(vertical){
            ret+="V";
        }else{
            ret+="H";
        }
        return ret;
    }
}
