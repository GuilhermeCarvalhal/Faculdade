numeros = []

def main():
    for i in range(5):
        numeros.append(i+1)

    numeros.remove(3)
    numeros.insert(2, 6)

    print(*numeros)

main()
