********************请务必按照以下步骤进行********************

一.安装所需要的库
    1.python需要安装的库：
        1.1 pandas
        1.2 tqdm
        1.3 neo4j
        1.4 numpy
        1.5 torch： 如果没装去官网https://pytorch.org/安装
        1.6 fastNLP：pip install fastNLP （建议用清华源）
        1.7 spacy： pip install spacy （建议用清华源）
        1.8 安装fastNLP和spacy后，请在终端运行命令"python -m spacy download en"
    2. neo4j数据库
        安装Graph Data Science （gds）
        步骤如下：
        1.根据neo4j版本，去看应该下载哪个版本的gds
            https://neo4j.com/docs/graph-data-science/current/installation/supported-neo4j-versions/
        2.下载gds，并安装，直接看官方教程
            https://neo4j.com/docs/graph-data-science/current/installation/neo4j-server/

二.运行文件
    1.运行Embed_preprocess.py文件,作用是构建embedding，尽需运行一次，如果已经运行过且想重新运行，请运行Data_clear.py删除之前的数据。
    2.运行ReCommend.py文件，作用是进行推荐，可以多次运行，不影响。
    3.请不要随意运行Data_clear.py，否则他会把训练好的模型和构建的图删去，除非你想重新构建图和训练模型。

