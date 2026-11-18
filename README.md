# SauceDemo QA Automation — Java + Selenium + TestNG + REST Assured

A portfolio-ready QA assignment project using **Java 17**, **Selenium WebDriver**, **TestNG**, **Maven**, and **REST Assured**.

- UI application: https://www.saucedemo.com/
- API under test: https://reqres.in/
- UI design: Page Object Model (POM)
- Browser driver: Selenium Manager (no separate WebDriverManager dependency)
- Failure evidence: automatic screenshots in `target/screenshots/`

> The live SauceDemo was exercised in Chrome on 2026-11-18. Two findings are documented as reproduced, and one unconfirmed automation investigation is retained under `docs/bug-reports/`. Exploratory checks are kept separate from the regression suite because they intentionally fail when the seeded-user defects reproduce.

## 1. Requirements

- JDK 17 or newer (`java -version`)
- Maven 3.9 or newer (`mvn -version`)
- Google Chrome installed
- Internet access to SauceDemo and ReqRes
- IntelliJ IDEA (recommended, but not required)

Selenium Manager (included with Selenium 4) resolves the compatible ChromeDriver automatically when the tests start.

## 2. Open in IntelliJ

1. Extract the ZIP file.
2. Open IntelliJ IDEA → **Open** → select the `saucedemo-qa-java` folder.
3. IntelliJ should recognise `pom.xml` as a Maven project. Select **Load Maven Project** if prompted.
4. Set Project SDK to JDK 17 or newer: **File → Project Structure → Project SDK**.
5. Wait for Maven dependencies to finish downloading.

## 3. Run the complete assignment suite

From the IntelliJ Terminal, in the folder containing `pom.xml`:

```bash
mvn clean test
```

Run with a visible Chrome window (default):

```bash
mvn clean test -Dheadless=false
```

Run headless (useful for CI):

```bash
mvn clean test -Dheadless=true
```

Run only the API suite:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng-api.xml
```

Run only the UI regression suite:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng-ui.xml
```

Run the three `problem_user` exploratory checks separately:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng-bugs.xml
```

The bug-investigation suite can fail when an investigated behaviour is observed. That is intentional: inspect the test output and screenshots before classifying the finding. A passing check means the candidate issue was not reproduced in that run.

Measure a `performance_glitch_user` login separately:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng-performance.xml -Dheadless=true
```

## 4. Test users

| Username | Purpose |
|---|---|
| `standard_user` | Main positive UI flow |
| `locked_out_user` | Login negative scenario |
| `problem_user` | Exploratory investigation only |
| `performance_glitch_user` | Slow-login investigation/manual timing |

Password for the seeded users: `secret_sauce`.

## 5. Test coverage

### UI automation

- Valid, invalid, empty-username and locked-out login scenarios
- Product listing visibility and count
- Product sorting (price low-to-high and name Z-to-A)
- Add/remove single product and add multiple products to cart
- Checkout required-field validation
- Complete checkout and order confirmation

### ReqRes API automation

- `GET /api/users?page=2`: HTTP status, page metadata, and user object fields
- `POST /api/users`: HTTP status and response fields
- `POST /api/login` without password: validation error contract
- `GET /api/users/23`: not-found status

ReqRes documentation distinguishes classic demo endpoints from newer project endpoints. These tests target the classic examples. If a future API change produces an authentication or rate-limit response, consult the current docs before changing assertions.

## 6. Screenshots and reports

On UI test failure, `TestListener` captures a browser screenshot under:

```text
target/screenshots/<test-name>-<timestamp>.png
```

TestNG and Surefire reports are generated under `target/surefire-reports/`.

## 7. Manual test documentation

- `docs/test-plan.md` — scope, approach, environment, entry/exit criteria and risks
- `docs/test-cases.md` — 36 manual test cases
- `docs/test-cases.csv` — spreadsheet-friendly test case export
- `docs/bug-reports/` — two reproduced findings with screenshots and one unconfirmed investigation
- `docs/github-board-setup.md` — instructions for creating GitHub Issues and a Project board

## 8. GitHub Actions

A workflow is included at `.github/workflows/maven.yml`. It runs the Maven suite on pushes and pull requests, then uploads Surefire reports and screenshots as artifacts.

## 9. Evidence and reporting integrity

Do not state that a candidate bug is confirmed until you have reproduced it on the live site. Add the actual browser screenshot and exact steps/actual result to the corresponding GitHub Issue. The `problem_user` checks are exploratory candidates, not proof by themselves.
