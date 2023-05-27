package UrzadSkarbowy;
import BankAccount.History;
import BankAccount.KontoBankowe;
import java.util.Random;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UrzadSkarbowy {
    private String nazwa;
    private String adres;

//    int void kontroluj(List<KontoBankowe> ListaKont, LocalDateTime month, LocalDateTime year){
    public void kontroluj(List<KontoBankowe> kontoBankoweList, int month, int year){

//        LocalDate before = LocalDate.of(year,month,1);
//        LocalDate after= LocalDate.of(year,month,LocalDate.MAX.getDayOfMonth());
//        for (KontoBankowe kontoBankowe: kontoBankoweList){
//            for(History history: kontoBankowe.getHistories()){
//                if(!history.getDateTime().isAfter(after) && !history.getDateTime().isBefore(before) )
//                    System.out.println("dupa");
//            }
//        }
    }
    public void zlozWyjasnienie(String tresc, KontoBankowe k ){
        if(k.checkIfBlocked()){
            Random rand = new Random();
            int n = rand.nextInt(100);
            if(n>94){
                System.out.println("Konto nadal zablokowane");
            }
            else{
                k.setZablokowane(false);
                System.out.println("Konto odblokowane");
            }
        }else {
            System.out.println("Konto nie jest zablokowane");
        }
    }
}
