package Anotações;
/*

Tipos abstratos de dados - INtrodução a estruturas de dados

Em java não se usa a linguagem ponteiro, e sim REFERENCIA

Ponteiro: é uma variaável que guarda endereço de emmória, ou seja, aponta para um local na memória 
onde um objeto está armazenado.

*/
public class Celula {
    Cliente c1 = new Cliente(1, "aa");
    Cliente c2 = null;
    //c2.codigo = 9 ->> nesse caso mudamos o código do objeto que c2 aponta logo ficaria 9/aa,
    //  mas como c2 é null, não podemos acessar o código do objeto, pois c2 não aponta para nenhum objeto ainda.
    c2 = c1; //c2 recebe a referencia de c1, ou seja, c2 aponta para o mesmo objeto que c1 aponta
    c2=null; //c2 recebe null, ou seja, c2 não aponta para nenhum objeto, mas c1 continua apontando para o objeto criado
    c2 = c1.clone(); //c2 recebe uma nova referencia, ou seja, c2 aponta para um novo objeto criado a partir do objeto apontado por c1    
}
