# 🎮 GameBox: Interactive JavaFX Game Suite

> A curated collection of mini cognitive games powered by **JavaFX** and structured around fundamental **Data Structures and Algorithms (DSA)**.  
> Designed for both fun and functionality — supporting cognitive stimulation and educational exploration.

![Java](https://img.shields.io/badge/built%20with-Java%2019-blue)
![JavaFX](https://img.shields.io/badge/UI-JavaFX%2019-brightgreen)
![DSA](https://img.shields.io/badge/focus-DSA%20Algorithms-orange)

---

## 📦 Overview

GameBox is a **JavaFX desktop application** consisting of multiple logic and reflex-based mini-games. Each game is designed to:
- Demonstrate the practical use of **core data structures**
- Provide engaging user interaction with **cognitive stimulation**
- Support **timed and untimed** modes for flexible play

> Originally developed as a **Final Group Project** under **Prof. Jones Yu** for the **Program Structure and Algorithms** course.

---

## 🧠 Use Case & Audience

- 🧓 Ideal for **memory training** or **cognitive therapy** (e.g., Alzheimer’s)
- 🧑‍💻 Perfect for **CS learners** to visualize **DSA** in action
- 🎮 Suitable for casual play or focused mental exercises

---


## 🎲 Game Modules

### 🃏 1. **Card Match Game**
- **Logic**: Flip cards to find emoji pairs
- **Data Structures**: `HashMap<Button, String>`, `List<String>`
- **Features**: Move tracking, real-time match feedback, dynamic shuffling

### 🧠 2. **Memory Recall Game**
- **Logic**: View emojis briefly and recall later
- **Data Structures**: `Set`, `List`
- **Features**: Promotes short-term memory and recognition skills

### 🚦 3. **Reaction Timer Game**
- **Logic**: Click when prompted to test reflex speed
- **Data Structures**: `Queue`, `PriorityQueue`
- **Features**: Tracks reaction times, stores top scores, round-based play

### 📚 4. **Flashcard Game**
- **Logic**: Match icon to correct label from 4 options
- **Data Structures**: `List<Flashcard>`
- **Features**: Random flashcard generation, flexible timer mode

### 🧭 5. **Path Finder Game**
- **Logic**: Navigate 5x5 grid using BFS to find shortest path
- **Data Structures**: `Queue<Node>`, Graph via 2D Grid
- **Features**: Dynamic grid generation, blocked path simulation

### 🌲 6. **Binary Search Tree Game**
- **Logic**: Traverse BST to guess hidden number
- **Data Structures**: `TreeNode`, `Stack`, Recursion
- **Features**: Educational for BST traversal, user-driven search

---

## 🛠 Tech Stack

| Component       | Technology                       |
|----------------|----------------------------------|
| Language        | Java 19                          |
| UI Framework    | JavaFX SDK 19                    |
| IDE             | Eclipse                          |
| Version Control | Git                              |
| Data Structures | List, Set, HashMap, Queue, Stack, TreeNode, Recursion |

---

## 📐 Architecture Diagram (High-Level)

```plaintext
[GameBox Launcher]
        |
        ├── Card Match Game         (HashMap, List)
        ├── Memory Recall Game      (Set, List)
        ├── Flashcard Game          (List)
        ├── Reaction Game           (Queue, PriorityQueue)
        ├── Path Finder Game        (Grid Graph, BFS)
        └── Binary Search Tree Game (Stack, Tree, Recursion)
