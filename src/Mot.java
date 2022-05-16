public class Mot {
    int i;
    int x;
    int y;
    int val;
    boolean vertical;//true pour vertical
    boolean grid=false;
    String mot;
    public Mot(int i, int val,String mot){
        this.i=i;
        this.val=val;
        this.mot=mot;
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
