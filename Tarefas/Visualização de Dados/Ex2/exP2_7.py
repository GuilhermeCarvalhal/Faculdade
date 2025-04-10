def main():
    frutas = ["maça", "morango", "pera", "laranja", "mamão"]

    frutas.sort(reverse=True)
    frutas.pop(1)
    frutas.insert(0, "banana")
    frutas.append("uva")
    if 'morango' in frutas:
        print("Morango está na lista")
    else:
        print("Morango não está na lista")
    print(frutas)
main()