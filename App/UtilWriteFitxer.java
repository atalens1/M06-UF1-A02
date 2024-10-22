package App;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import Model.Article;
import Model.Encarrec;

public class UtilWriteFitxer {

    public void TextMultiLinea(String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles, String fileName) {
    /* Aquest mètode s'encarrega d'agafar els detalls de l'encarrec i formatar-lo a un fitxer pla,
     * el qual no és un csv sinó una mena de comprovant pel client
     */

        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName))) {
            
            bw1.write("Nom del client: " + nomCli);
            bw1.newLine();
            bw1.write("Telefon del client: " + telCli);
            bw1.newLine();
            bw1.write("Data de l'encarrec: " + dataEncarrec);
            bw1.newLine();
            bw1.write(String.format("%-15s %-10s %-15s%n", "Quantitat","Unitats","Article"));
            bw1.write(String.valueOf("=").repeat(15)+" " +String.valueOf("=").repeat(10)+" "+String.valueOf("=").repeat(15));
            bw1.newLine();
            for (Article article:articles) {
                bw1.write(String.format("%-15s %-10s %-15s%n",article.getnombreUnitats(),article.gettipusUnitat(),article.getNomArticle()));
                bw1.newLine();
            }

        } catch (Exception e) {
            System.out.println("Error");
        } 

    }

    public void csvLinea(String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles, String fileName) {
    /* Aquest mètode s'encarrega d'agafar els detalls de l'encarrec i formatar-lo a un fitxer pla separat per comes (csv)
     */
        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName))){

            String csvArticles = "";

            String csvLinea = nomCli + ";" + telCli + ";" + dataEncarrec + ";";

            for (Article article : articles) {
                csvArticles =  csvArticles + 
                            article.getNomArticle() + ";" + 
                            article.getnombreUnitats() + ";" +
                            article.gettipusUnitat() + ";" ;
            }

            bw1.write(csvLinea + csvArticles);

            
        } catch (Exception e) {
            System.out.println("Error");
        }

    }

    public void csvLineaObjEn(Encarrec encarrec, String fileName) {
    /* Aquest mètode és una alternativa per si es fa servir una classe anomenada Encarrec, la qual 
     * fa servir la classe Article
     */
        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName))){

            bw1.write(encarrec.toCSV());
            
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    public void binari (String nomCli, String telCli, String dataEncarrec, ArrayList<Article> articles, String fileName) {
    /* Aquest mètode s'encarrega d'agafar els detalls de l'encarrec i formatar-lo a un fitxer binari
     */

        try (DataOutputStream ds1 = new DataOutputStream(new FileOutputStream(fileName))) {

            ds1.writeUTF(nomCli);
            ds1.writeUTF(telCli);
            ds1.writeUTF(dataEncarrec);
            
            for (Article article:articles) {
                ds1.writeUTF(article.getNomArticle());
                ds1.writeFloat(article.getnombreUnitats());
                ds1.writeUTF(article.gettipusUnitat());
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }
    
    public void SerilitzarAleatori (Encarrec encarrec, String fileName) {

        try (RandomAccessFile rafenc1 = new RandomAccessFile(fileName, "rw");
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ObjectOutputStream objectOut = new ObjectOutputStream(byteArrayOut)) {

            objectOut.writeObject(encarrec);
            //veure necessitat de fer el flush
            objectOut.flush();
            byte[] encarrecBytes = byteArrayOut.toByteArray();

            rafenc1.write(encarrecBytes);



        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
