import pandas as pd

dados = {
    'id': list(range(1, 11)),
    'nome': ['Ana', 'Bruno', 'Carlos', 'Daniela', 'Eduardo', 'Fernanda', 'Gustavo', 'Helena', 'Igor', 'Joana'],
    'data_nascimento': ['1990-01-15', '1985-06-23', '1992-03-10', '1988-11-05', '1995-07-20',
                        '1991-09-14', '1993-02-28', '1989-12-01', '1996-08-17', '1994-04-03'],
    'cpf': ['123.456.789-00', '234.567.890-11', '345.678.901-22', '456.789.012-33', '567.890.123-44',
            '678.901.234-55', '789.012.345-66', '890.123.456-77', '901.234.567-88', '012.345.678-99']
}

df = pd.DataFrame(dados)
print("DataFrame original:")
print(df)

df = df.drop(index=5).reset_index(drop=True)
print("\nDataFrame após deletar a sexta linha:")
print(df)


cidades = ['São Paulo', 'Rio de Janeiro', 'Belo Horizonte', 'Curitiba', 'Recife',
           'Salvador', 'Brasília', 'Fortaleza', 'Porto Alegre']  # 9 cidades, pois removemos 1 linha
df['cidade'] = cidades

print("\nDataFrame após adicionar a coluna 'cidade':")
print(df)


print("\nBusca: Pessoas de 'São Paulo'")
print(df[df['cidade'] == 'São Paulo'])

print("\nFiltro: Pessoas nascidas após 1990")
print(df[pd.to_datetime(df['data_nascimento']) > pd.to_datetime('1990-01-01')])

print("\nFiltro: Nomes que começam com a letra 'D' ou posterior")
print(df[df['nome'].str[0] >= 'D'])