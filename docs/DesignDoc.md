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

## Note for the admin these pages may look different.


### View Tier
> Our view tier follows a SPA architecture, using the best pratices perscribed by angular.

> For example we use app-routing to choose what components to display
> We store all 'logic' functions, and anything that deals with the api in services (see: product.service, login.service)
> Components store the data that they display, and call upon services for any data that needs external tools. 

> _You must also provide sequence diagrams as is relevant to a particular aspects 
> of the design that you are describing.  For example, in e-store you might create a 
> sequence diagram of a customer searching for an item and adding to their cart. 
> Be sure to include an relevant HTTP reuqests from the client-side to the server-side 
> to help illustrate the end-to-end flow._


### ViewModel Tier
> _Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class diagrams) with some details such as critical attributes and methods._


### Model Tier
> _Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class diagrams) with some details such as critical attributes and methods._

### Static Code Analysis/Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements._

> _With the results from the Static Code Analysis exercise, 
> discuss the resulting issues/metrics measurements along with your analysis
> and recommendations for further improvements. Where relevant, include 
> screenshots from the tool and/or corresponding source code that was flagged._

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._
