# Airpay Money — Calculators — Deep Analysis

| | |
|---|---|
| Module | Bottom nav → **Calculators** ("Financial Calculators" hub) |
| Tech | Native Flutter (no WebView) inside `com.finmapp.fininfinity/.MainActivity` |
| Device | RZCW724DGXZ, 1080 × 2340 px |
| Capture method | `adb shell uiautomator dump` + `screencap`, parsed to `*.parsed.txt` |
| Raw artifacts | `target/money/manual-capture/uidump-calc-*.{xml,png,parsed.txt}` |
| Scope | The hub plus each of the 15 calculator landing screens, at default values and after scrolling to the end. Per the brief, the downstream journeys behind **Start SIP / Start Investment / Apply Now / Invest Now / Save Tax with Funds / Start Saving** were **not** entered — their button locators are recorded here instead. |
| Data entered | None. Every screen was read at its shipped defaults. Two values were perturbed accidentally by a swipe landing on a slider (EMI loan amount, Budget income) and are called out where relevant. |
| Screens | `airpay_money/.../screens/calculators/CalculatorsHubScreen.java`, `CalculatorScreen.java` |

---

## 1. Capture inventory

| Screen / state | Artifact prefix |
|---|---|
| Calculators hub, top of grid | `uidump-calc-02-home`, `-03-hub` |
| Calculators hub, scrolled (all 15 tiles) | `uidump-calc-04-hub-scrolled`, `-05-hub-end` |
| EMI Calculator | `uidump-calc-emi-00-probe`, `-02-scrolled`, `-03-end` |
| EMI "Edit Interest Rate" sheet | `uidump-calc-emi-04-edit` |
| SIP Calculator | `uidump-calc-sip-01-top`, `-02-scroll1` … `-04-scroll3`, `-10-top` |
| SIP "Monthly Investment" sheet | `uidump-calc-sip-11-edit-sheet` |
| Lumpsum Calculator | `uidump-calc-lumpsum-01-top` … `-04-scroll3` |
| PPF Calculator | `uidump-calc-ppf-01-top` … `-04-scroll3` |
| EPF Calculator | `uidump-calc-epf-01-top` … `-04-scroll3` |
| FD, "By Deposit Amount" tab | `uidump-calc-fd-01-top` … `-04-scroll3` |
| FD, "By Target Amount" tab | `uidump-calc-fd-05-target-tab`, `-06-target-scroll` |
| NPS Calculator | `uidump-calc-nps-01-top` … `-04-scroll3` |
| Retirement Calculator | `uidump-calc-retirement-01-top` … `-04-scroll3` |
| Crorepati Calculator | `uidump-calc-crorepati-01-top`, `-02-scroll1` |
| Term Insurance tile → Retirement screen (routing defect) | `uidump-calc-recheck-00-hub`, `uidump-calc-terminsurance-10-verify`, `-11-scroll` |
| Buy vs Rent Calculator | `uidump-calc-buyvsrent-01-top` … `-04-scroll3` |
| Taxation Indian Equity | `uidump-calc-taxindian-01-top` … `-04-scroll3` |
| Taxation International Equity | `uidump-calc-taxintl-01-top`, `-02-scroll1` |
| Inflation Calculator | `uidump-calc-inflation-10-top`, `-11-scroll` |
| Inflation base-year picker | `uidump-calc-inflation-12-baseyear` |
| Inflation history table, expanded | `uidump-calc-inflation-14-showmore` |
| Budget Calculator | `uidump-calc-budget-10-top`, `-11-scroll`, `-12-end` |
| Budget "Your Monthly Income" sheet | `uidump-calc-budget-04-scroll3` |

---

## 2. Navigation map

