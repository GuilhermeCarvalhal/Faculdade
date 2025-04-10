def main():
    nova = []
    nova2 = [11, 12]

    for i in range(10):
        nova.append(i+1)

    nova.pop(0)
    nova.pop(-1)

    nova.append(nova2)

    print(nova)

main()