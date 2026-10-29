# Money Products + FinSight — Deep Scan Flow

## Order (one go)

1. **Products** (Investments → Insurance → Loans → Tax)  
2. **FinSight** (after Products residual / hub complete)  
3. Calculators — later

## Hard rules

1. Money stays foreground — do **not** relaunch (`am start` / system Back forbidden mid-run).
2. Leave: **app Back** → Close/Dismiss → **swipe** → **Products/FinSight tab**. Never system Back.
3. No other apps (Share / Twitter / browser).
4. Loader ≤ **60s** then skip tile / next.
5. Fast PIN (ADB digits). Focus steal → **`activateApp` only**.
6. Resume skips `*-x-done` / existing FinSight ids.

## Products sections

| # | Section | Modules |
|---|---------|---------|
| 1 | Investments | MF → NPS → Gold → FD → US Stocks → Indian Stocks |
| 2 | Insurance | 2W → 4W → Health → Life |
| 3 | Loans | Gold Loan → Credit Cards → Personal Loan → Home Loan → LAP |
| 4 | Tax | Tax Filing & Planning |

## FinSight (after Products)

Tab **FinSight** → hub scan → **only open labels already visible** (no scroll-hunt for missing).  
Includes `Invest Now`, `Take Action`, `Compare and File`, `Tax Filing`, `Retirement`, etc.  
Missing CTA → skip immediately. Inside a screen: open Invest Now / Start Now if present → scan → leave.

## Current residual focus

Already deep-done: MF…FD, stocks, insurance, Gold Loan, Personal Loan, Home Loan (+ CC landing).  
Soft-skip if `*-dash` or `*-x-done` exists → residual = **LAP**, **Tax**, then **FinSight**.  
Faster: MAX_CONTROLS=4, MAX_NEST=1; FinSight no per-sub publishReport.
