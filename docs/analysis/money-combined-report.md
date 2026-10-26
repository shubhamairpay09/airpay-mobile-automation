# Airpay Money — Combined capture report

| | |
|---|---|
| Product | airpay money (`com.finmapp.fininfinity`, STAGING **2.0.124 (193)**) |
| Device | RZCW724DGXZ, 1080 × 2340 |
| Window | Last 10 days of this project chat (through 22 Sep 2026) |
| Combined from | All deep-analysis files under `docs/analysis/` plus the coverage audit |
| Raw dumps | `target/money/manual-capture/` — **837** unique uidumps |
| Overall score | **82 / 100** (re-checked against disk) |

This file is the single packet: chat walkthrough, then every written report in full.

---

## Chat walkthrough (last 10 days)

All Money analysis reports were produced in the long capture thread [Money profile and modules](e034280d-171f-4b78-9b77-332fcd5a75e5). Other recent threads in this repo were Capital locators / crawl, not Money reports.

| When (approx) | What you asked | What was delivered |
|---|---|---|
| Earlier in the same thread | Stand up `airpay_money`, login smoke, then deep-capture products | Module + Java XPaths + features for MF, FD, NPS (no standalone MD) |
| 21 Sep ~15:00 | Capture Tax Filing & Planning, then report | `money-tax-filing-and-planning.md` — ClearTax WebView, 17 a11y + 11 defects |
| 21 Sep ~15:57 | Financial Health Check with pincode 247001, full flow | `money-financial-health-check.md` — 76/100 Good, 73.0 risk, 8 diagnosis slides |
| 21 Sep ~16:39 | Calculators — all screens, no Start SIP / Apply Now | `money-calculators.md` — 15 tiles; Term Insurance → Retirement; PPF lumpsum math |
| 21 Sep evening | Profile from avatar; Personal → Bank HDFC0000088; settings; hamburger **modules** (not URLs); Java not Python | `money-profile.md` + `Profile*Xpaths.java` |
| 21 Sep 18:53 | “What about FAQ and about airpay money” | FAQs open marketing homepage (F1). About content is hamburger About Us |
| 21 Sep 18:54 | Score everything we covered; put it all in the report | First `money-coverage-score.md` (overall 78 — later corrected) |
| 21 Sep 18:59 | Re-check from the start: is everything in the report? | Audit vs 837 dumps. Indian Stocks (26) and US Stocks (14) had been missed. Overall **82** |
| 22 Sep | This request: walk last 10 days, collect all reports, combine, send | This file |

**Reports that exist as MD (included below):**

1. Coverage scorecard + disk audit  
2. Profile  
3. Calculators  
4. Financial Health Check  
5. Tax Filing & Planning  

**Captured but never given a deep MD** (summarized in the scorecard only): Mutual Funds (328 dumps), Fixed Deposit (54), NPS (33), Indian Stocks (26), US Stocks (14).

**Not opened:** Digital Gold buy/sell, 2W/4W/Health/Life quotes, Credit Card, Business Loan, Notifications.

---

## How to read the rest

Each part below is the original report unchanged (same headings, defect IDs, artifact names). Part 1 is the scorecard; Parts 2–5 are the module analyses.

| Part | File | Score |
|---|---|---:|
| 1 | `money-coverage-score.md` | App-wide **82** |
| 2 | `money-profile.md` | **88** |
| 3 | `money-calculators.md` | **95** |
| 4 | `money-financial-health-check.md` | **92** |
| 5 | `money-tax-filing-and-planning.md` | **75** |

---



---

# Part 1 — Coverage scorecard and disk audit

# Airpay Money — Capture coverage scorecard

| | |
|---|---|
| App | airpay money (`com.finmapp.fininfinity`, STAGING **2.0.124 (193)**) |
| Device | RZCW724DGXZ, 1080 × 2340 |
| Date | 2026-09-21 |
| Method | One tap → `uiautomator dump` + screencap → Java XPaths. No Python. |
| Raw dumps | `target/money/manual-capture/uidump-*.{xml,png,parsed.txt}` |
| Score meaning | **100** = dumped + Java locators + reported, every in-scope control. Deliberate skips (PIN complete, Log Out confirm, web Login, real KYC/OTP, blog posts, footer) do **not** reduce the score. Product defects that we captured still count as covered. |

**Overall in-scope capture: 82 / 100** (re-checked against 837 dumps on disk, 2026-09-21)

The first scorecard under-counted **Indian Stocks** and **US Stocks** (dumps exist; they were listed as “not opened”). Mutual Funds dumps are **328**, not 296. Deep MD reports exist only for Profile, Calculators, FHC, and Tax — MF / FD / NPS / stocks are captured as dumps + Java/features, not as a written analysis.

---

## 1. Headline scores

| Module | Score | Dumps (png) | Java XPaths | Analysis report | Depth |
|---|---:|---:|---|---|---|
| **Profile** (avatar → settings) | **88** | 153 | `ProfileXpaths`, `ProfileDetailsXpaths`, `ProfileWebMenuXpaths` | [money-profile.md](money-profile.md) | Hub, all PROFILE rows, all GENERAL rows, hamburger modules |
| **Financial Calculators** | **95** | 99 | `CalculatorsHubXpaths`, `CalculatorScreenXpaths` | [money-calculators.md](money-calculators.md) | All 15 tiles, defaults + end of page, both FD tabs |
| **Financial Health Check** (native) | **92** | 69 | FinSight screens + `FinSightDiagnosisXpaths` | [money-financial-health-check.md](money-financial-health-check.md) | E2E to 76/100 + 8 diagnosis slides |
| **Mutual Funds** | **90** | 328 | 20+ `MutualFunds*Xpaths` | Cucumber `mutual_funds.feature` (**no MD report**) | Risk Q1–Q5, dashboard, curated, index, goals, SIP/goal calc, portfolio, tx, KYC prompt |
| **Tax Filing & Planning** | **75** | 41 | none this pass | [money-tax-filing-and-planning.md](money-tax-filing-and-planning.md) | Landing 20 folds, login email, drawer, Rewards, Basic Plan |
| **Fixed Deposit** | **85** | 54 (`fd` 33 + `fdx` 16 + `fd2` 5) | `FixedDepositsLandingXpaths`, `FdPlanBookingXpaths`, `Fixerra*` | Cucumber `fixed_deposit.feature` (**no MD report**) | Landing filters, Suryoday booking, Fixerra WebView to Aadhaar |
| **NPS** | **80** | 33 | `NpsLandingXpaths` + `NpsKyc*` | Cucumber `nps.feature` (**no MD report**) | Landing + KYC through PAN photo mismatch |
| **Indian Stocks** | **70** | 26 (`uidump-in-*`) | Home tile only | **none** | Search All/Equities/ETFs, TCS equity details + Buy, IRFC, ETF, holdings, orders (All/Placed/Completed/Failed) |
| **US Stocks (Stockal)** | **60** | 14 (`uidump-us-*`) | Home tile only | **none** | Stacks landing, login (empty/filled/password), signup country + empty error. No trade. |
| **Login / auth** | **70** | `uidump-pin-unlock`, `uidump-account-setup`, `uidump-after-pin` | `PinUnlock`, `Biometric`, `AccountSetup`, welcome/OTP | `login.feature` + login T&C/Privacy | Smoke path; consent T&C from welcome |
| **Home product hub** | **80** | `uidump-home`, `uidump-products` | `HomeXpaths`, `ProductsHubXpaths` | — | Tile locators; carousel 1–3 |
| **Digital Gold** | **15** | TX empty tab only | Home tile + Profile TX chip | — | No buy/sell flow |
| **Insurance (2W / 4W / Health / Life)** | **15** | FHC “See plans” screens exist | FHC plan screens | — | Product tiles not crawled |
| **Credit Card / Business Loan** | **5** | 0 | `LoanApplyScreen` stub | — | Not opened |
| **Notifications** | **5** | 0 | Home icon locator | — | Not opened |
| **FinSight tab (post-score home)** | **40** | `uidump-financial-health-score` | `FinSightHomeXpaths` | inside FHC report | Reassess / View Details locators; tab not re-crawled |

Dump prefix counts on disk (837 unique): `mf` 328, `prof` 153, `calc` 99, `fh` 69, `tax` 41, `nps` 33, `fd` 33, **`in` 26**, `fdx` 16, **`us` 14**, `fd2` 5, plus pin/home/products/nav.

---

## 2. Profile — item score (this week’s work)

Entry: Home avatar `Open settings`. Java: `ProfileXpaths` / `ProfileDetailsXpaths` / `ProfileWebMenuXpaths`. Report: `docs/analysis/money-profile.md`.

### 2.1 PROFILE SETTINGS — **93 / 100**

| # | Item | Score | What we captured | Gap |
|---|---|---:|---|---|
| 1 | Profile hub (header, avatar, both sections, help bar) | 100 | `uidump-prof-H1`, `-L-root`, `-M-scroll`, `-N-settings` | — |
| 2 | Change profile picture | 80 | Opens Photos picker (`-J-root`) | No crop/confirm |
| 3 | Personal Details read + edit | 95 | Name/parent/email/DOB/gender/marital/PAN; edit form; DOB wheel; marital sheet (5); gender Male/Female | Other not selected; Update not saved |
| 4 | Employment lock | 100 | Locked banner + Got it (`personal-08`, `-09`) | Product lock after KYC (F4) |
| 5 | Work Details | 95 | Accordion, org/company/designation search, no-result, year/month sheets, Via bank/Cheque, empty Submit | Experience not required; submit not filled with real job |
| 6 | Address Details | 95 | Permanent filled, Current empty, add form, pincode 247001, 11 residence types, empty Submit | Current never saved |
| 7 | Bank Account | 90 | Empty list, Add bank, HDFC search, IFSC **HDFC0000088**, verify form, empty validation, dummy submit | Dummy add returns empty list (F2) — captured |
| 8 | Document Details | 100 | ` My Documents` / No Document found | Empty is the live state |
| 9 | KYC Details | 100 | KYC VERIFIED, masked Aadhaar/PAN | Read-only |
| 10 | Transaction history | 100 | MF / Digital Gold / NPS chips; All / One Time / SIP / Redemption | Empty copy only |
| 11 | Contact us (in-app) | 90 | Prefill, subject, message, Attach → Photos | Send not tapped (by design) |

### 2.2 GENERAL SETTINGS — **82 / 100**

| # | Item | Score | What we captured | Gap |
|---|---|---:|---|---|
| 12 | Screen Lock | 80 | Toggle row dumped (`set-01`) | No a11y checked (A3) |
| 13 | Change PIN | 70 | Keypad 0–9, Backspace, “0 of 6” (`set-02`) | Not completed (by design). Hardware back → launcher (F3) |
| 14 | Utilities | 100 | Opens Financial Calculators hub | Internals in calculators report |
| 15 | Terms & Conditions | 90 | WebView (`set-05`) | Full legal scroll not every fold |
| 16 | Privacy Policy | 90 | WebView (`set-06`) | Same |
| 17 | Account Management | 100 | No Consent Found + Dismiss | — |
| 18 | Rate Us | 90 | 👎/👍, thumbs-down sheet, 3 star rows, optional text | Submit not tapped. Close can land Home (F10) |
| 19 | **FAQs** | **55** | Row locator + two opens (`set-12-faqs`, `faq-01-open`). Lands on **marketing homepage** | See §3 — no FAQ list in the app |
| 20 | **About airpay money** | **75** | Settings row on `faq-00-now`. Full About **page** via hamburger About Us (`web-01-about` + end/last) | Settings-row tap not dumped as its own prefix (same WebView chrome) |
| 21 | Follow us on | 60 | 4 NAF icons (IG/FB/LinkedIn/YT) | Destinations not opened (external) |
| 22 | App version | 100 | STAGING / 2.0.124 (193) | — |
| 23 | Log Out | 50 | Locator only | Confirm not opened (by design) |

