conjunto1 = {1,2,3,4}
conjunto2 = {3,4,5,6}

uniao = conjunto1.union(conjunto2)
print(uniao)

intersecao = conjunto1.intersection(conjunto2)
print(intersecao)

if "elemento" in conjunto1:
    print(f"Elemento está presente em conjunto1.")
else:
    print(f"Elemento não está presente em conjunto1.")
