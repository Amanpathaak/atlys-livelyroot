# Atlys — SDET Take‑Home (UI + API)

Single Maven repo containing:
- **UI tests**: Selenium + TestNG (Page Objects, separate locator classes)
- **API tests**: Rest‑Assured + TestNG (with an embedded **WireMock** stub for the placeholder API)

> ✅ By default, API tests run against a local mock (WireMock).  
> 🔁 To point at a real endpoint, set `-Dmock=false -DapiBaseUri=https://searchserverapi.com`.

---

## Project Structure

```
atlys-sdet-assignment/
├─ pom.xml
├─ testng.xml
├─ docs/
│  └─ test-plan.csv
├─ src/test/java/com/atlys/common/
│  ├─ BaseTest.java
│  └─ TestConfig.java
├─ src/test/java/com/atlys/ui/locators/
│  ├─ HomePageLocators.java
│  └─ SearchResultsLocators.java
├─ src/test/java/com/atlys/ui/pages/
│  ├─ HomePage.java
│  └─ SearchResultsPage.java
├─ src/test/java/com/atlys/ui/tests/
│  └─ SearchTests.java
├─ src/test/java/com/atlys/api/client/
│  └─ SearchApiClient.java
├─ src/test/java/com/atlys/api/tests/
│  └─ SearchApiTests.java
└─ src/test/resources/
   ├─ config.properties
   ├─ jsonschemas/widgets-schema.json
   └─ testdata/valid-search-terms.csv
```

---

## How to Run

### Prereqs
- JDK 17+
- Maven 3.9+
- Google Chrome installed

### Commands

Run **UI tests** only:
```bash
mvn -Dgroups=ui -Dheadless=true test
```

Run **API tests** only (default: WireMock on localhost:8089):
```bash
mvn -Dgroups=api test
```

Run **API tests** against the real endpoint:
```bash
mvn -Dgroups=api -Dmock=false -DapiBaseUri=https://searchserverapi.com test
```

Run **everything**:
```bash
mvn test
```

Override the **base URL** (optional):
```bash
mvn -Dgroups=ui -DbaseUrl=https://www.livelyroot.com test
```

---

## Notes on UI Design

- Test classes use Page Objects and **separate locator classes** under `com.atlys.ui.locators.*`.
- Assertions are explicit and meaningful (URL looks like search, product count/no‑results message, etc.).
- Headless mode is on by default; pass `-Dheadless=false` to watch the browser.

---

## API Strategy (since the endpoint is a placeholder)

The assignment specifies `GET https://searchserverapi.com/getwidgets` but couldn't find the real service.
So the suite **embeds WireMock** as a local stub server with realistic responses:

- `GET /getwidgets?q=monstera` → **200** with a non‑empty list
- `GET /getwidgets` (missing `q`) → **400** with error payload
- `GET /getwidgets?q=zzzxqwy` → **200** with an empty list

We can point to a live service later by setting `-Dmock=false -DapiBaseUri=<real-base-uri>`.

---

## Which Test Cases Are Automated

From the test plan (below), the following are automated in this repo:

- **TC‑01** Positive search returns results (UI)`
- **TC‑02** Invalid search shows no results (UI)
- **TC‑03** Trims spaces still returns results (UI)
- **API‑01/02/03** Valid, Missing Param, No Matches (API)

---

## Troubleshooting

- If Chrome fails to launch, ensure Chrome is installed and reachable. Selenium Manager will download the driver automatically.
- For flaky selectors, update `HomePageLocators.java` or `SearchResultsLocators.java` only — tests and pages won’t need changes.
- On CI, keep `-Dheadless=true`.

---
