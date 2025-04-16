notas = {"João": 9.5, "Pedro": 2.5, "Maria": 3}

aluno = "João"
print(f"{aluno}, {notas["João"]}")

notas.pop("João")

for alunos in notas:
    print(f"Nome: {alunos} | Nota: {notas[alunos]}")