```
Money bottom nav → Button cd=[Calculators]  [711,2187][1052,2322]
   │
   ├─ header: Button cd=[Open settings] · View cd=[Hello] / cd=[Shubham Verma] · ImageView cd=[Notifications]
   ├─ hero:   unlabelled ImageView · View cd=[Financial Calculators]
   │          View cd=[Empower your money decisions with our suite of calculators]
   │
   └─ 3-column tile grid, 15 tiles, scrolls as one list
        row 1   EMI Calculator            SIP Calculator             Lumpsum Calculator
        row 2   PPF Calculator            EPF Calculator             FD Calculator
        row 3   NPS Calculator            Retirement Calculator      Crorepati Calculator
        row 4   Term Insurance Calc.      Buy vs Rent Calculator     Taxation Indian Equity
        row 5   Taxation Intl. Equity     Inflation Calculator       Budget Calculator

   every tile → a single-page calculator screen with the same skeleton:
        unlabelled back chevron  [0,95][158,253]
        View cd=[<screen title>]  [203,137][…,210]
        View cd=[<one-line description>]
        N × { slider row : View/Button cd=[<value>, <label>\n<value>\n<label>]
              + SeekBar  cd=[<fill %>] }
        result block   View cd=[<label>]  +  View cd=[<amount>]
        optional donut / bar chart with a bare numeric content-desc
        optional sticky footer Button  [45,2154][1035,2295]

   tapping any value chip → bottom sheet
        View cd=[<label>]   ("Edit <label>" on EMI only)
        unlabelled close Button
        EditText  txt=[<formatted value>]
        Button cd=[Save Changes]
        View cd=[Scrim]
```

### Where each calculator leads (not followed)

| Calculator | Sticky footer CTA | Locator |
|---|---|---|
| EMI | — | — |
| SIP | **Start SIP** | `//android.widget.Button[@content-desc='Start SIP']` |
| Lumpsum | **Start Investment** | `//android.widget.Button[@content-desc='Start Investment']` |
| PPF | — | — |
| EPF | **Save Tax with Funds** | `//android.widget.Button[@content-desc='Save Tax with Funds']` |
| FD (both tabs) | **Apply Now** | `//android.widget.Button[@content-desc='Apply Now']` |
| NPS | — | — |
| Retirement | **Start Saving** | `//android.widget.Button[@content-desc='Start Saving']` |
| Crorepati | — | — |
| Term Insurance | **Start Saving** (because the tile opens the Retirement screen) | `//android.widget.Button[@content-desc='Start Saving']` |
| Buy vs Rent | — | — |
| Taxation Indian Equity | — | — |
| Taxation International Equity | **Invest Now** | `//android.widget.Button[@content-desc='Invest Now']` |
| Inflation | — (has an in-page **Show More** at `[683,2143][976,2278]`) | `//android.widget.Button[@content-desc='Show More']` |
| Budget | — | — |

All sticky CTAs share the bounds `[45,2154][1035,2295]`, so `//android.widget.Button[@content-desc='…']` is the only reliable discriminator.

---

## 3. Screen-by-screen element inventory

### 3.0 Hub (`uidump-calc-05-hub-end`)

Tiles are `android.view.View` nodes whose `content-desc` is the visible label; each wraps an unlabelled `NAF ImageView` icon. The tile's clickable bounds cover icon and label, but the vertical gap between them is **not** hit-testable — a tap at the geometric centre of the `EMI Calculator` tile `[56,275][379,478]`, i.e. `(217, 376)`, does nothing. Taps must land on the label text (`(217, 416)`).

### 3.1 EMI Calculator (`uidump-calc-emi-00-probe`, `-03-end`)

- Description: "Get a clear & structured breakdown of your monthly EMI you would need to pay towards your loan."
- Three rows, uniquely in the module rendered as `android.widget.Button` with an `Edit …` affordance baked into the name: `cd=[₹500,000, Edit Loan Amount\nLoan Amount\n₹500,000\nLoan Amount]`, `cd=[13.0%, Edit Interest Rate…]`, `cd=[5 yr, Edit Tenure…]`
- Results: `Loan Amount ₹ 5,00,000` · `Interest Amount ₹ 1,82,592` · `Total Payment ₹ 6,82,592` · **`EMI Amount ₹ 11,377`**
- Donut centred on `₹ 6,82,592` / `Total Payment`, legend `Loan Amount` / `Interest Amount`
- Edit sheet: `View cd=[Edit Interest Rate]`, unlabelled close `Button [889,657][1024,792]`, `EditText txt=[13.0]`, `Button cd=[Save Changes]`, `View cd=[Scrim]`

