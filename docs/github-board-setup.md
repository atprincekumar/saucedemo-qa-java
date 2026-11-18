# GitHub Issues and Project board setup

The two confirmed findings are BUG-001 and BUG-003. BUG-002 remains an unconfirmed investigation and should not be published as a confirmed defect. Complete the GitHub setup once this folder is pushed to the intended public repository.

## 1. Create a public repository and push

From the project root:

```bash
git init
git add .
git commit -m "Add Java Selenium and REST Assured QA assignment"
git branch -M main
# Create an empty public repository on GitHub, then:
git remote add origin https://github.com/<your-username>/saucedemo-qa-java.git
git push -u origin main
```

## 2. Create three GitHub Issues

Create one issue for each confirmed bug report in `docs/bug-reports/`. Include title, actual environment, account, steps, expected result, actual result, severity, priority, and attach your screenshot. Do not create or publish an issue for a candidate that you could not reproduce.

## 3. Create a Project board

Create a GitHub Project (Board view) linked to the repository. Configure a Status field with exactly these options:

- To Do
- In Progress
- Done

Add each confirmed issue to the project. New issues should start in **To Do**. Move issues to **In Progress** when investigating and **Done** only after the fix/verification is complete.

## 4. Add board link to README

After creating the Project, add its URL to the README and include the repository and Project URLs in your assignment submission.
