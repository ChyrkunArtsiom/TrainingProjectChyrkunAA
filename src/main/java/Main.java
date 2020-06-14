import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.resource.MessageManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        String a = null;
        System.out.println(a);
    }

    public Test createmap(){
        Test map = new Test();
        map.change("test", "b");
        return map;
    }

    public static void test(Test t) {
        t.change("test", "test");
    }

    class Test{
        HashMap<String, String> map;
        Test(){
            map = new HashMap<>();
        }
        public void change(String a, String b){
            map.put(a, b);
        }
    }
}
