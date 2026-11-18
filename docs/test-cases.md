# Manual Test Cases — SauceDemo

**Priority values:** High, Med, and Low. Preconditions are listed per case; unless stated otherwise, use a current supported browser and `secret_sauce` for seeded users.

| ID | Title | Preconditions | Steps | Expected Result | Priority |
|---|---|---|---|---|---|
| TC-001 | Login with standard user | Login page open | Enter `standard_user` / `secret_sauce`; select Login | User lands on Products page; six products are visible | High |
| TC-002 | Login with empty fields | Login page open | Leave both fields empty; select Login | Required-username validation is displayed; user remains on login page | High |
| TC-003 | Login with empty username | Login page open | Enter password only; select Login | Username-required validation is displayed | High |
| TC-004 | Login with empty password | Login page open | Enter `standard_user` only; select Login | Password-required validation is displayed | High |
| TC-005 | Login with invalid password | Login page open | Enter valid username and incorrect password; select Login | Generic invalid-credentials message is displayed; no authenticated session is created | High |
| TC-006 | Login with unknown username | Login page open | Enter an unknown username and `secret_sauce`; select Login | Generic invalid-credentials message is displayed | Med |
| TC-007 | Locked-out user | Login page open | Enter `locked_out_user` / `secret_sauce`; select Login | Access is blocked and the locked-out message is displayed | High |
| TC-008 | Problem user login | Login page open | Enter `problem_user` / `secret_sauce`; select Login | Login succeeds; record any account-specific visual/functional anomalies | Med |
| TC-009 | Performance user login timing | Login page open; timing method available | Start timer at Login click; stop when Products is visible; repeat 3 times for `standard_user` and `performance_glitch_user` | Both users reach Products; record all six times; performance user's median is at most 5 seconds and its maximum is reported | Med |
| TC-010 | Password masking | Login page open | Type password; inspect the password field | Characters are masked; password is not exposed as plain text | Med |
| TC-011 | Login error dismissal | A login validation error is visible | Use the close control on the error banner | Error banner closes and does not overlap the login controls | Low |
| TC-012 | Product listing content | Logged in as standard user | Inspect all product cards | Each card shows a product name, description, price, image, and Add to cart action | High |
| TC-013 | Product image/name consistency | Logged in as standard user | Open each product detail page; compare title, image, price and description with its card | Details correspond to the selected product | High |
| TC-014 | Sort names A–Z | Products page visible | Select Name (A to Z) | Product names are in ascending alphabetical order | Med |
| TC-015 | Sort names Z–A | Products page visible | Select Name (Z to A) | Product names are in descending alphabetical order | Med |
| TC-016 | Sort prices low–high | Products page visible | Select Price (low to high) | Numeric prices are in ascending order (not lexicographic order) | High |
| TC-017 | Sort prices high–low | Products page visible | Select Price (high to low) | Numeric prices are in descending order | High |
| TC-018 | Sort persists after product navigation | Products page visible | Select a non-default sort; open a product; navigate back | Sort order remains selected, or any reset is consistent with documented product behaviour | Low |
| TC-019 | Add one product | Products page visible; cart empty | Add Sauce Labs Backpack | Button changes to Remove; cart badge shows 1 | High |
| TC-020 | Add multiple products | Products page visible; cart empty | Add three different products | Cart badge shows 3; each selected product is shown once in cart | High |
| TC-021 | Remove product from inventory | One product already added | Select Remove on that product | Product is removed and cart badge decreases correctly | High |
| TC-022 | Remove product from cart | Cart contains at least one product | Remove one product from cart | Removed product disappears; badge and remaining items are consistent | High |
| TC-023 | Cart contents persist while navigating | One or more products added | Open cart; continue shopping; return to cart | Selected items and count remain consistent | Med |
| TC-024 | Empty cart display | Cart is empty | Open cart | Empty cart state is clear; no phantom cart line items or badge are shown | Med |
| TC-025 | Attempt checkout with empty cart | Cart is empty | Select Checkout; attempt to continue with valid checkout information | Empty-cart purchase must not be silently submitted; confirm expected rule with product owner and record actual behaviour | High |
| TC-026 | Checkout with all fields blank | Cart contains one product | Open Checkout; leave first name, last name, postal code blank; select Continue | Required-field validation appears and progression is blocked | High |
| TC-027 | Checkout with missing first name | Cart contains one product | Fill last name and postal code only; select Continue | First-name validation appears; user remains on step one | High |
| TC-028 | Checkout with missing last name | Cart contains one product | Fill first name and postal code only; select Continue | Last-name validation appears; user remains on step one | High |
| TC-029 | Checkout with missing postal code | Cart contains one product | Fill first and last name only; select Continue | Postal-code validation appears; user remains on step one | High |
| TC-030 | Checkout with apostrophe/hyphen/Unicode names | Cart contains one product | Enter values such as `O'Neil`, `Anne-Marie`, and `José`; continue | Valid name punctuation/Unicode is handled consistently; values are not corrupted | Med |
| TC-031 | Checkout with special characters / markup | Cart contains one product | Enter harmless markup-like text such as `<b>QA</b>` in name; enter `@@@###` as postal code; continue | Input is handled safely as text; no script/markup is executed; format acceptance/rejection follows agreed rules | Med |
| TC-032 | Review order totals | Cart contains products with known prices | Complete information step; compare item subtotal, tax, and total | Subtotal equals sum of item prices; total equals subtotal plus displayed tax | High |
| TC-033 | Complete checkout | Cart contains one product | Enter valid information; Continue; verify overview; Finish | Confirmation page is shown once and order completion message is visible | High |
| TC-034 | Browser Back after checkout completion | Checkout has been completed | Use browser Back, then Forward; inspect order state and buttons | Completed order is not duplicated; navigation does not create an unintended second order | Med |
| TC-035 | Logout and route protection | Logged in | Open menu; select Logout; attempt direct navigation to `/inventory.html` | User returns to login; protected inventory is not available without authentication | High |
| TC-036 | Responsive layout | Logged in; desktop and 390×844 viewport available | Resize viewport; inspect menu, cards, sort control, cart and checkout forms | Controls remain usable; content is not clipped or overlapping | Med |

## Execution record

Use the following table in a copy of this file or in your issue tracker after executing the cases. Do not mark a case Pass based only on expected behaviour.

| ID range | Status | Actual result / evidence | Defect ID |
|---|---|---|---|
| TC-001–TC-012 | Not Run |  |  |
| TC-013–TC-024 | Not Run |  |  |
| TC-025–TC-036 | Not Run |  |  |
