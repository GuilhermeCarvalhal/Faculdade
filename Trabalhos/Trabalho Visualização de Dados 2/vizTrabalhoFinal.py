import pandas as pd
import matplotlib.pyplot as plt
import plotly.express as px
import plotly.graph_objects as go
from plotly.subplots import make_subplots
import seaborn as sns
import numpy as np
import statsmodels.api as sm
from sqlalchemy import create_engine
import requests

engine = create_engine('postgresql://postgres:master@localhost:5432/tbfinal2') #CARREGAR O BANCO DE DADOS

# Carregar os dados somente com as colunas desejadas 
enem_df = pd.read_csv('MICRODADOS_ENEM_2023.csv', sep=';', encoding='latin1', usecols=[
    'NU_INSCRICAO', 'NU_ANO', 'SG_UF_PROVA',
    'NU_NOTA_MT', 'NU_NOTA_REDACAO',
    'NU_NOTA_CN', 'NU_NOTA_CH', 'NU_NOTA_LC'
])
analf_df = pd.read_csv('5.5.csv', sep=';', usecols=[
    'Granularidade_Geografica', 'Localidade', 'UF', 'Valor', 'Ano'
    ])

enem_df = enem_df.dropna(how='all', subset=['NU_NOTA_MT', 'NU_NOTA_REDACAO', 'NU_NOTA_CN', 'NU_NOTA_CH', 'NU_NOTA_LC']) #Apenas colunas com todas as notas NA são apagadas
analf_df = analf_df.dropna(subset=['Valor']) #Sem valor de analfabetismo = delete

# Cria o media enem, tira a coluna INSCRICAO e faz a média das notas, dropa o index para não dar conflito com o db
media_enem = enem_df.groupby('SG_UF_PROVA').mean()
media_enem = media_enem.drop(columns= {'NU_INSCRICAO'})
media_enem[['NU_NOTA_MT', 'NU_NOTA_REDACAO', 'NU_NOTA_CN', 'NU_NOTA_CH', 'NU_NOTA_LC']] = media_enem[['NU_NOTA_MT', 'NU_NOTA_REDACAO', 'NU_NOTA_CN', 'NU_NOTA_CH', 'NU_NOTA_LC']].round(2)
media_enem = media_enem.reset_index()

# Adaptação para db
media_enem.columns = media_enem.columns.str.lower()
analf_df.columns = analf_df.columns.str.lower()
enem_df.columns = enem_df.columns.str.lower()

analf_df['valor'] = analf_df['valor'].str.replace(',', '.', regex=False).astype(float)

#Conectar ao postgres e fazer o insert dos dados
analf_df.to_sql('analfabetismo', engine, if_exists='append', index=False) #LEMBRAR DE COMENTAR ESSA LINHA DEPOIS DA PRIMEIRA EXECUCAO
enem_df.to_sql('resultados_enem', engine, if_exists='append', index=False)
media_enem.to_sql('medias_enem', engine, if_exists='append', index=False)

