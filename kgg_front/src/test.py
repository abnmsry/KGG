#!/usr/bin/python 
# _*_ coding:utf-8 _*_ 
# import cgi 
# import cgitb 
# import json 
# import sys 

# cgitb.enable() 
# form = cgi.FieldStorage() 
# ID = form.getvalue("ID") 
# NAME = form.getvalue("NAME") 
# result = {"ID":ID, "NAME":NAME} 
# # todo something print 'Content-Type: application/json\n\n' 
# print(json.dumps(result))   

from flask import  Flask,render_template
app=Flask(__name__)

@app.route('/')
def index():
    msg="my name is caojianhua, China up!"
    return render_template("index.html"，data=msg)  #加入变量传递

if __name__=="__main__":
    app.run(port=2020,host="127.0.0.1",debug=True)