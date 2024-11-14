package App;

import java.util.ArrayList;

import Model.Article;
import Model.Encarrec;

public class GestioEncarrec {

        public float CalculaPreuTotal(ArrayList<Article> articles) {

        float PreuTotal = 0;

        for (Article art:articles) {
            PreuTotal = PreuTotal + art.getnombreUnitats()*art.getPreuUnitat();
        }

        return PreuTotal;
    }

    public String toCSV(Encarrec encarrec) {
        StringBuilder cliDet = new StringBuilder();
        cliDet.append(encarrec.getNomCli()).append(";")
              .append(encarrec.getTelCli()).append(";")
              .append(encarrec.getDataEncarrec()).append(";");
    
        // Usar StringBuilder para añadir cada artículo
        encarrec.getArticles().forEach(article -> cliDet.append(article.toCSV()));

        cliDet.append(";").append(encarrec.getPreuTotal()).append(";");
    
        return cliDet.toString();
    }

}
