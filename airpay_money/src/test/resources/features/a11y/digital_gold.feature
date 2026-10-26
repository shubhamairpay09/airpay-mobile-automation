@airpay-money @money @android @a11y @capture @digital-gold
Feature: Capture Digital Gold screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @DIGITAL_GOLD_001
  Scenario: Digital Gold screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-gold-10-open.xml" as "gold 10 open"
    And I capture saved hierarchy dump "uidump-gold-11-buy.xml" as "gold 11 buy"
    And I capture saved hierarchy dump "uidump-gold-12-sell.xml" as "gold 12 sell"
    And I capture saved hierarchy dump "uidump-gold-13-coins.xml" as "gold 13 coins"
    And I capture saved hierarchy dump "uidump-gold-14-locker.xml" as "gold 14 locker"
    And I capture saved hierarchy dump "uidump-gold-15-tx.xml" as "gold 15 tx"
    And I capture saved hierarchy dump "uidump-gold-16-menu.xml" as "gold 16 menu"
    And I capture saved hierarchy dump "uidump-gold-17-after-coins-back.xml" as "gold 17 after coins back"
    And I capture saved hierarchy dump "uidump-gold-18-locker.xml" as "gold 18 locker"
    And I capture saved hierarchy dump "uidump-gold-19-tx.xml" as "gold 19 tx"
    And I capture saved hierarchy dump "uidump-gold-20-dashboard.xml" as "gold 20 dashboard"
    And I capture saved hierarchy dump "uidump-gold-21-menu.xml" as "gold 21 menu"
    And I capture saved hierarchy dump "uidump-gold-21-menu-Digital-Gold.xml" as "gold 21 menu Digital Gold"
    And I capture saved hierarchy dump "uidump-gold-22-menu-Login.xml" as "gold 22 menu Login"
    And I capture saved hierarchy dump "uidump-gold-22-tx.xml" as "gold 22 tx"
    And I capture saved hierarchy dump "uidump-gold-23-locker.xml" as "gold 23 locker"
    And I capture saved hierarchy dump "uidump-gold-23-menu-App-Store.xml" as "gold 23 menu App Store"
    And I capture saved hierarchy dump "uidump-gold-24-buy-itype.xml" as "gold 24 buy itype"
    And I capture saved hierarchy dump "uidump-gold-24-locker-silver.xml" as "gold 24 locker silver"
    And I capture saved hierarchy dump "uidump-gold-25-buy-rs100.xml" as "gold 25 buy rs100"
    And I capture saved hierarchy dump "uidump-gold-25-locker-gold.xml" as "gold 25 locker gold"
    And I capture saved hierarchy dump "uidump-gold-26-buy-gold.xml" as "gold 26 buy gold"
    And I capture saved hierarchy dump "uidump-gold-26-buy-rs200.xml" as "gold 26 buy rs200"
    And I capture saved hierarchy dump "uidump-gold-27-buy-invest-type.xml" as "gold 27 buy invest type"
    And I capture saved hierarchy dump "uidump-gold-27-buy-rs500.xml" as "gold 27 buy rs500"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @DIGITAL_GOLD_002
  Scenario: Digital Gold screens part 2
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-gold-28-buy-grams.xml" as "gold 28 buy grams"
    And I capture saved hierarchy dump "uidump-gold-28-buy-rs1000.xml" as "gold 28 buy rs1000"
    And I capture saved hierarchy dump "uidump-gold-29b-buy-privacy-end.xml" as "gold 29b buy privacy end"
    And I capture saved hierarchy dump "uidump-gold-29-buy-privacy.xml" as "gold 29 buy privacy"
    And I capture saved hierarchy dump "uidump-gold-29-buy-rs2000.xml" as "gold 29 buy rs2000"
    And I capture saved hierarchy dump "uidump-gold-30-buy-gmchip.xml" as "gold 30 buy gmchip"
    And I capture saved hierarchy dump "uidump-gold-31-buy-gmchip.xml" as "gold 31 buy gmchip"
    And I capture saved hierarchy dump "uidump-gold-31-buy-ticked.xml" as "gold 31 buy ticked"
    And I capture saved hierarchy dump "uidump-gold-32-buy-gmchip.xml" as "gold 32 buy gmchip"
    And I capture saved hierarchy dump "uidump-gold-33-buy-gmchip.xml" as "gold 33 buy gmchip"
    And I capture saved hierarchy dump "uidump-gold-34-filter-Clear-all.xml" as "gold 34 filter Clear all"
    And I capture saved hierarchy dump "uidump-gold-35-filter-Metal-Weight.xml" as "gold 35 filter Metal Weight"
    And I capture saved hierarchy dump "uidump-gold-35-sell-gold.xml" as "gold 35 sell gold"
    And I capture saved hierarchy dump "uidump-gold-36-filter-Price-Range.xml" as "gold 36 filter Price Range"
    And I capture saved hierarchy dump "uidump-gold-36-sell-grams.xml" as "gold 36 sell grams"
    And I capture saved hierarchy dump "uidump-gold-37-filter-Less-than-2g.xml" as "gold 37 filter Less than 2g"
    And I capture saved hierarchy dump "uidump-gold-37-sell-silver.xml" as "gold 37 sell silver"
    And I capture saved hierarchy dump "uidump-gold-38-filter-2-4-gms.xml" as "gold 38 filter 2 4 gms"
    And I capture saved hierarchy dump "uidump-gold-38-sell-silver-grams.xml" as "gold 38 sell silver grams"
    And I capture saved hierarchy dump "uidump-gold-39-filter-4-6-gms.xml" as "gold 39 filter 4 6 gms"
    And I capture saved hierarchy dump "uidump-gold-39-sell-scroll.xml" as "gold 39 sell scroll"
    And I capture saved hierarchy dump "uidump-gold-40-coins.xml" as "gold 40 coins"
    And I capture saved hierarchy dump "uidump-gold-40-filter-6-8-gms.xml" as "gold 40 filter 6 8 gms"
    And I capture saved hierarchy dump "uidump-gold-41-coins-filter.xml" as "gold 41 coins filter"
    And I capture saved hierarchy dump "uidump-gold-41-filter-8-gms.xml" as "gold 41 filter 8 gms"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @DIGITAL_GOLD_003
  Scenario: Digital Gold screens part 3
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-gold-42-filter-Apply.xml" as "gold 42 filter Apply"
    And I capture saved hierarchy dump "uidump-gold-50-buy-silver.xml" as "gold 50 buy silver"
    And I capture saved hierarchy dump "uidump-gold-51-buy-silver-grams.xml" as "gold 51 buy silver grams"
    And I capture saved hierarchy dump "uidump-gold-52-buy-silver-rs.xml" as "gold 52 buy silver rs"
    And I capture saved hierarchy dump "uidump-gold-53-buy-silver-rs100.xml" as "gold 53 buy silver rs100"
    And I capture saved hierarchy dump "uidump-gold-53-buy-silver-rs200.xml" as "gold 53 buy silver rs200"
    And I capture saved hierarchy dump "uidump-gold-53-buy-silver-rs500.xml" as "gold 53 buy silver rs500"
    And I capture saved hierarchy dump "uidump-gold-54-coins-grid.xml" as "gold 54 coins grid"
    And I capture saved hierarchy dump "uidump-gold-55-filter-open-2-4-gms.xml" as "gold 55 filter open 2 4 gms"
    And I capture saved hierarchy dump "uidump-gold-55-filter-open-4-6-gms.xml" as "gold 55 filter open 4 6 gms"
    And I capture saved hierarchy dump "uidump-gold-55-filter-open-6-8-gms.xml" as "gold 55 filter open 6 8 gms"
    And I capture saved hierarchy dump "uidump-gold-55-filter-open-8-gms.xml" as "gold 55 filter open 8 gms"
    And I capture saved hierarchy dump "uidump-gold-55-filter-open-Less-than-2g.xml" as "gold 55 filter open Less than 2g"
    And I capture saved hierarchy dump "uidump-gold-56-filtered-2-4-gms.xml" as "gold 56 filtered 2 4 gms"
    And I capture saved hierarchy dump "uidump-gold-56-filtered-4-6-gms.xml" as "gold 56 filtered 4 6 gms"
    And I capture saved hierarchy dump "uidump-gold-56-filtered-6-8-gms.xml" as "gold 56 filtered 6 8 gms"
    And I capture saved hierarchy dump "uidump-gold-56-filtered-8-gms.xml" as "gold 56 filtered 8 gms"
    And I capture saved hierarchy dump "uidump-gold-56-filtered-Less-than-2g.xml" as "gold 56 filtered Less than 2g"
    And I capture saved hierarchy dump "uidump-gold-57-coins-after-filters.xml" as "gold 57 coins after filters"
    And I capture saved hierarchy dump "uidump-gold-60-coin-Augmont-0-1Gm-Gold-Coin-999-Purity-P.xml" as "gold 60 coin Augmont 0 1Gm Gold Coin 999 Purity P"
    And I capture saved hierarchy dump "uidump-gold-60-coin-Augmont-0-5Gm-Gold-Coin-999-Purity-P.xml" as "gold 60 coin Augmont 0 5Gm Gold Coin 999 Purity P"
    And I capture saved hierarchy dump "uidump-gold-60-coin-Augmont-10Gm-Gold-Coin-999-Purity-Pu.xml" as "gold 60 coin Augmont 10Gm Gold Coin 999 Purity Pu"
    And I capture saved hierarchy dump "uidump-gold-60-coin-Augmont-1Gm-Gold-Coin-999-Purity-Pur.xml" as "gold 60 coin Augmont 1Gm Gold Coin 999 Purity Pur"
    And I capture saved hierarchy dump "uidump-gold-60-coin-Augmont-20Gm-Gold-Coin-999-Purity-Pu.xml" as "gold 60 coin Augmont 20Gm Gold Coin 999 Purity Pu"
    And I capture saved hierarchy dump "uidump-gold-60-coin-Augmont-2Gm-Gold-Coin-999-Purity-Pur.xml" as "gold 60 coin Augmont 2Gm Gold Coin 999 Purity Pur"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @DIGITAL_GOLD_004
  Scenario: Digital Gold screens part 4
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-gold-60-coin-Augmont-50Gm-Gold-Coin-999-Purity-Pu.xml" as "gold 60 coin Augmont 50Gm Gold Coin 999 Purity Pu"
    And I capture saved hierarchy dump "uidump-gold-60-coin-Augmont-5Gm-Gold-Coin-999-Purity-Pur.xml" as "gold 60 coin Augmont 5Gm Gold Coin 999 Purity Pur"
    And I capture saved hierarchy dump "uidump-gold-60-coin-Augmont-8Gm-Gold-Coin-999-Purity-Pur.xml" as "gold 60 coin Augmont 8Gm Gold Coin 999 Purity Pur"
    And I capture saved hierarchy dump "uidump-gold-61-coin-Augmont-0-1Gm-Gold-Coin-999-Purity-P-end.xml" as "gold 61 coin Augmont 0 1Gm Gold Coin 999 Purity P end"
    And I capture saved hierarchy dump "uidump-gold-61-coin-Augmont-0-5Gm-Gold-Coin-999-Purity-P-end.xml" as "gold 61 coin Augmont 0 5Gm Gold Coin 999 Purity P end"
    And I capture saved hierarchy dump "uidump-gold-61-coin-Augmont-10Gm-Gold-Coin-999-Purity-Pu-end.xml" as "gold 61 coin Augmont 10Gm Gold Coin 999 Purity Pu end"
    And I capture saved hierarchy dump "uidump-gold-61-coin-Augmont-1Gm-Gold-Coin-999-Purity-Pur-end.xml" as "gold 61 coin Augmont 1Gm Gold Coin 999 Purity Pur end"
    And I capture saved hierarchy dump "uidump-gold-61-coin-Augmont-20Gm-Gold-Coin-999-Purity-Pu-end.xml" as "gold 61 coin Augmont 20Gm Gold Coin 999 Purity Pu end"
    And I capture saved hierarchy dump "uidump-gold-61-coin-Augmont-2Gm-Gold-Coin-999-Purity-Pur-end.xml" as "gold 61 coin Augmont 2Gm Gold Coin 999 Purity Pur end"
    And I capture saved hierarchy dump "uidump-gold-61-coin-Augmont-50Gm-Gold-Coin-999-Purity-Pu-end.xml" as "gold 61 coin Augmont 50Gm Gold Coin 999 Purity Pu end"
    And I capture saved hierarchy dump "uidump-gold-61-coin-Augmont-5Gm-Gold-Coin-999-Purity-Pur-end.xml" as "gold 61 coin Augmont 5Gm Gold Coin 999 Purity Pur end"
    And I capture saved hierarchy dump "uidump-gold-61-coin-Augmont-8Gm-Gold-Coin-999-Purity-Pur-end.xml" as "gold 61 coin Augmont 8Gm Gold Coin 999 Purity Pur end"
    And I capture saved hierarchy dump "uidump-gold-62-coin-Augmont-0-1Gm-Gold-Coin-999-Purity-P-qty.xml" as "gold 62 coin Augmont 0 1Gm Gold Coin 999 Purity P qty"
    And I capture saved hierarchy dump "uidump-gold-62-coin-Augmont-0-5Gm-Gold-Coin-999-Purity-P-qty.xml" as "gold 62 coin Augmont 0 5Gm Gold Coin 999 Purity P qty"
    And I capture saved hierarchy dump "uidump-gold-62-coin-Augmont-10Gm-Gold-Coin-999-Purity-Pu-qty.xml" as "gold 62 coin Augmont 10Gm Gold Coin 999 Purity Pu qty"
    And I capture saved hierarchy dump "uidump-gold-62-coin-Augmont-1Gm-Gold-Coin-999-Purity-Pur-qty.xml" as "gold 62 coin Augmont 1Gm Gold Coin 999 Purity Pur qty"
    And I capture saved hierarchy dump "uidump-gold-62-coin-Augmont-20Gm-Gold-Coin-999-Purity-Pu-qty.xml" as "gold 62 coin Augmont 20Gm Gold Coin 999 Purity Pu qty"
    And I capture saved hierarchy dump "uidump-gold-62-coin-Augmont-2Gm-Gold-Coin-999-Purity-Pur-qty.xml" as "gold 62 coin Augmont 2Gm Gold Coin 999 Purity Pur qty"
    And I capture saved hierarchy dump "uidump-gold-62-coin-Augmont-50Gm-Gold-Coin-999-Purity-Pu-qty.xml" as "gold 62 coin Augmont 50Gm Gold Coin 999 Purity Pu qty"
    And I capture saved hierarchy dump "uidump-gold-62-coin-Augmont-5Gm-Gold-Coin-999-Purity-Pur-qty.xml" as "gold 62 coin Augmont 5Gm Gold Coin 999 Purity Pur qty"
    And I capture saved hierarchy dump "uidump-gold-62-coin-Augmont-8Gm-Gold-Coin-999-Purity-Pur-qty.xml" as "gold 62 coin Augmont 8Gm Gold Coin 999 Purity Pur qty"
    And I capture saved hierarchy dump "uidump-gold-63-coins-fold-1.xml" as "gold 63 coins fold 1"
    And I capture saved hierarchy dump "uidump-gold-63-coins-fold-2.xml" as "gold 63 coins fold 2"
    And I capture saved hierarchy dump "uidump-gold-63-coins-fold-3.xml" as "gold 63 coins fold 3"
    And I capture saved hierarchy dump "uidump-gold-63-coins-fold-4.xml" as "gold 63 coins fold 4"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @DIGITAL_GOLD_005
  Scenario: Digital Gold screens part 5
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-gold-63-coins-fold-5.xml" as "gold 63 coins fold 5"
    And I capture saved hierarchy dump "uidump-gold-98-dashboard-after-coins.xml" as "gold 98 dashboard after coins"
    And I capture saved hierarchy dump "uidump-gold-99-dashboard-final.xml" as "gold 99 dashboard final"
    And I capture saved hierarchy dump "uidump-gold-coins-fold-1.xml" as "gold coins fold 1"
    And I capture saved hierarchy dump "uidump-gold-coins-fold-2.xml" as "gold coins fold 2"
    And I capture saved hierarchy dump "uidump-gold-coins-fold-3.xml" as "gold coins fold 3"
    And I capture saved hierarchy dump "uidump-gold-coins-fold-4.xml" as "gold coins fold 4"
    And I capture saved hierarchy dump "uidump-gold-coins-fold-5.xml" as "gold coins fold 5"
    And I capture saved hierarchy dump "uidump-gold-coins-fold-6.xml" as "gold coins fold 6"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
