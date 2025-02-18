# BookStore
# KATA - DISTRIBUTED DEVELOPER - Online Bookstore

## Overview

This project is a code kata that involves creating a Simple Online Bookstore. The system consists of a front-end built with React and a back-end using a Spring Boot RESTful API. Users will be able to view a list of books, add them to their shopping cart, modify the quantity of items, and remove items from the cart. A simple checkout process will provide an order summary.

## Getting Started

### Prerequisites
- Java JDK 11 or higher
- Node.js 14 or higher
- Maven for Spring Boot
- H2 Database

### Backend Setup
1. Clone the backend repository:
   ```bash
   git clone <backend-repo-url>
   cd <backend-directory>

API Endpoints:
GET /api/books: Retrieve the list of books.
POST /api/cart/add: Add a book to the shopping cart.
GET /api/cart/{userId}: Retrieve the user's shopping cart.
DELETE /api/cart/remove/{userId}/{itemId}: Remove an item from the cart.
POST /api/checkout/{userId}: Checkout the cart 
POST /api/auth/register : User Registration
POST /api/auth/login: User Login

validates the parameters receives: 

CashStockId must exist.  

CashStockStatusName of the CashStockId provided must be “CLOSE”. 

CashUnitId provided must exist. 

Total amount of the list of Cash Unit Stock is corresponding with the CashStockOperationAmount. Example 2 notes of 50€ must have a CashStockOperationAmount = 100€. 

CashStockOperationAmount is the same as the amount registered in the Cash Stock. 

The list of Cash Unit Stock provided must contain the same Cash Unit registered in the last Cash Stock Session and the stage of the Cash Unit Stock. 

Employee data provided must match with the SAML. 

Branch (UnitRobiId) of the Cash Stock provided must match with the SAML