### 3.2 SIP Calculator (`uidump-calc-sip-01-top`, `-04-scroll3`)

- Rows: `Monthly Investment ₹ 1,000` (slider at **0%**, i.e. the minimum) · `Investment Period 5 yr` · `Desired Return 12%`
- Results: `Invested Amount ₹ 60,000` · `Est. Returns ₹ 21,670` · `Total Value ₹ 81,670`
- Donut exposes **two** overlapping numeric nodes with identical bounds, `cd=[27]` and `cd=[100]`; centre `₹ 81,670` / `Total Return`; legend `Total Return` / `Total investment`
- `Button cd=[Start SIP]`
- Edit sheet title is the bare label `Monthly Investment` and the `EditText` is pre-filled with `₹ 1,000` — symbol and comma included

### 3.3 Lumpsum Calculator (`uidump-calc-lumpsum-01-top`)

- Rows: `Total investment ₹ 5,00,000` · `Expected Return (p.a) 12%` · `Tenure 10 yr`
- Results: `Investment Amount ₹ 5,00,000` · `Est. Returns ₹ 10,52,924` · `Total value ₹ 15,52,924`
- Donut `cd=[68]`, centre `₹ 15,52,924` / `Total Return`
- `Button cd=[Start Investment]`

### 3.4 PPF Calculator (`uidump-calc-ppf-01-top`)

- Rows: `Annual Investment ₹ 1,50,000` (slider at **100%**, i.e. pinned to the maximum) · `Investment Period 15 yr` · `Expected Return (p.a) 7.10%`
- Results: `Investment Amount ₹ 1,50,000` · `Total Interest Earned ₹ 2,69,695` · `Maturity Value ₹ 4,19,695`
- Donut `cd=[64]`, centre `₹ 4,19,695` / `Maturity Amount`; both legend nodes have degenerate bounds `[0,0][0,0]`
- No CTA

### 3.5 EPF Calculator (`uidump-calc-epf-01-top`, `-04-scroll3`)

Six sliders — the longest form in the module: `Current Age 25.00` · `Retirement Age 61.00` · `Monthly Salary (Basic + DA) ₹ 1,00,000` · `Annual Increase in Salary 5.00%` · `Your Contribution 12.00%` · `Company's Contribution 3.67%`. `EPF Interest Rate 8.1%` is read-only, marked with a `View cd=[Fixed]` pill.

Results: `Your contribution 12.00%` · `Employer contribution 3.67%` · `Total Contribution Rate 15.67%` · `Total interest earned ₹ 5,22,54,029` · donut `cd=[74]` centred on `₹ 7,02,75,091` / `Estimated EPF Value`, caption `Estimated EPF at Retirement (Age 61)`, legend `Total Interest` / `Total investment`. `Button cd=[Save Tax with Funds]`.

### 3.6 FD Calculator (`uidump-calc-fd-01-top`, `-05-target-tab`)

Title is **`Fixed Deposit`**. The only calculator with tabs: `View cd=[By Deposit Amount]` `[56,281][574,396]` and `View cd=[By Target Amount]` `[602,281][1080,396]`; neither exposes a selected state.

| Tab | Inputs | Result |
|---|---|---|
| By Deposit Amount | `Amount (in INR) ₹10,000` · `Interest Rate % 7.00%` · `Tenure 60 months` | `Maturity Value ₹ 14,026`, `Total Interest Earned ₹ 4,026` |
| By Target Amount | `Target amount ₹5,00,000` · `Interest Rate % 7.00%` · `Tenure 60 months` | `Deposit Amount ₹ 3,56,493`, `Total Interest Earned ₹ 1,43,507` |

