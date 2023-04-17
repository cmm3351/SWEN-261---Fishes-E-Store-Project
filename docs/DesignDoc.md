---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information
* Team name: b-fishes
* Team members
  * Cristian Malone
  * Connor McRoberts
  * Harbor Wolff

## Executive Summary

Our rare fish e-store is a cutting-edge platform designed to provide customers with an easy-to-use and secure online shopping experience. The website is built using a single-page application (SPA) architecture, utilizing the Angular framework for the front-end and Java Springboot for the back-end. This technology combination enables us to provide a fast, responsive and dynamic user interface.

The website allows users to create an account and log in. Once logged in, users can browse our extensive selection of rare fish, view detailed product information, and add their desired fish to their shopping cart. The shopping cart is designed to be user-friendly, allowing users to easily edit and update their orders before checkout.

The website also features an admin login, providing the administrators with a secure back-end portal to manage product information, customer data, and order fulfillment. Administrators can add, edit, and remove products, view and manage customer data, and process orders, ensuring the website's operations run smoothly.

In conclusion, our rare fish e-store offers customers an exciting and hassle-free shopping experience. With our extensive selection of rare fish, user-friendly shopping cart, we are confident that our customers will enjoy shopping with us.

### Purpose
> The purpose of this project is to allow customers to purchase their favorite
> exotic fish, all while our product owner gets to make some profit while providing 
> a needed service.

### Glossary and Acronyms
> _Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| SPA | Single Page |


## Requirements

> The store owner should have complete control of the displayed inventory. 
> This includes updating, adding, and deleting items from the inventory.

> The users of the store should be displayed the inventory on a home page.
> From this homepage the users should be able to view individual items in the
> store with greater detail (reviews, extra info, etc.). The user also have the ability
> to log in with a unique username and password

> Once at the detailed item page, the user should have a functional shopping cart.
> Where they can add, delete items from their cart.
> The cart is persistant and will remain with the user throughout multiple sessions.

> When a user clicks the cart icon, they will see the items in their cart,
> and they will be given the option to checkout. 


### Definition of MVP
> Fully functional e-store application as mentioned in the section above.
> A reward points system made for users.
> A review system below each item detail page

### MVP Features
> Each fish bought from our store = 1 point
> Upon a user having more than 10 points, they can receive 1 item for free.
> Every user has the ability to leave a review under each item, and update it as they see fit.

### Roadmap of Enhancements
> Only allow users who purchase the item to review it
> Allow reviews to contain more than just an integer 1-5. Contain a long string for the review.


## Application Domain

This section describes the application domain.

![Domain Model](swen_domain.png)

> _Provide a high-level overview of the domain for this application. You
> can discuss the more important domain entities and their relationship
> to each other._


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 

The Model stores the application data objects including any functionality to provide persistance. 

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.


### Overview of User Interface

> The user iterface contains 5 pages.

> The home page contains a login button, the inventory.

> The item detail page contains the item, user reviews, and a 'add to cart' button

> The cart page contains the items that the user has added to the cart, the rewards points the user has and a 'checkout' button.

> The login pager and the create account pages both contain fields for the users to enter in information. Such as username and password.

### Note for the admin these pages may look different.
> Item Detail page
![Item detail](item_detail.png)

> Checkout page
![Checkout page](checkout.png)

> Login page
![Login page](login.png)

> Inventory page
![Inventory page](inventory.png)

### View Tier
> Our view tier follows a SPA architecture, using the best pratices perscribed by angular.

> For example we use app-routing to choose what components to display

> We store all 'logic' functions, and anything that deals with the api in services (see: product.service, login.service)

> Components store the data that they display, and call upon services for any data that needs external tools. 

> _You must also provide sequence diagrams as is relevant to a particular aspects 
> of the design that you are describing.  For example, in e-store you might create a 
> sequence diagram of a customer searching for an item and adding to their cart. 
> Be sure to include an relevant HTTP reuqests from the client-side to the server-side 
> to help illustrate the end-to-end flow.

>Checking out
![Checkout Sequence](seqCheckout.jpg)
>Searching for an item
![Search For an Item Sequence](seqSearchItem.jpg)


