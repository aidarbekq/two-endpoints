### two-endpoints


##This project has two endpoints for managing payment links: _parse-link_ and _create-link_.


#Endpoints:


**POST /parse-link**  
`http://localhost:8081/parse-link`  
_payload_ :  
`
{
    "paymentLink": "link"
}
`

**POST /create-link**  
`http://localhost:8081/parse-link`  
_payload_ :  
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

