# json-to-csv

Challenge #1

General
The task purpose is converting a results from noSQL database that arrives as JSON into CSV file.
About noSQL databases
NoSQL databases (i.e: MongoDB) stores data in documents . A document may looks like:
{
"id":123,
"name":"jac",
"creationDate":"20150412
02:23:24"
}
Important: Each document in a collection (equivalent to SQL's table) may have different fields, although the best
practice is to have them as similar as possible.
Motivation
Analysis tools (i.e: excel) still likes having data in a tabular format like CSV.
the CSV columns should represent all the fields appeared in the JSON documents.

Task
write a code that executes a query (make some mock as described below), receives a cursor to iterates query results
(JSON documents) and eventually create a single CSV file.
Cursor cur = client.query()?
while (cur.hasNext())
{
Json json = cur.next()?
// do something
}
Assumptions
? cursor next() returns a json. feel free to you your favourite library or just assume that a field from json can be
retrieved by json.get("fieldName")
? query result set may be huge


-------------------------------------------

Challenge #2
“Merge sort is a recursive algorithm that continually splits a list in half. If the list is empty
or has one item, it is sorted by definition (the base case). If the list has more than one
item, we split the list and recursively invoke a merge sort on both halves.”
1. What is the computational complexity of the merge sort algorithm in Big O
notation ? e.g. O(N) , O(logN)
2. Implement a merge sort algorithm that sorts any set of integers from a standard
input . The implementation should print line the original list and print line the
sorted list.