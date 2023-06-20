1 import sys
 2 import os
 3 import re
 4 import gc
 5 import json
 6 import logging 
 7 import telnetlib
 8 import socket
 9 from websocket import create_connection
10 
11 global ws
12 
13 try:
14     ws = create_connection("ws://localhost:8329/websocket")
16     result =  ws.recv()
17     print "Received '%s'" % result
18 except socket.error as error:
19     print "==== websocket error"
20 
21 def close_socket():
22     try:
23         ws.close() # 需关闭连接
24         logger.debug("finish the test success")
25     except:
26         #ws = None
27         logger.debug(" try except finish the test ")
28 
29 
30 def switchCases(caseType, res):
31                 try:
32                     ws.send(json.dumps( sendBeginMsgFormat(sqlRes[caseType][0],caseType,g_logDir,dict["cardid"])))   # 通知服务器，执行用例开始   
33
34                 except:              
35                     ws.send(json.dumps(dict)) # 统一封装成json格式给服务器      
36                     tear_down()
