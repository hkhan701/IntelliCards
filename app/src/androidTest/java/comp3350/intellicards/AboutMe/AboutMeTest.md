# About Me System Tests
## Total Flashcards
1. Login as a Registered User (username: user71, password: pass71)
2. Verify Login
3. Select ‘profile’ button 
4. Select ‘about me’ button
5. Verify total flashcard count is 0
6. Return to sets page
7. Select ‘set 7.1’ button
8. Create a new flashcard with fields “test question”/”answer”/”helpful hint”
9. Confirm creation of new flashcard
10. Return to the ‘about me’ page
11. Verify total flashcard count is 1
12. Click back button
13. Click logout button

## Total Sets
1. Login as a Registered User (username: user72, password: pass72)
2. Verify Login
3. Select ‘profile’ button
4. Select ‘about me’ button
5. Verify total sets is 0
6. Return to the home page
7. Create a new set
8. Return to the ‘about me’ page
9. Verify total sets is 1
10. Click back button
11. Click logout button

## Login Count
1. Login as a Registered User (username: user73, password: pass73)
2. Verify Login
3. Select ‘profile’ button
4. Select ‘about me’ button
5. Verify total logins is 1
6. Click back button
7. Click logout button
8. Login again as the same registered user (username: user73, password: pass73)
9. Verify Login
10. Select ‘profile’ button 
11. Select ‘about me’ button
12. Verify total logins is 2
13. Click back button
14. Click logout button

## Accuracy Report
1. Login as a Registered User (username: user74, password: pass74)
2. Verify Login
3. Select ‘profile’ button 
4. Select ‘about me’ button
5. Verify accuracy report is 0/0
6. Return to home page
7. Select ‘set 7.4’
8. Select ‘test’ button
9. Answer 1 card correctly, and 1 incorrectly
10. Return to ‘about me’ page
11. Verify accuracy report is 1/2
12. Click back button
13. Click logout button