import by.chyrkun.training.service.resource.MessageManager;

import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        boolean a = true;
        boolean b = true;
        boolean c = true;

        if (!a) {
            System.out.println("a");
        }
        else if(!b){
            System.out.println("b");
        }
        else if (c){
            System.out.println("c");
        }
    }
}
