package App;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import Model.Article;
import Model.Encarrec;

public class UtilReadFitxer {

        public void FormatCSV(String folder, String fileName) {
        String filInputName = folder + fileName + ".csv";

        try (BufferedReader bwfilInp = new BufferedReader(new FileReader(filInputName))) {

            String linea = "";
            String[] contingut;

            while ((linea = bwfilInp.readLine()) != null){

                contingut = linea.split(";", 0);
                int id = Integer.parseInt(contingut[0]);
                String nomCli = contingut[1];
                String telCli = contingut[2];
                String dataEncarrec = contingut[3];

                ArrayList<Article> articles = new ArrayList<>();

                int j = 3;

                while (j < contingut.length) {
                    Article a1 = new Article();
                    a1.setNomArticle(contingut[j]);
                    j++;
                    a1.setnombreUnitats(Float.parseFloat(contingut[j]));
                    j++;
                    a1.settipusUnitat(contingut[j]);
                    j++;
                    a1.setPreuUnitat(Float.parseFloat(contingut[j]));
                    articles.add(a1);
                    j++;
                }

                Float preuTotal = Float.parseFloat(contingut[j]);

                Encarrec enc = new Encarrec(id, nomCli, telCli, dataEncarrec, articles, preuTotal);

                FormatEncarrec(enc);
            } 

        } catch (FileNotFoundException fn) {
                System.out.println ("No es troba el fitxer");         
        } catch (IOException io) {
        System.out.println ("Error d'E/S"); 
        }
    }

