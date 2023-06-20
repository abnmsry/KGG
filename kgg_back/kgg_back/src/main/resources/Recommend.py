# coding:utf-8
import sys
import pandas as pd
from neo4j import GraphDatabase
import numpy as np




# ------推荐部分-------

# 定义一个距离函数，函数距离为L2距离，输入的两个参数x和y都是list且长度相同
def l2_distance(x, y):
    return sum((np.array(x)-np.array(y))**2)**0.5

# 定义一个推荐函数，第一个参数为文章标题title，第二个参数为推荐的数量k，根据embedding，返回距离最小的k篇文章
def recommend(title, k):
    print("incoming")
    print(title)
    print(k)
    driver = GraphDatabase.driver("bolt://localhost:7687", auth=("neo4j", "knowledge_graph"))

    # 创建一个会话对象
    session = driver.session()
    query="""
    MATCH (p:Paper)
    WHERE p.title="%s"
    RETURN p.embedding LIMIT 1
    """ % title
    result = session.run(query)
    df = pd.DataFrame([dict(_) for _ in result])
    embedding = df.iloc[0].values[0]
    query="""
    MATCH (p:Paper)
    RETURN p.title, p.embedding
    """
    result = session.run(query)
    df = pd.DataFrame([dict(_) for _ in result])
    df['distance'] = df['p.embedding'].apply(lambda x: l2_distance(x, embedding))
    # 取df中distance最小的k个值（注意除去自身）
    return df.nsmallest(k+1, 'distance').iloc[1:]


if __name__ == '__main__':
    arg1 = sys.argv[2]
    arg2 = sys.argv[3]
    print(arg1)
    recommend(arg1,arg2)

# 测试推荐效果
# result=recommend("Neural Approaches for Data Driven Dependency Parsing in Sanskrit",3)
# print(result)