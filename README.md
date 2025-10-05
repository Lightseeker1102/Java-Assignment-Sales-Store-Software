# Java-Assignment-Sales-Store-Software
sales store software , built as java assignment 2 , it has the basic functions that a sales software should need
Assignment 1: Inventory and Billing System – Specification Document
Problem Statement:
Design and implement a Java console application for an Inventory and Billing
System that handles product purchases, sales, customer types, and a reward points
system for premium customers. The application should demonstrate object-oriented
principles (encapsulation, inheritance, aggregation, polymorphism) and maintain
accurate stock quantities.

Class Requirements:

1. Product
2. ProductTaxCategory
3. Customer
4. PremiumCustomer
5. SaleDetail
6. Sale
7. PurchaseDetail
8. Purchase

Business Rules:
1. Stock must be verified before confirming a sale. If insufficient stock is available,
the sale should not proceed.
2. Stock quantity must be updated immediately after a sale or purchase.
3. Premium customers earn 1 point for every ₹100 spent (after discounts, before
tax). Once points reach 200 or more, a discount of ₹10 per 100 points is applied,
and redeemed points are deducted.
4. Regular customers do not receive points or discounts.
5. Sale and purchase details should link directly to the associated products.

Console Interface Requirements:
1. The program should provide a menu with options:
o Add Product
o Add Customer
o Make a Purchase
o Make a Sale
o Display Products
o Exit
2. Input validations must be performed for all user entries.
3. Encapsulation must be followed for all attributes.

Expected Output Behavior:
After each transaction, the system should display:
• Sale Bill including:
o Product list
o Quantities
o Prices
o Taxes
o Discounts (if applicable)
o Total amount
o Updated reward points (for premium customers)
• Updated Stock after every sale or purchase.

Questions for Students:
1. Draw the UML Class Diagram for the system.
2. Implement the classes with the necessary data members and methods for
system functionality and business rules.
3. Use encapsulation, inheritance, aggregation, and polymorphism wherever
required.
4. Implement the main method for a menu-driven system.
