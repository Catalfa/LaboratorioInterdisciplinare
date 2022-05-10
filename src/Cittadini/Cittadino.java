package centrivaccinali;
import java.util.*;
import java.io.*;
import java.text.ParseException;
/**
 * classe per la gestione dei cittadini.
 * @author Catalfamo Rosario
 * @since 12/01/2022
 */
public class Cittadino implements Controlli {
    //attributi
    private static final List<String[]> RuntimeException = null;
    private String nome;
    private String cognome;
    String cf;
    private String indirizzo;
    private String DataNascita;
    private String psw;
    public String invio = " ";
    static File file = new File("Cittadini_Registrati.dati.txt");//file usato per salvare i cittadini vaccinati che hanno effettuato la registrazione
    static Scanner sc = new Scanner(System.in);



    /**
     * funzione per la registrazione di un cittadino precedentemente vaccinato.
     * @author Catalfamo Rosario
     * @throws ClassNotFoundException
     * @since 12/01/2022
     */
    public void registrazione() throws IOException, ParseException, ClassNotFoundException 
    {
        System.out.println("inserire nome:");
        String n = sc.next();
        if(n.equalsIgnoreCase("back")) return ;

        while (n == null) {
            System.out.println("inserimento non valido");
            System.out.println("inserire nome:");
            n = sc.next();
            if(n.equalsIgnoreCase("back")) return ;

        }
        System.out.println("inserire cognome:");
        String c = sc.next();
        if(c.equalsIgnoreCase("back")) return ;

        while (c == null) {
            System.out.println("inserimento non valido");
            System.out.println("inserire cognome:");
            c = sc.next();
            if(c.equalsIgnoreCase("back")) return ;

        }
        System.out.println("inserire Codice Fiscale:");
        String cf = sc.next().toLowerCase();
        if(cf.equalsIgnoreCase("back")) return ;

        Boolean a = Controlli.controllocf(cf);//controlla che il codice fiscale rispetti il proprio pattern
        Boolean b = Controlli.controllocf2(cf);//controlla che il codice fiscale non sia già registrato
        Boolean d=Controlli.controllocf2vac(cf);//controlla che il codice fiscale sia associato ad una vaccinazione
        if (!a) {
            while (!a) {//se non rispetta il pattern ripeti l'operazione
                System.out.println("inserimento non valido");
                System.out.println("inserire Codice Fiscale:");
                cf = sc.next();
                if(cf.equalsIgnoreCase("back")) return ;
                a = Controlli.controllocf(cf);
            }
        }

        if (!b) {//se già registrato, si può effettuare il login o registrare un nuovo account
            System.out.println("codice fiscale già registrato");
            System.out.println();
            System.out.println("inserire 1 per effettuare il login");
            System.out.println("inserire 2 per registrare un nuovo account");
            String x = sc.next();
            if(x.equalsIgnoreCase("back")) return ;

            switch (x) {
                case "1":
                    this.login();
                    break;

                case "2":
                    this.registrazione();
                    break;

                default:
                    System.out.println("inserimento non valido");
                    break;
            }

        }

        if(d){//re non risulta un vaccinazione associato al codice fiscale, rifiuta l'operazione
            System.out.println("non risulta vaccinazione intestata a questo cofice fiscale");
            System.out.println();
            return;
        }
        System.out.println("inserire indirizzo:");
        String ind = sc.next();
        sc.nextLine();
        if(ind.equalsIgnoreCase("back")) return ;
        while (ind == null) {//controlla che venga inserita qualcosa
            System.out.println("inserimento non valido");
            System.out.println("inserire indirizzo:");
            ind = sc.nextLine();
            if(ind.equalsIgnoreCase("back")) return ;

        }
        System.out.println("inserire data di nascita nel seguente formato" + " giorno/mese/anno" + ":");
        String data = sc.next();
        if(data.equalsIgnoreCase("back")) return ;

        Boolean controllodata = Controlli.controllodata(data);//controlla che la data di nascita rispetti il pattern 
        while (!controllodata) {
            System.out.println("inserimento non valido");
            System.out.println("inserire data di nascita nel seguente formato" + " giorno/mese/anno" + ":");
            data = sc.next();
            if(data.equalsIgnoreCase("back")) return ;

            controllodata = Controlli.controllodata(data);
        }
        boolean go=false;
        String psw1="";
        while(!go){
            System.out.println("inserisce psw:");
            psw1 = sc.next();
            if(psw1.equalsIgnoreCase("back")) return ;
            psw1=this.SetPsw(psw1);//richiama la funzione per impostare la password
            if(psw1!=null) go=true;
        }
        System.out.println("Registrato con successo");
        Cittadino user = this.datinuovo(n, c, cf, ind, data, psw1); //richiama il costruttore della classe Cittadino che imposta tutti gli attributi precedentemente inseriti
        this.salva(user);//richiama il metodo per il salvataggio dei dati del cittadino nel file Cittadini_Registrati.txt
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
     * costruttore della classe Cittadino.
     * @param nome nome del cittadino
     * @param cognome cognome del cittadino
     * @param cf codice fiscale del cittadino
     * @param indirizzo indirizzo di residenza del cittadino
     * @param data data di nascita del cittadino
     * @param psw password utilizzata per il login
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    public Cittadino datinuovo(String nome, String cognome, String cf, String indirizzo, String data, String psw) 
    {
        // metodo richiamato nella registrazione
        Cittadino user = new Cittadino();
        user.nome = nome;
        user.cognome = cognome;
        user.cf = cf;
        user.indirizzo = indirizzo;
        user.DataNascita = data;
        user.psw = psw;
        return user;

    }
    /**
     * metodo di salvataggio dei dati del cittadino all'interno del file "Cittadini_Registrati.dati.txt".
     * @param cittadino istanza della classe Cittadino, della quale saranno salvati i dali nel file
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    private void salva(Cittadino cittadino) throws IOException 
    {
        /* metodo che inizialmente controlla se il file è esistente,in tal caso,i dati
        *da salvare vengono concatenati (divisi da virgole) in una sola stringa e
        stampati sul file.
        Altrimenti, crea il file e s,i dati
        *da salvare vengono concatenati (divisi da virgole) in una sola stringa e
        stampati sul file.*/
        Boolean c = false;
                while (!c) {
                if (Cittadino.file.exists()) {
                    c = true;
                    FileOutputStream fos = new FileOutputStream("Cittadini_Registrati.dati.txt", true);
                    PrintWriter scrivi = new PrintWriter(fos);
                    String line = (cittadino.nome + ',' + cittadino.cognome + ',' + cittadino.cf + ',' + cittadino.psw + ',' + cittadino.DataNascita + ','+ cittadino.indirizzo);
                    scrivi.println(line);
                    scrivi.close();
                } else {
                    file.createNewFile();
                    PrintWriter scrivi = new PrintWriter(Cittadino.file);
                    String line = (cittadino.nome + ',' + cittadino.cognome + ',' + cittadino.cf + ',' + cittadino.psw + ',' + cittadino.DataNascita + ','+ cittadino.indirizzo);
                    scrivi.println(line);
                    scrivi.close();
                    c=true;
                    }
                }
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
        return lista;}else{return RuntimeException;}

    }
    /**
     * metodo selettore che stampa a terminale i dati del cittadino passato come parametro.
     * @param cf codice fiscale del cittadino di cui si vogliono vedere i dati, cerca all'interno del file "Cittadini_Registrati.dati.txt" utilizzando il codice fiscale come chiave primaria di ricerca.
     * @author Catalfamo Rosario
     * @since 12/01/2022
     */
    public void ShowDati(String cf) throws FileNotFoundException 
    {
        //ricerca e stampa la strginga di dati del cittadino che ha effettuato l'accesso utilizzando il codice fiscale come chiave primaria
        List<String[]> c = this.leggifile(file);
        for (String[] s : c) {
            if (s[2].equals(cf))
                System.out.println(s[0]+','+s[1]+','+s[2]+','+s[4]+','+s[5]);
        }
    }
    /**
     * login utente registrato.
     * @author Catalfamo Rosario
     * @throws ClassNotFoundException
     * @since 12/01/2022
     */
    public void login() throws IOException, ClassNotFoundException 
    {

        List<String[]> c = this.leggifile(file); //richiama il metodo che converte un file in una lista di stringhe
        String cf = "";
        Boolean b = false;
        
        System.out.println("inserire Codice Fiscale:");
        
        cf = sc.next().toLowerCase();
        if(cf.equalsIgnoreCase("back")) return;
        Boolean controllocf = Controlli.controllocf(cf);//controlla che il cf rispetti il pattern
        while (!controllocf) {
            System.out.println("inserimento non valido");
            System.out.println("inserire Codice Fiscale:");
            cf = sc.next();
            if(cf.equalsIgnoreCase("back")) return;
            controllocf = Controlli.controllocf(cf);
        }
        System.out.println("inserire password:");
        String psw = sc.next();
        if(psw.equalsIgnoreCase("back")) return;
        while (psw == null) {//controlla che vanga inserita qualcosa 
            System.out.println("inserimento non valido");
            System.out.println("inserire password:");
            psw = sc.next();
            if(psw.equalsIgnoreCase("back")) return;
        }
        Boolean ceckcf = false; //variabile usata per controllare che venga trovato il codice fiscale all'interno della lista di stringhe ottenuta dalla conversione del file Cittadini_Registrati
        Boolean ceckpsw = false;//variabile usata per controllare che la password inserita corrispondi alla password associata al codice fiscale
        String[] s = new String[5];

        for (String[] d : c) {
            if (cf.equalsIgnoreCase(d[2])) {

            ceckcf = true;

            if (psw.equalsIgnoreCase(d[3])) {
                ceckpsw = true;
                s = d;
            }
            }
        }
        if (!ceckcf) {//Se il codice fiscale non viene trovato nel file significa che non è stata effettuata alcuna registrazione con questo codice fiscale
            System.out.println("codice fiscale non registrato");
            this.login();
        }
        if (ceckcf && ceckpsw) {//codice fiscale e password coincidono

            
            System.out.println("accesso effettuato");
            System.out.println("i tuoi dati: ");
            System.out.println(s[0]+','+s[1]+','+s[2]+','+s[4]+','+s[5]);
            b = true;
            sc.nextLine();

        } else if (ceckcf && !ceckpsw) {//il codice fiscale viene trovato la password non coincide a quella associata a questo codice fiscale
            System.out.println("password errata");
            this.login();
        }

        Boolean go = false;
        while (!go) { //menù per la segnalazione degli eventi avversi, visibile solo dopo aver effettuato l'accesso
                if (b) {
                    System.out.println("inserire '1' per segnalare eventi avversi");
                    System.out.println("inserire '2' per uscire ");
                    String inv = sc.next();
                    if(inv.equalsIgnoreCase("back")) return;
                    switch (inv) {
                        case "1":
                            Eventi ev=new Eventi();
                            ev.Interface(s[2]); //richiama l'interfaccia delle segnalazioni 
                            break;
                        case "2":
                            go=true;
                            break;


                        default:
                            System.out.println("inserimento non valido");

                            break;
                    }
                }

            
        }
        sc.close();
    }
}