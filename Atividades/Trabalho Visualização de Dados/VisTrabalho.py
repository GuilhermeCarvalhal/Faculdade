import pandas as pd #Biblioteca responsável por ler o dataset
import matplotlib.pyplot as plt #Criação e customização de gráficos
import geopandas as gpd #Transformação de coordenadas em geometrias
from shapely.geometry import Point #Representação de geometrias (pontos, polígonos) para análise espacial. (Conversão de latitudes e longitudes)
import folium  #Geração de mapas interativos
from folium.plugins import MarkerCluster 

df = pd.read_csv("dataset-limpo.csv")  # Lendo arquivo
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
sp_municipios = gpd.read_file("SP_Municipios_2023.shp")
municipio = sp_municipios[sp_municipios['NM_MUN'] == 'São Paulo'] #NM_MUN = Nome Municipio (Está especificado no manual de uso)

#Setando geometria - OBRIGATÓRIO PARA O RECONHECIMENTO DE COORDENADAS PELO FOLIUM E A GEOPANDAS
geometry = [Point(xy) for xy in zip(df['longitude'], df['latitude'])]

# Prepara os dados de crime com a GeoPandas
crimes = gpd.GeoDataFrame(df, geometry=geometry, crs="EPSG:4326") #crs = geografic coordinate system, é um sistema
#Se transforma em um conjunto de dados mapeáveis onde cada linha irá conter os atributos das colunas em uma localização espacial 
#Geometry = ponto no mapa com informações
crimes = crimes.to_crs(municipio.crs) #garante que nosso GeoDataFrame vai ter o mesmo crs que o nosso SHP

def main():
    g1(crimes, municipio)   #Gráfico 1
    g2(crimes, municipio)  #Gráfico 2
    g3(crimes, municipio)  #Gráfico 3 
    g4()  #Gráfico 4
    #curioso()
    
def g1(crimes, local):

    #Compara visualmente 4 períodos do dia com cores de um colormap
    
    # Definir períodos
    periodos = [
        ('Madrugada', 0, 6),
        ('Manhã', 6, 12),
        ('Tarde', 12, 18),
        ('Noite', 18, 24)
    ]
    
    # Criar figura com 4 subplots OU SEJA 2x2 = 4
    fig, axs = plt.subplots(2, 2, figsize=(20, 16)) #figsize = tamanho 
    axs = axs.flatten() #axs = eixo
    
    # Usar um colormap (ex: 'viridis', 'plasma', 'rainbow')
    cores = plt.cm.get_cmap('plasma', len(periodos))
    
    for i, (nome, inicio, fim) in enumerate(periodos): #Madruga, 0, 6 | Enumerate é um jeito de ler o indice com todos os valores da lista de tuplas "periodos"
        # Filtrar crimes
        crimes_periodo = crimes[(crimes['hora'] >= inicio) & (crimes['hora'] < fim)] #Filtra TODOS os crimes cometidos entre INICIO e FIM FIM OBS: Nome representa madrugada, manhã...
        crimes_em_local = gpd.sjoin(crimes_periodo, local, how="inner", predicate="within") #Mescla os crimes com o SHP, predicate garante que somente os crimes nos limites 
        #do municipio sejam plotados
        
        # Plotar o mapa
        local.plot(ax=axs[i], color='lightgray', edgecolor='black', alpha=0.3)
        
        # Cada crime é um ponto 'o' com uma cor e transparência única 
        crimes_em_local.plot(ax=axs[i], marker='o', color=cores(i), markersize=10, alpha=0.7,
                         label=f'{nome} ({inicio}h-{fim}h)')
        
        #Configs dos subplots (legenda pequena acima deles)
        axs[i].set_title(f'{nome} ({inicio}h-{fim}h)\nTotal de crimes: {len(crimes_em_local)}', fontsize=14)
        axs[i].legend() 
        axs[i].set_axis_off()
    #Titulo geral
    fig.suptitle(f'Distribuição Temporal de Crimes em São Paulo ({menor_ano}-{maior_ano})', fontsize=10)
    plt.tight_layout() #Ajusta espaçamento
    plt.show() #Mostra o mapa


