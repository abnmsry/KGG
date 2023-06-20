import pandas as pd
import tqdm
from neo4j import GraphDatabase
import numpy as np
import torch
from fastNLP.embeddings.torch.static_embedding import StaticEmbedding
from fastNLP import Vocabulary

driver = GraphDatabase.driver("bolt://localhost:7687", auth=("neo4j", "knowledge_graph"))

# 创建一个会话对象
session = driver.session()

# 取所有文章的title属性，进行embedding初始化
query="""
MATCH (p:Paper)
RETURN p.title as title
"""
result=session.run(query)
df = pd.DataFrame([dict(_) for _ in result])
vocab = Vocabulary()
for index, row in tqdm.tqdm(df.iterrows()):
    vocab.add_word_lst(row['title'].split())

# 第一次执行会需要下载glove的预训练词向量，下载完成后会自动加载
embed = StaticEmbedding(vocab, model_dir_or_name='en-glove-6b-50d')

# 计算得到每个title的word_embedding后，写入数据库中
for index, row in tqdm.tqdm(df.iterrows()):
    words = torch.LongTensor([[vocab.to_index(word) for word in row['title'].split()]])
    embedding = torch.mean(embed(words),dim=1).squeeze(0).detach().numpy()
    query="""
    MATCH (p:Paper)
    WHERE p.title = $title
    SET p.word_embedding = $word_embedding
    """
    session.run(query, title=row['title'], word_embedding=embedding)

# 为后续graphSage训练，创建一张图，作者仅用h_index属性，文章仅用word_embedding属性
query = """
CALL gds.graph.project(
  'graph',
  {
    Author: {
        properties: ['h_index']
    },
    Paper: {
        properties: ['word_embedding']
    }
  },
  {
    CITE: {
        orientation: 'NATURAL'
    },
    WRITE: {
        orientation: 'NATURAL'
    }
  }
)
"""
result=session.run(query)
for record in result:
    print(record)


# 利用GraphSAGE进行embedding
query = """
CALL gds.beta.graphSage.train(
    'graph',
    {
    modelName: 'myGraphSageModel',
    featureProperties: ['word_embedding','h_index'],
    projectedFeatureDimension: 64,
    nodeLabels: ['Author','Paper'],
    aggregator: 'mean',
    relationshipTypes: ['WRITE','CITE'],
    embeddingDimension: 64,
    batchSize: 512,
    tolerance: 0.0001,
    learningRate: 0.01,
    epochs: 10,
    maxIterations: 200,
    searchDepth: 3,
    negativeSampleWeight: 20,
    randomSeed: 42
    }
) YIELD modelInfo as info
"""

result=session.run(query)
for record in result:
    print(record['info'])
    break

# 把embedding保存到每一个节点的properties中
query="""
CALL gds.beta.graphSage.write(
    'graph',
    {
    modelName: 'myGraphSageModel',
    writeProperty: 'embedding'
    }
)
"""
result = session.run(query)
for record in result:
    print(record)



