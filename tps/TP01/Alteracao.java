import java.util.Scanner;
import java.util.Random;

public class Alteracao {
    public static boolean isFim(String s) {
        return s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M';
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random(0);

        String s0 = sc.nextLine();
        while (!isFim(s0)) {
            char letra1 = (char) ('a' + random.nextInt(26));
            char letra2 = (char) ('a' + random.nextInt(26));

            String resultado = s0.replace(letra1, letra2);

            System.out.println(resultado);
            s0 = sc.nextLine();
        }
        sc.close();
    }
}