def carregaDadosRegiao():
    # Mapeamento de UF para Região (necessário para conectar os datasets)
    uf_regiao = {
        'AC': 'Norte', 'AP': 'Norte', 'AM': 'Norte', 'PA': 'Norte', 'RO': 'Norte', 'RR': 'Norte', 'TO': 'Norte',
        'AL': 'Nordeste', 'BA': 'Nordeste', 'CE': 'Nordeste', 'MA': 'Nordeste', 'PB': 'Nordeste', 
        'PE': 'Nordeste', 'PI': 'Nordeste', 'RN': 'Nordeste', 'SE': 'Nordeste',
        'DF': 'Centro-Oeste', 'GO': 'Centro-Oeste', 'MT': 'Centro-Oeste', 'MS': 'Centro-Oeste',
        'ES': 'Sudeste', 'MG': 'Sudeste', 'RJ': 'Sudeste', 'SP': 'Sudeste',
        'PR': 'Sul', 'RS': 'Sul', 'SC': 'Sul'
    }

    # Adicionar coluna de região ao dataset do ENEM
    media_enem['regiao'] = media_enem['sg_uf_prova'].map(uf_regiao)

    # Calcular média por região do ENEM
    media_enem_regiao = media_enem.groupby('regiao').agg({
        'nu_nota_mt': 'mean',
        'nu_nota_redacao': 'mean', 
        'nu_nota_cn': 'mean',
        'nu_nota_ch': 'mean',
        'nu_nota_lc': 'mean'
    }).round(2).reset_index()

    # Calcular média geral por região
    media_enem_regiao['media_geral'] = media_enem_regiao[['nu_nota_mt', 'nu_nota_redacao', 'nu_nota_cn', 'nu_nota_ch', 'nu_nota_lc']].mean(axis=1)

    # Verificar os dados de analfabetismo disponíveis (Utilizado durante criação do código para troubleshooting)
    print("Valores únicos em 'localidade':")
    print(analf_df['localidade'].unique())
    print("\nValores únicos em 'ano':")
    print(analf_df['ano'].unique())
    print("\nPrimeiras linhas do dataset de analfabetismo:")
    print(analf_df.head())

    # Pegar dados de analfabetismo por região em 2023
    # Vamos tentar diferentes filtros para encontrar os dados corretos
    analf_regiao_2023 = analf_df.loc[(analf_df['localidade'] == 'Região') & (analf_df['ano'] == 2023)].copy()

    if analf_regiao_2023.empty:
        print("Tentando filtro alternativo para analfabetismo...")
        # Tenta outros filtros possíveis
        analf_regiao_2023 = analf_df.loc[analf_df['ano'] == 2023].copy()
        if analf_regiao_2023.empty:
            # Se não tiver 2023, pega o ano mais recente
            ano_recente = analf_df['ano'].max()
            print(f"Usando ano mais recente disponível: {ano_recente}")
            analf_regiao_2023 = analf_df.loc[analf_df['ano'] == ano_recente].copy()

    print(f"Dados de analfabetismo encontrados: {len(analf_regiao_2023)} registros")
    print(analf_regiao_2023.head())

    # Verificar se temos dados por região
    if 'uf' in analf_regiao_2023.columns:
        analf_regiao_2023['regiao'] = analf_regiao_2023['uf']
        
        # Se não tiver dados por região, vamos criar um mapeamento manual
        regioes_brasil = ['Norte', 'Nordeste', 'Sudeste', 'Sul', 'Centro-Oeste']
        if not any(regiao in analf_regiao_2023['regiao'].values for regiao in regioes_brasil):
            print("Dados não estão por região. Vamos usar dados por UF e mapear...")
            # Usar dados por UF e fazer média por região
            analf_uf = analf_df.loc[analf_df['ano'] == analf_df['ano'].max()].copy()
            
            # Mapear UFs para regiões no dataset de analfabetismo
            analf_uf['regiao'] = analf_uf['uf'].map(uf_regiao)
            analf_regiao_2023 = analf_uf.groupby('regiao')['valor'].mean().reset_index()
            analf_regiao_2023 = analf_regiao_2023.dropna()

    print("Dados de analfabetismo processados:")
    print(analf_regiao_2023)
    print("Dados de ENEM por região:")
    print(media_enem_regiao)
    print("======" * 20)
    
    # Merge dos datasets
    dados_completos = pd.merge(media_enem_regiao, analf_regiao_2023[['regiao', 'valor']], on='regiao', how='inner')
    dados_completos.rename(columns={'valor': 'taxa_analfabetismo'}, inplace=True)
    return dados_completos


def main():
    dados = carregaDadosRegiao()
    
    #probabilidade_media_alta('SP')
    #probabilidade_media_alta('MA')
    
    #g1() #Gráfico dispersão mostra a proporção entre redação/taxa
    g1_2() #Gráfico barras duplas mostra a proporção entre redação/taxa
    g2(dados) #desvio percentual nacional da média do enem
    g3() #Gráfico calor relação entre a nota de matemática e analfabetismo
    g4() #Scatter Mostra a correlação entre a chance de tirar uma média acima de 600 com a taxa de analfabetismo
    g5(dados) #Média geral região barras
    
    resposta = gerar_resposta_groq(f"Analise os seguintes dados resumidos da taxa de analfabetismo e média do enem no Brasil: {dados.to_dict(orient='records')}")
    print(resposta) #LLM Groq
   
    
