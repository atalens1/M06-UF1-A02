package Model;

public class Article {

    private String nomArticle;
    private float nombreUnitats;
    private String tipusUnitat;
    private float preuUnitat;

    public Article () {
    
    }

    public Article(String nomArticle, float nombreUnitats, String tipusUnitat) {

        ValidatenomArticle(nomArticle);
        ValidateUnitats(nombreUnitats);
        ValidatetipusUnitat(tipusUnitat);


        this.nomArticle = nomArticle;
        this.nombreUnitats = nombreUnitats;
        this.tipusUnitat = tipusUnitat;

    }

    public Article(String nomArticle, float nombreUnitats, String tipusUnitat, float preuUnitat) {

        ValidatenomArticle(nomArticle);
        ValidateUnitats(nombreUnitats);
        ValidatetipusUnitat(tipusUnitat);
        ValidatepreuUnitat(preuUnitat);


        this.nomArticle = nomArticle;
        this.nombreUnitats = nombreUnitats;
        this.tipusUnitat = tipusUnitat;
        this.preuUnitat = preuUnitat;

    }

    public float getnombreUnitats() {
        return nombreUnitats;
    
    }
    public void setnombreUnitats(float nombreUnitats) {

        ValidateUnitats(nombreUnitats);

        this.nombreUnitats = nombreUnitats;
    }

    public String gettipusUnitat() {
        return tipusUnitat;
    }

    public void settipusUnitat(String tipusUnitat) {

        ValidatetipusUnitat(tipusUnitat);

        this.tipusUnitat = tipusUnitat;
    }

    public String getNomArticle() {
        return nomArticle;
    }
    
    public void setNomArticle(String nomArticle) {

        ValidatenomArticle(nomArticle);
        
        this.nomArticle = nomArticle;
    }

    public float getPreuUnitat() {
        return preuUnitat;
    }

    public void setPreuUnitat(float preuUnitat) {
        this.preuUnitat = preuUnitat;
    }

    @Override
    public String toString() { 
        return "Article [article=" + nomArticle + ", nombreUnitats=" + nombreUnitats + ", tipusUnitat=" + tipusUnitat
                + "]";
    }

    public String toCSV() {
        return nomArticle + ";" + nombreUnitats + ";" + tipusUnitat + ";" ;
    }

    private void ValidateUnitats(float nombreUnitats) {
        if (nombreUnitats < 0) {
            throw new ArithmeticException("Quantitat no vàlida");
        }
    }

    private void ValidatepreuUnitat(float preuUnitat) {
        if (preuUnitat < 0) {
            throw new ArithmeticException("Quantitat no vàlida");
        }
    }

    private void ValidatetipusUnitat(String tipusUnitat) {
        if ((tipusUnitat == null) || !(tipusUnitat.matches("^[a-zA-Z]+$")))  {
            throw new IllegalArgumentException("Unitats no vàlides");
        }
    }

    private void ValidatenomArticle(String nomArticle) {
        if (nomArticle == null)   {
            throw new IllegalArgumentException("Nom d'article no vàlid");
        }
    }
    
}
