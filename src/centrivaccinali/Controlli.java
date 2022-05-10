package centrivaccinali;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
/**
 * classe controlli.
 * @author Catalfamo Rosario
 * @since 12/01/2022
 */
interface Controlli {
int CFlenght = 16;//usato per verificare la lunghezza del codice fiscale
    

    /**
     * funzione che controlla che il codice fiscale rispetti il pattern.
     * @param cf codice fiscale da controllare.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
static boolean controllocf(String cf)
{
        if ((cf.length() == CFlenght) && cf.matches("([A-Za-z]{6})([0-9]{2})([A-Za-z])([0-9]{2})([A-Za-z])([0-9]{3})([A-Za-z])")) {// se il codice fiscale inserito rispetta la lunghezza e il formato ritorna true

            return true;

        } else {
            return false;
        }
    }
    /**
     * funzione che controlla che il codice fiscale non sia già presente all'interno del file "Cittadini_Registrati.dati.txt".
     * @param cf codice fiscale da controllare.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
static Boolean controllocf2(String cf) throws FileNotFoundException 
{
        if(Cittadino.file.exists()){//controlla che esista il file dei registrati, in caso contrario ritorna true perché non vi sono account registrati con questo codice fiscale
        Scanner scf = new Scanner(Cittadino.file);
        if (scf.hasNextLine()) {
            String s;
            while (scf.hasNextLine()) {
                s = scf.nextLine();
                String[] prl = s.split(",");
                  if (prl[2].equalsIgnoreCase(cf)) {//confronta il cofice fiscale con quelli letti dal file in caso corrispondano ritorna false
                  return false;
                  }
                 
            }
            return true;//se non trova un codice fsicale uguale ritrona true
            
        } else {//se nel file non vi è nessun dato ritorna true perché è il primo registrato
            return true;
        }
    }else{return true;}

}
    /**
     * funzione che controlla che il nome del centro non sia già presente all'interno del file "CentriVaccinali.txtt".
     * @param nome nome centroe.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
static Boolean controlloCentro(String nome) throws FileNotFoundException
{
    if(Cittadino.file.exists()){//controlla che esista il file dei Centri registrati, in caso contrario ritorna true perché non vi sono Centri Vaaccinali registrati con questo nome
    Scanner scf = new Scanner(Centro.fileCentri);
    if (scf.hasNextLine()) {
        String s;
        while (scf.hasNextLine()) {
            s = scf.nextLine();
            String[] prl = s.split(",");
              if (prl[0].equalsIgnoreCase(nome)) {//confronta il che nome coincida con quelli letti dal file, in tal caso ritorna false
              return false;
              }
             
        }
        return true;//se non trova un nome uguale ritrona true
        
    } else {//se nel file non vi è nessun dato ritorna true perché è il primo registrato
        return true;
    }
}else{return true;}

}

    /**
     * funzione che controlla se il codice fiscale sia già presente all'interno del file "Cittadini_Vaccinati.txt".
     * @param cf codice fiscale da controllare.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
static Boolean controllocf2vac(String cf) throws FileNotFoundException
{
        if(Centro.file.exists()){//controlla che esista il file dei vaccinati, in caso contrario ritorna true perché non vi sono vaccinati con questo codice fiscale
            Scanner scf = new Scanner(Centro.file);
            if (scf.hasNextLine()) {
                String s;
                while (scf.hasNextLine()) {
                    s = scf.nextLine();
                    String[] prl = s.split(",");
                      if (prl[0].equals(cf)) {//confronta il cofice fiscale con quelli letti dal file in caso corrispondano ritorna false
                      return false;
                      }
                     
                }
                return true;//se non trova un codice fsicale uguale ritrona true
                
            } else {//se nel file non vi è nessun dato ritorna true perché è il primo registrato
                return true;
            }
        }else{return true;}
    
}
    /**
     * funzione che controlla che la data rispetti il pattern "dd/MM/yyyy".
     * @param data data da controllare.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
static boolean controllodata(String data) throws ParseException
{
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");//imposta il formato della data
    Date oggi = format.parse("25/01/2022");//imposta il 25/01/2022 come attuale
    Date d = format.parse(data);
    if (d.before(oggi)) {//controlal che la data di nascita sia precedente a quella attuale
        String[] h = data.split("/");
        int mm = Integer.parseInt(h[1]);
        int dd = Integer.parseInt(h[0]);
        if (dd < 31) {//controlla che i giorni siano meno di 31
            if (mm < 12) {//controlla che i mesi siano meno di 12
                char[] x = data.toCharArray();
                    Boolean a = false;
                    if (x[2] == '/' && x[5] == '/') {//controlla il formato della data inserita, se rispettato ritorna true
                        a = true;
                        return a;
                    } else {
                        return a;
                    }
                } else {
                    return false;
                }
            } else {return false;}

        } else {
            return false;
        }
    }
/**
     * funzione che controlla l'esistenza del Centro di Vaccinazione .
     * @param nome nome centro vaccinazione.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
static boolean cercaCentro(String nome) throws FileNotFoundException{
        if(Centro.fileCentri.exists()){//controlla che esista il file dei centri registrati, in caso contrario ritorna false perché non vi centri registrati
            Scanner scf = new Scanner(Centro.fileCentri);
            if (scf.hasNextLine()) {
                String s;
                while (scf.hasNextLine()) {
                    s = scf.nextLine();
                    String[] prl = s.split(",");
                      if (prl[0].equals(nome)) {//confronta il nome con quello dei centri registrati, se esiste ritorna true
                      return true;
                      }
                     
                }
                return false;//se non trova un nome uguale ritorna false
                
            } else {//se nel file non vi è nessun dato ritorna false perché non vi sono centri registrati
                return false;
            }
        }else{return false;}
    
    }
    /**
     * funzione che mostra il centro di vaccinazione del codice fiscale inserito come parametro.
     * @param cf codice fiscale.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    static String [] GetCVac(String cf) throws FileNotFoundException
    {
        String [] aux=new String[2];
        if(Centro.file.exists()){
            Scanner scf = new Scanner(Centro.file);
            if (scf.hasNextLine()) {
                String s;
                while (scf.hasNextLine()) {
                    s = scf.nextLine();
                    String[] prl = s.split(",");
                      if (prl[0].equals(cf)) {
                          aux[0]=prl[4];
                          aux[1]=prl[1];
                      return aux;
                      }
                     
                }
                return null;
                
            } else {
                return null;
            }
        }else{return null;}
    
}
    /**
     * funzione che il commento non sia oiù lungo di 256 caratteri.
     * @param commento commento scritto dall'utente.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
static Boolean controlloCommento(String commento){
        if(commento.length()>256){
            return false;
        }else{
            return true;
        }
}


}