def probabilidade_media_alta(uf, limite=600, df_enem=enem_df, df_analf=analf_df): #Função utilizada para calcular a probabilidade de um aluno tirar acima do "limite"
    try:
        # Filtrar alunos da UF e calcular média geral
        df_uf = df_enem[df_enem['sg_uf_prova'] == uf].copy()
        if len(df_uf) == 0:
            return 0.0

        # Calcular média geral (todas as 5 áreas)
        cols_notas = ['nu_nota_mt', 'nu_nota_redacao', 'nu_nota_cn', 'nu_nota_ch', 'nu_nota_lc']
        df_uf['media_geral'] = df_uf[cols_notas].mean(axis=1)

        # Contar alunos com média >= limite
        total_alunos = len(df_uf)
        alunos_media_alta = len(df_uf[df_uf['media_geral'] >= limite])
        prob = alunos_media_alta / total_alunos

        # Taxa de analfabetismo da UF
        taxa_analf = df_analf[df_analf['uf'] == uf]['valor'].mean()

        # Saída formatada
        print(f"\nUF: {uf} | Taxa Analfabetismo: {taxa_analf:.3f}%")
        print(f"Alunos com média ≥ {limite}: {alunos_media_alta:,}/{total_alunos:,}")
        print(f"Probabilidade: {prob:.4f} ({prob*100:.2f}%) | 1 em cada {int(1/prob):,} alunos")

        return prob

    except Exception as e:
        print(f"Erro em {uf}: {str(e)}")
        return 0.0

def g1(): 
    # Unir os dados pela UF
    df_merge = pd.merge(media_enem, analf_df[analf_df['ano'] == 2023], left_on='sg_uf_prova', right_on='uf')

    plt.figure(figsize=(10,6))
    sns.scatterplot(data=df_merge, x='valor', y='nu_nota_redacao', hue='sg_uf_prova', s=100)

    plt.title('Nota Média de Redação vs. Taxa de Analfabetismo por UF (2023)')
    plt.xlabel('Taxa de Analfabetismo (%)')
    plt.ylabel('Nota Média de Redação (ENEM)')
    plt.legend(bbox_to_anchor=(1.05, 1), title="UF")
    plt.tight_layout()
    plt.savefig('grafico_1')
    plt.show()

def g1_2(): 
    #faz um merge dentre os dataframe
    df_bar = pd.merge(media_enem, analf_df[analf_df['ano'] == 2023], left_on='sg_uf_prova', right_on='uf')
    df_bar = df_bar.sort_values('valor', ascending=False)

    x = np.arange(len(df_bar['sg_uf_prova']))
    width = 0.35

    fig, ax1 = plt.subplots(figsize=(14,6))

    ax1.bar(x - width/2, df_bar['nu_nota_redacao'], width, label='Nota Redação')
    ax1.set_ylabel('Nota Média - Redação')
    ax1.set_xticks(x)
    ax1.set_xticklabels(df_bar['sg_uf_prova'], rotation=45)

    ax2 = ax1.twinx()
    ax2.bar(x + width/2, df_bar['valor'], width, color='red', alpha=0.6, label='Analfabetismo (%)')
    ax2.set_ylabel('Taxa de Analfabetismo (%)')

    ax1.set_title('Nota de Redação vs. Taxa de Analfabetismo por Estado (2023)')
    fig.legend(loc='upper right', bbox_to_anchor=(1,1), bbox_transform=ax1.transAxes)
    plt.tight_layout()
    plt.savefig('grafico_1_2')
    plt.show()

