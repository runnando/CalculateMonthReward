# CalculateMonthReward
## Description
#### 1. This is a spring-boot application which connect with POSTGRESQL database
#### 2. There are two entities: transaction and record; transaction's column: ID,userId, name, amount; By calculating, new information will store in the object record, and return to the front page; Record consists of userId, name, rewardPerMonth, and totalamount; rewardPerMonth is a map which store each month's reward point  
#### 3. There are three apis:
  - getAllCustomersPoint() | "/api/v1/transaction" | return all customers' information
  - getAllCustomersPoint() | "/api/v1/transaction/GetPoints" | return all customers' reward points per month and the total amount
  - getAllCustomersPoint() | "/api/v1/transaction/GetPoints/{userId}" | return the customer's reward point per month and total amount based on user id
  
#### 4. NatureUtil: This class has two tool functions: calculatePoint and calculateTotalPoint. They are used to calculate reward point based double amount and total reward point based on a hashMap which store each month's reward point
#### 5. This application havs controller test and service test

