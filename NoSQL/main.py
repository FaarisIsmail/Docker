import boto3
import csv

s3 = boto3.resource('s3', 
    aws_access_key_id='access_key', 
    aws_secret_access_key='secret_key' 
) 

try: 
    s3.create_bucket(Bucket='faaris1660', CreateBucketConfiguration={ 
        'LocationConstraint': 'us-west-2'}) 
except Exception as e: 
    print (e) 

bucket = s3.Bucket("faaris1660") 
bucket.Acl().put(ACL='public-read')

body = open('./data/exp1.csv', 'rb')
o = s3.Object('faaris1660', 'test').put(Body=body )
s3.Object('faaris1660', 'test').Acl().put(ACL='public-read') 

dyndb = boto3.resource('dynamodb', 
    region_name='us-west-2', 
    aws_access_key_id='access_key', 
    aws_secret_access_key='secret_key' 
 ) 

try: 
    table = dyndb.create_table( 
        TableName='DataTable', 
        KeySchema=[ 
            { 
                'AttributeName': 'PartitionKey', 
                'KeyType': 'HASH' 
            }, 
            { 
                'AttributeName': 'RowKey', 
                'KeyType': 'RANGE' 
            } 
        ], 
        AttributeDefinitions=[ 
            { 
                'AttributeName': 'PartitionKey', 
                'AttributeType': 'S' 
            }, 
            { 
                'AttributeName': 'RowKey', 
                'AttributeType': 'S' 
            }, 
 
        ], 
        ProvisionedThroughput={ 
            'ReadCapacityUnits': 5, 
            'WriteCapacityUnits': 5 
        } 
    ) 
except Exception as e: 
    print (e) 
    #if there is an exception, the table may already exist.   if so... 
    table = dyndb.Table("DataTable") 

#wait for the table to be created 
table.meta.client.get_waiter('table_exists').wait(TableName='DataTable') 
print(table.item_count) 

with open('./data/experiments.csv', 'r') as csvfile: 
    csvf = csv.reader(csvfile, delimiter=',', quotechar='|') 
    next(csvf)
    for item in csvf:
        print (item) 
        body = open('./data/'+item[4], 'rb') 
        s3.Object('faaris1660', item[4]).put(Body=body ) 
        md = s3.Object('faaris1660', item[4]).Acl().put(ACL='public-read') 
         
        url = " https://s3-us-west-2.amazonaws.com/faaris1660/"+item[4] 
        metadata_item = {'PartitionKey': item[0], 'RowKey': item[1],  
                 'temp' : item[1], 'conductivity' : item[2], 'concentration' : item[3], 'url':url}  
        try: 
            table.put_item(Item=metadata_item) 
        except Exception as e: 
            print (e)

response = table.get_item( 
    Key={ 
        'PartitionKey': '1', 
        'RowKey': '-1',
    } 
) 
item = response['Item'] 
print(item) 

response