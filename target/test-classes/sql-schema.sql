-- create database if not exists ims_test;
-- USE ims_test;
-- drop table if exists Customers;
-- drop table if exists Orders;
-- drop table if exists Items;
-- drop table if exists Basket;

USE ims_test;
DELETE FROM ims_test.Orders;
DELETE FROM ims_test.Basket;
DELETE FROM ims_test.Customers;
DELETE FROM ims_test.Items;