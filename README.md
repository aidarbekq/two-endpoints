# two-endpoints


This controller has two endpoints for managing payment links: parse-link and create-link.


Endpoints:


**POST /parse-link**
`http://localhost:8081/parse-link`
`
{
    "paymentLink": "link"
}
`

**POST /create-link**
`http://localhost:8081/parse-link`
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

