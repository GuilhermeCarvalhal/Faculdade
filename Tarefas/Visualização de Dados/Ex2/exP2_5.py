def main():
    meses = ("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
         "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
    mes = meses[2]

    numeros = (1, 2, 3, 4)

    concatenacao = meses + numeros

    print(f"{len(concatenacao)} e {concatenacao[-1]}")

main()