Below the result, a rate card `7.00% p.a.` / `Interest Rate %` and an `Interest Rate %` table — header `Tenure` / `At Maturity` / `Monthly` / `Quarterly` / `Half-yearly`, rows `12-14` (7.40 / 7.16 / 7.20 / 7.27), `15` (7.45 / 7.21 / 7.25 / 7.32), `45-60` (8.05 / 7.77 / 7.82 / 7.89), footnote "*All interest rate shown above annually". `Button cd=[Apply Now]`.

### 3.7 NPS Calculator (`uidump-calc-nps-01-top`, `-04-scroll3`)

- Rows: `Amount Invested Monthly (in INR) ₹ 5,000` · `Age (in years) 30` · `Expected Rate of Return (in %) 10%`
- Results: `Maturity Amount ₹ 1,91,41,384` · `Total Tax Saving ₹ 44,71,902` · `Investment Amount ₹ 21,00,000` · `Growth Amount ₹ 1,70,41,384` · `Annual Tax Saving ₹ 16,500`
- Donut `cd=[11]`, label `Maturity Amount` rendered **above** its value (the reverse of every other calculator), legend `Investment Amount` / `Growth Amount`
- No CTA

### 3.8 Retirement Calculator (`uidump-calc-retirement-01-top`, `-04-scroll3`)

- Sliders: `Household expenses per month ₹ 25,000` · `Current age 30` · `Retirement age 60` · `Returns expectations after retirement 8%`
- Read-only: `Life Expectancy 80  yr` · `Inflation 6 %`
- Result: `Retirement Requirement ₹ 2,83,82,616`
- `Button cd=[Start Saving]`

### 3.9 Crorepati Calculator (`uidump-calc-crorepati-01-top`)

- Rows: `SIP Amount ₹ 25,000` · `Expected ROI (p.a) 12.0%`
- Results: `Invested Amount ₹ 40,23,699` · `Expected Amount ₹ 1,00,00,000` · `Avg. Historical Return 12.0%` · `Time required to become a Crorepati` → **`13 years 5 months`**
- Single viewport, no scroll, no chart, no CTA

### 3.10 Term Insurance Calculator

Not reachable. The tile opens the Retirement Calculator — see defect **F1**.

### 3.11 Buy vs Rent Calculator (`uidump-calc-buyvsrent-01-top`, `-04-scroll3`)

Title is `Buy Vs Rent calculator`. Six sliders: `Property Price (in INR) ₹ 1,00,00,000` · `Down Payment (in INR) ₹ 20,00,000` · `Loan Tenure (in Years) 25 yr` · `Rate of Interest on Loan (in %) 11 %` · `Monthly Rent (in INR) ₹ 15,000` · `Rent Increase Every Year (in %) 8 %`.

Result is a two-card comparison — `₹ 5,42,74,326` / `Value of your house in 25 years` against `₹ 5,47,75,911` / `Value of investment in 25 years` — then a verdict, `You may be better off staying in a rented house instead of buying it.` and `You will be having ₹ 5,01,585 more as a return on your investment.` No CTA.

### 3.12 Taxation Indian Equity (`uidump-calc-taxindian-01-top`)

Structurally different from every other calculator: each field is **four** nodes — a label `View`, an unlabelled `NAF View` holding the tappable chip, a value `View`, and a combined `View cd=[<value>, <label>]` that shares bounds with the `SeekBar`.

- `Investment Amount ₹ 1,00,000` · `Sale Amount ₹ 1,20,000` · `Holding Period 1.0 yr`
- Helper: "More than 1 year is Long Term, less than or equal to 1 year is Short Term."
- Results: `Capital Gain ₹ 20,000` · `Tax Payable ₹ 3,000`
- No CTA

### 3.13 Taxation International Equity (`uidump-calc-taxintl-01-top`)

- Rows: `Investment Amount ₹ 1,00,000` · `Sale amount ₹ 1,20,000` · `Holding Period 1 yr` · `Income tax slab 30%` (slider pinned at 100%)
- Results: `Capital Gain ₹ 20,000` · `Tax Payable ₹ 6,000`
- `Button cd=[Invest Now]`

