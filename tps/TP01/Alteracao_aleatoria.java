import java.util.Scanner;
import java.util.Random;

public class Alteracao { // O nome da classe deve ser igual ao nome do arquivo
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Random gerador = new Random();
        gerador.setSeed(4); // Semente para o sistema de correção

        // Corrigido: declarando e lendo a primeira linha
        String s0 = sc.nextLine();

        while (!s0.equals("FIM")) {
            String resultado = ""; 

            // Sorteio das letras
            char letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            char letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));

            for (int i = 0; i < s0.length(); i++) {
                if (s0.charAt(i) == letra1) {
                    resultado = resultado + letra2; 
                } else {
                    resultado = resultado + s0.charAt(i);
                }
            }
            
            System.out.println(resultado);
            s0 = sc.nextLine(); // Próxima linha
        }
        sc.close();
    }
}
//dificuldade 8/10, pois o raciocinio foi facil de entender, mas tive que pesquisar um pouco sobre a função "Math.abs" e "Random" para poder fazer o programa funcionar corretamente.
// também tive que pesquisar sobre a função "nextInt" para poder gerar números aleatórios e a função "setSeed" para poder definir a semente do gerador de números aleatórios.