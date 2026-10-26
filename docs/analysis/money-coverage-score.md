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