### 3.14 Inflation Calculator (`uidump-calc-inflation-10-top`, `-12-baseyear`, `-14-showmore`)

- `Enter amount ₹ 1,00,000.00` (chip + slider) — the only amount in the module carrying decimals
- Two year pickers rendered as `Button cd=[1960]` (Base year) and `Button cd=[2026]` (End year). Tapping one opens a full-screen list of `Button cd=[<year>]` from 1960 upward plus a `View cd=[Dismiss]` spanning `[0,0][1080,2340]`
- Result: `You'll be able to buy it for` → `₹ 5,06,965.66`
- `Historical data for inflation` table, header `Year` / `Inflation`, rows merged into one node each (`2026\n3.20%`, `2025\n3.16%`, `2024\n4.83%`, `2023\n5.60%`, `2022\n6.70%`); `Button cd=[Show More]` appends `2021\n5.13%`, `2020\n6.62%`, …

### 3.15 Budget Calculator (`uidump-calc-budget-10-top`, `-12-end`)

- `Your Monthly\nIncome` with a value chip `₹ 1,00,000` and a slider
- **Current Allocation** — the only free-text inputs in the module: three unlabelled `EditText` nodes with `txt=[45000]`, `txt=[20000]`, `txt=[25000]` for `Needs` / `Wants` / `Savings`, each mirrored by a formatted `₹ 45,000` and a `45%` badge
- **Ideal Allocation** — `Needs ₹ 50,000` · `Wants ₹ 30,000` · `Savings ₹ 20,000`
- **Difference** — `₹ 5,000` / `₹ 10,000` / `₹ 5,000` and `10.00%` / `33.33%` / `25.00%`
- No CTA

---

## 4. Accessibility findings

Severity: **S1** blocks the task for assistive-tech users, **S2** makes it substantially harder, **S3** is a quality defect.