### 2.3 Website hamburger (from Profile WebView) — **88 / 100**

Tap **Menu**, then the **module**. Do not open URLs / footer / web Login.

| # | Module | Score | Artifact | Note |
|---|---|---:|---|---|
| 24 | Drawer open/close | 100 | `web-08-menu`, `web-09-humburg` | FHC / Learn / Resources toggles |
| 25 | What is FHC | 90 | `web-11-what-fhc` | Hero + sample score; off-screen STEP copy |
| 26 | How it Works | 90 | `web-12-how-works` | STEP 1–3 visible |
| 27 | Start FHC (web) | 40 | `web-13-start-fhc` | Not entered — native FHC already 92 |
| 28 | Blogs | 85 | `web-07-blogs`, `-posts`, `-cards` | Listing + pager; posts not opened |
| 29 | About Us | 95 | `web-01-about`, `-end`, `-last` | This is the About airpay money **content** |
| 30 | Contact Us (web) | 90 | `web-03-contact`, `-end`, `-last` | Emails; lead form not filled |
| 31 | Testimonials | 90 | `web-05-testimonials`, `-end` | — |
| 32 | Web Login | 0 | Locator only | Not tapped (by design) |
| 33 | Footer / Grievance / Sachet / social | 0 | — | Not tapped (by design) |

---

## 3. FAQs and About airpay money (explicit)

These two General Settings rows are what was still in question.

### FAQs — 55 / 100 (destination covered; content missing because the app does not show FAQs)

| Check | Status |
|---|---|
| Row on General Settings | Yes — `Button cd=[FAQs]` `[68,1308][1013,1466]` on `uidump-prof-faq-00-now` |
| Java locator | `ProfileXpaths.FAQS` |
| Tapped from settings | Yes — `uidump-prof-set-12-faqs` and `uidump-prof-faq-01-open` |
| What actually opens | In-app WebView **marketing homepage**: “Your Journey to Financial Wellness Starts Here”, Start Financial Health Check, Download App, QR FAB. Tree on latest open is 3 nodes (chrome + opaque WebView). |
| Native FAQ accordion (Regulatory / Grievance) | **Does not appear.** `FaqsScreen.java` still looks for Capital-style FAQ copy; Money does not use that screen. |
| Homepage FAQ block / View All | Present off-screen in some earlier WebView DOM dumps; **not scrolled or expanded** this pass |
| Defect | **F1 High** — FAQs does not open FAQs |

Covered for automation: we can assert the row, tap it, and assert we are **not** on an FAQ heading. Not covered: expanding a real FAQ answer, because the settings entry never reaches one.

### About airpay money — 75 / 100

| Check | Status |
|---|---|
| Row on General Settings | Yes — `Button cd=[About airpay money]` `[68,1494][1013,1651]` on `faq-00-now` |
| Java locator | `ProfileXpaths.ABOUT_AIRPAY_MONEY` |
| About **content** captured | Yes — hamburger **About Us** `uidump-prof-web-01-about` (+ `-end`, `-last`): “Financial wellness, simplified for India”, Our Story, June 2024, stats (0+ downloads vs 1.5 lakh+ copy = **F8**) |
| Settings-row tap as its own dump | **No separate prefix.** Same WebView chrome as FAQs/T&C/Privacy; About Us module is the page we documented |
| Web Login / footer on that page | Not tapped |

Covered for automation: row locator + About Us heading `ProfileWebMenuXpaths.ABOUT_HEADING`. Residual gap: one dedicated dump after tapping the settings row (likely the same homepage or About page).

---

## 4. Other modules — what we actually did

### Financial Calculators — 95

All 15 hub tiles opened. Defaults + scrolled results. EMI and SIP edit sheets. FD By Deposit / By Target. Inflation year picker + Show More. Budget income sheet. Term Insurance tile **opens Retirement** (F1, verified twice). Sticky CTAs (`Start SIP`, `Apply Now`, …) locators recorded, journeys not entered.

### Financial Health Check — 92

Start Now → Personal (DOB, dependant 0–5, pincode 247001) → Income (loan type 7, validation) → Insurance Yes → Savings (investment type 11, goal icons) → Risk (I already know + Q1–Q5, 73.0) → Summary → **76/100 Good** → 8 diagnosis slides + Plan of Action. Not opened: See plans / Start SIP / share PDF.

### Mutual Funds — 90

Largest dump set (328). Risk intro + Q1–Q5 + result; dashboard folds; recommendation list + filters + HDFC search; fund details (returns, calculator, peer, holdings, AMC, objective, disclaimer, KYC gate); all goal types created; curated lists (Better than FD, High Returns, Short/Long Term, Tax Saving, Emergency, Bluechip, Better Than RD) each with search + 5 filter sheets + a fund; index (Nifty 50 / Mid / 100 / Small / Large / Global) same pattern; risk-profile edit; Goal Calculator + SIP Calculator sheets/sliders; empty Portfolio, Help WebView, Transactions tabs, Investor Profile. No standalone MD analysis (feature file is the inventory).

### Fixed Deposit — 85

Discover FD landing, senior toggle, year filters, category chips; Suryoday booking (tenure / payout / renewal / amount); Fixerra WebView (sort, tax saver, amount errors, yield, Aadhaar hand-off). Booking not completed.

### NPS — 80

Landing locators. KYC status, marital, employment, Aadhaar consent, Digilocker/OTP WebView, PAN form, PAN photo crop/picker, mismatch after submit. Later KYC steps not finished.

### Tax Filing & Planning — 75

ClearTax WebView landing (20 folds), login email empty/focus, hamburger PRODUCTS/RESOURCES/COMPANY, Rewards (`[object Object]` F9), File ITR Now → Basic Plan. No OTP, no Book an Expert, no Java xpaths this pass.

### Login / Home — 70 / 80

Welcome → mobile OTP smoke (`login.feature`). PIN unlock, biometric, account setup, email verify as screen objects. Guest consent T&C / Privacy features exist. Home tiles: Mutual Funds, NPS, Digital Gold, Fixed Deposit, US Stocks, Indian Stocks, 2 Wheeler, 4 Wheeler, Health, Life. Notifications icon not opened.

### Indian Stocks — 70 (dumps on disk; missing from first scorecard)

26 dumps: hub, Search (All / Equities / ETFs, TCS, no-results), Equity Details TCS with **Buy**, IRFC, ETF details, stock idea, portfolio Holdings, Orders tabs (All 5 / Placed 0 / Completed 2 / Failed 1) plus order detail (completed / rejected). **Buy not tapped.** No Java `*Xpaths` class. No MD report.

### US Stocks (Stockal) — 60 (dumps on disk; missing from first scorecard)

14 dumps: Stacks landing, Login (empty error, filled, password masked/visible, Forgot Password/Username), Sign up (country, empty error), Chrome tab `airpay.stockal.com`. No trade. No Java xpaths. No MD report. Some `uidump-in-hub-*` files actually show the same Stacks copy as US — treat hub dumps carefully.

---

## 5. Accessibility and defects already logged

| Report | A11y IDs | Functional IDs |
|---|---|---|
| Profile | A1–A12 (Follow-us NAF, Screen Lock unchecked, Change PIN no back, WebView overlay…) | F1–F14 (FAQs homepage, dummy bank silent fail, PIN back to launcher, About 0+ stats…) |
| Calculators | A1–A15 | F1–F15 (Term Insurance → Retirement, PPF lumpsum math…) |
| FHC | A1–A11 | through F14 |
| Tax | A1–A17 | F1–F11 |

---

## 6. Deliberate skips (do not treat as unfinished)

- Complete Change PIN or Log Out
- Send Contact Us / Rate Us / web lead forms
- Web Login / Sign up
- Blog article URLs, Popular Tags, footer (Grievance, Sachet, social)
- Web Start FHC (native FHC already done)
- Calculator sticky CTAs into purchase
- FHC See plans / share PDF
- Tax OTP / Book an Expert checkout
- FD/NPS/MF live payment or partner OTP
- Real bank / PAN / Aadhaar numbers

---

## 7. Not opened (still pulls the overall score)

| Area | Why 0–15 |
|---|---|
| Digital Gold buy/sell | Only Profile TX empty tab + Home tile |
| 2W / 4W / Health / Life quotes | Home tiles; FHC plan screens are diagnosis CTAs, not the product hub |
| Credit Card | Not opened |
| Business Loan | `LoanApplyScreen` exists; not crawled |
| Notifications | Icon only |
| Follow-us destinations | External apps |
| Settings FAQs accordion | App never shows it (F1) |
| Indian Stocks **Buy** / live order | Details dumped; Buy not tapped |
| US Stocks trade | Stopped at Stockal login/signup |

---

## 8. Automation artifacts

| Kind | Location |
|---|---|
| Profile locators | `airpay_money/.../screens/profile/ProfileHubScreen.java`, `ProfileDetailsScreen.java`, `ProfileWebMenuScreen.java` |
| Calculators | `CalculatorsHubXpaths.java`, `CalculatorScreenXpaths.java` |
| MF / FD / NPS | matching `*Xpaths.java` + `screens/product/*` |
| Capture features | `mutual_funds.feature`, `fixed_deposit.feature`, `nps.feature`, login + hamburger features |
| Deep analysis | `docs/analysis/money-profile.md`, `money-calculators.md`, `money-financial-health-check.md`, `money-tax-filing-and-planning.md` |
| This scorecard | `docs/analysis/money-coverage-score.md` |

---

## 9. Audit: is everything we captured in a written report?

**No.** Re-checked every dump prefix against the four MD analyses + this scorecard.

| On disk | In a deep MD report? | In Java XPaths? | In this scorecard now? |
|---|---|---|---|
| Profile 153 | Yes — `money-profile.md` | Yes | Yes |
| Calculators 99 | Yes — `money-calculators.md` | Yes | Yes |
| FHC 69 | Yes — `money-financial-health-check.md` | Partial (FinSight screens) | Yes |
| Tax 41 | Yes — `money-tax-filing-and-planning.md` | No | Yes |
| Mutual Funds 328 | **No MD** — only `mutual_funds.feature` | Yes | Yes (summary only) |
| FD + Fixerra 54 | **No MD** — only `fixed_deposit.feature` | Yes | Yes (summary only) |
| NPS 33 | **No MD** — only `nps.feature` | Yes | Yes (summary only) |
| Indian Stocks 26 | **No** — omitted from first scorecard | Home tile only | **Yes after this audit** |
| US Stocks 14 | **No** — omitted from first scorecard | Home tile only | **Yes after this audit** |
| Gold / insurance quotes / credit / loan / notifications | No dumps | tiles / stubs | Listed as not opened |

Profile inventory gaps that are now listed: `uidump-prof-faq-00-now`, `uidump-prof-faq-01-open`, `uidump-prof-web-13-start-fhc`.



---

# Part 2 — Profile

# Airpay Money — Profile — Deep Analysis

| | |
|---|---|
| Module | Home avatar → **Profile** (Profile settings + General settings) |
| Tech | Native Flutter for Profile / settings / contact / bank / KYC. In-app **WebView** for FAQs, About, T&C, Privacy, and the website hamburger. Package `com.finmapp.fininfinity/.MainActivity` |
| Device | RZCW724DGXZ, 1080 × 2340 px |
| Capture method | `adb shell uiautomator dump` + `screencap`, parsed to `*.parsed.txt` |
| Raw artifacts | `target/money/manual-capture/uidump-prof-*.{xml,png,parsed.txt}` |
| Scope | Personal Details through Bank Account (dummy IFSC `HDFC0000088`), Documents, KYC, Contact us, Transaction history, Profile/General settings, Follow us on, and every hamburger **module**. Financial Calculators already covered in `money-calculators.md` — Utilities was opened only to confirm it routes there. |
| Data entered | Dummy bank number, IFSC `HDFC0000088` (Noida Sec 18), pincode `247001`. No real sensitive data, PIN change, Log Out confirm, Contact Send, Rate Us submit, or web Login. |
| Xpaths | `airpay_money/.../screens/profile/ProfileHubScreen.java`, `ProfileDetailsScreen.java`, `ProfileWebMenuScreen.java` |

