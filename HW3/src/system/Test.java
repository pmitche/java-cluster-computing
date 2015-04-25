package system;

import java.util.ArrayList;

/**
 * Created by Kyrre on 23.04.2015.
 */
public class Test {

    public static void main(String[] args) {
        ArrayList<String> al = new ArrayList<String>();
        al.add("sg");
        al.add("sg");
        al.add("sg");
        al.add("sg");
        al.add("sg");
        al.add("sg");

        al.add(4, "derp");

        Closure c = new Closure(null,0);
        System.out.println(c.id);

    }
}
