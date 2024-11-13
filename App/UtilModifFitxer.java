package App;

import java.io.IOException;
import java.io.RandomAccessFile;

public class UtilModifFitxer {

    
    public void ModificarEncarrec(String folder, String fileName,int idEnc, String nouTel, String novaData) {

        String filInputName = folder + fileName + ".dat";

        try (RandomAccessFile ram1 = new RandomAccessFile(filInputName, "rw")) {

            while(ram1.getFilePointer() < ram1.length()){

                int idEncarrec = ram1.readInt(); 

                if (idEnc == idEncarrec ) {

                    ram1.skipBytes(100);

                    if (!nouTel.equals("")) {
                        StringBuffer sbf1 = new StringBuffer(nouTel);
                        sbf1.setLength(12);
                        ram1.writeChars(sbf1.toString());
                    } else {
                        ram1.skipBytes(24);   
                    }
    
                    if (!novaData.equals("")) {
                        StringBuffer sbf1 = new StringBuffer(novaData);
                        sbf1.setLength(12);
                        ram1.writeChars(sbf1.toString());
                    } else {

                        ram1.skipBytes(24);
                    }

                } else {
                    ram1.skipBytes(48);
                }

                //preu total
                ram1.skipBytes(4);       

                //mida de l'array d'articles
                int size = ram1.readInt();
                            
                for (int j = 0; j<size; j++) {
                    //Mida total de cada article: 68 posicions fixes.
                    //en bytes: 50*2 + 4 + 10*2 + 4
                    ram1.skipBytes(128);
                }

                /*recordem que la última posició és la longitud del registre,
                  sumem 4 ja que és el que ocupa aquest enter*/
                ram1.skipBytes(4);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}
