Algoritmo "3"
//Escreva um algoritmo que leia o valor de um vendedor, o salário fixo e o total de vendas efetuadas por ele no mês
//(em dinheiro). Sabendo que este vendedor ganha 15% de comissão sobre suas vendas efetuadas e salário no final do
//mês.

var

nome : caracter
salario, vendas : real

inicio

escreval("Olá funcionário, digite seu nome: ")
leia(nome)
escreval("Digite o seu salário em reais: ")
leia(salario)
escreval("Digite o seu total de vendas este mês em reais: ")
leia(vendas)

escreval("Parabéns ", nome, " o valor de seu salário final é: R$", (salario + (vendas * 0.15)))

fimalgoritmo