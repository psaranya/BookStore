8# BookStore
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

CashStock:
pk:cashStockid
cashstocklastsessionid
cashstockstatusname
cashstockamount

Cashstocksession:
pk:cashstoksessionid
fk:cashstockid
cashstocklaststagename

Cashunitstock:
pk:cashunitstockid;
fk:cashunitid
fk:cashstocksessionid

cashunit:
pk:cashunitid
cashunitvalueamount


The list of Cash Unit Stock provided must contain the same Cash Unit registered in the last Cash Stock Session and the stage of the Cash Unit Stock. 

Employee data provided must match with the SAML. 

@Service
public class ValidationService {

    private boolean validateOpenCashStock(CashStocksActivateContextRequest request) {
        CashStock cashStock = getCashStock(Integer.parseInt(request.getCashStockId()));

        Integer totalQuantity = request.getCashUnitStockContextList().stream().mapToInt(CashUnitStockContext ::getCashUnitStockQuantity).sum();
        List<CashUnit> cashUnitList = request.getCashUnitStockContextList().stream().map(src -> {
            return getCashUnit(Integer.parseInt(src.getCashUnitId()));
        }).collect(Collectors.toList());

        if(cashStock == null && cashUnitList.isEmpty()) {
            return false;
        }
        CashStockSession cashStockSession = getCashStockSession(Integer.parseInt(cashStock.getCashStockLastSessionId()));

        if(!cashStock.getCashStockStatusName().equals("CLOSE") && !cashStock.getCashStockAmount().equals(totalQuantity)) {
            return false;
        }

        return true;

    }

    public CashStock getCashStock(Integer cashStockId) {
        CashStock cashStock = new CashStock();
        cashStock.setCashStockId(cashStockId);
        cashStock.setCashStockStatusName("CLOSE");
        cashStock.setCashStockAmount(100);
        return cashStock;
    }

    public CashUnit getCashUnit(Integer cashUnitId) {
        CashUnit cashUnit = new CashUnit();
        cashUnit.setCashUnitId(cashUnitId);
        return cashUnit;
    }

    public CashStockSession getCashStockSession(Integer cashStockSessionId) {
        CashStockSession cashStockSession = new CashStockSession();
        cashStockSession.setCashStockSessionId(cashStockSessionId);
        cashStockSession.setCashStockSessionStatusName("CLOSE");
        return cashStockSession;
    }

}


Branch (UnitRobiId) of the Cash Stock provided must match with the SAML


@Service
public class ValidationService {

    @Autowired
    private CashStockRepository cashStockRepository;

    @Autowired
    private CashUnitRepository cashUnitRepository;

    @Autowired
    private CashStockSessionRepository cashStockSessionRepository;

    public boolean validateOpenCashStock(CashStocksActivateContextRequest request) {
        // 1. Validate CashStockId exists
        CashStock cashStock = cashStockRepository.findById(Integer.parseInt(request.getCashStockId()))
                .orElseThrow(() -> new IllegalArgumentException("CashStockId does not exist"));

        // 2. Validate CashUnitId list
        List<CashUnit> cashUnitList = request.getCashUnitStockContextList().stream()
                .map(src -> cashUnitRepository.findById(Integer.parseInt(src.getCashUnitId()))
                        .orElseThrow(() -> new IllegalArgumentException("CashUnitId does not exist: " + src.getCashUnitId())))
                .collect(Collectors.toList());

        // 3. Validate CashStockSession exists
        CashStockSession cashStockSession = cashStockSessionRepository.findById(
                Integer.parseInt(cashStock.getCashStockLastSessionId()))
                .orElseThrow(() -> new IllegalArgumentException("CashStockSession does not exist"));

        // 4. Validate CashStockStatusName is "CLOSE"
        if (!"CLOSE".equals(cashStock.getCashStockStatusName())) {
            throw new IllegalArgumentException("CashStockStatusName must be 'CLOSE'");
        }

        // 5. Compute total cash value from provided CashUnitStocks
        int totalCashValue = request.getCashUnitStockContextList().stream()
                .mapToInt(cus -> {
                    CashUnit cashUnit = cashUnitRepository.findById(Integer.parseInt(cus.getCashUnitId()))
                            .orElseThrow(() -> new IllegalArgumentException("CashUnitId not found: " + cus.getCashUnitId()));
                    return cus.getCashUnitStockQuantity() * cashUnit.getCashUnitValueAmount();
                }).sum();

        // 6. Validate the computed amount with the requested CashStockOperationAmount
        if (!cashStock.getCashStockAmount().equals(totalCashValue)) {
            throw new IllegalArgumentException("CashStockOperationAmount does not match the total calculated value");
        }

        return true;
    }
}

