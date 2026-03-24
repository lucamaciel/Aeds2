import java.util.Scanner;

public class senha {

    // Método que faz a mágica da validação
    public static boolean validacao(String s) {

        // Lembrete: length() com parênteses é permitido!
        // Se a senha for menor que 8, já para tudo e retorna falso.
        if (s.length() < 8) {
            return false;
        }

        // IMPORTANTE: Criar as variáveis FORA do for.
        // Se criar dentro do if, elas resetam toda hora e não somam nada.
        int maiusculas = 0;
        int minusculas = 0;
        int numeros = 0;
        int caracter_especial = 0;

        // O 'for' vai caminhar pela string letra por letra
        for (int i = 0; i < s.length(); i++) {
            char temp = s.charAt(i); // Pega a letra da posição atual

            // Testa se é MAIÚSCULA (Intervalo A até Z)
            if (temp >= 'A' && temp <= 'Z') {
                maiusculas++; // Achei uma! Soma no contador
            }
            // Testa se é MINÚSCULA (Intervalo a até z)
            else if (temp >= 'a' && temp <= 'z') {
                minusculas++;
            }
            // Testa se é NÚMERO (Intervalo '0' até '9' - usar aspas!)
            else if (temp >= '0' && temp <= '9') {
                numeros++;
            }
            // Se não caiu em nenhum dos de cima, sobrou ser ESPECIAL
            else {
                caracter_especial++;
            }
        }

        // TESTE FINAL: Só é SIM se tiver pelo menos 1 de cada categoria
        // Se algum desses for zero, o '&&' vai dar falso.
        return (maiusculas >= 1 && minusculas >= 1 && numeros >= 1 && caracter_especial >= 1);
    }

    // Método manual para o FIM (já que .equals é proibido)
    public static boolean isFim(String s) {
        return (s.length() == 3 &&
                s.charAt(0) == 'F' &&
                s.charAt(1) == 'I' &&
                s.charAt(2) == 'M');
    }

    // A main é onde o programa realmente começa a rodar
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Fica lendo enquanto o usuário mandar linha
        while (sc.hasNextLine()) {
            String s = sc.nextLine();

            // Se a linha for "FIM", o break quebra o loop e encerra
            if (isFim(s)) {
                break;
            }

            // Manda a senha pro validador e vê o que ele responde
            if (validacao(s)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }

        sc.close(); // Fechar o scanner pra não gastar memória à toa
    }
}