## Questão completa — do zero

---

### O que a questão pede

Você tem esse somatório:

Sum de i=1 até n de [(10i+2)² - (10i+1)²]

Precisa fazer 2 coisas: achar uma fórmula fechada (sem somatório) e provar por indução.

---

## Parte 1 — simplificar o que está dentro do somatório

O termo dentro é (10i+2)² - (10i+1)².

Isso é diferença de quadrados: a² - b² = (a+b)(a-b)

- a = 10i+2
- b = 10i+1
- a+b = 20i+3
- a-b = 1

Então (a+b)(a-b) = (20i+3)(1) = 20i+3

O somatório agora ficou mais simples:

Sum de i=1 até n de (20i+3)

---

## Parte 2 — resolver o somatório (achar a fórmula fechada)

Separa em dois somatórios:

Sum(20i) + Sum(3)

O 20 é constante, sai na frente:

20 * Sum(i) + Sum(3)

Agora aplica duas fórmulas conhecidas:

- Sum(i) → usa Gauss: n(n+1)/2
- Sum(3) → constante somada n vezes: 3n

Substituindo:

20 * n(n+1)/2 + 3n

O 20 dividido por 2 vira 10:

10n(n+1) + 3n

Abre o parênteses:

10n² + 10n + 3n

Fórmula fechada encontrada:

S(n) = 10n² + 13n

---

## Parte 3 — prova por indução

### Base (n=1)

Testa os dois lados com n=1 pra ver se a fórmula funciona.

Lado esquerdo — calcula o somatório diretamente com i=1:

(10·1+2)² - (10·1+1)² = 12² - 11² = 144 - 121 = 23

Lado direito — pega a fórmula fechada que achou na Parte 2 e coloca n=1:

S(1) = 10(1)² + 13(1) = 10 + 13 = 23

23 = 23 ✅ — base provada.

---

### Hipótese

Pega a fórmula fechada da Parte 2 e troca n por k — só isso:

S(k) = 10k² + 13k

Você assume que isso é verdade para algum k.
Não prova, só assume pra usar no próximo passo.

---

### Passo indutivo

Agora precisa provar que funciona para k+1.

Começa separando o último termo do somatório:

S(k+1) = S(k) [hipótese acima] + termo com i=k+1

O termo com i=k+1 vem da Parte 1 — o termo simplificado era 20i+3,
então coloca i=k+1:

= [10k² + 13k] (hipótese) + [20(k+1) + 3]

Abre o parênteses:

= 10k² + 13k + 20k + 20 + 3
= 10k² + 33k + 23

Agora expande o que S(k+1) deveria ser segundo a fórmula da Parte 2 com n=k+1:

10(k+1)² + 13(k+1)
= 10(k²+2k+1) + 13k + 13
= 10k² + 20k + 10 + 13k + 13
= 10k² + 33k + 23 ✅

Os dois lados deram 10k² + 33k + 23 — prova concluída.

---

## Resumo de onde veio cada coisa

| Parte | De onde veio |
|---|---|
| 20i+3 | diferença de quadrados aplicada no termo original |
| 10n²+13n | Gauss + constante×n aplicados no somatório |
| 23=23 na base | fórmula com n=1 vs somatório com i=1 |
| 10k²+13k na hipótese | fórmula fechada com n trocado por k |
| +[20(k+1)+3] no passo | termo simplificado da Parte 1 com i=k+1 |