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

    public void TextMultiLinea(ArrayList<Encarrec> encarrecs, String fileName) {
    /* Aquest mètode s'encarrega d'agafar els detalls de l'encarrec i formatar-lo a un fitxer pla,
     * el qual no és un csv sinó una mena de comprovant pel client
     */

        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName))) {

            for(Encarrec encarrec:encarrecs) {
                bw1.write("Nom del client: " + encarrec.getNomCli());
                bw1.newLine();
                bw1.write("Telefon del client: " + encarrec.getTelCli());
                bw1.newLine();
                bw1.write("Data de l'encarrec: " + encarrec.getDataEncarrec());
                bw1.newLine();
                bw1.write(String.format("%-15s %-10s %-15s %-10s%n", "Quantitat","Unitats",
                                        "Article","Preu unitari"));
                bw1.write(String.valueOf("=").repeat(15)
                                +" "+String.valueOf("=").repeat(10)
                                +" "+String.valueOf("=").repeat(15)
                                +" "+String.valueOf("=").repeat(10));
                bw1.newLine();
                for (Article article:encarrec.getArticles()) {
                    bw1.write(String.format("%-15s %-10s %-15s %-10s%n",article.getnombreUnitats()
                            ,article.gettipusUnitat(),article.getNomArticle(),article.getPreuUnitat()));
                    bw1.newLine();
                }
                bw1.newLine();
                bw1.write("Preu total de l'encàrrec: " + encarrec.getPreuTotal());
                bw1.newLine();
            }

        } catch (Exception e) {
            System.out.println("Error");
        } 

    }

    public void csvLineaObjEn(ArrayList<Encarrec> encarrecs, String fileName) {
    /* Aquest mètode és una alternativa per si es fa servir una classe anomenada Encarrec, la qual 
     * fa servir la classe Article
     */
        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName))){

            GestioEncarrec genc = new GestioEncarrec();

            for(Encarrec encarrec:encarrecs){

                bw1.write(genc.toCSV(encarrec));
            }
            
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    public void binari (ArrayList<Encarrec> encarrecs, String fileName) {
    /* Aquest mètode s'encarrega d'agafar els detalls de l'encarrec i formatar-lo a un fitxer binari
     */

        try (DataOutputStream ds1 = new DataOutputStream(new FileOutputStream(fileName))) {

            for(Encarrec encarrec:encarrecs) {
                ds1.writeUTF(encarrec.getNomCli());
                ds1.writeUTF(encarrec.getTelCli());
                ds1.writeUTF(encarrec.getDataEncarrec());
                
                for (Article article:encarrec.getArticles()) {
                    ds1.writeUTF(article.getNomArticle());
                    ds1.writeFloat(article.getnombreUnitats());
                    ds1.writeUTF(article.gettipusUnitat());
                    ds1.writeFloat(article.getPreuUnitat());
                }
             
                ds1.writeFloat(encarrec.getPreuTotal());
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void EscripturaAleatori (ArrayList<Encarrec> encarrecs, String fileName) {

        try (RandomAccessFile raw1 = new RandomAccessFile(fileName, "rw")) {
            for (Encarrec encarrec:encarrecs){

                int longRecord = 0;

                StringBuffer sbf1 = null;

                sbf1 = new StringBuffer(encarrec.getNomCli());
                sbf1.setLength(50);
                raw1.writeChars(sbf1.toString());
                longRecord += sbf1.toString().length() * 2; //2 bytes per cada char escrit.
    
                /* Si es valida el format del telèfon, realment es podria
                fer com writeUFT */
    
                sbf1 = new StringBuffer(encarrec.getTelCli());
                sbf1.setLength(12);
                raw1.writeChars(sbf1.toString());
                longRecord += sbf1.toString().length() * 2; //2 bytes per cada char escrit.
    
                /* Si es valida el format de la data d'encàrrec, realment es podria
                fer com writeUFT */
    
                sbf1 = new StringBuffer(encarrec.getDataEncarrec());
                sbf1.setLength(12);
                raw1.writeChars(sbf1.toString());
                longRecord += sbf1.toString().length() * 2; //2 bytes per cada char escrit.
    
                for (Article art:encarrec.getArticles()) {
                    sbf1 = new StringBuffer(art.getNomArticle());
                    sbf1.setLength(50);
                    raw1.writeChars(sbf1.toString());
                    longRecord += sbf1.toString().length() * 2; //2 bytes per cada char escrit.
    
                    raw1.writeFloat(art.getnombreUnitats());
                    longRecord += 4;  //4 bytes un float
    
                    sbf1 = new StringBuffer(art.gettipusUnitat());
                    sbf1.setLength(10);
                    raw1.writeChars(sbf1.toString());
                    longRecord += sbf1.toString().length() * 2; //2 bytes per cada char escrit.
    
                    raw1.writeFloat(art.getPreuUnitat());
                    longRecord += 4;  //4 bytes un float
                }
                /*com no sabem la longitud, fem servir un marcador que ens indiqui on acaba
                l'array d'articles*/
                raw1.writeUTF("/");
                longRecord += "/".length() * 2; //pensem que UTF ocupa a més 2 bytes per indicar la llargària
    
                raw1.writeFloat(encarrec.getPreuTotal());
                longRecord += 4;  //4 bytes un float

                //escrivim la longitud del registre
                raw1.writeInt(longRecord);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void EscripturaSerial (ArrayList<Encarrec> encarrecs, String fileName) {

        ObjectOutputStream serializador = null;

        try {
            serializador = new ObjectOutputStream(new FileOutputStream(fileName));

            serializador.writeObject(encarrecs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void SerilitzarAleatori (ArrayList<Encarrec> encarrecs, String fileName) {

        try (RandomAccessFile rafenc1 = new RandomAccessFile(fileName, "rw");
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ObjectOutputStream objectOut = new ObjectOutputStream(byteArrayOut)) {

            objectOut.writeObject(encarrecs);
            //veure necessitat de fer el flush
            objectOut.flush();
            byte[] encarrecBytes = byteArrayOut.toByteArray();

            rafenc1.write(encarrecBytes);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