def g2(crimes, local):
    #Cria um gráfico de hexágonos mostrando os locais em que ocorrem mais crimes utilizando da escala logarítmica
    crimes_em_local = gpd.sjoin(crimes, local, how="inner", predicate="within") #Mescla os crimes com o SHP, predicate garante que somente os crimes nos limites 
    #do municipio sejam plotados            

    fig, ax = plt.subplots(figsize=(15, 12)) #cria uma figura única de 15x12 polegadas
    local.plot(ax=ax, color='lightgray', edgecolor='black', alpha=0.3)
        
    hb = ax.hexbin( #hb = hexbin
        x=crimes_em_local.geometry.x, #longitude 
        y=crimes_em_local.geometry.y, #latitude
        gridsize=50,  #Número de hexágonos no grid (mais = áreas menores e mais precisas).
        cmap='plasma',  # Escala de cores alternativa: 'viridis', 'inferno'
        alpha=0.8, #Transparência
        bins='log',  # Escala logarítmica
        edgecolor='none'  # Remove bordas dos hexágonos
    )
        
    # Barra de cores (Legenda)
    cb = fig.colorbar(hb, ax=ax, label='Densidade de Crimes (escala log)')
    cb.outline.set_visible(False)  # Remove borda da barra de cores
    
        
    # Configurações
    ax.set_title(f'Hotspots de Criminalidade em São Paulo ({menor_ano}-{maior_ano})\n', fontsize=16, pad=20) #titulo
    ax.set_axis_off() #Remove eixos
        
    # Adiciona legenda explicativa
    plt.figtext(0.15, 0.08, 
                "Cada hexágono representa uma área com concentração de crimes.\n"
                "Cores mais quentes indicam maior densidade de ocorrências.",
                ha="left", fontsize=10, bbox=dict(facecolor='white', alpha=0.7)) 
        
    plt.tight_layout() #Formata o mapa
    plt.show() #mostra

def g3(crimes, local):

    crimes_em_sp = gpd.sjoin(crimes, local, how="inner", predicate="within")
    
    # Calcula o percentil de 95% de valor de prejuizo e remove 5% dos maiores valores para evitar distorção na visualização
    q95 = crimes_em_sp['valor_prejuizo'].quantile(0.95)
    crimes_filtrados = crimes_em_sp[crimes_em_sp['valor_prejuizo'] <= q95]
    
    # Configura o plot
    fig, ax = plt.subplots(figsize=(15, 12))
    
    # Plota o município
    local.plot(ax=ax, color='lightgray', edgecolor='black', alpha=0.5)
    
    # Plota os crimes com cor e tamanho proporcional ao prejuízo
    scatter = crimes_filtrados.plot(
        ax=ax, 
        column='valor_prejuizo',  # Cor baseada no valor
        cmap='YlOrRd',  # Escala amarelo-laranja-vermelho
        markersize=crimes_filtrados['valor_prejuizo']/1000,  # Tamanho proporcional divido por 1000 para ajustar a escala
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
        f"Análise de {len(crimes_filtrados):,} crimes registrados | "
        f"Média de prejuízo: R${crimes_filtrados['valor_prejuizo'].mean():.2f}",
        ha="center", fontsize=10, bbox=dict(facecolor='white', alpha=0.7, edgecolor='lightgray'))
    
    plt.figtext(0.02, 0.65, 
        "Tamanho e cor do ponto proporcional ao valor do prejuízo",
        ha="left", fontsize=10, bbox=dict(facecolor='white', alpha=0.7))
    
    plt.tight_layout()

    plt.figtext(0.5, 0.02, 
        f"Prejuízo acumulado: R${crimes_filtrados['valor_prejuizo'].sum():,.2f}",
        ha="center", fontsize=10, bbox=dict(facecolor='white', alpha=0.7, edgecolor='lightgray'))
    
    plt.show()



