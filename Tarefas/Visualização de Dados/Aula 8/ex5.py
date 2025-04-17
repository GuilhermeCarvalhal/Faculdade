import pandas as pd

clientes = {
    'ID': [1, 2, 3],
    'Nome': ['Alice', 'Bruno', 'Carla']
}
df_clientes = pd.DataFrame(clientes)

pedidos = {
    'ID do Cliente': [1, 2, 1, 3, 2],
    'Total do Pedido': [250.0, 100.0, 75.5, 300.0, 50.0]
}
df_pedidos = pd.DataFrame(pedidos)

df_resultado = pd.merge(df_pedidos, df_clientes, left_on='ID do Cliente', right_on='ID')

df_resultado = df_resultado[['Nome', 'ID do Cliente', 'Total do Pedido']]

print("Pedidos com nome dos clientes:")
print(df_resultado)