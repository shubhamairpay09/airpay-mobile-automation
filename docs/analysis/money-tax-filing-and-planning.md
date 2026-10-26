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
