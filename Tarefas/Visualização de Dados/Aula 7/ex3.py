strrandom = "Em uma pequena vila escondida entre montanhas, os moradores viviam em perfeita harmonia com a natureza. As manhãs eram sempre embaladas pelo canto dos pássaros e o aroma de pão fresco que saía das janelas das casas. As crianças corriam pelas ruas de paralelepípedo enquanto os mais velhos contavam histórias antigas sob as sombras das árvores. Era como se o tempo passasse mais devagar ali, permitindo que cada momento fosse vivido com mais calma e significado."

lista_palavras = strrandom.split()

print(lista_palavras)

contagem_palavras = {}

for i in lista_palavras:
    if i in contagem_palavras:
        contagem_palavras[i] += 1
    else:
        contagem_palavras[i] = 1

for i in sorted(contagem_palavras):
    print(f"{i}: {contagem_palavras[i]}")