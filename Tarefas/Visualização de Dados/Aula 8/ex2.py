import pandas as pd

dados = {
    'ano': ['2014', '2011', '2012', '2011', '2012', '2013'],
    'estado': ["PR", "SC", "RS", "RJ", "MG", "SP"],
    'desempenho': [1.5, 10, 3.6, 2.4, 2.9, 3.2]
}

df = pd.DataFrame(dados)

nova_linha = {'ano': '2011', 'estado': 'PR', 'desempenho': 3}

# Usando loc (atenção ao índice!)
df.loc[len(df)] = nova_linha

print(df)

df['divida'] = [1000, 2000, 3000, 4000, 5000, 6000, 7000]

print(df)

print("Estados do Paraná (PR):")
print(df[df['estado'] == 'PR'])

print("Desempenho maior que 3.0:")
print(df[df['desempenho'] > 3.0])