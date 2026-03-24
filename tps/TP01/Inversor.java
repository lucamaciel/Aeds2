import java.util.Scanner;

public class Inversor {

    // A função que inverte. O 'i' é o nosso ponteiro que começa no final.
    public static void inverter(String s, int i) {
        // Se o i chegar em -1, a string acabou, então para.
        if (i >= 0) {
            // Imprime o caractere da ponta e chama a função pro vizinho da esquerda
            System.out.print(s.charAt(i));
            inverter(s, i - 1);
        }
    }

    // Pra fugir do .equals() que o professor proibiu
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String linha = sc.nextLine();

            if (isFim(linha))
                break;

            // Começa do length-1 porque string em Java é 0-based
            inverter(linha, linha.length() - 1);
            System.out.println(""); // Pula a linha pro próximo caso
        }
        sc.close();
    }
}