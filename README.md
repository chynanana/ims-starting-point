Coverage: 43.7%
# Project Title
This project is an Inventory management system that I have created to allow a user to do a collection of things. The system consists of four different tables; Customers, Basket, Orders and Items. A customer will be able to Create, Delete and Update objects in each of these tables.
I will be creating a database using SQL which will work with code that I write in Java using the Eclipse IDE. 
 

## Getting Started
CREATE TABLE Customers(
Customer_ID INT PRIMARY KEY AUTO_INCREMENT,
Name VARCHAR(50),
Address VARCHAR(50),
Postcode VARCHAR(50),
Date DATETIME
);

CREATE TABLE Orders(
Order_ID INT PRIMARY KEY AUTO_INCREMENT,
fk_Customer_ID  INT NOT NULL,
Placed DATE NOT NULL,
  FOREIGN KEY (fk_Customer_ID) REFERENCES Customers(Customer_ID)
  );


CREATE TABLE Items(
Item_ID INT PRIMARY KEY AUTO_INCREMENT,
Product VARCHAR(50),
Price DECIMAL(22, 2)
);

CREATE TABLE Basket(
Entry INT PRIMARY KEY AUTO_INCREMENT,
fk_Order_ID INT,
fk_Item_ID  INT NOT NULL,
Quantity INT NOT NULL,
FOREIGN KEY(fk_Order_ID) REFERENCES Orders(Order_ID),
FOREIGN KEY(fk_Item_ID) REFERENCES Items(Item_ID)
);


### Prerequisites

Eclipse
MySQL 
Git bash 




### Installing

A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Tests are ran using JUnit  and you run them by pressing ctrl + SHIFT + F11

### Unit Tests 

Tests are ran using JUnit  and you run them by pressing ctrl + SHIFT + F11
Tests are run in order to check that my database is functioning correctly without affecting the data stored in my table 


## Deployment

I would need to create a jar file and change the SQL information like username and password 

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **Nick Johnson** - *Refinements* - [nickrstewarttds](https://github.com/nickrstewarttds)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

Vinesh Ghela
