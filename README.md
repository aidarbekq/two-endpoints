### two-endpoints


This project has two endpoints for managing payment links: _parse-link_ and _create-link_.


**server port is 8081**

Endpoints:


**POST /parse-link**  
`http://localhost:8081/parse-link`  
_json payload_:  
`
{
    "paymentLink": "link"
}
`

**POST /create-link**  
`http://localhost:8081/parse-link`  
_json payload_ :  
`
{
   "standardVersion": "",
   "typeOfPaymentLink": "",
   "informationAboutServiceProvider": "",
   "mcc": "",
   "currencyCode": "",
   "paymentAmount": "",
   "providerName": "",
   "dataChecksum": ""
}
`

