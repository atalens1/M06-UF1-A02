package App;

import java.util.ArrayList;

import Model.Article;

public class GestioEncarrec {

        public float CalculaPreuTotal(ArrayList<Article> articles) {

        float PreuTotal = 0;

        for (Article art:articles) {
            PreuTotal = PreuTotal + art.getnombreUnitats()*art.getPreuUnitat();
        }

        return PreuTotal;
    }
}
