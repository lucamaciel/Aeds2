import java.util.Scanner;

//
public class SomaDigitos {

    // Método recursivo para somar os dígitos
    public static int somaDigitos(int n) {
        if (n < 10) {
            return n; // Caso de parada
        }
        return (n % 10) + somaDigitos(n / 10);
        // pega o ultimo digito + a soma dos restantes
    }

    // Função manual para comparar o "FIM" (substituindo o meu_strcmp do C)
    public static boolean isFim(String s) {
        return (s.length() == 3 &&
                s.charAt(0) == 'F' &&
                s.charAt(1) == 'I' &&
                s.charAt(2) == 'M');
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Lendo a primeira entrada
        if (sc.hasNext()) {
            String entrada = sc.next();

            // Enquanto não for "FIM"
            while (!isFim(entrada)) {

                // Converte para número e trata negativos
                int num = Integer.parseInt(entrada);
                if (num < 0) {
                    num = -num; // se o cara digitar negativo transforma pra positivo
                }

                // Calcula e imprime, sem eu colocar em uma variavel, achei melhor
                System.out.println(somaDigitos(num));

                // Lê a próxima entrada
                if (sc.hasNext()) {
                    entrada = sc.next();
                } else {
                    break;
                }
            }
        }
        sc.close();
    }
}