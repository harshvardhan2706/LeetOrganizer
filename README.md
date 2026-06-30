# 🚀 LeetOrganizer

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange" />
  <img src="https://img.shields.io/badge/Maven-Build-blue" />
  <img src="https://img.shields.io/badge/Platform-Windows-success" />
  <img src="https://img.shields.io/badge/License-MIT-green" />
  <img src="https://img.shields.io/badge/Status-Stable-brightgreen" />
</p>

> **LeetOrganizer** is a Java-based desktop automation tool that automatically organizes LeetCode solutions into language-specific folders, generates a professional README, and synchronizes the repository with GitHub.

---

# ✨ Features

- 📂 Automatically organizes LeetCode solutions by programming language.
- ☕ Supports Java solutions.
- 🗄 Supports SQL solutions.
- 📄 Automatically generates a clean `README.md`.
- 📊 Displays solution statistics.
- 🔄 Git integration.
- ⬇ Automatically pulls the latest repository changes.
- ⬆ Automatically commits and pushes updates to GitHub.
- 🚀 One-click execution using a Windows EXE.
- ⚡ Built using Java 21 and Maven.
- 🛠 Easily extensible for additional languages.

---

# 📸 Workflow

```text
LeetCode Submission
        │
        ▼
LeetHub Updates Repository
        │
        ▼
LeetOrganizer
        │
        ├── Pull Latest Changes
        ├── Scan Repository
        ├── Detect Language
        ├── Organize Files
        ├── Generate README
        ├── Commit Changes
        └── Push to GitHub
```

---

# 📁 Repository Structure

```text
LeetCode-Solutions
│
├── Java
│   ├── 0001-two-sum
│   ├── 0002-add-two-numbers
│   └── ...
│
├── SQL
│   ├── 0175-combine-two-tables
│   ├── 0176-second-highest-salary
│   └── ...
│
└── README.md
```

---

# ⚙️ How It Works

### Step 1

The application connects to your local LeetCode repository.

### Step 2

It scans every newly added problem folder.

### Step 3

It detects the programming language.

Example

```
0001-two-sum
```

↓

```
Java/
    0001-two-sum
```

Example

```
0175-combine-two-tables
```

↓

```
SQL/
    0175-combine-two-tables
```

---

### Step 4

Automatically generates an updated README.

---

### Step 5

Synchronizes changes with GitHub.

```
git pull
git add .
git commit
git push
```

---

# 📊 Example Console Output

```text
=========================================
         LeetOrganizer v1.0
=========================================

Pulling latest changes...

Scanning repository...

Problems Found : 5

Moving Java Problems...
Moving SQL Problems...

Generating README...

Git Synchronization...

Repository Updated Successfully.

Done.
```

---

# 🏗 Project Structure

```text
LeetOrganizer
│
├── src
│   ├── main
│   │   ├── java
│   │   │
│   │   ├── Console.java
│   │   ├── Config.java
│   │   ├── Constants.java
│   │   ├── FolderScanner.java
│   │   ├── GitManager.java
│   │   ├── Organizer.java
│   │   ├── ReadmeGenerator.java
│   │   ├── Statistics.java
│   │   └── Main.java
│
├── config.properties
├── pom.xml
└── README.md
```

---

# 🛠 Technologies Used

- Java 21
- Maven
- Git
- GitHub
- Java NIO
- ProcessBuilder
- File System API

---

# 🚀 Getting Started

## Clone Repository

```bash
git clone https://github.com/harshvardhan2706/LeetOrganizer.git
```

---

## Build

```bash
mvn clean package
```

---

## Run

```bash
java -jar target/LeetOrganizer-1.0.0.jar
```

---

## Windows

Simply double-click

```
LeetOrganizer.exe
```

---

# ⚙ Configuration

Edit `config.properties`

```properties
repository.path=C:/Users/YourName/Documents/GitHub/LeetCode-Solutions
```

---

# 📈 Current Capabilities

| Feature | Status |
|----------|:------:|
| Java Support | ✅ |
| SQL Support | ✅ |
| Auto Organization | ✅ |
| README Generation | ✅ |
| Git Pull | ✅ |
| Git Commit | ✅ |
| Git Push | ✅ |
| Windows EXE | ✅ |

---

# 🚧 Roadmap

## Version 1.1

- Support Python
- Support C++
- Support JavaScript
- Better logging

---

## Version 1.2

- GUI Application
- Drag-and-drop repository selection
- Settings page

---

## Version 2.0

- Folder Watcher
- Background synchronization
- Windows Notifications
- Auto-start with Windows

---

## Version 3.0

Support additional coding platforms

- GeeksforGeeks
- HackerRank
- CodeChef
- Codeforces
- AtCoder

---

# 🤝 Contributing

Contributions are welcome!

If you have ideas for improvements or discover a bug:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Open a Pull Request

---

# 📄 License

This project is licensed under the **MIT License**.

---

# 👨‍💻 Author

**Harshvardhan Gandharv**

- GitHub: https://github.com/harshvardhan2706
- LinkedIn: https://www.linkedin.com/in/harshvardhan-gandharv/

---

# ⭐ Support

If you found this project useful,

**Please consider giving it a ⭐ on GitHub!**

It motivates future improvements and helps others discover the project.

---

<p align="center">
Made with ❤️ using Java
</p>

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