---

## 1. Capture inventory

### 1.1 Profile hub

| Screen / state | Artifact prefix |
|---|---|
| Profile root, header + PROFILE SETTINGS | `uidump-prof-H1`, `-I-root`, `-K-root`, `-L-root`, `-help-01` |
| Profile scrolled to GENERAL SETTINGS | `uidump-prof-M-scroll` |
| Settings end (Follow us / version / Log Out) | `uidump-prof-N-settings` … `-S-settings` |

### 1.2 Personal / Work / Address

| Screen / state | Artifact prefix |
|---|---|
| Personal Details read-only | `uidump-prof-personal-01-top` |
| Personal Details edit | `uidump-prof-personal-02-edit` |
| DOB wheel | `uidump-prof-personal-04-dob`, `-05-dob-cancel` |
| Marital status sheet | `uidump-prof-personal-06-marital`, `-07-marital-dismiss` |
| Employment locked banner | `uidump-prof-personal-08-employment`, `-09-gotit`, `-10-menu` |
| Gender Male / Female | `uidump-prof-personal-11-gender-female`, `-12-gender-male` |
| Work Profile accordion | `uidump-prof-work-01-top`, `-02-expand` |
| Work form + org/designation/company pickers | `uidump-prof-work-05-orgtype` … `-11-company` |
| Org-type search / no-result | `uidump-prof-work-06-orgtype-search`, `-07-orgtype-noresult` |
| Experience year / month sheets | `uidump-prof-work-14-exp-year`, `-15-exp-month` |
| Work empty Submit validation | `uidump-prof-work-16-submit-empty` |
| Address Details (permanent filled, current empty) | `uidump-prof-addr-01-top` |
| Add Residential Address + pincode 247001 | `uidump-prof-addr-02-add`, `-03-pincode` |
| Residence type picker (11 values) | `uidump-prof-addr-04-residence`, `-05-residence-scroll` |
| Address empty Submit validation | `uidump-prof-addr-07-submit-empty` |

### 1.3 Bank / Documents / KYC / Contact / Transactions

| Screen / state | Artifact prefix |
|---|---|
| Select Bank Account (empty list + Add bank) | `uidump-prof-bank-18-result` |
| Select your Bank / HDFC search | `uidump-prof-bank-15-add`, `-G-root` |
| IFSC `HDFC0000088` → Noida Sec 18 | `uidump-prof-bank-16-ifsc88` |
| Verify Bank Account form | `uidump-prof-bank-17-form`, `-12-scrolled`, `-14-verify-result` |
| Empty holder/account validation | `uidump-prof-bank-10-submit-empty` |
| Dummy account submit (silent empty list) | `uidump-prof-bank-18-result` |
| My Documents empty | `uidump-prof-docs-01-top` |
| KYC Details, verified, masked IDs | `uidump-prof-docs-02-icon` |
| Contact Us form (prefilled name/email/phone) | `uidump-prof-contact-02-subject`, `-kyc-01-top` (misnamed dump; this is Contact) |
| Attach File → Google Photos picker | `uidump-prof-contact-04-attach`, `-J-root` |
| Transaction History tabs | `uidump-prof-tx-01` … `-07-tabs-end` |

### 1.4 General settings

| Screen / state | Artifact prefix |
|---|---|
| Screen Lock (no a11y checked state) | `uidump-prof-set-01-screenlock` |
| Change PIN keypad (not completed) | `uidump-prof-set-02-changepin` |
| Hardware back from Change PIN → launcher | `uidump-prof-set-03-pin-cancel` |
| Utilities → Financial Calculators hub | `uidump-prof-set-04-utilities` |
| Terms & Conditions WebView | `uidump-prof-set-05-tnc` |
| Privacy Policy WebView | `uidump-prof-set-06-privacy` |
| Account Management "No Consent Found" | `uidump-prof-set-07-acctmgmt` |
| Rate Us thumbs | `uidump-prof-set-08-rateus` |
| Thumbs-down feedback sheet | `uidump-prof-set-09-ratedown`, `-10-ratestar` |
| Rate Us close → Home | `uidump-prof-set-11-rateclose` |
| FAQs → marketing homepage (not FAQ content) | `uidump-prof-set-12-faqs`, `uidump-prof-faq-00-now` (settings row), `uidump-prof-faq-01-open` |

### 1.5 Website hamburger (tap Menu, then each **module**)

| Screen / state | Artifact prefix |
|---|---|
| Drawer closed / open | `uidump-prof-web-08-menu`, `-09-humburg` |
| FHC module expanded | `uidump-prof-web-10-fhc-mod`, `-12a-fhc` |
| Learn / Resources expanded | `uidump-prof-set-15-resources`, `-web-04-menu`, `-web-06-learn` |
| About Us | `uidump-prof-web-01-about`, `-01-about-end`, `-01-about-last` |
| Contact Us (web) | `uidump-prof-web-03-contact`, `-03-contact-end`, `-03-contact-last` |
| Testimonials | `uidump-prof-web-05-testimonials`, `-05-testimonials-end` |
| Blogs listing / tags / cards / pager | `uidump-prof-web-07-blogs`, `-07-blogs-posts`, `-07-blogs-cards` |
| What is FHC | `uidump-prof-web-11-what-fhc` |
| How it Works | `uidump-prof-web-12-how-works` |
| Start FHC (web landing only) | `uidump-prof-web-13-start-fhc` |

Not opened on purpose: web **Login**, footer links (Grievance, Sachet, Instagram…), blog article URLs. Web Start FHC was dumped at landing only — native FHC is already in `money-financial-health-check.md`.

---

## 2. Navigation map

```
Home → Button cd=[Open settings]  (avatar / Open settings)
   │
   ├─ header  ImageView cd=[Change profile picture]
   │          View cd=[Shubham Verma\n+91 79068 62513\nVerified\n…email…\nVerified]
   │
   ├─ PROFILE SETTINGS
   │     Button  Personal Details
   │     Button  Work Details          → title "Work Profile" accordion → "Add Your Work Details"
   │     Button  Address Details       → Current (empty) + Permanent (filled) + add form
   │     Button  Bank Account          → Select Bank Account → Add bank → HDFC → IFSC
   │     Button  Document Details      → " My Documents" / No Document found!
   │     Button  KYC Details, Verified → KYC VERIFIED, masked Aadhaar/PAN
   │     ImageView  Transaction history
   │     ImageView  Contact Us
   │
   ├─ Button  How can we help you?     (sticky; same as help entry)
   │
   └─ GENERAL SETTINGS
         Button  Screen Lock           (toggle, no checked state)
         Button  Change PIN            (keypad; no in-app back)
         Button  Utilities             → Financial Calculators hub
         Button  Terms & Conditions    → WebView
         Button  Privacy Policy        → WebView
         Button  Account Management    → "No Consent Found"
         Button  Rate Us               → 👎 / 👍 sheet
         Button  FAQs                  → WebView marketing homepage (not FAQs)
         Button  About airpay money    → WebView
         View    Follow us on          + 4 NAF ImageViews
         View    STAGING / App Version 2.0.124 (193)
         View    Log Out               (confirm not tapped)

WebView chrome (T&C / Privacy / FAQs / About / hamburger destinations)
   NAF back chevron  [56,123][169,219]
   Button text=[Menu]  → drawer
        View cd=[Start Financial Health Check]
        TextView Financial Health Check  (toggle)
             View cd=[What is FHC]
             View cd=[How it Works]
             Button text=[Start FHC]
        TextView Learn  (toggle)
             View cd=[Blogs]
        TextView Resources  (toggle)
             View cd=[About Us]
             View cd=[Contact Us]
             View cd=[Testimonials]
   Button cd=[Login]     — not tapped
   Button text=[Download airpay money App QR Code]  FAB — avoid when swiping
```

---

## 3. Screen-by-screen element inventory

### 3.1 Profile hub (`uidump-prof-H1`, `-L-root`, `-M-scroll`, `-N-settings`)

- Header combines name, `+91 79068 62513`, email, and two `Verified` chips in one `content-desc`.
- `Change profile picture` is an `ImageView` covering the avatar (`[68,188][1013,553]` when the header is expanded; `[68,95][1013,181]` when collapsed). Tapping it opens the system Photos picker (`uidump-prof-J-root`) — no crop confirm was taken.
- PROFILE SETTINGS rows are `Button`s except Transaction history and Contact Us (`ImageView`).
- KYC row accessible name is `KYC Details, Verified`.
- Sticky `How can we help you?` sits at `[56,2257][1024,2340]` and remains visible while scrolling into GENERAL SETTINGS.
- GENERAL SETTINGS: Screen Lock, Change PIN, Utilities, Terms & Conditions, Privacy Policy, Account Management, Rate Us, FAQs, About airpay money.
- Follow us on: four clickable `ImageView`s with **empty** `content-desc` at x `146 / 349 / 551 / 754`. Footer of the marketing site labels the same four as Instagram, Facebook, LinkedIn, YouTube.
- Version: `STAGING` / `App Version 2.0.124 (193)`.
- Log Out is a `View` (not a Button) at `[56,1851][1024,2045]`. Confirm dialog was not opened.

### 3.2 Personal Details (`uidump-prof-personal-01-top`, `-02-edit`)

Read-only grid: Name `Shubham Verma`, Mobile `+91 79068 62513`, Father's name `Kailash Verma`, Mother's name `Sunita Devi`, Email, DOB `16 May 1994`, Employment `Salaried`, Marital `Single`, Gender `Male`, PAN `Xxxxxx654R`. Pencil `Button cd=[Edit personal details]` at `[833,1375][968,1510]`.

Edit form: `EditText` hints Full name / Father's name / Mother's name / Mobile Number; email is a non-clickable `View`; DOB is a compound `View`; gender is three `ImageView`s Male / Female / Other; marital and employment are compound `View`s; sticky `Update`.

DOB wheel: Cancel / Done plus three `SeekBar`s (`1994`, `May`, `16`). Marital sheet: Single, Married, Widowed, Divorced, Separated. Employment tap shows a **locked** banner: "Your financial accounts has been created with our partners, & your profile has been verified. These details cannot be edited further…" with `Got it`. Grammar: "has been" / "accounts".

### 3.3 Work Details (`uidump-prof-work-01-top` … `-16-submit-empty`)

Hub row title is **Work Details**; the screen title is **Work Profile** (empty accordion). Expanding it retitles to **Add Your Work Details**.

Fields: Select company / organization type / designation (searchable full-screen lists), Monthly Income, Work Email, Experience in current company (`0 year` / `0 month`), Total experience (same), Mode of salary (`Via bank` radio **checked**, `Cheque` unchecked). Radios are NAF.

Org-type search concatenates queries (`Software` + `zzzzz` → `Softwarezzzzz`) and no-results is an **empty list with no message**. Year sheet uses singular `1 year` / `2 year` for every value; same for months.

Empty Submit: `Please select company` / `organization type` / `designation`, `Please enter Monthly Income`, `Please enter work email`. Experience and salary mode are not required.

### 3.4 Address Details (`uidump-prof-addr-01-top` … `-07-submit-empty`)

Current Address section is empty. Permanent Address is filled: `S/O: Kailash Verma 5 ITC Road`, `Babuji Nagar Saharanpur`, State `Uttar Pradesh`, City `Saharanpur`, Pincode `247001`. Landmark has a label and no value. The pencil is mislabelled **`Edit personal details`**. A second `Address Details` card at `[0,1285][1080,1791]` is the add entry.

Add form title **Residential Address**: Flat/House, Area/locality, Landmark, Pincode (city/state auto-fill), Mobile, Residence type. Pincode `247001` fills city `Saharanpur` / State `Uttar Pradesh` in the `EditText` hint. Ownership picker (11): Owned-by-self spouse, Owned-by-parent sibling, Rented staying alone, Rented with family, Rented with friends, Company provided, Hostel, Leased, Paying guest, Bachelor rented accommodation, Mortgage.

