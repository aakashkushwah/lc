# How to push this repository to GitHub

This project is set up with a sensible `.gitignore` for a Spring Boot project. The repository has been initialized locally and an initial commit has been created containing only source and config files. Generated files like `/build`, compiled classes, and IDE files are excluded via `.gitignore`.

Follow these steps to create a remote repository on GitHub and push your code:

1. Create a repository on GitHub either via the website or the GitHub CLI.
   - Website: Go to https://github.com/new, name the repository `lc` (or any name you prefer), choose Public or Private, and do NOT initialize with a README or .gitignore (we already have one).
   - GitHub CLI: If you have `gh` installed and authenticated, you can run:

```bash
# create a private repo under your account, replace 'your-username' and/or set --public
gh repo create your-username/lc --private --source=. --remote=origin --push
```

2. If you used the website to create the repo, you'll see the commands to add the remote and push. Run the commands from your project directory (example):

```bash
# replace <USERNAME> and <REPO> with your values
git remote add origin git@github.com:<USERNAME>/<REPO>.git
# or if you prefer https:
# git remote add origin https://github.com/<USERNAME>/<REPO>.git

git branch -M main

git push -u origin main
```

3. Verify on GitHub that the repository contains the project source files and not generated files (like `build/` or `*.class`).

Notes
- The included `.gitignore` excludes build output and class files. If you have additional generated directories in your environment, add them to `.gitignore` before pushing.
- If you'd like, I can attempt to install or use `gh` to create the remote automatically, or I can create the remote via the GitHub API if you provide a token. For security, I recommend you run the `git remote add` and `git push` commands locally.

