package Model;
import java.util.ArrayList;

public class Encarrec {

    private int id;   
    private String nomCli;
    private String telCli;
    private String dataEncarrec;
    private ArrayList<Article> articles;
    private float PreuTotal;

    public Encarrec(int id, String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles, float PreuTotal) {
        this.id = id;
        this.nomCli = nomCli;
        this.telCli = telCli;
        this.dataEncarrec = dataEncarrec;
        this.articles = articles;
        this.PreuTotal = PreuTotal;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getNomCli() {
        return nomCli;
    }


    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }


    public String getTelCli() {
        return telCli;
    }


    public void setTelCli(String telCli) {
        this.telCli = telCli;
    }


    public String getDataEncarrec() {
        return dataEncarrec;
    }


    public void setDataEncarrec(String dataEncarrec) {
        this.dataEncarrec = dataEncarrec;
    }


    public ArrayList<Article> getArticles() {
        return articles;
    }


    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

        
    public float getPreuTotal() {
        return PreuTotal;
    }


    public void setPreuTotal(float preuTotal) {
        PreuTotal = preuTotal;
    }

    @Override
    public String toString() {
        return "Encarrec [id=" + id + ", nomCli=" + nomCli + ", telCli=" + telCli + ", dataEncarrec=" + dataEncarrec
                + ", articles=" + articles + ", PreuTotal=" + PreuTotal + "]";
    }

}
