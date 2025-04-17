import pandas as pd

alunos = {
    'Nome': ["Guilherme", "Sabrina", "Lucas"],
    'Idade': [15, 17, 21],
    'Nota': [9, 10, 4]
}
df = pd.DataFrame(alunos)

media_nota = df['Nota'].mean()

print(f"{media_nota:.2f}")


faixas = pd.cut(df['Idade'], bins=[10, 15, 20, 25], labels=['10-15', '16-20', '21-25'])

df['Faixa Etária'] = faixas

media_por_faixa = df.groupby('Faixa Etária')['Nota'].mean().reset_index()

print("Média das notas por faixa etária:")
print(media_por_faixa)