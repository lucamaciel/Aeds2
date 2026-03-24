import java.util.Scanner;

public class ls {

    // X1: Verifica se a string é composta SOMENTE por vogais
    public static boolean isVogal(String s) {
        if (s.length() == 0)
            return false;
        int acertos = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Aceita vogais minúsculas e maiúsculas
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                    c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                acertos++;
            }
        }
        // Retorna SIM (true) se todos os caracteres forem vogais
        return acertos == s.length();
    }

    // X2: Verifica se a string é composta SOMENTE por consoantes
    public static boolean isConsoante(String s) {
        if (s.length() == 0)
            return false;
        int acertos = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Verifica se é letra básica (A-Z) sem acentos ou cedilha
            boolean ehLetra = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            // Verifica se é vogal
            boolean ehVogal = (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                    c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U');

            // É consoante se for letra E não for vogal
            if (ehLetra == true && ehVogal == false) {
                acertos++;
            }
        }
        return acertos == s.length();
    }

    // X3: Verifica se a string é um número Inteiro
    public static boolean isInteiro(String s) {
        if (s.length() == 0)
            return false;
        int acertos = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Compara com os caracteres '0' a '9' da tabela ASCII
            if (c >= '0' && c <= '9') {
                acertos++;
            }
        }
        return acertos == s.length();
    }

    // X4: Verifica se a string é um número Real
    public static boolean isReal(String s) {
        if (s.length() == 0)
            return false;
        int pontosOuVirgulas = 0;
        int digitos = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                digitos++;
            } else if (c == '.' || c == ',') {
                pontosOuVirgulas++;
            }
        }
        // Regra: tudo deve ser dígito ou ponto/vírgula, e no máximo 1 separador
        boolean formatoCorreto = (digitos + pontosOuVirgulas) == s.length();
        return formatoCorreto && pontosOuVirgulas <= 1;
    }

    // Método auxiliar para verificar o fim da leitura sem usar .equals()
    public static boolean isFim(String s) {
        return s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M';
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String entrada = sc.nextLine();

            // Critério de parada manual (FIM)
            if (isFim(entrada)) {
                break;
            }

            // Gera as saídas baseadas nos métodos acima
            String x1 = isVogal(entrada) ? "SIM" : "NAO";
            String x2 = isConsoante(entrada) ? "SIM" : "NAO";
            String x3 = isInteiro(entrada) ? "SIM" : "NAO";
            String x4 = isReal(entrada) ? "SIM" : "NAO";

            // Imprime no formato: X1 X2 X3 X4
            System.out.println(x1 + " " + x2 + " " + x3 + " " + x4);
        }
        sc.close();
    }
}