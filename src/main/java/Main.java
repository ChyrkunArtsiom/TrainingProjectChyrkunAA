import by.chyrkun.training.service.resource.MessageManager;

import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try{
            test(0);
        }catch (ArithmeticException ex){
            System.out.println(ex);
        }
        System.out.println("end");
    }

    public static void test(int n){
        int a = 10/n;
    }
}