def g2(dados):
    # Calcular médias nacionais
    medias_brasil = {
        'mt': dados['nu_nota_mt'].mean(),
        'redacao': dados['nu_nota_redacao'].mean(),
        'cn': dados['nu_nota_cn'].mean(),
        'ch': dados['nu_nota_ch'].mean(),
        'lc': dados['nu_nota_lc'].mean()
    }
    
    # Calcular desvio percentual das notas
    dados_desvio = dados.copy()
    for area in ['mt', 'redacao', 'cn', 'ch', 'lc']:
        dados_desvio[f'desvio_{area}'] = ((dados[f'nu_nota_{area}'] - medias_brasil[area]) / medias_brasil[area]) * 100
    
    # Preparar matriz para o heatmap (incluindo taxa de analfabetismo)
    heatmap_data = dados_desvio.set_index('regiao')[[
        'desvio_mt', 'desvio_redacao', 'desvio_cn', 'desvio_ch', 'desvio_lc', 'taxa_analfabetismo'
    ]]
    heatmap_data.columns = ['Matemática', 'Redação', 'Ciências Nat.', 'Ciências Hum.', 'Linguagens', 'Taxa Analf. (%)']
    
    # Configurar o gráfico
    plt.figure(figsize=(14, 6))
    
    # Criar dois colormaps diferentes para as diferentes escalas
    # Para as notas (desvio percentual): centrado em 0
    # Para analfabetismo: escala normal (valores absolutos)
    
    # Dividir os dados
    notas_data = heatmap_data.iloc[:, :-1]  # Todas exceto a última coluna
    analf_data = heatmap_data.iloc[:, -1:]   # Apenas a última coluna
    
    # Criar subplots lado a lado
    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(16, 6), gridspec_kw={'width_ratios': [5, 1]})
    
    # Heatmap das notas
    sns.heatmap(
        notas_data,
        cmap='RdYlGn',
        center=0,
        annot=True,
        fmt='.1f',
        linewidths=.5,
        cbar_kws={'label': 'Desvio Percentual (%)'},
        annot_kws={'size': 10, 'color': 'black'},
        ax=ax1
    )
    
    # Heatmap da taxa de analfabetismo
    sns.heatmap(
        analf_data,
        cmap='Reds',
        annot=True,
        fmt='.1f',
        linewidths=.5,
        cbar_kws={'label': 'Taxa Analfabetismo (%)'},
        annot_kws={'size': 10, 'color': 'black'},
        ax=ax2
    )
    
    # Configurações do primeiro subplot
    ax1.set_title('Desvio Percentual das Notas ENEM em Relação à Média Nacional', pad=20)
    ax1.set_xlabel('Área do ENEM')
    ax1.set_ylabel('Região')
    
    # Configurações do segundo subplot  
    ax2.set_title('Taxa de Analfabetismo', pad=20)
    ax2.set_xlabel('')
    ax2.set_ylabel('')
    ax2.set_yticklabels([])  # Remove labels do eixo Y (já estão no primeiro gráfico)
    
    plt.tight_layout()
    plt.savefig('grafico_2')
    plt.show()

def g3():
    # Preparar os dados
    df_heatmap = media_enem[['sg_uf_prova', 'nu_nota_mt']].merge(
        analf_df.groupby('uf')['valor'].mean().reset_index(),
        left_on='sg_uf_prova',
        right_on='uf'
    )
    
    # Criar matriz para o heatmap
    pivot_table = df_heatmap.pivot_table(
        index='uf',
        values=['nu_nota_mt', 'valor'],
        aggfunc='mean'
    ).sort_values('valor', ascending=False)
    
    # Normalizar os dados para escala 0-1
    # Criar uma tabela dinâmica que agrupa os dados por estado ('uf'), 
    # calculando a média das colunas 'nu_nota_mt' (nota de matemática) 
    # e 'valor' (taxa de analfabetismo) para cada estado. 
    # Em seguida, ordenar a tabela pela taxa de analfabetismo em ordem decrescente.
    pivot_table['nota_mt_norm'] = (pivot_table['nu_nota_mt'] - pivot_table['nu_nota_mt'].min()) / (pivot_table['nu_nota_mt'].max() - pivot_table['nu_nota_mt'].min())
    pivot_table['analf_norm'] = (pivot_table['valor'] - pivot_table['valor'].min()) / (pivot_table['valor'].max() - pivot_table['valor'].min())
    
    # Criar o heatmap com Seaborn
    plt.figure(figsize=(12, 8))
    sns.heatmap(
        pivot_table[['nota_mt_norm', 'analf_norm']],
        cmap='YlOrRd',
        annot=pivot_table[['nu_nota_mt', 'valor']].round(2),
        fmt='.2f',
        linewidths=.5,
        cbar_kws={'label': 'Escala Normalizada (0-1)'}
    )
    
    plt.title('Relação entre Nota de Matemática no ENEM e Taxa de Analfabetismo por UF', pad=20)
    plt.xlabel('Variáveis')
    plt.ylabel('Unidades Federativas')
    plt.xticks(ticks=[0.5, 1.5], labels=['Nota Matemática', 'Taxa Analfabetismo'], rotation=0)
    plt.tight_layout()
    plt.savefig('grafico3')
    plt.show()

