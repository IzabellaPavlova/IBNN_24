# IBNN_24
HackaTUM-24 Check24 challenge

## Car Rental Offers

The goal is to build a high-performance backend for a car rental application that handles rental offers. The system should allow partners to create offers and customers to search for them efficiently. It must support the following REST API endpoints:

1. **POST /api/offers**: Add new rental offers (blocking until offers are ready for retrieval).
2. **GET /api/offers**: Search offers using mandatory and optional filters, including hierarchical region filtering.
3. **DELETE /api/offers**: Clear all offers before each test scenario.

Performance will be evaluated on correctness and speed under various scenarios, including concurrent read/write operations. A leaderboard ranks participants based on test results.

## Implementation
1. **POST /api/offers**: application endpoint
2. **DELETE /api/offers**: application endpoint
3. **GET /api/offers**: dummy application endpoint + SQL Query ´get_query.sql´. SQL query implement the whole filtering and data aggregation for the given parameters and return JSON.

### Mandatory Get Parameters
- **$1**: regionID
- **$2**: timeRangeStart
- **$3**: timeRangeEnd
- **$4**: numberDays
- **$5**: sortOrder
- **$6**: pageSize
- **$7**: offset
- **$8**: priceRangeWidth
- **$9**: minFreeKilometerWidth
### Optional Get Parameters
- **$10**: minNumberSeats
- **$11**: minPrice
- **$12**: maxPrice
- **$13**: carType
- **$14**: onlyVollkasko
- **$15**: minFreeKilometer

## Results
Compare ´del.json´ (test server logs) with ´res.json´ (local sql query run results)

## Challenges and Learnings
- Docker, docker-compose, setting up database on a remote server
- Java backend development and implementation of parameterized SQL queries 

## Links
[Challenge documentation - HackaTUM](https://hackatum.check24.de/docs/main-challenge/introduction)

[DevPost Submission](https://devpost.com/software/name-58ypau)
