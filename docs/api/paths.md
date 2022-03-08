
<a name="paths"></a>
## Resources

<a name="links-controller_resource"></a>
### Links-controller
Links Controller


<a name="deletelinkusingdelete"></a>
#### deleteLink
```
DELETE /link/delete/{link}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**link**  <br>*required*|link|string|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|No Content|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


##### Produces

* `\*/*`


##### Example HTTP request

###### Request path
```
/link/delete/string
```


<a name="redirectusingget"></a>
#### redirect
```
GET /link/redirect/{link}
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**link**  <br>*required*|link|string|
|**Query**|**id**  <br>*required*|id|integer (int64)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Produces

* `\*/*`


##### Example HTTP request

###### Request path
```
/link/redirect/string?id=0
```


<a name="registerlinkusingpost"></a>
#### registerLink
```
POST /link/register
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**linkRegistrationRequest**  <br>*required*|linkRegistrationRequest|[LinkRegistrationRequest](definitions.md#linkregistrationrequest)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|string|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Consumes

* `application/json`


##### Produces

* `\*/*`


##### Example HTTP request

###### Request path
```
/link/register
```


###### Request body
```json
{
  "expirationTimeInDays" : 0,
  "fullLink" : "string",
  "user_id" : 0
}
```


##### Example HTTP response

###### Response 200
```json
"string"
```


<a name="loadstatstopnusingget"></a>
#### loadStatsTopN
```
GET /link/stats/all
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**count**  <br>*optional*|count|integer (int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [Stats](definitions.md#stats) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Produces

* `\*/*`


##### Example HTTP request

###### Request path
```
/link/stats/all
```


###### Request body
```json
{ }
```


##### Example HTTP response

###### Response 200
```json
[ {
  "count" : 0,
  "short_link" : "string"
} ]
```


<a name="loadstatsuniquetopnusingget"></a>
#### loadStatsUniqueTopN
```
GET /link/stats/unique
```


##### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**count**  <br>*optional*|count|integer (int32)|


##### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|< [Stats](definitions.md#stats) > array|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


##### Produces

* `\*/*`


##### Example HTTP request

###### Request path
```
/link/stats/unique
```


###### Request body
```json
{ }
```


##### Example HTTP response

###### Response 200
```json
[ {
  "count" : 0,
  "short_link" : "string"
} ]
```



