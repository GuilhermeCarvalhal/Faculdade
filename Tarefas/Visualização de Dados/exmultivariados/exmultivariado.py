import pandas as pd
import plotly.express as px
import plotly.graph_objects as go

# Carregamento dos dados
df = pd.read_excel('Transações_PIX_052025.xlsx')

# Filtra apenas a região Sul
regiao_df = df[df['Regiao'] == 'SUL']

# Verifica colunas relacionadas à Pessoa Jurídica
pj_columns = ['VL_PagadorPJ', 'VL_RecebedorPJ', 'QT_PagadorPJ', 'QT_RecebedorPJ', 'QT_PES_PagadorPJ', 'QT_PES_RecebedorPJ']

def main():
    ex1()
    ex2()
    ex3()


def ex1():
    # Cria o gráfico de paralelas coordenadas
    fig = px.parallel_coordinates(
        regiao_df,
        dimensions=pj_columns,
        color='VL_PagadorPJ', 
        color_continuous_scale=px.colors.diverging.Tealrose,
        title="Transações Pessoa Jurídica - Região Sul"
    )

    fig.show()

def ex2():

    ararangua = regiao_df[regiao_df['Municipio'] == 'ARARANGUÁ']
    lages = regiao_df[regiao_df['Municipio'] == 'LAGES']

    ararangua_media = ararangua[pj_columns].mean()
    lages_media = lages[pj_columns].mean()

    df_comparativo = pd.DataFrame([ararangua_media, lages_media], index=['Araranguá', 'Lages'])

    df_norm = df_comparativo / df_comparativo.max()

    categorias = df_norm.columns.tolist()
    categorias += [categorias[0]]  # Fecha o círculo
    fig = go.Figure()

    for cidade in df_norm.index:
        valores = df_norm.loc[cidade].tolist()
        valores += [valores[0]]  # Fecha o círculo
        fig.add_trace(go.Scatterpolar(
            r=valores,
            theta=categorias,
            fill='toself',
            name=cidade
        ))

    fig.update_layout(
        title='Radar: Pessoa Jurídica - Araranguá vs Lages',
        polar=dict(radialaxis=dict(visible=True, range=[0, 1])),
        showlegend=True
    )

    fig.show()


def ex3():
    sc_df = df[df['Estado'] == 'SANTA CATARINA']

    # Cria o gráfico de dispersão 3D
    fig = px.scatter_3d(
        sc_df,
        x='QT_RecebedorPF',
        y='QT_PES_RecebedorPF',
        z='VL_RecebedorPF',
        color='Municipio',
        title='Dispersão 3D - Pessoa Física em SC',
        labels={
            'QT_RecebedorPF': 'Qtde Transações Recebedor PF',
            'QT_PES_RecebedorPF': 'Qtde Pessoas Recebedor PF',
            'VL_RecebedorPF': 'Valor Recebido PF'
        }
    )
    fig.show()
main()