Empty Submit (with pincode already filled): Please enter Flat…, Area…, landmark, mobile number, Please select your ownership.

### 3.5 Bank Account (`uidump-prof-bank-*`)

Select Bank Account: empty list, `Add bank`, disabled-looking `Verify` (`clk=false`). Add bank → Select your Bank (search). Typing `HDFC` lists `HDFC Bank`. Branch search `HDFC0000088` returns `G-28 & G-29 SECTOR 18 NOIDA … 201301 (HDFC0000088)`.

Verify Bank Account shows bank + IFSC, holder name (prefilled `Shubham Verma`), account number. Empty Submit: `Please enter Bank account holder name`, `Please enter Bank account`. Dummy number `12345678901234988654` + Add bank returned **silently to the empty Select Bank Account list** — no success toast, no row.

### 3.6 Documents and KYC

My Documents: title has a leading space (` My Documents`), `No Document found!`. KYC Details: `KYC VERIFIED`, Name as per PAN `Shubham Verma`, Aadhaar `XXXXXXXX5752`, PAN `XXXXXX654R`. Read-only.

### 3.7 Contact Us (in-app)

Title Contact Us. Prefills Full Name, Email, Phone (`7906862513`) with `+91 ` prefix. Subject hint `Subject\nWhat do you want to convey` (NAF). Message NAF. Attach File opens Google Photos. `Send Message` was not tapped.

### 3.8 Transaction History

Title Transaction History. Product chips Mutual Funds / Digital Gold / NPS. MF sub-tabs All / One Time / SIP / Redemption (All appears after switching products). Empty: `No Transaction` / `We don't see any transaction records from history`.

### 3.9 General settings behaviour

| Row | What happened |
|---|---|
| Screen Lock | Tap does not expose `checked`/`selected`. Visual state only. |
| Change PIN | `Enter your New PIN` / `Create a unique 6-digit PIN` / `MPIN entered: 0 of 6 digits` + Digit 0–9 and Backspace. **No in-app back.** Hardware back left the app (`uidump-prof-set-03-pin-cancel` is the launcher). |
| Utilities | Opens the native Financial Calculators hub (already analysed). |
| T&C / Privacy | In-app WebView. Use the chevron `[56,123][169,219]`, not hardware back. |
| Account Management | Overlay `No Consent Found` + Dismiss. |
| Rate Us | `Enjoying the airpay money app?` with 👎 / 👍. 👎 → `We're sorry to hear that` + 3×5 unlabelled star rows (KYC, App Speed, Payment) + optional feedback + Submit Feedback (not submitted). Close (NAF) can dump the user on Home. |
| FAQs | WebView **marketing homepage**, not an FAQ list. Re-opened 2026-09-21 (`uidump-prof-faq-01-open`): same hero “Your Journey to Financial Wellness Starts Here”. Native FAQ accordion never appears. Score **55/100** — row + destination covered; FAQ content does not exist on this entry (F1). |
| About airpay money | Settings row dumped on `uidump-prof-faq-00-now`. About **content** is hamburger About Us (`web-01-about`): “Financial wellness, simplified for India”. Score **75/100**. |
| Follow us on | Four unlabelled icons. |
| Log Out | Locator captured; confirm not opened. |

### 3.10 Website hamburger modules

Drawer: orange **Start Financial Health Check**, then three toggles (Financial Health Check / Learn / Resources). Toggles are `TextView` + `Image cd=[Toggle]` — the row itself is often not `clickable=true`; tap the label/toggle coordinates.

| Module | Landing |
|---|---|
| What is FHC | Hero "Your Finances, clearly assessed", sample score card Aarav Mehta 63/100 Good, sticky Start Financial Health Check. Off-screen: What is Financial Health Check?, STEP1–5 copy. |
| How it Works | "How we turn your numbers into your Financial Wellness Score", STEP 1 Tell Us the Basics, STEP 2 Income & Expenses, STEP 3… |
| About Us | "Financial wellness, simplified for India", Our Story, launched June 2024, stats cards showing **0+** App Downloads / MAU despite hero copy "1.5 lakh+". |
| Contact Us (web) | General Support `kabir@airpay.money`, `020 6911 0300`; Credit Cards HDFC / novio emails; Corporate `connect@airpay.money`; amplify lead form (not filled). |
| Testimonials | "Trusted by 1.5 lakh+ Users Across India", "What Our Customers Say", 5.0 stars. |
| Blogs | "airpay money Blog", Popular Tags, category chips, article cards, pager Prev / 1 / 2 / Next. First card "10 Common Mutual Fund Jargons…". Article URLs were **not** opened (tap the Blogs **module**, not deep links). |

WebView dumps always include a hidden login overlay (`Welcome to airpay money`, `Login / Sign up`) with non-zero bounds even when the screenshot shows only the page. Do not tap those coordinates. QR FAB `[891,2110][1035,2254]` sits on the scroll path — swipe at **x = 200**.

---

## 4. Accessibility

| ID | Severity | Issue |
|---|---|---|
| A1 | High | Follow-us icons: four `NAF` `ImageView`s, `content-desc=''`. |
| A2 | High | Rate Us thumbs use emoji-only names `👎` / `👍`. Feedback stars are NAF Buttons with no value. |
| A3 | High | Screen Lock has no `checked`/`selected`. |
| A4 | High | Change PIN has no back/close in the a11y tree. Hardware back exits the app. |
| A5 | High | WebView Menu / Close / many CTAs use `text=` with empty `content-desc`. Login overlay and closed drawer nodes stay in the dump with stale or `[0,0]` bounds. |
| A6 | Medium | Contact Us and Transaction history on the hub are `ImageView`s while sibling rows are `Button`s. |
| A7 | Medium | Address pencil is `Edit personal details`. Document title has a leading space (` My Documents`). |
| A8 | Medium | Work salary radios are NAF; only the wrapping `View` is named. |
| A9 | Medium | WebView in-app back chevron is NAF (`[56,123][169,219]`). |
| A10 | Medium | Two Home header buttons share `Menu option` (also in `HomeXpaths`). |
| A11 | Low | Gender options are `ImageView`s; marital/employment selected state is only in the compound `content-desc`. |
| A12 | Low | Bank `Verify` is not clickable on the empty list. Add bank after dummy submit gives no announcement. |

---

## 5. Functional and content defects

| ID | Severity | Defect | Evidence |
|---|---|---|---|
| F1 | **High** | **FAQs** opens the marketing homepage, not FAQs. | `uidump-prof-set-12-faqs` WebView; later dumps show homepage copy "Your Journey to Financial Wellness Starts Here" |
| F2 | **High** | Dummy **Add bank** with HDFC0000088 + dummy account number returns to an **empty** Select Bank Account with no error and no row. | `uidump-prof-bank-17-form` → `-18-result` |
| F3 | **High** | **Change PIN** has no in-app back. Hardware back lands on the **launcher**. | `uidump-prof-set-02-changepin` then `-03-pin-cancel` |
| F4 | High | Personal **employment type** is locked after KYC with copy "Your financial accounts **has** been created…". | `uidump-prof-personal-08-employment` |
| F5 | Medium | Address edit affordance is labelled **Edit personal details**. Current Address is an empty heading with no empty-state text. | `uidump-prof-addr-01-top` |
| F6 | Medium | Work org-type **no-results** search shows a blank list (no "no results" message). Search concatenates successive `input text` (`Softwarezzzzz`). | `uidump-prof-work-07-orgtype-noresult` |
| F7 | Medium | Experience pickers use singular **year** / **month** for every count (`2 year`, `5 month`). Sheets have no title. | `uidump-prof-work-14-exp-year`, `-15-exp-month` |
| F8 | Medium | About Us stats render **0+** downloads / MAU while the same site claims 1.5 lakh+ users. | `uidump-prof-web-01-about` |
| F9 | Medium | Profile Contact Us and web Contact Us are different products (in-app ticket form vs marketing emails). FAQs does not deep-link to either. | `uidump-prof-contact-02-subject` vs `uidump-prof-web-03-contact` |
| F10 | Medium | Rate Us close (NAF X) can leave Profile and land on **Home**. | `uidump-prof-set-11-rateclose` |
| F11 | Low | Transaction History empty copy: "We don't see any transaction records from history". | `uidump-prof-tx-02-gold` |
| F12 | Low | Bank Verify on an empty list is present but not clickable. | `uidump-prof-bank-18-result` |
| F13 | Low | WebView login overlay is in the a11y tree on every hamburger page and can steal taps if coordinates are taken from the dump instead of the screenshot. | Every `uidump-prof-web-*` |
| F14 | Low | Document title leading space (` My Documents`). KYC dump was also saved as `uidump-prof-kyc-01-top` while that file is actually Contact Us. | `uidump-prof-docs-01-top`, `-kyc-01-top` |

---

## 6. Automation notes for this module

- **Entry.** Tap Home `Open settings` (avatar). Do not tap random screen coordinates — the Profile icon is the module. Same rule on the website: tap **Menu**, then the **module** (What is FHC, Blogs, About Us…). Do not open article/footer/Login URLs.
- **Flutter back.** Prefer the in-app chevron (`Back` or NAF `[56,123][169,219]` in WebView). Hardware back from Change PIN and from WebView can exit the app.
- **WebView swipes.** Use `x=200` so the QR FAB is not hit. Wait 3–4s after each module tap; dumps include off-screen DOM.
- **Do not submit.** Contact Send, Rate Us Submit Feedback, Log Out confirm, Change PIN digits, web Login / Sign up, Start FHC (web) beyond the landing copy.
- **Bank.** Search `HDFC` → `HDFC Bank` → IFSC `HDFC0000088`. Dummy account is safe; expect silent empty list (F2).
- **Locked personal fields.** Employment type shows Got it; do not expect Update to persist KYC-locked values.
- **Locators.** Native screens: `content-desc` on `Button`/`View`. WebView hamburger: mix of `text='Menu'` and `content-desc='About Us'`. Follow-us and Rate Us stars need index/bounds.

---

## 7. Out of scope (explicit)

- Completing PIN change or Log Out.
- Sending Contact Us or Rate Us feedback.
- Tapping web Login.
- Opening blog posts, Popular Tags, or footer links (Grievance Redressal, Sachet Portal, social).
- Starting web FHC (native FHC is `docs/analysis/money-financial-health-check.md`).
- Financial Calculators internals (`docs/analysis/money-calculators.md`).

---

## 8. Coverage score (Profile only)

Full app scorecard: [`money-coverage-score.md`](money-coverage-score.md).

| Slice | Score |
|---|---:|
| PROFILE SETTINGS (personal → KYC, contact, TX) | **93** |
| GENERAL SETTINGS | **82** |
| Website hamburger modules | **88** |
| **Profile module overall** | **88** |

| Row still asked about | Score | Evidence |
|---|---:|---|
| FAQs | 55 | `uidump-prof-set-12-faqs`, `uidump-prof-faq-01-open` — homepage, not FAQs |
| About airpay money | 75 | Settings row `uidump-prof-faq-00-now`; page `uidump-prof-web-01-about` |



---

# Part 3 — Financial Calculators

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
| Xpaths | `airpay_money/.../screens/calculators/CalculatorsHubScreen.java`, `CalculatorScreen.java` |

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



---

# Part 4 — Financial Health Check

# Airpay Money — Financial Health Check (FHC) — Deep Analysis

