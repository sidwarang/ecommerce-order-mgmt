import os, inspect, json
import mysql.connector
import json
import itertools
from mysql.connector import errorcode
from json import dumps
from bottle import route, run, response, request, get, put, post, delete
from pymongo import MongoClient
from bson.json_util import dumps
from datetime import datetime

client = MongoClient("mongodb://localhost:27017");
db = client["products"];
collection = db["shirts"];

#mysql-REST
#
#GET: LIST SHOE

@get('/shoe/<shoeId>', method='GET')
def getShoe(shoeId):
    output=""
    try:
        cnx = mysql.connector.connect(user='root',password='ec2',database='orders')
        cursor = cnx.cursor()
        cursor.execute("select * from shoes where shoeID= %s" %shoeId)
        result = cursor.fetchall()
        output = { "shoeId": result[0][0], "shoeName": result[0][1],"shoeQuantity":result[0][2],"createdBy":result[0][3],"Created Date":result[0][4].strftime('%m/%d/%Y %HH:%MM:%SS')}
        print(output)
    except mysql.connector.Error as err:
        if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
            print("Something is wrong with your user name or password")
        elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print("Database does not exists")
        else:
                    print(result)
    else:
                        cnx.close()
    response.content_type = 'application/json'
    return dumps(output)

#GET: LIST SHOES

@get('/shoe', method='GET')
def getShoe():
    def dictfetchall(cursor):
	desc = cursor.description
	return [dict(itertools.izip([col[0] for col in desc], row)) 
            for row in cursor.fetchall()]
    output=[]
    try:
        cnx = mysql.connector.connect(user='root',password='ec2',database='orders')
        cursor = cnx.cursor()
        cursor.execute("select * from shoes")
        result = dictfetchall(cursor)
	output = dumps(result)
    except mysql.connector.Error as err:
        if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
            print("Something is wrong with your user name or password")
        elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print("Database does not exists")
        else:
                    print(result)
    else:
                        cnx.close()
    response.content_type = 'application/json'
    return output
#POST: SAVE SHOE NAME

@post('/shoes', method='POST')
def jsonPOST():
    data = request.json
    shoeId = data['shoeId']
    if data == None:
        return json.dumps({'Status':"No Data Found In Request! Please Check the Request"})
    else:
        try:
            cnx = mysql.connector.connect(user='root',password='ec2',database='orders')
            cursor = cnx.cursor()
            cur = cnx.cursor()
            add_shoe = ("INSERT INTO shoes(shoeId,shoeName,shoeQuantity,createdBy) VALUES (%(shoeId)s, %(shoeName)s, %(shoeQuantity)s, %(createdBy)s)")
            cursor.execute(add_shoe,data)
            cur.execute("select * from shoes where shoeID= %s" %shoeId)
            result = cur.fetchall()
            cnx.commit()
        except mysql.connector.Error as err:
            if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
                print("Something is wrong with your user name or password")
            elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print("Database does not exists")
            else:
                print(err)
        else:
            cnx.close()
    return json.dumps({'Status':"Success!", 'Created Date': result[0][4].strftime('%m/%d/%Y %HH:%MM:%SS')})

#DELETE: DELETE SHOE NAME

@delete('/shoes', method='DELETE' )
def jsonDelete():
    data = request.json
    output=""
    try:
        shoeId = data['shoeId']
        add_shoe = ("delete from shoes where shoeId=%(shoeId)s")
        output="All items Deleted for given 'shoeId'"
    except Exception:
	try:
            shoeName = data['shoeName']
            add_shoe = ("delete from shoes where shoeName=%(shoeName)s")
            output="All items Deleted for given 'shoeName'"
        except Exception:
            try:
                createdBy = data['createdBy']
                add_shoe = ("delete from shoes where createdBy=%(createdBy)s")
                output="All items Deleted for given 'createdBy'"
            except Exception:
                try:
                    shoeQuantity = data['shoeQuantity']
                    add_shoe = ("delete from shoes where shoeQuantity=%(shoeQuantity)s")
                    output="All items Deleted for given 'shoeQuantity'"
                except Exception:
                    return json.dumps({'Status':"Failed! Please Check the Request"})
		    pass     
    if data == None:
        return json.dumps({'Status':"No Data Found In Request! Please Check the Request"})
    else:
        try:
            cnx = mysql.connector.connect(user='root',password='ec2',database='orders')
            cursor = cnx.cursor()
            cursor.execute(add_shoe,data)
            cnx.commit()
        except mysql.connector.Error as err:
            if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
                print("Something is wrong with your user name or password")
            elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print("Database does not exists")
            else:
                print(err)
        else:
            cnx.close()
    return json.dumps({'Status':"Success!","Details":output})

