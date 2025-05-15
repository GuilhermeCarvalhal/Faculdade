# Importar bibliotecas
import yfinance as yf
import pandas as pd
import matplotlib.pyplot as plt
import plotly.graph_objects as go
import plotly.offline as pyo

# ==========================
# Baixar dados da WEGE3 desde 2020
# ==========================
df = yf.download("WEGE3.SA", start="2020-01-01", end="2025-01-01", group_by='ticker')
df.to_csv("WEGE3_historico.csv")  # Salvar dataset

# ==========================
# Corrigir MultiIndex (Plotly precisa disso limpo)
# ==========================
if isinstance(df.columns, pd.MultiIndex):
    df.columns = df.columns.droplevel(0)

# Garantir que o índice é datetime
df.index = pd.to_datetime(df.index)

def main():
    ex1()
    ex2()
    ex3()

def ex1():
    # ==========================
    # Calcular a média móvel de 30 dias
    # ==========================
    df['Média Móvel 30d'] = df['Close'].rolling(window=30).mean()

    # ==========================
    # Gráfico 1: Linha (Fechamento + Média Móvel)
    # ==========================
    plt.figure(figsize=(12, 6))
    plt.plot(df.index, df['Close'], label='Fechamento', color='blue')
    plt.plot(df.index, df['Média Móvel 30d'], label='Média Móvel 30 dias', color='orange')
    plt.title("Preço de Fechamento da WEGE3 com Média Móvel de 30 dias")
    plt.xlabel("Data")
    plt.ylabel("Preço (R$)")
    plt.legend()
    plt.grid(True)
    plt.show()

def ex2():
    # ==========================
    # Encontrar a data com maior volume negociado
    # ==========================
    data_maior_volume = df['Volume'].idxmax()
    valor_maior_volume = df['Volume'].max()

    print(f"Maior volume negociado: {valor_maior_volume:,} em {data_maior_volume.date()}")

def ex3():
    # ==========================
    # Calcular o retorno diário
    # ==========================
    df['Retorno Diário'] = df['Close'].pct_change()

    # ==========================
    # Gráfico: Retorno Diário
    # ==========================
    plt.figure(figsize=(12, 6))
    plt.plot(df.index, df['Retorno Diário'], label='Retorno Diário', color='purple')
    plt.title("Retorno Diário da WEGE3")
    plt.xlabel("Data")
    plt.ylabel("Retorno (%)")
    plt.grid(True)
    plt.legend()
    plt.show()

main()