| | |
|---|---|
| Module | Personal Finance → Financial Health Check |
| Tech | Native Flutter (no WebView) inside `com.finmapp.fininfinity/.MainActivity` |
| Device | RZCW724DGXZ, 1080 × 2340 px |
| Capture method | `adb shell uiautomator dump` + `screencap`, parsed to `*.parsed.txt` |
| Raw artifacts | `target/money/manual-capture/uidump-fh-*.{xml,png,parsed.txt}` (58 states) |
| Data entered | Pincode `247001` as requested, plus non-sensitive sample amounts (income 1,00,000 / expenses 40,000 / loan 5,00,000 / insurance 12,000 & 15,000 / bank balance 2,50,000 / PPF 1,00,000). No PAN, OTP, card or bank credentials. |
| Outcome | Flow completed end to end: FHC score **76/100 "Good"**, risk profile **73.0 "Moderately Aggressive"**, net worth **₹-1.50L**, 8-slide diagnosis report reached. |

---

## 1. Capture inventory

| Screen / state | Artifact prefix |
|---|---|
| Money Home entry point (FHC card + Start Now) | `uidump-fh-state` |
| Step 1 Personal Details | `uidump-fh-01-entry` |
| Date of Birth wheel picker | `uidump-fh-02-dob-picker` |
| Dependant dropdown, top / mid / end | `uidump-fh-03-dependant`, `-04-dependant-scrolled`, `-05-dependant-end` |
| Personal Details, empty pincode validation | `uidump-fh-06-next-empty-pincode` |
| Pincode 247001 → city/state auto-filled | `uidump-fh-07-pincode-filled` |
| Step 2 Income & Expense (empty / filled) | `uidump-fh-08-income-expense`, `-09-income-filled` |
| Running loan = Yes | `uidump-fh-10-loan-yes` |
| Add Loan Details (empty) | `uidump-fh-11-add-loan` |
| Loan Type dropdown, top / end | `uidump-fh-12-loan-type`, `-13-loan-type-end` |
| Add Loan, all-empty validation | `uidump-fh-14-loan-add-empty` |
| Add Loan, progressive fill | `-15-loan-principal`, `-16-loan-emi`, `-17-loan-filled` |
| Loan added to Income & Expense | `uidump-fh-18-loan-added` |
| Step 3 Insurance Details (No / Yes) | `uidump-fh-19-insurance`, `-20-insurance-yes` |
| Insurance progressive fill | `-21-health-filled`, `-22-term-premium`, `-23-insurance-filled` |
| Step 4 Savings & Investments | `uidump-fh-24-savings`, `-25-investment-yes` |
| Add Investment Details | `uidump-fh-26-add-investment` |
| Investment Type dropdown, top / end | `-27-investment-type`, `-28-investment-type-end` |
| Investment filled + goal question | `uidump-fh-29-investment-filled` |
| Goal = Yes → New Goal / icon carousel | `-30-goal-yes`, `-31-new-goal`, `-32-goal-icons` … `-35-goal-icons-4` |
| Existing Goals empty state | `uidump-fh-38-existing-goals` |
| Investment added to Savings step | `uidump-fh-39-investment-added` |
| Step 5 Risk Profile intro | `uidump-fh-40-risk-profile`, `-46-back-to-intro` |
| "I already know" manual picker, all 5 options | `-41-already-know`, `-42`…`-45` |
| Risk questionnaire Q1–Q5 | `uidump-fh-47-risk-q1` … `-53-risk-q5` |
| Q1 Next with no answer (validation) | `uidump-fh-48-risk-q1-novalidate` |
| Risk Profile result gauge (73.0) | `uidump-fh-54-risk-result` |
| Result fed back into capability picker | `uidump-fh-55-health-score` |
| Summary Details (top / scrolled) | `-56-after-risk-next`, `-57-summary-scrolled` |
| FHC score 76/100 | `uidump-fh-58-score` |
| Diagnosis carousel, slides 1–8 | `-59-diagnosis`, `-60-diagnosis-gaps`, `-62-diag-03` … `-67-diag-08` |
| Plan of Action, scrolled (all 6 actions) | `uidump-fh-68-plan-scrolled` |

---

## 2. Navigation map

```
Money Home → Personal Finance → card "Financial Health Check"
   Button "Start Now"  [734,1932][998,2067]
     │
     ├─ 1  Personal Details          stepper: Personal Details · Income & Expense · Insurance Details
     │      Name (prefilled), Date of Birth → 3-wheel bottom sheet (year/month/day, Cancel/Done),
     │      gender Male/Female/Other, Dependant dropdown (0–5), Pincode → auto City + State
     │
     ├─ 2  Income & Expense Details
     │      Net Monthly Income, Monthly Household Expenses,
     │      "Do you have any running Loan?" Yes/No
     │        └─ Yes → "Add Loan" → Add Loan Details
     │             Loan Type (7 options), Principal Amount, EMI, Interest Rate (in %) → "Add"
     │             → loan card with unlabelled edit/delete icons
     │
     ├─ 3  Insurance Details          stepper: Insurance Details · Savings and Investments Details
     │      "Do you have any Insurance ?" Yes/No  (defaults to No)
     │        └─ Yes → Health Insurance {Annual Premium Paid, Total Sum Insured}
     │                 Term Insurance   {Annual Premium Paid, Total Sum Insured}
     │
     ├─ 4  Savings & Investments      stepper: Savings and Investments Details · Risk Profile
     │      Total Bank Balance (All Banks), "Are you doing any Investment?" Yes/No
     │        └─ Yes → "Add Investment" / "Add New" → Add Investment Details
     │             Investment Type (11 options), Current Value, Monthly Deposit Amount
     │             └─ "Do you wish to associate a goal with this <type> investment?" Yes/No
     │                  ├─ No  → "If no goal is mapped then this investment will be
     │                  │         considered as an Retirement Fund."
     │                  └─ Yes → [Existing Goals] | [New Goal]
     │                            Existing Goals → empty state "You don't have any existing goals"
     │                            New Goal → 10-icon horizontal carousel + Goal name + Goal amount
     │
     ├─ 5  Risk Profile intro  "Let's assess your risk taking capability"
     │        ├─ [Lets do risk profile] → Question 1/5 … 5/5 → "Your Risk Profile" gauge (73.0)
     │        │                           → returns to the capability picker, pre-selected
     │        └─ [I already know]        → "Let us know your risk capability"
     │                                     Aggressive / Moderately Aggressive / Balanced /
     │                                     Moderately Conservative / Conservative
     │
     ├─ 6  Summary Details  (My Personal Details, My Income and Expenses, My Insurance,
     │                       My Investments, My Loans, My Risk Appetite)
     │
     ├─ 7  Financial Health Check score  76 / OUT OF 100 / "Good" → [See the diagnosis]
     │
     └─ 8  Diagnosis — 8-slide horizontal carousel
            01 THE DIAGNOSIS · Your Money Diagnosed (net worth, assets, liabilities)
            02 UNFUNDED PROTECTION (term / health / emergency progress bars)
            GAP 01 OF 03 · INSURANCE PILLAR — Term Insurance    → [See plans]
            GAP 02 OF 03 · INSURANCE PILLAR — Health Insurance  → [See plans From ₹650/mo]
            GAP 03 OF 03 · LIQUIDITY PILLAR — Emergency Funds   → [Start SIP]
            03 NET WORTH BREAKDOWN
            04 YOUR PLAN OF ACTION (3 Moderate + 3 Low priority) → [Reassess]
            YOU'RE DONE · Save. Share. Act. → Share on WhatsApp / Download the PDF / Share on Email
```

---

## 3. Screen-by-screen element inventory

### 3.1 Personal Details (`uidump-fh-01-entry`)

- `Button cd=[Back]` `[51,118][143,230]`
- Title `View cd=[Personal Details]`; stepper `Personal Details` / `Income & Expense` / `Insurance Details` (the third is clipped to 19 px wide)
- `EditText hint=[Name] txt=[Shubham Verma]` — prefilled from the profile
- `View cd=[Date of Birth\n16 May 1994]` → bottom sheet with three `SeekBar` wheels (`1994`, `May`, `16`) plus `Button cd=[Cancel]` / `cd=[Done]` and a full-screen `View cd=[Dismiss]`
- Gender: three `ImageView` nodes `cd=[Male]` / `[Female]` / `[Other]`, no selected state
- `View cd=[Dependant (Spouse/ Kids/ Parents)\n0]` → dropdown with options **0–5**
- One `NAF EditText` whose hint carries the whole block: `hint=[Pincode\ncity name\nNA\nState\nNA]`. After `247001`: `Saharanpur` / `Uttar Pradesh`
- `Button cd=[Next]` `[56,2135][1024,2270]`

### 3.2 Income & Expense (`uidump-fh-08`, `-18`)

- Title `Income & Expense \nDetails`
- `NAF EditText hint=[Your Net Monthly Income]`, `NAF EditText hint=[Your Monthly Household Expenses]`
- Helper text merged into the parent container: `Monthly family expenses (Excluding\nInsurance Premium & loan EMIs)\nDo you have any running Loan?`
- Yes/No `RadioButton` pair (both `NAF`)
- After Yes: `View cd=[Add Loan]` `[782,1178][1024,1279]`
- Loan card after add: `Personal Loan / Principle Amount ₹5,00,000 / EMI ₹10,000 / Interest rate 12.0 %` plus two `NAF View` icons at `[563,1352]` and `[693,1352]` (edit and delete, both unlabelled)

**Loan Type — 7 options:** Home Loan, Two Wheeler Loan, Education Loan, Gold Loan, Personal Loan, Four Wheeler Loan, Loan Against Property

### 3.3 Insurance Details (`uidump-fh-19`, `-20`)

- `Do you have any Insurance ?` Yes/No, defaults to **No** (so the whole section is collapsed by default)
- After Yes, four `NAF EditText` fields with only two distinct hints: `Annual Premium Paid` and `Total Sum Insured`, each appearing twice. The `Health Insurance` / `Term Insurance` group headings live in the parent container's merged `content-desc`, not on the fields.

### 3.4 Savings & Investments (`uidump-fh-24`, `-39`)

- `NAF EditText hint=[Savings Account Details\nTotal Bank Balance (All Banks)]` — heading and label merged into one hint
- `Are you doing any Investment?` Yes/No
- After add: `My Investments` + `View cd=[Add New]`, and a card `Public  provident  fund\nThis investment is considered as Retirement Fund.` with `Current Value ₹ 1,00,000`. The visible trash icon on that card is **not present in the accessibility tree at all**, and Monthly Deposit Amount is not shown on the card.

**Investment Type — 11 options:** Fixed Deposit (FD), Recurring Deposit (RD), Mutual Funds (MF-Lumpsum), Mutual Funds (MF-SIP), Stocks, Public Provident Fund (PPF), National Pension System (NPS), Bond, Gold & Jewellery, Property — plus the `Investment Type` sheet heading.

**Goal association** — `Yes` reveals `Button cd=[Existing Goals]` and `Button cd=[New Goal]`. New Goal shows a 10-item horizontal icon carousel (**Retirement, Bike, Car, Education, Home, Laptop, Marriage, Mobile, Travels, Others**) plus `NAF EditText hint=[Goal name]` and `NAF EditText hint=[Goal amount]`. Existing Goals shows an illustration + "You don't have any existing goals".

### 3.5 Risk Profile

**Intro** — `Let's assess your risk taking capability` / `Mostly 80% of people don't understand about financial risk. Are you one of them?` with `Button cd=[Lets do risk profile]` and `Button cd=[I already know]`.

**Questionnaire** — `Question n/5` counter, a progress bar, and an unlabelled `NAF Button` back arrow. Questions and options:

| # | Question | Options |
|---|---|---|
| 1 | What would be your ideal time horizon for your investments | Less than 1 year · 1 to 3 years · 3 to 5 years · Above 5 years |
| 2 | Do you need income from this investment portfolio to cover your expenses during the specified investment period ? | Yes · No · Maybe |
| 3 | What is your expectations with regards to your investment returns? | Stable and predictable returns even if lower · Balanced returns with some fluctuations · Potentially higher returns with increased volatility |
| 4 | What is the maximum loss (within 1 year) that you can tolerate? | I don't like any capital loss · Upto 5% to 10% of my portfolio · Upto 10% to 20% of my portfolio · Above 20% |
| 5 | How would you typically respond if your investment portfolio experiences a loss beyond your specified level due to market fluctuations? | Sell all your investments immediately · Sell some investments and continue to hold the rest · Stay Invested |