| ID | Severity | Finding | Evidence | WCAG |
|---|---|---|---|---|
| A1 | S1 | Every slider in the module is a `SeekBar` whose `content-desc` is the **fill percentage**, not the value or the label. TalkBack announces "18%" for a ₹1,00,000 salary and "100%" for a ₹1,50,000 annual PPF contribution. The value and label live on a separate sibling node, so an adjustment gives no usable feedback. There are 48 such sliders across the 15 screens. | `epf-01` 9–10, `ppf-01` 5–6, `taxintl-01` 11–12 | 1.3.1, 4.1.2 |
| A2 | S1 | Slider rows merge label and value into one string and then repeat it: `cd=[₹ 1,000, Monthly Investment\n₹ 1,000\nMonthly Investment]`. The label is announced twice and cannot be separated from the value. | `sip-01` 5, `lumpsum-01` 5, `retirement-01` 5 | 1.3.1, 2.4.6 |
| A3 | S1 | The three Budget allocation `EditText`s have empty `content-desc`, empty `hint` and no label association. Their only identification is reading order relative to the `Needs` / `Wants` / `Savings` `View`s, which is invisible to a screen reader. | `budget-12-end` 8, 13, 18 | 1.3.1, 3.3.2, 4.1.2 |
| A4 | S1 | On Taxation Indian Equity the tappable value chip is an unlabelled `NAF View` with no name and no role, while the visible value sits in a different, non-clickable node. The control that changes the value is unreachable by name. | `taxindian-01` 6, 11, 16 | 1.3.1, 4.1.2 |
| A5 | S2 | The back chevron on all 15 calculator screens is a `NAF Button` with empty `content-desc`. | every `*-01-top` line 2 | 4.1.2 |
| A6 | S2 | The edit bottom sheet's close button is an unlabelled `NAF Button`, and the sheet's `EditText` has no accessible name — only the sheet title above it carries the field name. | `emi-04-edit` 3–4, `sip-11-edit-sheet` 3–4 | 4.1.2, 3.3.2 |
| A7 | S2 | Donut and bar charts expose a single bare number as their only text alternative: `cd=[68]` on Lumpsum, `cd=[64]` on PPF, `cd=[74]` on EPF, `cd=[11]` on NPS. On SIP, **two** such nodes (`27` and `100`) share identical bounds, so TalkBack reads "27, 100" with no context. | `lumpsum-01` 17, `ppf-01` 17, `epf-04` 16, `nps-01` 21, `sip-01` 17–18 | 1.1.1, 1.3.1 |
| A8 | S2 | The FD tabs are plain clickable `View`s with no tab role, no `selected` state and no tablist grouping, so the active tab is not announced. | `fd-01` 4–5, `fd-05` 4–5 | 1.3.1, 4.1.2 |
| A9 | S2 | Hub tiles are `View`s with no button role, and their icons are separate unlabelled `NAF ImageView`s. There is also no grid/list semantics, so nothing announces "1 of 15". | `05-hub-end` 7–36 | 1.3.1, 4.1.2 |
| A10 | S2 | Inflation history rows merge year and rate into one node (`2026\n3.20%`) with no row/cell semantics, and the FD rate table is a flat sequence of 20 unrelated `View`s with no association between a row header and its cells. | `inflation-10` 19–23, `fd-04` 17–36 | 1.3.1 |
| A11 | S2 | Result blocks are label/value pairs in sibling nodes with no programmatic association. `EPF Interest Rate` is worse: the label, the value `8.1%` and the `Fixed` pill are three unrelated `View`s. | `epf-01` 13–15 | 1.3.1 |
| A12 | S3 | Currency symbols are emitted as standalone `View cd=[₹ ]` nodes next to the Budget inputs, so amount and unit are never announced together. On PPF both chart legend nodes collapse to `[0,0][0,0]`. | `budget-10` 10, 15, 20; `ppf-01` 20–21 | 1.3.1 |
| A13 | S3 | The Inflation year picker's `View cd=[Dismiss]` spans the full screen `[0,0][1080,2340]`, overlapping all of the year buttons it is supposed to sit behind. The picker also has no title and no cancel control other than that barrier. | `inflation-12-baseyear` 2–18 | 1.3.1, 2.4.6 |
| A14 | S3 | The EMI donut legend and the SIP/EPF legends duplicate label text that already appears in the result block above them, producing the same string three times per screen for a screen-reader user. | `emi-03-end` 10–21 | 2.4.6 |
| A15 | S3 | Not verifiable from dumps, needs a manual pass: contrast of the orange sliders and orange-on-white CTAs, the grey read-only labels (`Life Expectancy`, `Inflation`, `EPF Interest Rate`), text reflow at 200%, and whether the 67 px-tall slider tracks meet the minimum target size. | — | 1.4.3, 1.4.4, 2.5.8 |

---

## 5. Functional and content defects

