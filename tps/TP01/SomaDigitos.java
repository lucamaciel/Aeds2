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

    public static int converterParaInteiro(String s) {
        int numero = 0;
        int inicio = 0;

        if (s.length() > 0 && s.charAt(0) == '-') {
            inicio = 1;
        }

        for (int i = inicio; i < s.length(); i++) {
            numero = (numero * 10) + (s.charAt(i) - '0');
        }

        if (inicio == 1) {
            numero = -numero;
        }

        return numero;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Lendo a primeira entrada
        if (sc.hasNext()) {
            String entrada = sc.next();

            // Enquanto não for "FIM"
            while (!isFim(entrada)) {

                // Converte para número e trata negativos
                int num = converterParaInteiro(entrada);
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