**Result** — `Your Risk Profile`, gauge value `73.0`, band `Moderately Aggressive`, descriptive paragraph, `Button cd=[Next]`. Note the back control here is a `Button cd=[Back]` spanning `[56,165][1024,300]` — the entire header row.

**Manual picker** (`I already know`) — five radio options, each expanding an inline description. All five descriptions were captured; `Aggressive` is pre-selected by default.

### 3.6 Summary Details (`uidump-fh-56`, `-57`)

Six collapsible cards: **My Personal Details** (Name, Date of Birth, gender, Dependent, Pincode:, City:), **My Income and Expenses**, **My Insurance**, **My Investments**, **My Loans**, **My Risk Appetite**.

### 3.7 Score and diagnosis

- Score card: `76` / `OUT OF 100` / `Good` / `YOUR FINANCIAL HEALTH SCORE`, then `YOUR FINANCIAL HEALTH IS ON THE RIGHT TRACK!` with a paragraph, and `Button cd=[See the diagnosis]`.
- Diagnosis slide 1: `MY NET WORTH ₹-1.50L`, `ASSETS ₹3.50L / 2 Categories`, `LIABILITIES ₹5.00L / 1 Categories`, `Button cd=[Show me the gaps]`.
- Slide 2: `₹87.40L Total Required Protection` with three progress rows — Term Insurance `₹50.00L of ₹1.37Cr` (37, BELOW TARGET), Health Insurance `₹5.00L of ₹5.00L` (100, HEALTHY), Emergency Funds `₹2.50L of ₹3.00L` (83, BELOW TARGET).
- Gap slides carry `Your cover` vs `Required Cover`, a `Shortfall` figure (₹86.90L term, ₹50.00K emergency), a `WHY IT MATTERS` paragraph, a CTA and a partner-network footer line.
- Net Worth Breakdown: `TOTAL ASSETS ₹3.50L`, Bank Savings `₹2.50L / 71%`, Public Provident Fund `₹1.00L / 29%`.
- Plan of Action: `6 actions, mentioned by urgency.`, `CURRENT SCORE 76`, three Moderate and three Low priority items, `Button cd=[Reassess]`.
- Final slide: three `ImageView` share actions and a truncated disclaimer.

---

## 4. Accessibility findings

Severity: **S1** blocks the task for assistive-tech users, **S2** makes it substantially harder, **S3** is a quality defect.

| ID | Severity | Finding | Evidence | WCAG |
|---|---|---|---|---|
| A1 | S1 | Every form field in the flow is a `NAF` `EditText` with empty `content-desc` and `text`; the only label is the `hint`, which disappears once the user types. After entry the field has no accessible name at all. | `uidump-fh-08` lines 10, 12; `-11` lines 6, 8, 9; `-20` lines 13–19; `-26` lines 7, 9 | 1.3.1, 3.3.2, 4.1.2 |
| A2 | S1 | Multiple labels, headings and even validation messages are collapsed into a single `EditText` hint. The pincode field's hint is `Pincode\ncity name\nNA\nState\nNA`, and on error it becomes `Pincode\nPlease enter a valid value\ncity name\nNA\nState\nNA` — a screen reader cannot separate the label, the error and the two derived read-only values. | `uidump-fh-01` line 14, `-06` line 14 | 1.3.1, 3.3.1, 3.3.2 |
| A3 | S1 | The four insurance amount fields expose only two distinct names (`Annual Premium Paid` ×2, `Total Sum Insured` ×2). Health vs Term is only conveyed by the parent container's merged `content-desc`, so the four fields are indistinguishable to a screen reader. | `uidump-fh-20` lines 7, 13, 15, 17, 19 | 1.3.1, 2.4.6 |
| A4 | S1 | Every radio button in the flow is a `NAF RadioButton` with no accessible name. The visible label is a sibling `View`, and the `checked` state lives on the unnamed radio rather than on the labelled node. | `-08` 13–16, `-19`, `-24` 10–13, `-29` 11–14, `-47`…`-53` | 1.3.1, 4.1.2 |
| A5 | S1 | The delete (trash) icon on the investment card is absent from the accessibility tree entirely — the action cannot be reached with a screen reader. The edit/delete icons on the loan card are present but are unlabelled `NAF View`s. | `uidump-fh-39` line 11 (whole card is one node); `-18` lines 15–16 | 2.1.1, 4.1.2 |
| A6 | S2 | Date-of-birth picker wheels are `SeekBar`s whose `content-desc` is only the current value (`1994`, `May`, `16`) with no label saying which wheel is which. | `uidump-fh-02` lines 4–6 | 1.3.1, 4.1.2 |
| A7 | S2 | Dropdown sheets (Dependant, Loan Type, Investment Type) render options as plain clickable `View`s with no option/listitem role, no selected state, and no set-size or position information. | `-03`…`-05`, `-12`, `-13`, `-27`, `-28` | 1.3.1, 4.1.2 |
| A8 | S2 | Gender selection is three `ImageView` nodes with no radio role and no selected state, so the current choice is not announced. | `uidump-fh-01` lines 10–12 | 1.3.1, 4.1.2 |
| A9 | S2 | Progress bars on the protection and gap slides are exposed as bare numeric `View`s — `37`, `100`, `83` — with no label, no unit and no `ProgressBar` role. TalkBack reads "37" with no context. | `-60` lines 10, 14, 18; `-62` line 7 | 1.3.1, 4.1.2 |
| A10 | S2 | The 8-slide diagnosis is a horizontal pager with no accessible affordance: the page-indicator dots are not in the tree, there is no next/previous control, and nothing announces "slide 3 of 8". The only way forward is a horizontal swipe. | `-59`…`-67`, dots visible only in the PNGs | 1.3.1, 2.1.1, 4.1.2 |
| A11 | S2 | Gauge/donut charts (risk score 73.0, FHC score 76) expose only the number, with the band label in a separate node and no text alternative for the chart itself. | `-54` lines 4–6, `-58` lines 4–6 | 1.1.1, 1.3.1 |
| A12 | S2 | Reading order on the Plan of Action is scrambled: the priority index nodes (`1`, `2`, `3`) sit between the previous item's description and the next item's title, so TalkBack reads "…Enhance cover to meet recommended levels, Moderate Emergency fund, 2, Increase your amount…". | `uidump-fh-68` lines 5–23 | 1.3.2 |
| A13 | S2 | The share actions on the final slide are `ImageView`s, not `Button`s, so the role announced is "image" rather than "button". | `uidump-fh-67` lines 7–9 | 4.1.2 |
| A14 | S2 | The questionnaire back arrow is a `NAF Button` with no `content-desc` — the only way to revisit a previous answer is unlabelled. The same applies to the diagnosis header back button. | `-47`…`-53` line 2; `-58`…`-67` line 2 | 4.1.2 |
| A15 | S2 | The stepper is not exposed as a progress indicator: labels are plain `View`s with no current-step state, and it shows a sliding two-to-three-label window rather than "step 2 of 6", so neither sighted nor screen-reader users can tell how many steps remain. | `-01` 5–7, `-08` 5–7, `-20` 5–6, `-24` 5–6 | 1.3.1, 4.1.2 |
| A16 | S3 | Currency symbols are separate non-focusable `View cd=[₹  ]` nodes next to the input, so the amount and its unit are never announced together. Several such nodes have degenerate bounds `[0,0][0,0]`. | `-08` 9, 11; `-31` 20 | 1.3.1 |
| A17 | S3 | The goal-icon carousel items (`Retirement`, `Bike`, …) have no selected state and no role, and the validation hint "Please select Goal icon" is buried in the parent container's merged `content-desc` rather than being a live region. | `-32`…`-35` | 3.3.1, 4.1.2 |
| A18 | S3 | Not verifiable from dumps, needs a manual pass: colour contrast of the orange-on-white CTAs and the grey secondary label text, text reflow at 200%, and the touch-target size of the 85 px loan-card edit/delete icons. | — | 1.4.3, 1.4.4, 2.5.8 |

---

## 5. Functional and content defects

| ID | Severity | Defect | Reproduction |
|---|---|---|---|
| F1 | High | Summary Details cards are fixed-height and clip their own content. "Monthly Expenses(Groceries,Rent,Commute" is cut mid-word ("…Grocerie / s Rent Commute"), and the My Insurance card visually truncates "Health Insurance - Sum insured" and hides both Term Insurance rows. The user is asked to "ensure it is correct" against data they cannot read. | Complete steps 1–5 → Summary Details; see `uidump-fh-56-after-risk-next.png` and `-57-summary-scrolled.png` |
| F2 | High | Tapping **Next** on risk-profile Q1 with no option selected is a silent no-op — no navigation, no toast, no inline error, and the button is not visually disabled. | Q1 → tap Next at (796, 1628); `uidump-fh-48-risk-q1-novalidate` is byte-for-byte the same tree as `-47` |
| F3 | Medium | The screen title overlaps the back button on every step whose title wraps to two lines: the back chevron is drawn on top of the first character ("…dd Investment Details", "…avings & Investments", "Let us know your risk capability"). | Any of `-31`, `-39`, `-41` PNGs |
| F4 | Medium | The software keyboard covers the bottom fields and the primary CTA, and the form does not scroll the focused field into view. Filling Income & Expense required dismissing the keyboard before the loan radio buttons became reachable. | Step 2 → focus Monthly Household Expenses → the Yes/No radios and Next are hidden |
| F5 | Medium | Validation copy on Add Loan Details is wrong and inconsistent: "Please enter **principle** amount" (should be *principal*, and the field itself is labelled "Principal Amount") and "Please enter **emi**" (should be *EMI*). | Add Loan Details → tap Add with all fields empty; `uidump-fh-14-loan-add-empty` lines 7, 10 |
| F6 | Medium | The same misspelling is persisted onto the loan card: it reads "Principle Amount" where the input said "Principal Amount". | `uidump-fh-18-loan-added` line 14 |
| F7 | Medium | The pincode validation message is generic — "Please enter a valid value" — and is rendered inside the field's hint rather than as a field error, so it is easy to miss and does not say what is wrong. | Step 1 → leave Pincode empty → Next; `uidump-fh-06` line 14 |
| F8 | Medium | The Emergency Funds gap slide keeps the insurance footer: the CTA is "Start SIP" but the strapline still reads "Compare 8 Insurance - airpay partner network". | Diagnosis → swipe to GAP 03 OF 03 · LIQUIDITY PILLAR; `uidump-fh-64-diag-05` lines 16–17 |
| F9 | Low | Pluralisation bug: "**1 Categories**" for the single liability category. | Diagnosis slide 1; `uidump-fh-59-diagnosis` line 14 |
| F10 | Low | Grammar and casing errors in copy: "For the above investment, Do you want to **mapped** with existing goal or creating a new goal ?", "will be considered as **an** Retirement Fund", "What is your **expectations** with regards to…", "**Lets** do risk profile" (missing apostrophe), "**gender**" lowercase in the summary while every sibling label is title case, and "Do you have any Insurance **?**" with a space before the question mark. | `-29` 10/15, `-40` 5, `-51` 4, `-56` 5, `-19` 7 |
| F11 | Low | Inconsistent label punctuation in the summary: `Pincode:` and `City:` carry trailing colons while Name, Date of Birth, gender and Dependent do not. | `uidump-fh-56` line 5 |
| F12 | Low | The disclaimer on the final slide is truncated to "airpay money facilitates access to financial products…" with no way to expand or read the full text. | `uidump-fh-67-diag-08` line 10 |
| F13 | Low | The `Add Investment Details` screen renders the chosen type in the goal question with inconsistent casing and stray double spaces: heading "Public Provident Fund (PPF)", question "this **Public provident fund** investment", card "Public  provident  fund" (two spaces). | `-29` 5/10, `-39` 11 |
| F14 | Low | Completing the 5-question risk questionnaire returns the user to the manual "I already know" picker with the computed band pre-selected, and requires a second Next. The extra step is unexplained and lets the user silently override the result they just computed. | `-54` → Next → `-55-health-score` is the capability picker, not the score |

