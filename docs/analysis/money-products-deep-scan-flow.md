# Money Products — Deep Scan Screen Flow

Source of truth for ordered a11y deep coverage. FinSight is **out of scope** until Products completes.

## Global rules

1. Unlock PIN once → keep Money in foreground (no system Back to launcher).
2. Walk screens **in the order below**; scan each screen into the scorecard.
3. On error / stuck: in-app Back until **Products → Investments hub**, then start the **next** module.
4. Do **not** submit KYC, payments, or invest. Open → scan → leave.
5. Prefer short post-tap settles (no long hard waits).

## Products hub sections (top → bottom)

| Order | Section | Modules |
|------:|---------|---------|
| 1 | Investments | Mutual Funds → NPS → Digital Gold → Fixed Deposit → US Stocks → Indian Stocks |
| 2 | Insurance | 2 Wheeler → 4 Wheeler → Health → Life |
| 3 | Loans & Credit Cards | Gold Loan → Credit Cards → Personal Loan → Home Loan → Loan Against Property |
| 4 | Personal Finance | Tax Filing & Planning |

**Entry:** Bottom tab **Products** → confirm `Investments` + tiles (not MF/NPS interior).

---

## 1. Mutual Funds (template)

**Entry:** Products → Investments → Mutual Funds  
**Exit:** Back until Investments hub → next module (NPS).

| Step | Screen / action | Scorecard id prefix | Notes |
|-----:|-----------------|---------------------|-------|
| 0 | MF Dashboard (top) | `mf-dash` | Title Mutual Funds + Dashboard tab |
| 1 | Header: logo / Airpay Money | `mf-hdr-logo` | Open if tappable → back |
| 2 | Header: Bookmark | `mf-hdr-bookmark` | Open → scan → back |
| 3 | Header: Help (?) | `mf-hdr-help` | Open → scan → back |
| 4 | Create Mutual Fund Account | `mf-create-acct` | Scan only; no KYC submit |
| 5 | My Goals → Add Goal | `mf-add-goal` | Create Goal sheet; back |
| 6 | Open one existing goal | `mf-goal` | Monthly SIP / One Time / buttons; back |
| 7 | Recommendation → View all | `mf-rec-list` | List |
| 7a | Open one recommended fund | `mf-rec-fund` | Bookmark, Share, One Time, Monthly, 1M/3M… |
| 7b | Fund: Returns / Peer / Holdings | `mf-rec-fund-*` | Nested links; back to fund → list → dash |
| 8 | Curated Funds → View all Mutual Funds | `mf-curated` | Better than FD, High returns, Short term… |
| 8a | Open one curated / category fund | `mf-curated-fund` | Scan → back |
| 9 | Index Funds (e.g. Nifty 50) | `mf-index` | Open one → scan → back |
| 10 | Your risk profile → Edit | `mf-risk-edit` | Scan; do not finish long questionnaire |
| 11 | Reassess | `mf-reassess` | Open → scan → back |
| 12 | Import Funds | `mf-import` | Open → scan → back |
| 13 | Goal Calculator | `mf-calc-goal` | Scan → back |
| 14 | SIP Calculator | `mf-calc-sip` | Scan → back |
| 15 | Tab Portfolio | `mf-tab-portfolio` | Scan + light scroll → Dashboard |
| 16 | Tab Transactions | `mf-tab-tx` | Scan → Dashboard |
| 17 | Tab Profile | `mf-tab-profile` | Scan → Dashboard |
| 18 | Tab Dashboard | `mf-tab-dash` | Confirm home of MF |

---

## 2–6. Other Investments (same skeleton)

For each: open tile → dashboard scan → header/help if any → primary CTAs (scan-only) → bottom tabs if present → back to Investments.

| Module | Id prefix |
|--------|-----------|
| NPS | `nps-` |
| Digital Gold | `gold-` |
| Fixed Deposit | `fd-` |
| US Stocks | `us-` |
| Indian Stocks | `in-` |

---

## Insurance / Loans / Personal Finance

Same pattern: Products → scroll to section → open tile → deep-ish (landing + main CTAs + tabs) → hub → next.

---

## Resume

Skip scorecard ids already present. If `mf-dash` exists but children missing, MF walker continues from first missing step where practical; otherwise skip whole module only when a `mf-done` marker is written after a full pass.
