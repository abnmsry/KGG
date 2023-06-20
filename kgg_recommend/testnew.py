from flask import  Flask,render_template,request
import sys
import pandas as pd
from neo4j import GraphDatabase
import numpy as np


app=Flask(__name__)


@app.route('/',methods=['GET'])
def index():
    return 'welcome'

@app.route('/recommend',methods=['GET','POST'])
def recommend():
    title=request.form.get('title')
    # print(title)
    driver = GraphDatabase.driver("bolt://localhost:7687", auth=("neo4j", "knowledge_graph"))
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
    df['distance'] = df['p.embedding'].apply(lambda x: sum((np.array(x)-np.array(embedding))**2)**0.5)
    # 取df中distance最小的k个值（注意除去自身）
    return df.nsmallest(7, 'distance').iloc[1:].values.tolist()
    

if __name__=="__main__":
    app.run(port=2020,host="127.0.0.1",debug=True)