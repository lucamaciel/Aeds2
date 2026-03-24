import java.util.Scanner;

public class Alteracao {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int seed = 4;

        String s0 = sc.nextLine();
        while (!s0.equals("FIM")) {
            String resultado = "";

            // Sorteio das letras sem usar Random
            seed = (seed * 1103515245 + 12345) & 0x7fffffff;
            char letra1 = (char) ('a' + (seed % 26));

            seed = (seed * 1103515245 + 12345) & 0x7fffffff;
            char letra2 = (char) ('a' + (seed % 26));

            for (int i = 0; i < s0.length(); i++) {
                if (s0.charAt(i) == letra1) {
                    resultado = resultado + letra2;
                } else {
                    resultado = resultado + s0.charAt(i);
                }
            }

            System.out.println(resultado);
            s0 = sc.nextLine();
        }
        sc.close();
    }
}
