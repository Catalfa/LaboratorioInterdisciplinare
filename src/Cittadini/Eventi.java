package centrivaccinali;
import java.io.*;
import java.util.Scanner;


/**
 * classe per la segnalazione eventi avversi.
 * @author Catalfamo Rosario
 * @since 12/01/2022
 */
public class Eventi implements Controlli{
    private Centro ce=new Centro();
    private String[] segnalazioni=new String[7];
    File file=new File ("SegnalazioniCittadini.txt");//file usato per salvare le segnalazioni
    /**
     * funzione di salvataggio di una nuova segnalazione sotto forma di stringa, la quale controlla l'esistenza del file delle segnalazioni,
     * del centro di vaccinazione in cui è stato vaccinato l'utente e vi ci scrive la nuova segnalazione, in caso contrario, lo crea e vi ci scrive la segnalazione.
     * @param segn array di stringhe contenente il grado di severità delle patologie nel seguente ordine : emicrania, dolori intestinali, nausea e vomito, febbre, spossatezza, dolore al braccio, mancamenti.
     * @param vac composto in posizione 0 dal nome del centro vaccinale e in posizione 1 dal nome del vaccino.
     * @author Catalfamo Rosario
     * @throws ClassNotFoundException
     * @since 12/01/2022
     */
    private void newSegnalazione(String [] segn , String [] vac, String commento) throws IOException, ClassNotFoundException {
        String nomeFile="Vaccinati_"+vac[0]+".dati.txt";
        File f=new File(nomeFile);
        Boolean Exists=false;
        Boolean add=false;
        Boolean c = false;
        String [] files =new String [200];

        while (!add) {
        try{
            files=ce.deserializza();
        } catch (EOFException e ) {
            String str="";
            for (int i=0;i<files.length;i++){
                if(i==(files.length-1)){
                    str+=files[i];
                    break;
                }
                str+=(files[i]+',');
            }
            str+=','+nomeFile;
            String[]aux=str.split(",");
            ce.serializza(aux);
            }
            for(int i=0; i<files.length;i++){
                
                if (nomeFile.equals(files[i])){
                    while (!c) {
                        if(f.exists()){
                            c = true;
                            Exists=true;
                            FileOutputStream fos = new FileOutputStream(nomeFile, true);
                            PrintWriter scrivi = new PrintWriter(fos);
                            String line = (segn[0]+ ',' + segn[1] + ',' + segn[2] + ',' + segn[3] + ',' + segn[4] + ','
                            + segn[5]+','+ vac[1]+','+commento);
                            scrivi.println(line);
                            scrivi.close();
                            add=true;
                            return;
                        }else {
                            f.createNewFile();
                            PrintWriter scrivi = new PrintWriter(f);
                            String line = (segn[0]+ ',' + segn[1] + ',' + segn[2] + ',' + segn[3] + ',' + segn[4] + ','
                            + segn[5]+','+ vac[1]+','+commento);
                            scrivi.println(line);
                            scrivi.close();
                            c=true;
                        }
                    } 
                } 
            
            }
            if(!Exists){
                String str="";
                for (int i=0;i<files.length;i++){
                    if(i==(files.length-1))
                    {
                        str+=files[i];
                        break;
                    }
                    str+=(files[i]+',');
                }
                str+=','+nomeFile;
                String[]aux=str.split(",");
                ce.serializza(aux);
            }
        }
        
        
        
        
    }
    /**
     * interfaccia utente di segnalazione eventi avversi.
     * @author Catalfamo Rosario
     * @throws ClassNotFoundException
     * @since 12/01/2022
     */
    public void Interface(String cf) throws IOException, ClassNotFoundException{
        //interfaccia di segnalazione all'interno della quale viene dato un determinato grado di severità a determinate patologie
        
            Scanner sc=new Scanner(System.in);
                System.out.println("                     SEGNALAZIONI                  ");
                System.out.println("Inserire un numero da 1 a 10 in base alla severità:");
                System.out.println("Cefalea, emicrania");
                segnalazioni[0]=sc.nextLine();
                if(segnalazioni[0].equalsIgnoreCase("back")) return;
                System.out.println("Dolori intestinali");
                segnalazioni[1]=sc.nextLine();
                if(segnalazioni[1].equalsIgnoreCase("back")) return;
                System.out.println("Nausea e vomito");
                segnalazioni[2]=sc.nextLine();
                if(segnalazioni[2].equalsIgnoreCase("back")) return;
                System.out.println("Febbre");
                segnalazioni[3]=sc.nextLine();
                if(segnalazioni[3].equalsIgnoreCase("back")) return;
                System.out.println("Spossatezza");
                segnalazioni[4]=sc.nextLine();
                if(segnalazioni[4].equalsIgnoreCase("back")) return;
                System.out.println("Dolore al braccio");
                segnalazioni[5]=sc.nextLine();
                if(segnalazioni[5].equalsIgnoreCase("back")) return;
                System.out.println("Mancamenti");
                segnalazioni[6]=sc.nextLine();
                if(segnalazioni[6].equalsIgnoreCase("back")) return;
                System.out.println("");
                Boolean go=false;
                while(!go){
                    System.out.println("(facoltativo) inserire commento:   max 256 caratteri...");
                    String commento=sc.nextLine();
                    if(commento.equalsIgnoreCase("back")) return;
                    if(commento==""){
                        System.out.println("segnalazione inviata");
                        newSegnalazione(segnalazioni, Controlli.GetCVac(cf), commento);
                        go=true;
                    }
                    Boolean ok=Controlli.controlloCommento(commento);
                    if(ok){
                        System.out.println("segnalazione inviata");
                        newSegnalazione(segnalazioni, Controlli.GetCVac(cf), commento);
                        go=true;
                        

                    }else{
                    System.out.println("commento troppo lungo");
                    System.out.println(""); 
                    }
                    
                }


                

    }

}