---

## 6. Automation notes for this module

- **No Java screen/locator classes were produced in this pass.** This document is the deliverable. Existing `airpay_money/.../screens/finsight/*` classes cover an earlier FinSight capture and do not match these FHC screens one-to-one.
- Locate inputs by `@hint`, not `@content-desc` or `@text`: all fields have empty `content-desc`, and `@text` is only populated after the user types. Note that the hint of the pincode and bank-balance fields is a multi-line composite, so use `contains()` rather than equality.
- Radio buttons must be driven by tapping the labelled sibling `View` (`cd=[Yes]`, `cd=[No]`), not the `NAF RadioButton`. Read the selection state from the `RadioButton`'s `checked` attribute, which is on a different node than the label.
- Every layout shifts after a selection expands an inline description or a card is added, so re-dump before computing the next tap coordinate. Selecting a risk option moves the remaining options up by ~450 px.
- Dismiss the keyboard with `keyevent 4` after each amount field; the primary CTA is otherwise unreachable (F4).
- The diagnosis is a horizontal pager. Use `input swipe 900 1200 150 1200 300`; there is no button to advance and the page indicator is not in the tree, so track the slide index by asserting on the `NN / SECTION` eyebrow text (`01 / THE DIAGNOSIS`, `GAP 01 OF 03 · INSURANCE PILLAR`, …).
- Dropdown sheets must be closed via the full-screen `View cd=[Dismiss]` node if the flow needs to back out without choosing.
- Bottom-sheet wheel pickers (`SeekBar`) cannot be set by text. Either accept the prefilled DOB or drive them with vertical swipes and assert on the `content-desc` value.
- Amounts are auto-formatted with Indian grouping as the user types (`100000` → `1,00,000`), so assert on the formatted string.

---

## 7. Coverage

**Captured** — the entry point; all four data-entry steps with empty, partially filled and filled states; the DOB wheel picker; all three dropdown sheets enumerated to their ends (Dependant 0–5, 7 loan types, 11 investment types); the Add Loan and Add Investment sub-screens including the all-empty validation state; the goal-association branch with both Existing Goals (empty state) and New Goal (10-icon carousel); both risk-profile entry paths, all five questionnaire questions, the no-answer validation state, all five manual capability descriptions, and the result gauge; Summary Details top and scrolled; the 76/100 score screen; and all eight diagnosis slides plus the scrolled Plan of Action.

**Deliberately not captured** — the "See plans" / "Start SIP" / "Reassess" destinations (they leave FHC and enter the insurance and mutual-fund purchase journeys), the Share on WhatsApp / Download PDF / Share on Email actions, goal creation actually submitted with a name and amount, editing or deleting an existing loan or investment card, and the multi-loan / multi-investment repeat-add paths. These are the natural next targets if the module is revisited.



---

# Part 5 — Tax Filing and Planning

# Airpay Money — Tax Filing & Planning (ClearTax) — Deep Analysis

| | |
|---|---|
| Module | Personal Finance → Tax Filing & Planning |
| Partner / tech | ClearTax (Defmacro Software Pvt. Ltd.), rendered in an in-app Android `WebView` hosted by a Flutter shell |
| Host activity | `com.finmapp.fininfinity/.MainActivity` |
| Device | RZCW724DGXZ, 1080 × 2297 px viewport (header 0–247) |
| Capture method | `adb shell uiautomator dump` + `screencap`, parsed to `*.parsed.txt` |
| Raw artifacts | `target/money/manual-capture/uidump-tax-*.{xml,png,parsed.txt}` |
| Data entry | None. No email, PAN, OTP or payment data was entered anywhere in this flow. |
| Status | Capture closed at the "Basic Plan for Salaried Professionals" page. Book-an-Expert checkout, OTP and calculator sub-pages intentionally left out of scope. |

---

## 1. Capture inventory

| Screen / state | Artifact prefix | Notes |
|---|---|---|
| Money Home (entry point) | `uidump-tax-home`, `uidump-tax-itr-plan-fold1` | Flutter UI, native a11y nodes |
| PIN unlock (re-entry after app exit) | `uidump-tax-nav-1` | |
| Tax Planning landing, first paint | `uidump-tax-landing` | |
| Tax Planning landing, full page | `uidump-tax-landing-fold1` … `fold20` | 20 vertical folds, ~600 a11y nodes each |
| Login / Signup (email step) | `uidump-tax-login` | |
| Login, empty submit | `uidump-tax-login-empty` | |
| Login, input focused + keyboard | `uidump-tax-login-focused` | |
| Hamburger drawer, collapsed/PRODUCTS | `uidump-tax-menu`, `uidump-tax-menu-fold2`, `fold3` | |
| Hamburger drawer, RESOURCES expanded | `uidump-tax-menu-resources`, `-fold2` | |
| Hamburger drawer, COMPANY expanded | `uidump-tax-menu-company` | |
| Tab strip interaction attempts | `uidump-tax-tab-rewards`, `uidump-tax-tabstrip-scrolled` | Tabs proved non-interactive, see F8 |
| ClearTax Rewards page | `uidump-tax-rewards-page`, `-fold1` … `-fold3` | |
| File ITR Now → Basic Plan page | `uidump-tax-fileitr-1` | Screenshot only; a11y tree was empty, see A1 |

---

## 2. Navigation map

```
Money Home
  └─ Personal Finance section
       └─ Card "Tax Filing & Planning"        cd=[Tax Filing & Planning]  [211,1589][501,1719]
            subtitle: "Old regime or new? Know which saves you more — then file in minutes / FY 2026-27 / Powered by Cleartax"
            └─ Button "Start Now"             [236,2000][501,2135]
                 └─ WebView "Tax Planning"    header View cd=[Tax Planning] [0,95][1080,247]
                      back Button (NAF, unlabelled) [56,123][169,219]
                      ├─ Login/Signup          Button cd=[Login/Signup]  [582,254][936,372]
                      │    └─ email → "Next" → (OTP step, not captured)
                      ├─ Open menu             Button cd=[Open menu]     [953,268][1026,338]
                      │    ├─ PRODUCTS  ├─ RESOURCES  └─ COMPANY   (accordion sections)
                      ├─ File ITR Now (card CTA [101,1860][478,1970] and sticky footer button)
                      │    └─ "Basic Plan for Salaried Professionals"
                      ├─ Book an Expert (card CTA and sticky footer button)  — not opened
                      └─ View All Rewards →    └─ "ClearTax Rewards" page
```

Login and signup are a single screen: one email field that sends an OTP. There is no separate "Sign up" form, so "capture login and signup" is fully represented by `uidump-tax-login*`.

---

## 3. Screen-by-screen element inventory

### 3.1 Landing page (`uidump-tax-landing*`)

**Header / chrome**
- `View cd=[Tax Planning]` title bar, `Button` back (NAF, no content-desc)
- `Image cd=[Picture of the author]` avatar, `Button cd=[Login/Signup]`, `Button cd=[Open menu]`

**Promo strip** — "Exclusive Discount", "Use Code:", `CTBAIRM`, `Image cd=[copy coupon]` (**not clickable**, see F7)

**Hero** — `Image cd=[logo]`, "Exclusive ITR service for Airpay Money Users", "8 M+" + "Users", "4.6" (+ "Rating" glyph), trust badges: "100% Notice Protection", "1 Click Broker Integration", "Maximum Tax Refund"

**Primary CTA cards** (clickable containers with merged accessible names)
- "File ITR Yourself / No manual data entry / **File ITR Now**"
- "File with Top CAs / ITR filed in 24hrs / **Book an Expert**"

**Support block** — "For support, reach out to", "Partnership Support:", `9008400173`, link `partnershipsupport@clear.in`, "Request Callback >" (**plain text, not actionable**, F6)

**Sticky footer** — `Button cd=[File ITR Now]`, `Button cd=[Book an Expert]` (full-width, `[45,…][1035,…]`)

**Tab strip** — ITR · Self Filing Plans · Expert Assisted Plans · Rewards & Offers · Calculators & Resources. All plain `View`s, `clickable=false`, no selected state; "Calculators & Resources" has degenerate bounds `[0,0][0,0]`.

**Self filing plans** — value props (Zero Manual Data Entry, File Accurately in minutes, Auto ITR regime & form selection, 24*7 Instant Support) and three plan cards, each with `File Now` + `Know More`:

| Plan | Price | Income sources |
|---|---|---|
| Salary Income | ₹265 / ₹1057 onwards | Salary, Interest, Freelancing, Dividend |
| Capital Gains or F&O — *Popular*, "80+ brokers:" | ₹318 / ₹1269 onwards | Stocks & MFs, Rental, Interest, Salary |
| NRI or Foreign Income | ₹953 / ₹3812 onwards | Schedule FSI, Foreign Stocks, All Other Income |

Plus "Start filing & apply the coupon during checkout" → `Button cd=[Start filing]`.

**Expert assisted plans** — four feature tiles (Guaranteed 24-hour filing, Graphical Tax Reports, Choose from top-rated CAs, Direct interaction with CAs) whose images expose **raw asset filenames** as their text (`1746770426_clockcounterclockwise`, `1746771058_chartbar`, `1746771162_users`, `1746771196_videoconference`), and four plan cards:

| Tier | Segment badge | MRP → Price | Discount | Expander |
|---|---|---|---|---|
| Basic | SALARIED PROFESSIONALS, FILE IN 24 HRS | ₹6,247 → ₹1,562 | 75% OFF | — |
| Premium | TRADERS AND FREELANCERS, FILE IN 24 HRS | ₹9,997 → ₹2,500 | 75% OFF | "See more income sources" |
| Elite | GLOBAL WEALTH BUILDERS, INVESTORS FAVOURITE | ₹14,997 → ₹3,750 | 75% OFF | "See more income sources" |
| Luxe | YEAR-ROUND PEACE OF MIND, MAXIMISE WEALTH | ₹62,497 → ₹15,625 | 75% OFF | "All income sources covered" |

Each card carries `Book an Expert` + `See more details`.

**Tax advisory card** — "Get personalized tax advice from experts" with four bullets, ₹8,747 → ₹2,186, `Buy now`.

**Coupon banner** — "Extra 75% OFF", "Code: CTBAIRM", `Image cd=[copy coupon]`.

**Rewards teaser** — "Its raining rewards!" (sic), "File your ITR with us and get assured rewards from these top brands.", `View All Rewards →`, and **17 brand logos all named `img`**.

**Calculators and Resources** — toggle `View`s "Calculator" / "Guides" (no role, no selected state) over an **auto-rotating** card; across successive dumps the same slot showed HRA Calculator → PPF Calculator → Interest Calculator → SIP Calculator. `Know more →` link plus pager `Button`s labelled only `1`–`8`.

**Trust block** — GSP-license copy, then Data Encryption / Safe Storage / 100% Privacy Control, each with `Image cd=[icon]`.

**FAQ accordion** — 10 questions, each paired with an **unlabelled NAF `Button`** at `[970,…][1004,…]` as the expand toggle. Only the first question's answer body is present in the tree.

**Footer** — Clear Logo; Follow us on Facebook / Twitter / LinkedIn / GitHub / Instagram; Support; then large link farms:
- COMPANY (10): About us, Contact us, Careers, Media & press, User reviews, Engineering blog, Clear Library, FinTech glossary, ClearTax Chronicles, Trust & Safety
- PRODUCTS → ITR Self Filing Service (10), CA Assisted Filing Service (10), Other Services (6)
- CALCULATORS → Income Tax (5), Financial Planning (6), Other (6)
- GUIDES & BLOGS → ITR Blogs (10), Capital Gains Guide (7), Other Income Guides (7), Deduction Guides (6), Personal Finance Guides (6)
- MARKET TRACKERS → Gold Prices (6 cities), Silver Prices (6 cities), IPO (3)
- TOP MUTUAL FUNDS / TOP AMCS / STOCK MARKET INFO / BEST FUNDS — see F10, these repeat the same 7 share-price links three times under mismatched headings.