def g4(): 
    # Cria o mapa centralizado utilizando da média
    center_lat = df['latitude'].mean()
    center_lon = df['longitude'].mean()
    mapa = folium.Map(location=[center_lat, center_lon], zoom_start=11)

    # Cria grupos de camadas com clusters internos
    grupo_verde = folium.FeatureGroup(name='Prejuízo < 500 (Verde)').add_to(mapa)
    grupo_laranja = folium.FeatureGroup(name='Prejuízo < 2500 (Laranja)').add_to(mapa)
    grupo_vermelho = folium.FeatureGroup(name='Prejuízo < 5000 (Vermelho)').add_to(mapa)
    grupo_roxo = folium.FeatureGroup(name='Prejuízo > 5000 (Roxo)').add_to(mapa)

    # Clusters dentro dos grupos
    cluster_verde = MarkerCluster().add_to(grupo_verde)
    cluster_laranja = MarkerCluster().add_to(grupo_laranja)
    cluster_vermelho = MarkerCluster().add_to(grupo_vermelho)
    cluster_roxo = MarkerCluster().add_to(grupo_roxo)

    # Adiciona marcadores ao cluster correto                           #iterrows = método do pandas que passa por linhas do dataframe
    for _, row in df.dropna(subset=['latitude', 'longitude', 'bairro']).iterrows(): #dropNA = dropa valores que não possuem latitude, longitude e bairro
        prejuizo = row['valor_prejuizo']
        cor = get_color(prejuizo)
        popup = f"<strong>{row['id']}<br></strong> <span style='color: blue;'>{row['titulo']}</span><br><span style='color: red;'> Prejuízo: R$ {prejuizo:,.2f}</span>"
                    #O folium no final da execução irá salvar o arquivo como html, strong = negrito, span é um meio para chegar no style e mudar a cor do texto de popup

        marcador = folium.Marker(
            location=[row['latitude'], row['longitude']],
            popup=popup,
            icon=folium.Icon(color=cor)
        )

        if cor == 'green':
            marcador.add_to(cluster_verde)
        elif cor == 'orange':
            marcador.add_to(cluster_laranja)
        elif cor == 'red':
            marcador.add_to(cluster_vermelho)
        else:
            marcador.add_to(cluster_roxo)

    # Adiciona controle de camadas
    folium.LayerControl().add_to(mapa)

    # Salva o mapa
    mapa.save("mapa.html")

def get_color(prejuizo):
    if prejuizo < 500:
        return 'green'
    elif prejuizo < 2500:
        return 'orange'
    elif prejuizo < 5000:
        return 'red'
    else:
        return 'purple'
    
def curioso():
    while True:
        id_input = input("\nDigite o ID (ou 'sair' para encerrar): ")
        
        if id_input.lower() == 'sair':
            break
            
        try:
            id_procurado = int(id_input)
            dados = df[df['id'] == id_procurado]
            
            if dados.empty:
                print(f"\nID {id_procurado} não encontrado no dataset.")
            else:
                print("\n--- DADOS ENCONTRADOS ---")
                # Pega a primeira linha (caso haja múltiplas)
                registro = dados.iloc[0]
                
                # Lista das colunas que queremos mostrar
                colunas = [
                    "id", "created_at", "bairro", "descricao", "endereco",
                    "latitude", "longitude", "registrou_bo", "titulo", "valor_prejuizo",
                    "ano", "mes"
                ]
                
                # Exibe cada informação formatada
                for coluna in colunas:
                    valor = registro[coluna]
                    # Formatação especial para alguns campos
                    if coluna == "created_at" and pd.notna(valor):
                        valor = valor.strftime("%d/%m/%Y %H:%M:%S")
                    elif coluna == "valor_prejuizo" and pd.notna(valor):
                        valor = f"R$ {float(valor):,.2f}"
                    
                    print(f"{coluna.upper():<15} - {valor}")
                
                print("-" * 30)
                
        except ValueError:
            print("\nERRO: Por favor, digite um ID numérico válido.")

main()