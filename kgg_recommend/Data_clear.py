from neo4j import GraphDatabase

driver = GraphDatabase.driver("bolt://localhost:7687", auth=("neo4j", "knowledge_graph"))

# 创建一个会话对象
session = driver.session()

# 删除为了embedding所构建的图
query = """
CALL gds.graph.drop('graph')
"""
result = session.run(query)
for record in result:
    print(record)


# 删除为了embedding所训练的模型
query = """
CALL gds.beta.model.drop('myGraphSageModel')
"""
result = session.run(query)
for record in result:
    print(record)