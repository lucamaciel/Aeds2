//tipos abstratos de dados lineares - indução
//inserção em um vetor - deslocamento de elementos

import java.util.Scanner;

class Celula{
    public int elemento;
    public Celula prox;
    public Celula(int x) {
        this.elemento = x;
        this.prox = null;
     }
    public void inserir(int x) {
        Celula tmp = new Celula(x);
        tmp.prox = topo;
        topo = tmp;
        tmp = null;
    }
    public int remover() {
        if (topo == null) {
            throw new RuntimeException("Pilha vazia");
        }
        int elemento = topo.elemento;
        Celula tmp = topo;
        topo = topo.prox;
        tmp.prox = null;
        tmp = null;
        return elemento;
    }
    public void mostrar() {
        Celula tmp = topo;
        while (tmp != null) {
            System.out.print(tmp.elemento + " ");
            tmp = tmp.prox;
        }
        System.out.println();
     }

    //EX1) Faça um metodo que retorna a soma dos elementos da pilha
    public int soma() {
        int soma = 0;
        for ( Celula i = topo; i != null ; i = i.prox){
            soma = soma + i.elemento;
        }
        return soma;
}
    //EX2) Faça um metodo recursive que retorne a soma dos elementos da pilha
    public int soma_recursiva(Celula topo){
        if (topo == null) {
            return 0;
        }
        return topo.elemento + soma_recursiva(topo.prox);

    }
    //EX3) Faça um metodo que retorne o maior elemento da pilha
    public int maior() {
        if (topo == null) {
            throw new RuntimeException("Pilha vazia");
        }
        int maior = topo.elemento;
        //varrer a pilha para encontrar o maior elemento
        for ( Celula i = topo.prox; i != null ; i = i.prox){
            if (i.elemento > maior) {
                maior = i.elemento;
            }
        }
        return maior;
    }
    //Ex04) Faça um metodo recursivo que retorne o maior elemento da pilha
    public int maior_recursivo(Celula topo) {
        if (topo == null) {
            throw new RuntimeException("Pilha vazia");
        }
        if (topo.elemento > topo.prox.elemento) {
             return maior_recursivo(topo.prox);
        } else {
            return maior_recursivo(topo);
            //agora ta certo, a recurssão so para quando topo for null
            //e quando o elemento atual for maior que o próximo elemento, ai ele retorna o elemento atual
            //se o próximo elemento for maior que o elemento atual, ai ele retorna o próximo elemento, certinho agora
    }
    //EX05) Faça um metodo Iterativo para mostra ros elementos da pilha na ordem que foram inseridos
    
}