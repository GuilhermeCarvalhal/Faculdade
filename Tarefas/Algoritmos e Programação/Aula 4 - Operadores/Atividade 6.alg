Algoritmo "6"
//Faça um algoritmo que receba um valor que foi depositado e exiba o valor com rendimento após um mês.
//Considere fixo o juro da poupança em 0,70% a. m


var

poupanca, juro : real

inicio

escreval("Insira o valor que deseja depositar na poupança: ")
leia(poupanca)
juros <- poupanca * (0.70/100)
escreval("O rendimento de sua poupança após um mês gerou o seguinte valor: ", (poupanca+juros))

fimalgoritmo