    public void FormatBinari(String folder, String fileName) {

        String filInputName = folder + fileName + ".dat";
        ArrayList<Article> articles = new ArrayList<>();

        try (DataInputStream diStr1 = new DataInputStream(new FileInputStream(filInputName))) {

            float preuTotal = 0;

            int id = diStr1.readInt();
            String nomCli = diStr1.readUTF();
            String telCli = diStr1.readUTF();
            String dataEncarrec = diStr1.readUTF();


            try{

                while (diStr1.available()>0) {
                    String nomArticle = diStr1.readUTF();
                    float quantitat = diStr1.readFloat();
                    String unitat = diStr1.readUTF();
                    float preuUnitat = diStr1.readFloat();
                    Article art = new Article(nomArticle,quantitat,unitat,preuUnitat);
                    articles.add(art);
                    if (diStr1.readFloat() != 0) {
                        preuTotal = diStr1.readFloat();
                    }
                }

                Encarrec enc = new Encarrec(id, nomCli, telCli, dataEncarrec, articles, preuTotal);

                FormatEncarrec(enc);

            } catch (EOFException e) {
                System.out.println("Final de fitxer");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void LecturaAleatoria(String folder, String fileName) {

        int pos = 0;
        char aux;

        String filInputName = folder + fileName + ".dat";

        try (RandomAccessFile ram1 = new RandomAccessFile(filInputName, "r")) {

            while(ram1.getFilePointer() != ram1.length()){

                ArrayList<Article> articles = new ArrayList<>();

                ram1.seek(pos);
            //Id de l'encarrec
                int idEnc = ram1.readInt();

            //Nom del client
                char nomC[] = new char[50];

                for (int i = 0; i<nomC.length; i++) {
                    aux = ram1.readChar();
                    nomC[i] = aux;
                }

                String nomCli = new String(nomC).trim();

            //Telefon del client
                char telC[] = new char[12];
    
                for (int i = 0; i<telC.length; i++) {
                    aux = ram1.readChar();
                    telC[i] = aux;
                } 
                
                String telCli = new String(telC).trim();

            //Data d'encarrec
                char datEn[] = new char[12];
    
                for (int i = 0; i<datEn.length; i++) {
                    aux = ram1.readChar();
                    datEn[i] = aux;
                }

                String DataEnc = new String(datEn).trim();

            //preu total
                float preuTot = ram1.readFloat();

            //mida de l'array d'articles
                int size = ram1.readInt();
            
            //Llista d'articles de l'encarrec    
//                while(delimArticles) {
                for (int j = 0; j<size; j++) {

                    char nomAr[] = new char[50];

                    for (int i = 0; i<nomAr.length; i++) {
                        aux = ram1.readChar();
                        nomAr[i] = aux;
                    }

                    String nomArtic = new String(nomAr).trim();
    
                    float quantitat = ram1.readFloat();
    
                    char tipUn[] = new char[10];
    
                    for (int i = 0; i<tipUn.length; i++) {
                        aux = ram1.readChar();
                        tipUn[i] = aux;
                    }

                    String tipusUni = new String(tipUn).trim();
    
                    float preuUnitat = ram1.readFloat();

                    Article art1 = new Article(nomArtic,quantitat,tipusUni,preuUnitat);
                    articles.add(art1);
    
/*                     if (ram1.readUTF() != "/") {
                        delimArticles = false;
                    } */

                }

                Encarrec encarrec = new Encarrec(idEnc, nomCli, telCli, DataEnc, articles, preuTot);
                System.out.println(encarrec.toString());

                FormatEncarrec(encarrec);

                /*recordem que la última posició és la longitud del registre,
                sumem 4 ja que és el que ocupa aquest enter*/
                pos = pos + ram1.readInt() + 4;
        
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ModificarEncarrec(String folder, String fileName,int idEnc, String nouTel, String novaData) {

        int pos = 0;

        String filInputName = folder + fileName + ".dat";

        try (RandomAccessFile ram1 = new RandomAccessFile(filInputName, "rw")) {

            while(ram1.getFilePointer() != ram1.length()){

                ram1.seek(pos);

                if (idEnc == ram1.readInt()) {

                    ram1.skipBytes(100);

                    if (nouTel != "") {
                        StringBuffer sbf1 = new StringBuffer(nouTel);
                        sbf1.setLength(12);
                        ram1.writeChars(sbf1.toString());
                    } else {
                        char telC[] = new char[12];
    
                        for (int i = 0; i<telC.length; i++) {
                            ram1.readChar();
                        }    
                    }
    
                    if (novaData != "") {
                        StringBuffer sbf1 = new StringBuffer(novaData);
                        sbf1.setLength(12);
                        ram1.writeChars(sbf1.toString());
                    } else {
    
                        char datEn[] = new char[12];
    
                        for (int i = 0; i<datEn.length; i++) {
                            ram1.readChar();
                        }
                    }

                } else {
                    ram1.skipBytes(48);
                }

                //preu total
                ram1.skipBytes(4);       

                //mida de l'array d'articles
                int size = ram1.readInt();
                            
                            //Llista d'articles de l'encarrec    
                //                while(delimArticles) {
                                

/*                 char nomC[] = new char[50];

                for (int i = 0; i<nomC.length; i++) {
                    ram1.readChar();
                } */

                for (int j = 0; j<size; j++) {
                    //Mida total de cada article: 68 posicions fixes.
                    //en bytes: 50*2 + 4 + 10*2 + 4
                    ram1.skipBytes(118);

 /*                    char nomAr[] = new char[50];

                    for (int i = 0; i<nomAr.length; i++) {
                        ram1.readChar();
                    }
    
                    ram1.readFloat();
    
                    char tipUn[] = new char[10];
    
                    for (int i = 0; i<tipUn.length; i++) {
                        ram1.readChar();
                    }
    
                    ram1.readFloat(); */
                }

                /*recordem que la última posició és la longitud del registre,
                  sumem 4 ja que és el que ocupa aquest enter*/
                pos = pos + ram1.readInt() + 4;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @SuppressWarnings("unchecked")
    public void LecturaSerial (String folder, String fileName) {

        String filInputName = folder + fileName + ".dat";
        
        ObjectInputStream obread = null;

        ArrayList<Encarrec> encarrecs = null;

        try {
            obread = new ObjectInputStream(new FileInputStream(filInputName));

            encarrecs = (ArrayList<Encarrec>) obread.readObject();

            for (Encarrec encarrec:encarrecs) {
                System.out.println(encarrec);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar el ObjectInputStream después de usarlo
            if (obread != null) {
                try {
                    obread.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        

    }

    public static void FormatEncarrec(Encarrec encarrec) {
        System.out.printf(String.format("%n"));
        System.out.println("DETALL DE L'ENCARREC");
        System.out.println("====================================================");
        System.out.printf(String.format("%n"));
        System.out.println("Nom del client: " + encarrec.getNomCli());
        System.out.println("Telefon del client: " + encarrec.getTelCli());
        System.out.println("Data de l'encarrec: " + encarrec.getDataEncarrec());
        System.out.printf(String.format("%n"));
        System.out.printf(String.format("%-15s %-10s %-15s %-10s%n", "Quantitat","Unitats",
                                        "Article","Preu unitari"));
        System.out.printf(String.valueOf("=").repeat(15)
                        +" "+String.valueOf("=").repeat(10)
                        +" "+String.valueOf("=").repeat(15)
                        +" "+String.valueOf("=").repeat(10));
        for (Article article:encarrec.getArticles()) {
            System.out.printf(String.format("%n"));
            System.out.printf(String.format("%-15s %-10s %-15s %-10s%n",article.getnombreUnitats()
            ,article.gettipusUnitat(),article.getNomArticle(),article.getPreuUnitat()));
        }
        System.out.printf(String.format("%n"));
        System.out.println("Preu total de l'encàrrec: " + encarrec.getPreuTotal());
    }

}
