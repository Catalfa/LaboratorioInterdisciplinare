/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package centrivaccinali;

import java.io.*;
import java.lang.RuntimeException;
import java.lang.RuntimeException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * classe per la gestione dei centri vaccinali.
 * @author Barone Alessandro
 * @since 12/01/2022
 */
public class Centro implements Controlli, Serializable{
    Scanner sc=new Scanner(System.in);
    private String nome;
    private String[] indirizzo=new String[6];
    private String qualificatore;
    private String numCiv;
    private int cap;
    private String tipologia;
    private Boolean corretto=false;
    private String vaccino;
    private String dataVaccino;
    private String psw;
    private int dosi;
    private String civcontr;
    static File file = new File("Cittadini_Vaccinati.txt");
    private File fserializ =new File ("file.ser");
    private File [] totFile=new File [100];
    static File fileCentri = new File("CentriVaccinali.txt");
    private int nCentri=0;

    /**
     * funzione di registrazione di un centro vaccinale.
     * @author Barone Alessandro
     * @throws ParseException
     * @since 12/01/2022
     */
    public boolean RegistraCentroVaccinale() throws IOException, ParseException{
        boolean operazioneEffettuata=false;
        boolean exit=false;
        System.out.println("Registrazione centro vaccinale");
        System.out.println("Inserisci il nome del centro vaccinale");
        nome=sc.next();
        if(nome.equalsIgnoreCase("back")) return false;
        Boolean check=Controlli.controlloCentro(nome);
        if(check==false){
            System.out.println(" ");
            System.out.println("Centro Vaccinale già registrato ");
            System.out.println(" ");
            return operazioneEffettuata;
        }else{
            System.out.println("Indirizzo: ");
       indirizzo=inserimentoIndirizzo();
       if(indirizzo==null) return false;
       System.out.println("Inserisci la tipologia del centro vaccinale");
       tipologia=sc.next();
       if(tipologia.equalsIgnoreCase("back")) return false;
       tipologia=controlloTipologia(tipologia);
       if(tipologia==null) return false;
       System.out.println("Inserisci Password");
       psw=sc.next();
       if(psw.equalsIgnoreCase("back")) return false;
       psw=SetPsw(psw);
       if(psw==null) return false;
       System.out.println("");
       System.out.println("Centro Vaccinale registrato con successo");
       System.out.println("");
       salvaCentro(nome,indirizzo,tipologia,psw);
        operazioneEffettuata=true;
        return operazioneEffettuata;
        }
       
    }
    /**
     * funzione per impostare la password.
     * @param psw1 prima password inserita che verrà confermata con un secondo inserimento
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    public  String SetPsw(String psw1) throws ParseException 
    {
        while (psw1 == null) {//controlla che venga inserita qualcosa
            System.out.println("inserimento non valido");
            System.out.println("inserire password:");
            psw1 = sc.next();
            if(psw1.equalsIgnoreCase("back")) return null;
        }
        System.out.println("conferma psw:");
        String psw2 = sc.next();
        if(psw2.equalsIgnoreCase("back")) return null;
        char[] q = psw1.toCharArray();
        char[] w = psw2.toCharArray();
        Boolean ceck = false;
        try {//confronta le password per carattere
            for (int i = 0; i < psw1.length(); i++) {
                if (q[i] == w[i]) {
                    ceck = true;
                } else {
                    ceck = false;
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {//in caso siano di lunghezze diverse ripete il comando
            System.out.println("le password non coincidono");
            return this.SetPsw(psw1);
        }

        if (ceck) {
            return psw1;
        } else {//in caso fossero diverse ripete il comando
            System.out.println("le password non coincidono");
            return this.SetPsw(psw1);

        }

    }
    /**
     * login utente registrato.
     * @author Catalfamo Rosario
     * @throws ClassNotFoundException
     * @since 12/01/2022
     */
    public void login() throws IOException, ClassNotFoundException, ParseException 
    {
        Scanner in=new Scanner(System.in);
        List<String[]> c = Cittadino.leggifile(fileCentri); //richiama il metodo che converte un file in una lista di stringhe
        String nome = "";
        Boolean b = false;
        System.out.println("inserire nome Centro Vaccinale:");
        if (in.hasNext())
            nome = in.next();
            if(nome.equalsIgnoreCase("back")) return ;
        
        System.out.println("inserire password:");
        String psw = in.next();
        if(psw.equalsIgnoreCase("back")) return ;
        while (psw == null) {//controlla che vanga inserita qualcosa 
            System.out.println("inserimento non valido");
            System.out.println("inserire password:");
            psw = in.next();
        }
        Boolean ceckNome = false; //variabile usata per controllare che venga trovato il codice fiscale all'interno della lista di stringhe ottenuta dalla conversione del file Cittadini_Registrati
        Boolean ceckpsw = false;//variabile usata per controllare che la password inserita corrispondi alla password associata al codice fiscale
        String[] s = new String[5];

        for (String[] d : c) {
            if (nome.equalsIgnoreCase(d[0])) {

            ceckNome = true;

            if (psw.equalsIgnoreCase(d[8])) {
                ceckpsw = true;
                s = d;
            }
            }
        }
        if (!ceckNome) {//Se il codice fiscale non viene trovato nel file significa che non è stata effettuata alcuna registrazione con questo codice fiscale
            System.out.println("Centro Vaccinale non registrato");
            this.login();
        }
        if (ceckNome && ceckpsw) {//codice fiscale e password coincidono

            
            System.out.println("accesso effettuato");
            System.out.println("i tuoi dati: ");
            System.out.println(s[0]+','+s[1]+','+s[2]+','+s[4]+','+s[5]+','+s[6]+','+s[7]);
            b = true;

        } else if (ceckNome && !ceckpsw) {//il codice fiscale viene trovato la password non coincide a quella associata a questo codice fiscale
            System.out.println("password errata");
            this.login();
        }

        Boolean go = false;
        while (!go) { //menù per la segnalazione degli eventi avversi, visibile solo dopo aver effettuato l'accesso
                if (b) {
                    System.out.println("inserire '1' per registrare un nuovo vaccinato");
                    String inv=in.next();
                    if(inv.equalsIgnoreCase("back")) {
                        go=false;
                        return ;
                    }
                    switch (inv) {
                        case "1":
                            registraVaccinato();
                            
                            break;

                        default:
                            System.out.println("inserimento non valido");
                            break;
                    }
                }

            
        }
        
    }
    /**
     * funzione di inserimento indirizzo di ubicazione centro vaccinale.
     * @author Barone Alessandro
     * @since 12/01/2022
     */
    public String[] inserimentoIndirizzo(){
        System.out.println("Inserisci qualificatore (se via/viale/piazza)");
        qualificatore=sc.next();
        qualificatore=controlloQualificatore(qualificatore);
        indirizzo[0]=qualificatore;
        sc.nextLine();
        System.out.println("Inserisci il nome dell'indirizzo");
        indirizzo[1]=sc.nextLine();
        
         System.out.println("Inserisci il numero civico dell'indirizzo");
         String numCiv=sc.nextLine();
         indirizzo[2]=civcontr;  
         
        System.out.println("Inserisci il comune");
        indirizzo[3]=sc.nextLine();
        Boolean go=false;
           while(!go){
            System.out.println("Inserisci la sigla della provincia");
            indirizzo[4]=(sc.next().toUpperCase());
            if((indirizzo[4].length()==2) && (indirizzo[4].matches("([A-Z]{2})"))) 
                {go=true;}
            else
                {System.out.println("Inserimento non valido");}
          
           }
           
        System.out.println("Inserisci il cap");
          cap=sc.nextInt();
          String cap2=String.valueOf(cap);
        indirizzo[5]=cap2; 
        
        return indirizzo;
    }
    /**
     * metodo che legge il file passato come parametro e salva i dati in una lista di array di stringhe, onguno contenente i dati di un cittadino.
     * @param file file che verrà letto
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    public static List<String[]> leggifile(File file) throws FileNotFoundException 
    {
        // metodo che interpretà il file come una lista di array lunghezza 5
        if(file.exists()){Scanner scf = new Scanner(file);
        String s;
        List<String[]> lista = new ArrayList<>();
        while (scf.hasNextLine()) {
            s = scf.nextLine();
            lista.add(s.split(","));
        }
        scf.close();
        return lista;}else{return null;}

    }
    /**
     * funzione che mostra a terminale i dati di un centro vaccinale.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    public void ShowDati(String Nome) throws FileNotFoundException 
    {
        //ricerca e stampa la strginga di dati del cittadino che ha effettuato l'accesso utilizzando il codice fiscale come chiave primaria
        List<String[]> c = this.leggifile(fileCentri);
        Boolean exists=false;
        for (String[] s : c) {
            if (s[0].equalsIgnoreCase(Nome)){
                System.out.println("");
                System.out.println("Dati Centro: ["+s[0]+','+s[1]+','+s[2]+','+s[4]+','+s[5]+','+s[6]+','+s[7]+"]");
                exists=true;
                System.out.println("");
                System.out.println("Premere invio per tronare al menù cittadino...");
                sc.nextLine();
            }
        }
        if(!exists){
            System.out.println("Centro Vaccinale non registrato");
        }
    }
    /**
     * funzione utilizzata per mostrare i dati di un centro vaccinale.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    public void InfoCentro() throws FileNotFoundException
    {
        System.out.println("inserire il nome del centro:");
        String nome=sc.nextLine();
        ShowDati(nome);
    }
    /**
     * funzione di registrazione di un vaccinato.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    public void registraVaccinato() throws IOException, ParseException
    {
        System.out.println("inserire Codice Fiscale:");
        String cf = sc.next().toLowerCase();
        if(cf.equalsIgnoreCase("back")) return;
        Boolean a = Controlli.controllocf(cf);
        Boolean b = Controlli.controllocf2vac(cf);
        while (!a) {
            System.out.println("inserimento non valido");
            System.out.println("inserire Codice Fiscale:");
            cf = sc.next();
            if(cf.equalsIgnoreCase("back")) return;
            a = Controlli.controllocf(cf);
        }


        if (!b) {
            System.out.println("codice fiscale già registrato");
            return;
        }
       
        System.out.println("Seleziona vaccino somministratato");
            System.out.println(" 1:Pfizer / 2:Johnson & Jhonson / 3:Moderna / 4:Astrazeneca");
            Boolean b1=false;
            while(!b1){
                String v=sc.next();
                if(v.equalsIgnoreCase("back")) return;
                switch (v) {
                    case "1":
                    b1=true;
                    vaccino="Pfizer";                          
                        break;
                    case "2":
                    b1=true;
                    vaccino="Johnson & Johnson";
                        break;
                    case "3":
                    b1=true;
                    vaccino="Moderna";                     
                        break;            
                    case "4":
                    b1=true;
                    vaccino="Astrazeneca";
                        break;                    
                    default:
                    System.out.println("inserimento non valido");
                        break;
                    }
                }
        System.out.println("inserire data di vaccinazione nel seguente formato" + " giorno/mese/anno" + ":");
        dataVaccino = sc.next();
        if(dataVaccino.equalsIgnoreCase("back")) return;
        Boolean controllodata = Controlli.controllodata(dataVaccino);
        while (!controllodata) {
            System.out.println("inserimento non valido");
            System.out.println("inserire data di vaccinazione nel seguente formato" + " giorno/mese/anno" + ":");
            dataVaccino = sc.next();
            if(dataVaccino.equalsIgnoreCase("back")) return;
            controllodata = Controlli.controllocf(cf);
        }
        System.out.println("inserire il numero della dose");
        Boolean b2=false;
            while(!b2){
                String v=sc.next();
                if(v.equalsIgnoreCase("back")) return;
                switch (v) {
                    case "1":
                    b2=true;
                    dosi=1;                          
                        break;
                    case "2":
                    b2=true;
                    dosi=2; 
                        break;
                    case "3":
                    b2=true;
                    dosi=3;                     
                        break;            
                                       
                    default:
                    System.out.println("inserimento non valido");
                        break;
                    }
                }
        Boolean bi=false;
        while(!bi){
        System.out.println("inserire il nome del centro di vaccinazione");
        String nomeCentro=sc.next();
            if(nomeCentro.equalsIgnoreCase("back")) return;
        Boolean esiste=Controlli.cercaCentro(nomeCentro);
        if(esiste){
            salvaVaccinato(cf,vaccino ,dataVaccino, dosi, nomeCentro);
            bi=true;
        }else{
            System.out.println("centro inesistente");
        }}
        
        
                
    }
    /**
     * funzione di salvataggio dati, all'interno del file "Cittadini_Vaccinati.txt".
     * @param cf codice fiscale vaccinato.
     * @param vaccino nome vaccino somministrato.
     * @param datavac data somministrazione vaccino.
     * @param dose nuomero dose somministrata.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    private void salvaVaccinato(String cf,String vaccino, String datavac, int dose, String nomeCentro) throws IOException
    {
        
        Boolean c = false;
        while (!c) {
            if (file.exists()) {
                c = true;
                FileOutputStream fos = new FileOutputStream("Cittadini_Vaccinati.txt", true);
                PrintWriter scrivi = new PrintWriter(fos);
                String line = (cf +','+ vaccino+','+datavac +','+dose+','+nomeCentro);
                scrivi.println(line);
                scrivi.close();
            } else {
                file.createNewFile();
                PrintWriter scrivi = new PrintWriter(file);
                String line = (cf +','+ vaccino+','+datavac +','+dose+','+nomeCentro);
                scrivi.println(line);
                scrivi.close();
                c=true;
            }
        }
    }
    /**
     * funzione di controllo qualificatore indirizzo, controlla che venga inserito un qualificatore tra "via / viale / piazza".
     * @param qualificatore qualificantore da controllare.
     * @author Barone Alessandro
     * @since 12/01/2022
     */
    public String controlloQualificatore(String qualificatore)
    {
        while(corretto== false){
       if(qualificatore.equalsIgnoreCase("via") || qualificatore.equalsIgnoreCase("viale") || qualificatore.equalsIgnoreCase("piazza"))
           corretto=true;
       else{
           System.out.println("Errore, devi inserire uno tra: via, viale o piazza");
           System.out.println("Inserisci nuovamente il qualificatore");
           qualificatore=sc.next();
           if(qualificatore.equalsIgnoreCase("back")) return null;
       }
        } 
       return qualificatore.toLowerCase(); 
    }
    /**
     * funzione di controllo tipologia di centro vaccinale, controlla che venga inseta una tipologia tra "ospedaliero / azienda / hub".
     * @param tipologia tipologia da controllare.
     * @author Barone Alessandro
     * @since 12/01/2022
     */
    public String controlloTipologia(String tipologia)
    {
    Boolean corretto1=false;
        while(corretto1==false){
            if(tipologia.equalsIgnoreCase("ospedaliero") || tipologia.equalsIgnoreCase("aziendale") || tipologia.equalsIgnoreCase("hub"))
                corretto1=true;
            else{
                System.out.println("Errore devi inserire uno tra: ospedaliero, aziendale o hub");
                System.out.println("Inserisci nuovamente la tipologia");
                tipologia=sc.next();
                if(tipologia.equalsIgnoreCase("back")) return null;
            }
        }
        return tipologia.toLowerCase();
    }
    /**
     * funzione di salvataggio dati nel file "CentriVaccinali.txt".
     * @param nome nome centro vaccinale.
     * @param indirizzo indirizzo centro vaccinale.
     * @param tipologia tipologia centro vaccinale.
     * @author Barone Alessandro
     * @throws IOException
     * @since 12/01/2022
     */
    public void salvaCentro (String nome,String [] indirizzo,String tipologia,String psw) throws IOException
    {
        Boolean c = false;
        while (!c) {
            if (fileCentri.exists()) {
                c = true;
                FileOutputStream fos = new FileOutputStream("CentriVaccinali.txt", true);
                PrintWriter scrivi = new PrintWriter(fos);
                String line = (nome+','+indirizzo [0]+','+indirizzo [1]+','+indirizzo [2]+','+indirizzo [3]+','+indirizzo [4]+','+indirizzo[5] +','+tipologia+','+psw );
                scrivi.println(line);
                scrivi.close();
            } else {
                fileCentri.createNewFile();
                PrintWriter scrivi = new PrintWriter(fileCentri);
                String line = (nome+','+indirizzo [0]+','+indirizzo [1]+','+indirizzo [2]+','+indirizzo [3]+','+indirizzo [4]+','+indirizzo[5] +','+tipologia+','+psw );
                scrivi.println(line);
                scrivi.close();
                c=true;
            }
        }
    }
    /**
     * funzione che serializza l'array passato come parametro".
     * @param FileNames array contenente i nomi dei file.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    public void serializza(String [] FileNames) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        String str="";
        for (int i=0;i<FileNames.length;i++){
            if(i==(FileNames.length-1)){
                str+=FileNames[i];
                break;
            }
            str+=(FileNames[i]+',');
        }
        ObjectOutputStream objout=new ObjectOutputStream(new FileOutputStream("DB.txt"));
        objout.writeObject(str);
        objout.writeObject(new EndofFile());
        objout.close(); 

    }
    /**
     * funzione che deserializza l'array passato come parametro".
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    public String [] deserializza() throws ClassNotFoundException, IOException
    {   File f=new File("DB.txt");
        if(!f.exists()) f.createNewFile();
        ObjectInputStream objin=new ObjectInputStream(new FileInputStream("DB.txt"));
        Object obj=null;
        String [] aux=new String [100];
        while ((obj=objin.readObject()) instanceof EndofFile==false)
        {
        aux=((String) obj).split(",");
        }
        return aux;
    }
    
}