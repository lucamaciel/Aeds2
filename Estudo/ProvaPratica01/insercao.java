package Estudo.ProvaPratica01;

import java.util.Scanner;

public class insercao {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Digite o tamanho do vetor: ");
            int tam = sc.nextInt();

            int[] vet = new int[tam]; // Criação do vetor com o tamanho definido
            //IMPORTANTE : em java, para criar um vetor, tem que ser do tipo e do tamanho definido, nao tem como criar um vetor dinamico como em outras linguagens.

            for (int i = 0; i < tam; i++) {
                System.out.print("Digite um valor para o vetor: ");
                vet[i] = sc.nextInt();
            }

            insercaosort(vet, tam);

            System.out.print("Vetor ordenado: ");
            for (int i = 0; i < tam; i++) {
                System.out.print(vet[i] + " ");
            }
        }
    }
 // O método de ordenação por inserção, tem que ser feito com um for para percorrer o vetor, 
 // e um while para comparar os elementos e fazer as trocas.
    public static void insercaosort(int[] vet, int tam) {
        for (int i = 1; i < tam; i++) {
            int temp = vet[i]; //Guarda o valor do elemento atual, para fazer a comparação e as trocas
            int j = i - 1; // o J começa na posição anterior ao i, para comparar o elemento atual com os elementos anteriores

            while (j >= 0 && vet[j] > temp) {
                //aqui acontece a comparação, enquanto o j for maior ou igual a 0 e o elemento 
                // na posição j for maior que o temp, ou seja, o elemento atual, entao tem que
                //  fazer a troca
                vet[j + 1] = vet[j]; //passa o valor do elemento na posição j para a posição j+1,]
                //  ou seja, para a proxima posição, fazendo a troca
                j--;
            }
            vet[j + 1] = temp; //passa o valor guardado do elemento atual para a posição j+1, 
            // ou seja, para a proxima posição, fazendo a troca
        }
        //exemplo Pratico:
        //vet = [5, 2, 9, 1, 5]
        //i = 1, temp = 2, j = 0
        //while (j >= 0 && vet[j] > temp) -> while (0 >= 0 && 5 > 2) -> true
        //vet[j + 1] = vet[j] -> vet[1] = vet[0] -> vet = [5, 5, 9, 1, 5]
        //j-- -> j = -1
        //vet[j + 1] = temp -> vet[0] = 2 -> vet = [2, 5, 9, 1, 5]
        //i = 2, temp = 9, j = 1
        //while (j >= 0 && vet[j] > temp) -> while (1 >= 0 && 5 > 9) -> false
        //vet[j + 1] = temp -> vet[2] = 9 -> vet = [2, 5, 9, 1, 5]
        //i = 3, temp = 1, j = 2
        //while (j >= 0 && vet[j] > temp) -> while (2 >= 0 && 9 > 1) -> true
        //vet[j + 1] = vet[j] -> vet[3] = vet[2] -> vet = [2, 5, 9, 9, 5]
        //j-- -> j = 1
        //while (j >= 0 && vet[j] > temp) -> while (1 >= 0 && 5 > 1) -> true
        //vet[j + 1] = vet[j] -> vet[2] = vet[1] -> vet = [2, 5, 5, 9, 5]
        //j-- -> j = 0
        //while (j >= 0 && vet[j] > temp) -> while (0 >= 0 && 2 > 1) -> true
        //vet[j + 1] = vet[j] -> vet[1] = vet[0] -> vet = [2, 2, 5, 9, 5]
        //j-- -> j = -1
        //vet[j + 1] = temp -> vet[0] = 1 -> vet = [1, 2, 5, 9, 5]
        //i = 4, temp = 5, j = 3
        //while (j >= 0 && vet[j] > temp) -> while (3 >= 0 && 9 > 5) -> true
        //vet[j + 1] = vet[j] -> vet[4] = vet
        //j-- -> j = 2
        //while (j >= 0 && vet[j] > temp) -> while (2 >= 0 && 5 > 5) -> false
        //vet[j + 1] = temp -> vet[3] = 5 -> vet = [1, 2, 5, 5, 9]
        //Após a execução do método de ordenação por inserção, o vetor estará ordenado em ordem crescente, ou seja, [1, 2, 5, 5, 9].
    }
}