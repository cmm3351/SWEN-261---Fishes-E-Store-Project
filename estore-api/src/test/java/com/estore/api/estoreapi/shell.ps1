Write-Host "Running Curl commands...'r'n"

Write-Host "Fetching all products: 'r'n"
curl.exe -X GET 'http://localhost:8080/products'

Write-Host "'r'n attempting to add prodcut 'r'n"
curl.exe -X POST -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"name\": \"Cookiecutter Shark\", \"info\": \"Silly shark\", \"price\": 9000, \"quantity\": 2}'
curl.exe -X GET 'http://localhost:8080/products'

Write-Host "'r'n Attempting to delete product: 'r'n"
curl.exe -X DELETE 'http://localhost:8080/products/5'
curl.exe -X GET 'http://localhost:8080/products'

Write-Host "'r'n Attempting to get specific product: 'r'n"
curl.exe -X GET 'http://localhost:8080/products/1'

Write-Host "'r'n Attempting to search for product containing (ol): 'r'n"
curl.exe -X GET 'http://localhost:8080/products/?name=ol'

Write-Host "'r'n Attempting to update a product in store: 'r'n"
curl.exe -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": 1, \"name\": \"Literally Golden Koi\", \"info\": \"It is actually made of Gold\", \"price\": 40000, \"quantity\": 100}'
curl.exe -X GET 'http://localhost:8080/products/1'
Write-Host "'r'n"
curl.exe -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/products' -d '{\"id\": 1, \"name\": \"Golden Koi\", \"info\": \"Lorem ipsum dolor sit amet, consectetur\", \"price\": 990, \"quantity\": 34}'

curl.exe -X GET 'http://localhost:8080/products'
