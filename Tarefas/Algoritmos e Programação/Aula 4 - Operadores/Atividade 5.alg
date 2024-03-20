Algoritmo "5"
//Elaborar um algoritmo que efetue a apresentação do valor da conversão em real (R$) de um valor lido em dólar
//(U$$). O algoritmo deverá solicitar o valor da cotação do dólar e também a quantidade de dólares disponíveis com o
//usuário.

var

dolar, cotacao : real 

inicio

escreval("Digite o valor que você possue em dólares no momento: ")
leia(dolar)
escreval("Digite a cotação do dólar: ")
leia(cotacao)
escreval("A conversão de seu valor em doláres para reais é: ", (dolar * cotacao))


fimalgoritmo