### ViewModel Tier
Our ViewModel Tier consists of four files, two in the API and two in the Angular UI. In the API,
the ProductController.Java and UserController.Java files consist of functions that correspond
to every possible user interaction. When an interaction occurs, a function in the controller first
checks to see if that interaction was valid. If so, the function will call the corresponding
function in the persistance files to actually modify or retireve data, and return an HTTPStatus
of OK. Otherwise, the function will return an HTTPStatus that corresponds to that specifc error
with the request and notify the user of it.

In the UI, the ProductService and LoginService are responsibel for connecting the API to the 
user interface. When users interact with the website through the UI, each service contains the
URI to Controller mappings, and use them to call the specific HTTP controller functions that
correspond the user's action. This way, both the website UI and JSON information will update
at the same time.

### Model Tier
This tier of the design contains six Java files on the API side of the program's operation. Two 
files in the Model Tier, Product.Java and User.Java, directly retrieve and manipulate data from 
Product and User objects. For Product.java, each Product object contains an Integer id, a String 
name representing its name, a String info representing a description of the product, an Integer 
price representing its price, an Integer quantity representing the product's quantity, a String 
imgSource representing a link to the corresponding image for a product to be displayed, and a 
Map<String,Integer> representing the reviews for that product. The rest of the program is filled 
with various getters and setters for each attribute. For User.Java, each User object contains an
Integer id, a String username representing the user's username, a String password representing the
user's password, a Boolean isAdmin to indicate if the user has admin privileges, an Integer array
holding the Product ids of products in the user's cart, and an Integer rewards representing the 
user's current rewards points. Again, the rest of the program is filled with various getters and 
setters for each attribute.

The other four files deal with persistence. They call functions from the object classes to update 
the contents of the JSON files, which contain Product and User object information currently 
avaialble to the user. ProductDAO.java and UserDAO.Java are interfaces, while ProductFileDAO.java 
and UserFileDAO.Java implement those intefaces to actually modify data. Corresponding functions 
from the controller files call these functions whenever a user interacts with the website. Each 
FileDAO contains a String filename referencing the corresponding JSON, and an array of either
Product or User Objects representing the local instantiation of the current Products or User.
Whenever a function call is made, it updates the locally instantiated array and saves its contents
to the corresponding JSON file.

### Static Code Analysis/Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements._

One design improvement that we would make to our code base if given more time would be to implement
more unit tests. During our last Code analysis, our current coverage was only 77%. We have unit tests
in place to check for the correct operation of every function in the API, and they all pass. However,
for some of our features added in later Sprints, we did not have time to implement tests for other
HTTP Status results or failures of functions. This is most likely where our lack of coverage comes
from, and we would want to increase that coverage if possible.

Another design improvement that we would make to our code base if given more time would be to implement
more security and error handling in the API code. In the UI, we prevented elements or buttons from 
displaying to users in scenarios where errors could occur. For example, A user cannot look at their cart 
or add items to their cart without being logged in, because user that is not logged in does not have an 
existing cart, so adding an item to a cart would result in an error. Therefore, the website does not 
display those options to the user until they are logged in. However, if a user were to somehow bypass
these conditional displays, they could cause these errors to occur and there may not be a way to recover
from them. Most of the functions in the API have error piping, but some do not. Therefore, if more time
was given to increase encryption on accessing website elements and implement more error piping, that 
would definitely be ideal.

A third design improvement that we would like to make to our code base if given more time is to improve 
UI of the user review system. The button system for creating and editing reviews is very usable and
aesthetically pleasing. However, the display of the reviews themselves is relatively bland, displaying
only the user's name and their rating as a number out of five. If possible, we would make this display
more clear and complimentary to the website's visuals. We could potentially represent the user's 
reviews as stars or other icons, or even add an option for user's to describe their review in text.

> _With the results from the Static Code Analysis exercise, 
> discuss the resulting issues/metrics measurements along with your analysis
> and recommendations for further improvements. Where relevant, include 
> screenshots from the tool and/or corresponding source code that was flagged._

## Testing
> _This section will provide information about the testing performed
> and the results of the testing.

> TESTS WRITTEN:
 getProduct, createProduct, updateProduct, getProducts, searchProducts, deleteProduct
 getReview, createReview, editReview, deleteReview
 findUser, createUser
 addProductToCart, removeProductFromCart, showCart
 checkout, getRewardsPoints, useRewardsPoints, notEnoughRewardsPoints
 
RESULTS:
All written tests have passed.

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

![Current Coverage](coverage.png)

### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._