### 3.2 Login / Signup (`uidump-tax-login*`)

| Element | Locator evidence | Observation |
|---|---|---|
| ClearTax logo | `View`/`Image cd=[cleartax logo]` `[90,310][990,434]` | Container is clickable |
| Heading | `TextView txt=[Login to your account]` | |
| Field label | `View txt=[Enter Email]` `[180,971][396,1022]` | Sibling of the input, not associated with it |
| Helper text | `TextView txt=[You will receive an OTP on this email]` | |
| Email input | `EditText` **NAF**, `cd=[]`, `txt=[]`, `hint=[]` `[180,1140][900,1283]` | No accessible name at all (A3) |
| Submit | `Button txt=[Next]` `[180,1359][900,1497]` | |
| Trust art | `Image cd=[security logos]` | |
| Legal | `ClearTax is a product by Defmacro Software Pvt. Ltd.` | |
| Links | Privacy Policy, Terms of Use, Legal, Need Help? | Each a clickable `View` + inner `TextView` |

Empty submit: `uidump-tax-login-empty` is byte-for-byte equivalent to `uidump-tax-login` — no error node appeared (A4 / F5).
Focused state: `uidump-tax-login-focused` shows the page reflowing upward for the keyboard (footer links collapse to `y=1332`); the input still has no accessible name.

### 3.3 Hamburger drawer

Three accordion section `Button`s, none exposing expanded/collapsed state:

- **PRODUCTS** → *ITR FILING SERVICES*: Self ITR Filing, Hire an Expert for Tax Filing, Annual Subscription. *OTHER SERVICES*: Notice Management, HUF Services, Advance Tax Filing, Capital Gains Taxation, F&O Taxation, NRI Services, ESOPs & RSUs.
- **RESOURCES** → *ITR*: ITR Guide, Income Tax Calculator, Advance Tax calculator, Capital Gains Calculator. *PERSONAL FINANCE*: Financial Planning Guides, Financial Calculators, Gold Prices, Silver Prices, IPO.
- **COMPANY** → Support, About Us, Careers, Trust & Safety, Media & PR.

Items are `MenuItem` nodes with `text` only (no content-desc). The drawer exposes **no close control** in the tree, and all landing-page nodes remain in the a11y tree while it is open (A15).

### 3.4 ClearTax Rewards page

"ClearTax Rewards" heading, note "Exclusive offer codes will be visible on My Account page after you have successfully e-filed.", a primary button named **`eFile now,,[object Object]`**, a `Button cd=[animation]`, `Image cd=[hero-image]`, then offer categories — Fintech, Electronics, Education, Fashion, Lifestyle, Travel, Ecommerce, Co-working Space, Finance — where **every** offer CTA is named `[object Object]` (14+ buttons).

### 3.5 File ITR Now → plan page

Visible content: "Basic Plan for Salaried Professionals", "8 M+ Users", "4.6 Rating", "Benefits you unlock" (Instant Filing — 24hrs Guaranteed, Maximum Tax Savings, Real-time Tax Consultation, 24x7 Support), "Income sources covered" (Salary less than 50L, Rental income, Interest income). The accessibility tree at capture time contained only 3 nodes: title bar, back button, and an unlabelled `WebView` (A1).

![File ITR Now — Basic Plan for Salaried Professionals](C:/Users/shubham.verma/Desktop/mobile_automation/airpay-mobile-automation/target/money/manual-capture/uidump-tax-fileitr-1.png)

---

## 4. Accessibility findings

Severity: **S1** blocks the task for assistive-tech users, **S2** makes it substantially harder, **S3** is a quality defect.

| ID | Severity | Finding | Evidence | WCAG |
|---|---|---|---|---|
| A1 | S1 | WebView exposes no content for 15–20 s after navigation, and the ITR plan page never populated during capture — a screen reader finds only an unlabelled `WebView` | `uidump-tax-landing` (3 nodes) vs `fold1` (~600); `uidump-tax-fileitr-1` (3 nodes after 23 s) | 1.3.1, 4.1.2 |
| A2 | S1 | No virtualization: the whole document (~600 nodes) is in the tree at once with off-screen bounds clamped to `y=2297`. TalkBack linear navigation has to traverse hundreds of invisible elements to reach the next visible one | every `uidump-tax-landing-fold*` | 1.3.2, 2.4.3 |
| A3 | S1 | Email input has no accessible name: `EditText` with `NAF=true`, empty `content-desc`, `text` and `hint`. The visible "Enter Email" is an unassociated sibling | `uidump-tax-login` line 9 | 1.3.1, 3.3.2, 4.1.2 |
| A4 | S2 | Submitting an empty email produces no error node and no live-region announcement | `uidump-tax-login-empty` identical to `uidump-tax-login` | 3.3.1 |
| A5 | S1 | `[object Object]` used as the accessible name on every Rewards CTA, and `eFile now,,[object Object]` on the primary CTA | `uidump-tax-rewards-page` lines 11–58 | 2.4.6, 4.1.2 |
| A6 | S2 | FAQ expand toggles are `NAF` `Button`s with empty `content-desc` and no expanded/collapsed state | `uidump-tax-landing` lines 287, 291, 293 … | 2.4.6, 4.1.2 |
| A7 | S2 | Tab strip items are non-clickable `View`s with no tab role and no selected state; "Calculators & Resources" has `[0,0][0,0]` bounds and is unreachable | `uidump-tax-landing` lines 40–44 | 1.3.1, 4.1.2 |
| A8 | S2 | "Calculator" / "Guides" toggle is a pair of plain `View`s — no role, no selected state | `uidump-tax-landing` lines 259–260 | 1.3.1, 4.1.2 |
| A9 | S2 | Auto-rotating carousel with no pause/stop control; pager buttons named only `1`…`8` with no current-page state | rotating card differs across `landing`, `fold1`, `fold2`, `menu-resources` | 2.2.2, 4.1.2 |
| A10 | S2 | Non-text content unnamed or wrongly named: 17 reward logos all `cd=[img]`; three trust icons all `cd=[icon]`; four feature tiles expose raw asset filenames (`1746771058_chartbar`) | `uidump-tax-landing` lines 90–96, 241–257, 276–282 | 1.1.1 |
| A11 | S2 | Header back button is `NAF` with no `content-desc` — the only in-page way out of the module is unlabelled | every tax dump, line 3 | 4.1.2 |
| A12 | S2 | Drawer is not a modal: no close control in the tree and the underlying page stays in the a11y tree while it is open, so both drawer items and page content are reachable simultaneously | `uidump-tax-menu` lines 9–23 followed by lines 24+ | 1.3.1, 2.4.3 |
| A13 | S2 | Drawer accordions (PRODUCTS / RESOURCES / COMPANY) expose no expanded state | `uidump-tax-menu*` | 4.1.2 |
| A14 | S3 | Merged accessible names on the CTA cards: `File ITR Yourself No manual data entry File ITR Now` is announced as one string, and the inner "File ITR Now" text is not itself focusable | `uidump-tax-landing` lines 21–24 | 1.3.1, 2.4.4 |
| A15 | S3 | Prices are split across nodes so the currency symbol is announced separately from the amount (`₹` then `6,247`), and MRP vs discounted price carry no semantic distinction | `uidump-tax-landing` lines 101–106 | 1.3.1 |
| A16 | S3 | Metric labels split from values: `8 M+` / `Users`, and `4.6` with the "Rating" label in a separate node | `uidump-tax-landing` lines 15–17 | 1.3.1 |
| A17 | S3 | Not verifiable from dumps, needs a manual pass: text-resize/reflow at 200%, contrast of the blue-on-white plan headings, and touch-target size of the 34 px FAQ toggles and 62 px pager buttons | — | 1.4.4, 1.4.3, 2.5.8 |

---

## 5. Functional and content defects

| ID | Severity | Defect | Reproduction |
|---|---|---|---|
| F1 | High | Sticky "File ITR Now" dismissed the WebView and returned to Money Home instead of opening the plan page | Landing → scroll to sticky bar → tap `File ITR Now` at (540, 2066). The in-card CTA at (289, 1915) worked correctly. |
| F2 | High | Two short vertical swipes on the plan page popped the WebView and returned to Money Home | On "Basic Plan for Salaried Professionals", swipe (540,1400)→(540,1300) then back |
| F3 | Medium | Horizontal swipe on the rewards strip navigates to a separate ClearTax Rewards page instead of scrolling the carousel | Landing → rewards row → horizontal swipe |
| F4 | High | Device back from inside the module exits the entire app rather than returning to Money Home | Repeated `keyevent 4` in the module → device launcher; app had to be relaunched and the PIN re-entered |
| F5 | Medium | Empty email + "Next" gives no visible or announced error | Login → tap Next with the field empty |
| F6 | Medium | "Request Callback >" looks like a link but is a non-clickable `TextView` | Landing → support block |
| F7 | Medium | Copy-coupon icon (`cd=[copy coupon]`, both instances) is `clickable=false`, so the coupon cannot be copied | Landing → promo strip and coupon banner |
| F8 | Medium | Tab strip is decorative only — items are not clickable and "Calculators & Resources" has zero-size bounds, so that tab cannot be reached at all | Landing → tap any tab label; `uidump-tax-tab-rewards`, `uidump-tax-tabstrip-scrolled` show no state change |
| F9 | High | Rewards page renders `[object Object]` as the button label — an object is being passed where a string is expected in the CTA component | Rewards page, all offer cards |
| F10 | Low | Footer section headings do not match their content: "TOP MUTUAL FUNDS" lists share-price links, "STOCK MARKET INFO" and "BEST FUNDS" repeat the identical 7 links | Landing → footer |
| F11 | Low | Copy errors: "128-bit **SSI** certified and PCI **complaint**" (should be SSL / compliant), "**Its** raining rewards!" (It's) | Landing → trust block, rewards teaser |

---

## 6. Automation notes for this module

- **No Java screen/locator classes were produced for this module in this pass.** This document is the deliverable; page objects can be generated later from the parsed dumps if the module is brought into the regression suite.
- Prefer `@text` over `@content-desc` inside this WebView: most text nodes carry `text` only, while container nodes carry a merged `content-desc`.
- Never trust a fixed sleep after entering the module. Gate on node count (for example, wait until the parsed tree has more than ~50 nodes) before asserting or clicking; a 9-second wait consistently yielded the 3-node placeholder tree.
- Filter out off-screen nodes before interacting: any node with `y1 == 2297` or bounds `[0,0][0,0]` is present in the tree but not rendered.
- The Flutter host shell (Money Home tiles, "Start Now") needs ADB coordinate taps; Appium element clicks were unreliable there.
- Back navigation is unsafe (F4). Re-entry requires relaunching `com.finmapp.fininfinity/.MainActivity` and unlocking with the PIN, so any teardown for this module should assume a full app restart rather than a back-stack unwind.
- The Calculators carousel auto-rotates, so any assertion on the card title is inherently flaky; assert on the section heading and the pager instead.

---

## 7. Coverage

**Captured** — entry point, landing page in full (20 folds), login/signup email step plus empty-submit and focused states, hamburger drawer with all three sections expanded, ClearTax Rewards page (4 folds), and the File ITR Now → "Basic Plan for Salaried Professionals" page.

**Deliberately not captured** — OTP entry and anything past it (no data entry allowed), Book an Expert checkout, per-plan "See more details" sheets, expanded FAQ answers beyond the first, individual calculator and guide pages, and Annual Subscription. These are the natural next targets if this module is revisited.

