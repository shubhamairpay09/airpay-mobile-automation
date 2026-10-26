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
