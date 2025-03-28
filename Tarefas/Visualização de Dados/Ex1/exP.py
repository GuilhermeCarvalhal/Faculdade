def main():
    ex1(-3)
    ex2(7)
    ex3(9, 5, 8)
    ex4()

def ex1(num):
    if int(num) < 0:
        print(f"{num} é negativo.")
    else:
        print(f"{num} é positivo.")

def ex2(num):
    primo = True
    for i in range(2, int(num)):
        if int(num) % i == 0:
            primo = False
            break
    if primo:
        print(f"{num} É primo")
    else:
        print(f"{num} Não é primo")

def ex3(num1, num2, num3):
    nums = {num1, num2, num3}

    maior = max(nums, key=int)
    menor = min(nums, key=int)

    print(f"{maior} é o maior")
    print(f"{menor} é o menor")

def ex4():
    while True:
        try:
            n1 = float(input("Digite o primeiro número: "))
            n2 = float(input("Digite o segundo número: "))
            print()
            print("Selecione o operador")
            menu = input(f" 1 - Divisão\n 2 - Multiplicação\n 3 - Soma\n 4 - Subtração\n 0 - Sair\n Input: ")

            match int(menu):
                case 1:
                    result = div(n1, n2)
                case 2:
                    result = mult(n1, n2)
                case 3:
                    result = soma(n1, n2)
                case 4:
                    result = sub(n1, n2)
                case 0:
                    break

            print(f"O resultado é: {result}")
            print()

        except ValueError:
            print("Digite um número válido")

def div(a, b):
    return a / b

def mult(a, b):
    return a * b

def soma(a, b):
    return a + b

def sub(a, b):
    return a - b


main()
