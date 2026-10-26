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
| Screens | `airpay_money/.../screens/profile/ProfileHubScreen.java`, `ProfileDetailsScreen.java`, `ProfileWebMenuScreen.java` |

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
