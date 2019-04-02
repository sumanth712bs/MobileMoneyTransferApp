# MobileMoneyTransferApp


Details of the Application is present in Wiki page https://github.com/sumanth712bs/MobileMoneyTransferApp/wiki


**Steps to Run Application: **

mvn clean install

**mvn tomcat7:run**

Once Application started, Use below Endpoints

To Add New Account -> POST ->  http://localhost:8080/MobileMoneyTransferApp/webapi/account

Headers -> Content-Type : application/json and Body = 

`{ "accountId": 102, "balance": 500}`

To Get All Accounts -> GET ->  http://localhost:8080/MobileMoneyTransferApp/webapi/account

To Get Account BY ID -> GET ->  http://localhost:8080/MobileMoneyTransferApp/webapi/account/{accountId}

**To Deposit or to Withdraw money from created account**
To deposit money ->  http://localhost:8080/MobileMoneyTransferApp/webapi/transaction/deposit?accountId={accountId}&amount={amount}

eg:  http://localhost:8080/MobileMoneyTransferApp/webapi/transaction/deposit?accountId=101&amount=600

To withdraw money ->  http://localhost:8080/MobileMoneyTransferApp/webapi/transaction/withdraw?accountId={accountId}&amount={amount}

eg:  http://localhost:8080/MobileMoneyTransferApp/webapi/transaction/withdraw?accountId=101&amount=600

**Create 2 or more Accounts to Transfer money between accounts**

To Create new Transaction -> POST ->  http://localhost:8080/MobileMoneyTransferApp/webapi/transaction
Headers -> Content-Type : application/json and Body = 

`{ "fromAccountId": 103, "toAccountId": 104, "transactionId": 1001, "transferAmount": 100 }`

To Get All Transactions -> GET ->  http://localhost:8080/MobileMoneyTransferApp/webapi/transaction

To Get Transaction BY ID -> GET ->  http://localhost:8080/MobileMoneyTransferApp/webapi/transaction/1001
