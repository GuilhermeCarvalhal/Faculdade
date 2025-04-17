import pandas as pd

dados_vendas = {
    'Produto': ['Notebook', 'Mouse', 'Teclado', 'Monitor', 'Mouse', 'Notebook', 'Monitor', 'Teclado'],
    'Quantidade': [1, 2, 1, 1, 3, 2, 1, 2],
    'Preço': [3500.00, 50.00, 120.00, 800.00, 50.00, 3400.00, 820.00, 110.00],
    'Data': [
        '2023-09-01', '2023-09-01', '2023-08-31', '2023-09-02',
        '2023-09-01', '2023-09-03', '2023-09-01', '2023-08-30'
    ]
}

df_vendas = pd.DataFrame(dados_vendas)
print("DataFrame completo de vendas:")
print(df_vendas)

vendas_01_setembro = df_vendas[df_vendas['Data'] == '2023-09-01']
print("\nVendas realizadas em 2023-09-01:")
print(vendas_01_setembro)