def g4(limite=600):
    try:
        # Calcular dados para todas as UFs
        resultados = []
        for uf in enem_df['sg_uf_prova'].unique():
            prob = probabilidade_media_alta(uf, limite)
            if prob > 0:  # Ignora UFs sem dados
                taxa_analf = analf_df[analf_df['uf'] == uf]['valor'].mean()
                resultados.append({
                    'UF': uf,
                    'Taxa_Analfabetismo': taxa_analf,
                    'Probabilidade': prob,
                    'Inverso_Prob': 1/prob if prob > 0 else 0
                })
        
        df_resultados = pd.DataFrame(resultados)
        
        # Criar figura com Plotly (apenas LOWESS)
        fig = px.scatter(
            df_resultados,
            x='Taxa_Analfabetismo',
            y='Probabilidade',
            text='UF',
            trendline="lowess",
            trendline_color_override="red",
            title=f'Relação entre Taxa de Analfabetismo e Probabilidade de Média ENEM ≥ {limite}',
            labels={
                'Taxa_Analfabetismo': 'Taxa de Analfabetismo (%)',
                'Probabilidade': f'Probabilidade de Média ≥ {limite}'
            },
            hover_data={'Inverso_Prob': ':.1f'}
        )
        
        # Personalizar layout
        fig.update_traces(
            marker=dict(size=12, line=dict(width=2, color='DarkSlateGrey')),
            textposition='top center'
        )
        
        fig.update_layout(
            xaxis_title="Taxa de Analfabetismo (%)",
            yaxis_title=f"Probabilidade de Média ≥ {limite}",
            hovermode="closest",
            showlegend=False,
            height=600,
            width=900
        )
        
        # Calcular e adicionar correlação
        correlacao = df_resultados['Taxa_Analfabetismo'].corr(df_resultados['Probabilidade'])
        fig.add_annotation(
            x=0.95,
            y=0.95,
            xref='paper',
            yref='paper',
            text=f"Correlação: {correlacao:.2f}",
            showarrow=False,
            bgcolor="white",
            bordercolor="black"
        )
        
        fig.write_html("grafico4.html")

    except Exception as e:
        print(f"Erro ao gerar gráfico: {str(e)}")
        return None
    
def g5(dados_completos): 
    fig5 = make_subplots(
    rows=2, cols=1,
    subplot_titles=('Ranking: Média Geral ENEM por Região', 'Taxa de Analfabetismo por Região'),
    vertical_spacing=0.15
    )

    # Ordenar por média ENEM (decrescente)
    dados_ord_enem = dados_completos.sort_values('media_geral', ascending=True)

    # Gráfico de barras horizontais - ENEM
    fig5.add_trace(
        go.Bar(x=dados_ord_enem['media_geral'], 
            y=dados_ord_enem['regiao'],
            orientation='h',
            name='Média ENEM',
            marker_color='lightblue',
            text=dados_ord_enem['media_geral'].round(1),
            textposition='outside'),
        row=1, col=1
    )

    # Ordenar por analfabetismo (decrescente)
    dados_ord_analf = dados_completos.sort_values('taxa_analfabetismo', ascending=True)

    # Gráfico de barras horizontais - Analfabetismo
    fig5.add_trace(
        go.Bar(x=dados_ord_analf['taxa_analfabetismo'], 
            y=dados_ord_analf['regiao'],
            orientation='h',
            name='Taxa Analfabetismo',
            marker_color='lightcoral',
            text=dados_ord_analf['taxa_analfabetismo'].round(1),
            textposition='outside'),
        row=2, col=1
    )

    fig5.update_layout(
        height=800,
        showlegend=False,
        title_text="Ranking Regional: Performance ENEM vs Analfabetismo<br><sub>Observe a relação inversa entre os rankings</sub>",
        title_x=0.5
    )

    fig5.update_xaxes(title_text="Pontuação Média", row=1, col=1)
    fig5.update_xaxes(title_text="Taxa de Analfabetismo (%)", row=2, col=1)
    fig5.write_html("grafico5.html")

def gerar_resposta_groq(pergunta):
    url = "https://api.groq.com/openai/v1/chat/completions"

    headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer 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" #AQUI VAI A CHAVE PROFESSORA | LINK SE PRECISAR DE OUTRA: https://console.groq.com/home
    }

    data = {
        "model": "meta-llama/llama-4-scout-17b-16e-instruct",   
        "messages": [
            {
                "role": "user",
                "content": pergunta
            }
        ],
        "temperature": 0.7
    }

    response = requests.post(url, headers=headers, json=data)

    if response.status_code == 200:
        return response.json()["choices"][0]["message"]["content"]
    else:
        raise Exception(f"Erro {response.status_code}: {response.text}")

main()