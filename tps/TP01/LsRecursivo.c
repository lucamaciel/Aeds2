#include <stdio.h>

int validar(char* s, int i, int tipo, int virgulas) {
    if (s[i] == '\0' || s[i] == '\n' || s[i] == '\r') {
        return (i > 0); 
    }

    char c = s[i];
    int ok = 0;

    int ehVogal = (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                   c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U');
                   
    int ehLetra = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    
    int ehDigito = (c >= '0' && c <= '9');

    if (tipo == 1) {
        if (ehVogal) ok = 1;
    } 
    else if (tipo == 2) {
        if (ehLetra && !ehVogal) ok = 1;
    } 
    else if (tipo == 3) {
        if (ehDigito) ok = 1;
    } 
    else if (tipo == 4) {
        if (ehDigito) {
            ok = 1;
        } else if ((c == '.' || c == ',') && virgulas == 0) {
            ok = 1;
            virgulas = 1;
        }
    }

    if (ok == 0) return 0;

    return validar(s, i + 1, tipo, virgulas);
}

int isFim(char* s) {
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main() {
    char entrada[1000];

    while (fgets(entrada, 1000, stdin)) {
        if (isFim(entrada)) break;

        printf("%s ", validar(entrada, 0, 1, 0) ? "SIM" : "NAO");
        printf("%s ", validar(entrada, 0, 2, 0) ? "SIM" : "NAO");
        printf("%s ", validar(entrada, 0, 3, 0) ? "SIM" : "NAO");
        printf("%s\n", validar(entrada, 0, 4, 0) ? "SIM" : "NAO");
    }

    return 0;
}