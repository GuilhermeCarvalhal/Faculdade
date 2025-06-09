import pandas as pd #Biblioteca responsável por ler o dataset
import matplotlib.pyplot as plt #Criação e customização de gráficos
import geopandas as gpd #Transformação de coordenadas em geometrias
from shapely.geometry import Point #Representação de geometrias. (Conversão de latitudes e longitudes)
import folium  #Geração de mapas interativos
from folium.plugins import MarkerCluster 

df = pd.read_csv("Código\dataset-limpo.csv")  # Lendo arquivo
colunas_desejadas = [ #Filtrando as colunas que desejamos utilizar
    "id", "created_at", "bairro", "descricao", "endereco", "latitude", "longitude", 
    "registrou_bo", "titulo", "valor_prejuizo"
    ]

df = df[colunas_desejadas] #Utilizando o filtro
df['bairro'] = df['endereco'].str.extract(r'-\s([^,]+)') #removendo bairros 'São Paulo'

# Converter datas - Exemplo: Transforma "2024-03-15 12:34:00" em um formato que o python consiga identificar a data do acontecimento
df["created_at"] = pd.to_datetime(df["created_at"])

# Criar colunas úteis para separar o ano, mês e hora
df["ano"] = df["created_at"].dt.year
df["mes"] = df["created_at"].dt.month
df["hora"] = df["created_at"].dt.hour

# Definir menor ano e maior ano
menor_ano = df["ano"].min() 
maior_ano = df["ano"].max()

#Filtrando municipio escolhido
sp_municipios = gpd.read_file("Código\SP_Municipios_2023.shp")
municipio = sp_municipios[sp_municipios['NM_MUN'] == 'São Paulo'] #NM_MUN = Nome Municipio (Está especificado no manual de uso)

#Setando geometria - OBRIGATÓRIO PARA O RECONHECIMENTO DE COORDENADAS PELO FOLIUM E A GEOPANDAS
geometry = [Point(xy) for xy in zip(df['longitude'], df['latitude'])]

# Prepara os dados de crime com a GeoPandas
crimes = gpd.GeoDataFrame(df, geometry=geometry, crs="EPSG:4326") #crs = geografic coordinate system, é um sistema
#Se transforma em um conjunto de dados mapeáveis onde cada linha irá conter os atributos das colunas em uma localização espacial 
#Geometry = ponto no mapa com informações
crimes = crimes.to_crs(municipio.crs) #garante que nosso GeoDataFrame vai ter o mesmo crs que o nosso SHP

def main():
    g3(crimes, municipio)  #Gráfico 3 
    

def g3(crimes, local):

    crimes_em_local = gpd.sjoin(crimes, local, how="inner", predicate="within")

    # Configura o plot
    fig, ax = plt.subplots(figsize=(15, 12))
    
    # Plota o município
    local.plot(ax=ax, color='lightgray', edgecolor='black', alpha=0.5)
    
    # Plota os crimes com cor e tamanho proporcional ao prejuízo
    mapa = crimes_em_local.plot(
        ax=ax, 
        column='valor_prejuizo',  # Cor baseada no valor
        cmap='YlOrRd',  # Escala amarelo-laranja-vermelho
        markersize=crimes_em_local['valor_prejuizo']/1000,  # Tamanho proporcional divido por 1000 para ajustar a escala
        alpha=0.7,
        legend=True,
        legend_kwds={ #personaliza a legenda de cores
            'label': "Valor do Prejuízo (R$)",
            'orientation': "horizontal",
            'shrink': 0.5,
            'aspect': 40,
            'pad': 0.02
        }
    )
            
    # Configurações
    title = (
        f'Distribuição Espacial do Prejuízo por Crime\n'
        f'Município de São Paulo ({menor_ano}-{maior_ano})\n'
    )
    ax.set_title(title, fontsize=14, pad=20)
    ax.set_axis_off()
    
    # Adiciona nota explicativa
    plt.figtext(0.5, 0.05, 
        f"Análise de {len(crimes_em_local):,} crimes registrados | "
        f"Média de prejuízo: R${crimes_em_local['valor_prejuizo'].mean():.2f}",
        ha="center", fontsize=10, bbox=dict(facecolor='white', alpha=0.7, edgecolor='lightgray'))
    
    plt.figtext(0.02, 0.65, 
        "Tamanho e cor do ponto proporcional ao valor do prejuízo",
        ha="left", fontsize=10, bbox=dict(facecolor='white', alpha=0.7))
    
    plt.tight_layout()

    plt.figtext(0.5, 0.02, 
        f"Prejuízo acumulado: R${crimes_em_local['valor_prejuizo'].sum():,.2f}",
        ha="center", fontsize=10, bbox=dict(facecolor='white', alpha=0.7, edgecolor='lightgray'))
    
    plt.savefig("gráfico_com_outlier", dpi=300) #Salva    
    plt.show()


main()