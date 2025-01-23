import requests
import mysql.connector

MARKETPLACE_ID = 1  # O ID do marketplace que você está usando

def buscar_preco_produtos_mercado_livre(nome):
    base_url = "https://api.mercadolibre.com/sites/MLB/search"

    try:
        response = requests.get(base_url, params={
            "q": nome,
            "category": "MLB1260",  # Categoria de Filmes no Mercado Livre
            "limit": 1,
        })
        response.raise_for_status()  # Levanta um erro se a requisição falhar
        data = response.json()

        # Verifica se há itens nos resultados
        if data['results']:
            # Pega o primeiro item nos resultados
            item = data['results'][0]
            return {
                'price': item['price'],
                'url': item['permalink'],
                'image_url': item['thumbnail'],
                'title': item['title'],
            }
        else:
            print(f"NÃO ENCONTRADO: {nome}")
    except requests.exceptions.RequestException as e:
        print(f"ERRO: {nome}")
    return None

def get_all_produtos():
    # Query para pegar todos os filmes (sem a coluna 'estudio')
    query = """
    SELECT produto.titulo, produto.diretor, produto.ano, marketplace_product.id, produto.id
    FROM produto
    LEFT JOIN marketplace_product ON produto.id = marketplace_product.produto_id
    """
    cursor.execute(query)
    return cursor.fetchall()

def atualizar_marketplace_product(produto_id, price, marketplace_id, url, image_url, title):
    query = """
    UPDATE marketplace_product
    SET price = %s, marketplace_id = %s, url = %s, image_url = %s, title = %s
    WHERE produto_id = %s
    """
    cursor.execute(query, (price, marketplace_id, url, image_url, title, produto_id))
    db_connection.commit()

def criar_marketplace_product(produto_id, price, marketplace_id, url, image_url, title):
    query = """
    INSERT INTO marketplace_product (price, marketplace_id, url, image_url, title, produto_id)
    VALUES (%s, %s, %s, %s, %s, %s)
    """
    cursor.execute(query, (price, marketplace_id, url, image_url, title, produto_id))
    db_connection.commit()


db_connection = mysql.connector.connect(
    host="localhost",
    user="root",
    password="livros",
    database="bookrecomendation"  # Ou "movie_recommendation" se for um banco de dados de filmes
)
cursor = db_connection.cursor()

created_produtos = 0
updated_produtos = 0

# Obtém todos os produtos
produtos = get_all_produtos()
for produto in produtos:
    titulo = produto[0]
    diretor = produto[1]
    ano = produto[2]
    marketplace_product_id = produto[3]
    id = produto[4]

    # Buscar informações sobre o produto no Mercado Livre
    resultado = buscar_preco_produtos_mercado_livre(f"{titulo} - {diretor} - {ano}")

    if resultado:  # Se encontrou um resultado
        if marketplace_product_id:
            # Atualiza o marketplace_product com o novo preço e informações
            atualizar_marketplace_product(
                produto_id=id,
                price=resultado['price'],
                marketplace_id=MARKETPLACE_ID,
                url=resultado['url'],
                image_url=resultado['image_url'],
                title=resultado['title']
            )
            updated_produtos += 1
        else:
            # Cria um novo marketplace_product
            criar_marketplace_product(
                produto_id=id,
                price=resultado['price'],
                marketplace_id=MARKETPLACE_ID,
                url=resultado['url'],
                image_url=resultado['image_url'],
                title=resultado['title']
            )
            created_produtos += 1

print(f"{created_produtos} marketplace_product criados.")
print(f"{updated_produtos} marketplace_product atualizados.")