| ID | Severity | Defect | Reproduction |
|---|---|---|---|
| F1 | **High** | The **Term Insurance Calculator** tile opens the **Retirement Calculator**. There is no term-insurance calculator in the app despite the tile advertising one. Verified twice with a fresh hub dump in between confirming the tile occupies `[56,1266][379,1690]` and the tap landed inside it. | Calculators → scroll to row 4 → tap "Term Insurance Calculator"; `uidump-calc-recheck-00-hub` line 25 then `uidump-calc-terminsurance-10-verify` line 3 shows title `Retirement Calculator` and CTA `Start Saving` |
| F2 | **High** | The **PPF** calculator treats "Annual Investment" as a one-time lumpsum. At ₹1,50,000 / 15 yr / 7.10% it reports `Investment Amount ₹1,50,000` and `Maturity Value ₹4,19,695`, which is exactly `1.5L × 1.071¹⁵`. A genuine 15-year annual contribution of ₹1.5L would invest ₹22.5L and mature near ₹40.6L. The result is understated by roughly an order of magnitude and the "Investment Amount" line contradicts the "Annual" input label. | PPF Calculator at defaults; `uidump-calc-ppf-01-top` lines 5, 11–16 |
| F3 | Medium | Tile taps are dead in the gap between icon and label. A tap at the exact centre of the `EMI Calculator` tile does nothing; only the label text responds. Every tile is affected, so roughly the top half of each 323 × 203 px target is inert. | Calculators hub → tap `(217, 376)` inside `[56,275][379,478]`; `uidump-calc-emi-01-top` is an unchanged hub |
| F4 | Medium | Three screen titles disagree with their hub tile: `FD Calculator` → "**Fixed Deposit**", `Buy vs Rent Calculator` → "**Buy Vs Rent calculator**" (mid-sentence capital V, lowercase c), and `Term Insurance Calculator` → "Retirement Calculator" (F1). | `fd-01` 3, `buyvsrent-01` 3 |
| F5 | Medium | The Retirement screen clips its own slider label: "Returns expectations after" is rendered without "retirement", even though the accessibility tree carries the full string. | `uidump-calc-terminsurance-10-verify.png` |
| F6 | Medium | Two sliders ship pinned to an end stop, so the default state cannot be increased: SIP `Monthly Investment` sits at **0%** (₹1,000, the minimum) and PPF `Annual Investment` at **100%** (₹1,50,000, the maximum). Taxation International Equity's `Income tax slab` is likewise at 100% (30%). | `sip-01` 6, `ppf-01` 6, `taxintl-01` 12 |
| F7 | Medium | The edit sheet pre-fills the `EditText` with the *formatted* value rather than the raw number — `₹ 1,000` on SIP, `₹ 2,04,000` on Budget — so the user must delete a currency symbol and separators before typing. EMI is inconsistent again and pre-fills the raw `13.0`. | `sip-11-edit-sheet` 4, `budget-04-scroll3` 4, `emi-04-edit` 4 |
| F8 | Medium | Only EMI titles its edit sheet "Edit &lt;label&gt;" and only EMI exposes the `Edit …` affordance in the row's accessible name. The other 14 calculators use a bare label, so the same interaction is described two different ways. | `emi-04-edit` 2 vs `sip-11-edit-sheet` 2 |
| F9 | Low | Inconsistent capitalisation of result labels across screens that compute the same thing: `Total Value` (SIP) vs `Total value` (Lumpsum); `Total investment` (Lumpsum input, SIP legend) vs `Total Investment` nowhere; `Total Interest Earned` (PPF, FD) vs `Total interest earned` (EPF); `Your contribution` vs `Total Contribution Rate` on the same EPF card. | `sip-01` 15, `lumpsum-01` 15, `epf-04` 8/12/14 |
| F10 | Low | Age and percentage values are formatted as currency-style decimals on EPF — `Current Age 25.00`, `Retirement Age 61.00`, `Your Contribution 12.00%` — while the same concepts are plain integers on Retirement (`Current age 30`) and NPS (`Age (in years) 30`). | `epf-01` 5, 7, 16 vs `retirement-01` 7, `nps-01` 7 |
| F11 | Low | Stray double spaces and spaces before units leak into rendered values: `80  yr` and `6 %` on Retirement, `11 %` and `8 %` on Buy vs Rent, against `5 yr` and `12%` elsewhere. | `retirement-01` 12, 16; `buyvsrent-01` 11, 15 |
| F12 | Low | The Lumpsum description is the only one in the module missing its closing full stop, and the Crorepati description ends with a doubled one: "…expected ROI p.a..". | `lumpsum-01` 4, `crorepati-01` 4 |
| F13 | Low | The NPS donut renders its caption above the value while all other charts render it below, and the NPS chart overflows the viewport at default scroll (`[231,2098][849,2340]` is clipped at the screen edge). | `nps-01` 21–22 |
| F14 | Low | The Inflation year picker opens as a bare scrolling list with no title, no "Base year" / "End year" confirmation and no Cancel button — only an invisible full-screen dismiss barrier. It also starts at 1960 with no way to jump, so reaching 2026 needs a long scroll. | `inflation-12-baseyear` |
| F15 | Low | `Taxation Indian Equity` and `Taxation International Equity` compute the same ₹20,000 capital gain but the Indian screen offers no CTA while the International one offers `Invest Now`; similarly EMI, PPF, NPS, Crorepati, Buy vs Rent and Budget have no next step at all, so 7 of 15 calculators are dead ends. | §2 CTA table |