#PUT: UPDATE SHOE NAME

@put('/shoes', method='PUT')
def jsonPut():
    data = request.json
    try:
        shoeId = data['shoeId']
    except Exception:
        return json.dumps({'Status':"Failed!", 'Error': "No 'shoeId' found. Please Check the Request!"})
    try:
        shoeName = data['shoeName']
        try:
            shoeQuantity = data['shoeQuantity']
            try:
                createdBy = data['createdBy']
                add_shoe = ("update shoes set shoeName=%(shoeName)s,shoeQuantity=%(shoeQuantity)s,createdBy=%(createdBy)s where shoeId=%(shoeId)s ")
            except Exception:
                add_shoe = ("update shoes set shoeName=%(shoeName)s,shoeQuantity=%(shoeQuantity)s where shoeId=%(shoeId)s ")
        except Exception:
            add_shoe = ("update shoes set shoeName=%(shoeName)s where shoeId=%(shoeId)s ")
    except Exception:
	try:
            shoeQuantity = data['shoeQuantity']
            try:
                createdBy = data['createdBy']
		add_shoe = ("update shoes set shoeQuantity=%(shoeQuantity)s,createdBy=%(createdBy)s where shoeId=%(shoeId)s ")
	    except Exception:
		add_shoe = ("update shoes set shoeQuantity=%(shoeQuantity)s where shoeId=%(shoeId)s ")
	except Exception:
	    try:
                createdBy = data['createdBy']
		add_shoe = ("update shoes set createdBy=%(createdBy)s where shoeId=%(shoeId)s ")
	    except Exception:
		return json.dumps({'Status':"Failed!", 'Error': "No Data found to Update. Please Check the Request!"})
    if data == None:
        return json.dumps({'Status':"No Data Found In Request! Please Check the Request"})
    else:
        try:
            cnx = mysql.connector.connect(user='root',password='ec2',database='orders')
            cursor = cnx.cursor()
            cur = cnx.cursor()
            cursor.execute(add_shoe,data)
            cur.execute("select * from shoes where shoeID= %s" %shoeId)
            result = cur.fetchall()
            cnx.commit()
            print("Data Updated")
        except mysql.connector.Error as err:
            if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
                print("Something is wrong with your user name or password")
            elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print("Database does not exists")
            else:
                print(err)
        else:
            cnx.close()
    return json.dumps({'Status':"Success!", 'Created Date': result[0][4].strftime('%m/%d/%Y %HH:%MM:%SS')})

#mongodb-REST
#
#GET: LIST SHIRT

@get('/shirt', method='GET')
def shirts():
	response.content_type = 'application/json'
	try:
            return dumps(collection.find({},{'_id':False}));
	except StopIteration:
            return dumps({"STATUS":"NO DATA"})

#GET: LIST SHIRTS
    
@get('/shirt/<shirtid>', method='GET')
def shirtsGET(shirtid):
	response.content_type = 'application/json'
	x=collection.find({"shirtId": shirtid},{'_id':False});
	if not x:
		return dumps({"STATUS":"NO DATA"});
	return dumps(x)

#PUT: UPDATE SHIRT NAME

@put('/shirts', method='PUT')
def shirtsPUT():
    data = request.json
    data['timestamp']=datetime.now().strftime('%m/%d/%Y %HH:%MM:%SS')
    if data == None:
        return dumps({'Status':"No Data Found In Request! Please Check the Request"})
    else:
        try:
            collection.update({"shirtId": data['shirtId']}, {"$set": data}, True);
        except Exception:
            return dumps({"STATUS":"FAILED","Error" :"shirtId not provided"})
        return dumps({"STATUS":"UPDATED","Date Updated" :data['timestamp']})

#POST: SAVE SHIRT NAME

@post('/shirts', method='POST')
def shirtsPOST():
    data = request.json
    data['timestamp']=datetime.now().strftime('%m/%d/%Y %HH:%MM:%SS')
    if data == None:
        return dumps({'Status':"No Data Found In Request! Please Check the Request"})
    else:
        collection.insert(data);
        return dumps({"STATUS":"INSERTED","Date Created" :data['timestamp']})
    
#DELETE: DELETE SHIRT NAME

@delete('/shirts', method='DELETE' )
def shirtsDELETE():
    data = request.json
    if data == None:
        return dumps({'Status':"No Data Found In Request! Please Check the Request"})
    else:
        collection.remove(data);
        return dumps({"STATUS":"DELETED"})

run(host='0.0.0.0', port=8080, debug=True)


