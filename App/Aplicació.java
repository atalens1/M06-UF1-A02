package App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Model.Article;
import Model.Encarrec;

public class Aplicació {


    public static void main(String[] args) {

        System.out.println("GESTIO D'ENCARRECS");
        System.out.println("======================");

        MainMenu();

        DemanarOpcio();

        
    }

    public static void MainMenu() {

        System.out.println("Opcions disponibles");
        System.out.println("");
        System.out.println("1. Afegir nous encàrrecs");
        System.out.println("2. Mostrar per pantalla els encàrrecs");
        System.out.println("3. Modificar un encàrrec");
        System.out.println("4. Sortir");
        System.out.println("");
        System.out.print("Quina opció tries?: ");
    }

    public static void DemanarOpcio() {

        boolean ValidOpt = true;
        
        try (BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in))) {
            
            String opcio = reader1.readLine();

            switch (opcio) {
                case "1":
                    boolean addMoreEnc=true;

                    ArrayList<Encarrec> encarrecs = new ArrayList<>();

                    while(addMoreEnc) {
                        Encarrec enc = AfegirDadesEncarrec(reader1);
                        encarrecs.add(enc);
                        System.out.print("Voleu afegir un altre encàrrec? Si (S) per més afegir més: ");
                        if (!(reader1.readLine().matches("[Ss]"))) {    
                            addMoreEnc = false;
                        }
                    }

                    EscriureFitxers(reader1,encarrecs);
                    
                    break;

                case "2":
                    MostrarEncarrec(reader1);
                    break;
                
                case "3":


                case "4":

                    break;

            
                default:
                    System.out.println("Opció no vàlida");
                    ValidOpt = false;
            }

            if (!(opcio.equals("3"))) {

                String continuar = "";

                if (ValidOpt) {
                    System.out.println("Vols fer cap altra acció? Indicar S en cas afirmatiu");
                    continuar = reader1.readLine(); 
                }

                if ((continuar.matches("[Ss]")) || (!(ValidOpt))) {
                    
                    MainMenu();

                    DemanarOpcio();
                } else {
                    System.out.println("Que tinguis un bon dia!");
                }

            } else {
                System.out.println("Que tinguis un bon dia!");
            }



        } catch (IOException e) {

            e.printStackTrace();
        } 
    }

    public static Encarrec AfegirDadesEncarrec(BufferedReader reader) throws IOException {

        System.out.println("Introdueix les dades de l'encarrec: ");

        System.out.println("Nom del client: ");
        String nomCli = reader.readLine();

        System.out.println("Telèfon del client: ");
        String telCli = reader.readLine();

        System.out.println("Per quin dia el vols preparat (Dia (DD)/ Mes (MM) / Any (AAAA)?: ");
        String dataEncarrec = reader.readLine();

//Cridem a la classe encarregada de gestionar els articles dels encàrrecs        
        GestioArticle articleList = new GestioArticle();

//Cridem al mètode de la classe que demana les dades per poder afegir els articles
        ArrayList<Article> articles = articleList.AfegirArticles(reader);

//Cridem a la classe encarregada de gestionar l'encàrrec
        GestioEncarrec encarrec = new GestioEncarrec();

//Calculem el preu total de l'encàrrec
        float PreuEncarrec = encarrec.CalculaPreuTotal(articles);

        Encarrec enc = new Encarrec(nomCli, telCli, dataEncarrec, articles, PreuEncarrec);

        return enc;

//Fem l'escriptura als fitxers
//        EscriureFitxers(reader, nomCli, telCli, dataEncarrec, articles, PreuEncarrec);

    }

    public static void EscriureFitxers(BufferedReader reader, ArrayList<Encarrec> encarrecs) throws IOException {

        String extensio = "";

        String folder = "C:\\Users\\accesadades\\";

        String fileName = folder + "encarrecs_dia_" + System.currentTimeMillis() + extensio;

        System.out.println("En quin format vols escriure el fitxer?: ");
        System.out.println("1. text albarà");
        System.out.println("2. csv una línia");
        System.out.println("3. Binari");
        System.out.println("4. accés aleatori");
        System.out.println("5. serialitzat");

        String tipusFich = reader.readLine();

        UtilWriteFitxer uw1 = new UtilWriteFitxer();

        switch (tipusFich) {
            case "1":
                extensio = ".txt";
                fileName = fileName.concat(extensio);
                uw1.TextMultiLinea(encarrecs, fileName);
                break;
            
            case "2": 
                // Encarrec encarrec = new Encarrec(nomCli, telCli, dataEncarrec, articles);
                // csvLineaObjEn(encarrec, fileName);
                extensio = ".csv";
                fileName = fileName.concat(extensio);
                uw1.csvLineaObjEn(encarrecs,fileName);
                break;

            case "3":
                extensio = ".dat";
                fileName = fileName.concat(extensio);
                uw1.binari(encarrecs,fileName);
                break;

            case "4":
                extensio = ".dat";
                fileName = fileName.concat(extensio);
                uw1.EscripturaAleatori(encarrecs, fileName);
                break;
            
            case "5":
                extensio = ".dat";
                fileName = fileName.concat(extensio);
                uw1.EscripturaSerial(encarrecs, fileName);
                break;
        
            default:
                break;
        }

    }

    public static void MostrarEncarrec(BufferedReader reader) throws IOException {

        String folder = "C:\\Users\\accesadades\\";

        System.out.println("Quin tipus de fitxer voleu obrir?");
        System.out.println("1. Fitxer .csv");
        System.out.println("2. Fitxer binari .dat");
        System.out.println("3. Serialitzat .dat");

        String opcio = reader.readLine();
        
        System.out.println("Especifiqueu el nom del fitxer (i sols el nom) que voleu obrir sense la seva extensió");
        System.out.println("Assegureu que el fitxer està a la carpeta: " + folder);

        String fileName = reader.readLine();

        UtilReadFitxer ur1 = new UtilReadFitxer();

        if (opcio.equals("1")) {
            ur1.FormatCSV(folder, fileName);
        } else if (opcio.equals("2")) {
            ur1.FormatBinari(folder, fileName);
        } else if (opcio.equals("3")) {
            ur1.LecturaSerial(folder, fileName);
        }

    }

    public static void ModificarEncarrec(BufferedReader reader) throws IOException {

        String folder = "C:\\Users\\accesadades\\";
        String opcio = ""; 
        String nouTel = "";
        String novaData = "";

        System.out.println("Especifiqueu el nom del fitxer (i sols el nom) que voleu modificar sense la seva extensió");
        System.out.println("Assegureu que el fitxer està a la carpeta: " + folder);

        System.out.print("Voleu modificar el telèfon? (S) o (N)");
        opcio = reader.readLine();
        if (opcio.matches("[Ss]")){
            System.out.print("Indica el nou telèfon: ");
            nouTel = reader.readLine();
        }

        System.out.print("Voleu modificar el la data d'encàrrec? (S) o (N)");
        opcio = reader.readLine();
        if (opcio.matches("[Ss]")){
            System.out.print("Indica la nova data d'encàrrec: ");
            novaData = reader.readLine();
        }

        String fileName = reader.readLine();

        UtilReadFitxer ur1 = new UtilReadFitxer();
        ur1.ModificarEncarrec(folder, fileName, nouTel, novaData);

    }
    
}