---

## 6. Automation notes for this module

- **Entry.** `CalculatorsHubXpaths.TAB_CALCULATORS` is a `Button`, unlike the `View`-based tabs in `FinSightHomeXpaths`. From inside the Financial Health Check flow the hardware back key lands on this tab directly; from a calculator screen it does **not** work reliably — tap the in-app chevron at `(79, 174)` instead.
- **Tile taps must hit the label, not the tile centre** (F3). The grid scrolls as a single list; pinning it to the bottom with three `input swipe 540 1900 540 700 400` makes all 15 label coordinates stable, which is what `target/money/manual-capture/calc.ps1` does.
- **Never scroll a calculator screen at x = 540.** The sliders span `x 104..976` and a vertical swipe through them drags the thumb instead of scrolling — this silently rewrote the EMI loan amount from ₹5,00,000 to ₹51,62,100 mid-capture. Scroll at `x = 60` or `x = 1055`.
- **Locate fields by label with `contains()`**, e.g. `//android.view.View[contains(@content-desc,'Monthly Investment')]`. Equality never matches because the `content-desc` is the `"<value>, <label>\n<value>\n<label>"` composite, and it changes whenever the value changes.
- **Never assert on a `SeekBar`'s `content-desc`** — it is the fill percentage (A1). Read the value from the sibling row container.
- **Setting a value** means tapping the row's value chip, clearing the pre-filled formatted string, typing the new number, and tapping `Save Changes` (F7). Driving the `SeekBar` by swipe is not reproducible because the mapping from pixels to value differs per calculator.
- **Distinguish screens by title, not by tile label** (F4): `Fixed Deposit`, `Buy Vs Rent calculator`, and `Retirement Calculator` for the Term Insurance tile.
- **All sticky CTAs share bounds** `[45,2154][1035,2295]`, so match on `content-desc`. The four known destinations (`Start SIP`, `Start Investment`, `Apply Now`, `Invest Now`, `Save Tax with Funds`, `Start Saving`) leave the Calculators module and were deliberately not entered.
- **Amounts are Indian-grouped and prefixed with a non-breaking space** after the `₹` in most result nodes (`₹\u00a05,00,000`). Normalise whitespace before comparing, or assert with `contains()` on the digits only.
- Crorepati is the only calculator that fits one viewport; every other screen needs one to three scrolls to reach the result block, and EPF and Buy vs Rent need all three.
- A `uiautomator dump` taken immediately after a swipe occasionally returns an empty tree (`uidump-calc-crorepati-04-scroll3.parsed.txt`). Re-dump rather than treating it as a blank screen.

---

## 7. Coverage

**Captured** — the Calculators tab entry point; the hub grid scrolled end to end confirming exactly 15 tiles; all 15 tile destinations including the Term Insurance mis-route; every calculator's default state and its scrolled result block; both FD tabs; the Inflation base-year picker and the expanded history table; the shared value-edit bottom sheet on both its variants (EMI's "Edit &lt;label&gt;" and SIP's bare label); and the Budget free-text allocation inputs.

**Deliberately not captured** — the six sticky-CTA destinations (`Start SIP`, `Start Investment`, `Apply Now`, `Invest Now`, `Save Tax with Funds`, `Start Saving`), whose locators are recorded in §2 but which lead into the mutual-fund, FD and insurance purchase journeys; the Inflation end-year picker (identical to the base-year picker); repeated `Show More` presses to the end of the inflation history; and any recomputation after editing a value, since the brief was to document the shipped default state of each calculator screen.
