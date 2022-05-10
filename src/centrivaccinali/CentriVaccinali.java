/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package centrivaccinali;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * classe main programma.
 * @author Barone Alessandro
 * @since 12/01/2022
 */
public class CentriVaccinali {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
        Scanner sc=new Scanner(System.in);
        boolean fine_programma=false;  //la userò per gestire la chiusura del programma nel CASE "FINE"
        Centro c=new Centro();
        
        do{       
        System.out.println("");                             //menù per scelta tra operatore e cittadino
        System.out.println("Sei un operatore (op)");
        System.out.println("Sei un cittadino (ci)");
        System.out.println("Vuoi chiudere il programma (fine)");
        String opz=sc.nextLine().toLowerCase();// lettura scelta e ho usato LowerCase per mettere tutto quello scritto in input dall'utente in minuscolo in modo da non avere problemi con i case dello switch
        switch(opz){
            case "op":
                String scelta_op="";
                do{  //menù con azioni che puoi fare da operatore
                    System.out.println("1) Registrazione di un nuovo centro vaccinale");
                    System.out.println("2) Effettuare il login");
                    System.out.println("3) Esci");
                    scelta_op=sc.nextLine();
                    switch (scelta_op) {
                        case "1":
                            
                            boolean operazioneEffettuata=c.RegistraCentroVaccinale();
                            
                            break;
                            
                        case "2":
                            c.login();
                            break;
                            
                        case "3":
                            System.out.println("Uscita");
                            break;
                            
                        default:
                            System.out.println("Errore. Devi inserire un numero da 1 a 3");
                            break;
                    }
                            
                }while(!scelta_op.equalsIgnoreCase("3"));
                
                
                break;
                
            case "ci":
            Boolean returnCittadino=false;
            do{
                Cittadino user=new Cittadino();
                  String scelta_ci="";
                  //menù con azioni che puoi fare da cittadino
                    System.out.println("1) Cerca e visualizza le informazioni di un centro vaccinale");
                    System.out.println("2) Registrazione presso un centro vaccinale");
                    System.out.println("3) Effettua il login");
                    System.out.println("4) Esci");
                    scelta_ci=sc.nextLine();
                    switch(scelta_ci){
                        case "1":
                            c.InfoCentro();
                        break;
                            
                        case "2":
                            user.registrazione();
                            break;
                            
                        case "3":
                            user.login();
                            returnCittadino=true;
                            fine_programma=true;
                            break;
                            
                        case "4":
                             System.out.println("Uscita");
                             returnCittadino=true;
                            break;
                            
                        default:
                            System.out.println("Errore. Devi inserire un numero da 1 a 4");
                            break;
                        
                    }  
            }while(!returnCittadino);
                   

                break;
                
            case "fine":
                System.out.println("Fine");
                fine_programma=true;
                break;
                
            default:
                System.out.println("Errore. A seconda dell'opzione che vuoi selezionmare devi inserire uno tra:"); 
                 System.out.println("op");
                 System.out.println("ci");
                 System.out.println("fine");
                 break;
        }

        }while(!fine_programma);
    }
    
}
