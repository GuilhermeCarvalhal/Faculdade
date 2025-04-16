frase = "Não devemos reinventar as rodas."

letras = set()

fraselower = frase.lower()

for i in fraselower:
    if i.isalpha():  
        letras.add(i)

for letra in sorted(letras):
    print(letra)