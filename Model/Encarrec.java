package Model;
import java.util.ArrayList;

public class Encarrec {

    String nomCli;
    String telCli;
    String dataEncarrec;
    ArrayList<Article> articles;

    
    public Encarrec(String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles) {
        this.nomCli = nomCli;
        this.telCli = telCli;
        this.dataEncarrec = dataEncarrec;
        this.articles = articles;
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


    @Override
    public String toString() {
        return "Encarrec [nomCli=" + nomCli + ", telCli=" + telCli + ", dataEncarrec=" + dataEncarrec + ", articles="
                + articles + "]";
    }

    public String toCSV() {
        StringBuilder cliDet = new StringBuilder();
        cliDet.append(nomCli).append(";")
              .append(telCli).append(";")
              .append(dataEncarrec).append(";");
    
        // Usar StringBuilder para añadir cada artículo
        articles.forEach(article -> cliDet.append(article.toCSV()));
    
        return cliDet.toString();
    }

}
