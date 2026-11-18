# Test Plan — SauceDemo and ReqRes

## 1. Objective
Validate the main user journeys of SauceDemo (Swag Labs) and exercise ReqRes API contracts using a repeatable Java automation framework.

## 2. Scope

**In scope:** login validation, inventory visibility, product sorting, cart add/remove, checkout validation/completion, ReqRes GET/POST, missing required login data, not-found response, screenshots on UI failure, and CI execution.

**Out of scope:** payment processing, real customer data, load/security testing, and persistent storage verification for ReqRes mock-created records.

## 3. Test approach

- Manual testing: 36 documented scenarios with preconditions, steps, expected result and priority.
- UI automation: Selenium WebDriver + Java + TestNG using Page Object Model.
- API automation: REST Assured with HTTP status and JSON-field assertions.
- Exploratory testing: investigate `problem_user` behaviours in a separate suite so candidate failures do not fail the normal regression suite.
- Evidence: TestNG listener captures a screenshot when a UI test fails.

## 4. Environment

- OS: macOS (local execution) or Ubuntu (CI)
- Java: JDK 17+
- Build: Maven 3.9+
- Browser: Google Chrome, local visible or headless
- UI: https://www.saucedemo.com/
- API: https://reqres.in/

## 5. Entry criteria

- Project dependencies resolve successfully.
- Chrome is installed and launches under Selenium Manager.
- Demo sites are reachable.
- Test credentials are available and limited to the public seeded test accounts.

## 6. Exit criteria

- All planned test cases have an execution status and actual result.
- All failures are triaged as product defect, automation issue, environment issue, or expected negative behaviour.
- Candidate defects have current screenshots and reproducible steps before being reported as confirmed.
- Regression suite results and API assertions are saved from the test run.

## 7. Risks and assumptions

- Demo site behaviour or selectors can change without notice.
- `problem_user` is intentionally non-standard; exploratory findings must be checked against current site behaviour.
- ReqRes is a demo API. A create response should be validated from the response body, not by assuming the created resource is persistently retrievable.
- Network, rate limiting, WAF, or browser startup failures can cause environment-related test failures.

## 8. Deliverables

1. This test plan and `test-cases.md` / `test-cases.csv`.
2. Three exploratory candidate bug reports in `docs/bug-reports/`.
3. Java Selenium UI regression suite.
4. REST Assured API tests.
5. GitHub Actions workflow and board setup instructions.
