Write-Host "`r`nRunning Curl commands...`r`n"

Write-Host "Fetching all products: `r`n"
curl.exe -X GET 'http://localhost:8080/products'

Write-Host "`r`n`r`n`r`nAttempting to add product `r`n"
curl.exe -X POST -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"name\": \"Cookiecutter Shark\", \"info\": \"Silly shark\", \"price\": 9000, \"quantity\": 2}'
Write-Host "`r`n`r`n`tListing current products: `r`n"
curl.exe -X GET 'http://localhost:8080/products'

Write-Host "`r`n`r`n`r`nAttempting to delete product (id=5): `r`n"
curl.exe -i -X DELETE 'http://localhost:8080/products/5'
curl.exe -X GET 'http://localhost:8080/products'

Write-Host "`r`n`r`n`r`nAttempting to get specific product (id=1): `r`n"
curl.exe -X GET 'http://localhost:8080/products/1'

Write-Host "`r`n`r`n`r`nAttempting to search for product containing (ol): `r`n"
curl.exe -X GET 'http://localhost:8080/products/?name=ol'

Write-Host "`r`n`r`n`r`nAttempting to update a product in store: `r`n"
curl.exe -i -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": 1, \"name\": \"Literally Golden Koi\", \"info\": \"It is actually made of Gold\", \"price\": 40000, \"quantity\": 100}'
Write-Host "`r`n`r`n`tRetrieving updated product: `r`n"
curl.exe -X GET 'http://localhost:8080/products/1'
Write-Host "`r`n`r`n`tReverting updated product: `r`n"
curl.exe -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": 1, \"name\": \"Golden Koi\", \"info\": \"Lorem ipsum dolor sit amet, consectetur\", \"price\": 990, \"quantity\": 34}'

Write-Host "`r`n`r`n`r`nListing final products: `r`n"
curl.exe -X GET 'http://localhost:8080/products'
Write-Host "`r`n"

