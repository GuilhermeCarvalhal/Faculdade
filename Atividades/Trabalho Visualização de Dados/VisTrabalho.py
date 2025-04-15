import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import folium
from folium.plugins import MarkerCluster

df = pd.read_csv("dataset-limpo.csv")  # Lendo arquivo
colunas_desejadas = [ 
    "created_at", "bairro", "descricao", "endereco", "latitude", "longitude", 
    "registrou_bo", "titulo", "valor_prejuizo"
    ]

df = df[colunas_desejadas] #Filtrando o datasheet
df['bairro'] = df['endereco'].str.extract(r'-\s([^,]+)') #removendo bairros 'São Paulo'

    # Converter datas - Exemplo: Transforma "2024-03-15 12:34:00" em um formato que o python consiga identificar a data do acontecimento
df["created_at"] = pd.to_datetime(df["created_at"])

    # Criar colunas úteis para separa o ano e o mês
df["ano"] = df["created_at"].dt.year
df["mes"] = df["created_at"].dt.month

    # Definir menor ano e maior ano para título futuro
menor_ano = df["ano"].min() 
maior_ano = df["ano"].max()




def main():
    g1()
    #g2()
    #g3()
    
def g1():

    x = 10 #Top bairros crimes
    top_bairros = df['bairro'].value_counts().head(x) #Recebe a coluna bairro, valuecounts faz conta de quantas vezes os bairros aparecem e mostra os top X bairros

    plt.figure(figsize=(10,6)) #Tamanho do gráfico
    sns.barplot(x=top_bairros.values, y=top_bairros.index, palette='viridis') #Usa como x(Largura) 
    titulo = f"Top {x} Bairros com Mais Ocorrências entre {menor_ano} e {maior_ano}"
    plt.title(titulo)
    plt.xlabel("Número de Ocorrências")
    plt.ylabel("Bairro")
    plt.tight_layout()
    plt.show()

def g2():

    ocorrencias_mensais = df.groupby(['ano', 'mes']).size().reset_index(name='total')
    ocorrencias_mensais['data'] = pd.to_datetime(ocorrencias_mensais.rename(columns={'ano': 'year', 'mes': 'month'}).assign(day=1)[['year', 'month', 'day']])

    #print(ocorrencias_mensais) #Printa o número de ocorrências mês/ano

    plt.figure(figsize=(12,6))
    sns.lineplot(data=ocorrencias_mensais, x='data', y='total')
    plt.title("Tendência de Ocorrências Criminais em São Paulo ao Longo do Tempo")
    plt.xlabel("Data")
    plt.ylabel("Ocorrências")
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.show()  



def g3():
    mapa = folium.Map(
    location=[-23.55, -46.63],  # centro de SP
    zoom_start=11
)
    marker_cluster = MarkerCluster().add_to(mapa)

    for _, row in df.dropna(subset=['latitude', 'longitude', 'bairro']).iterrows():
        folium.Marker(
            location=[row['latitude'], row['longitude']],
            popup=row['bairro']
        ).add_to(marker_cluster)

    mapa.save("mapa.html")

main()