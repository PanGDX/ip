# Sigmund User Guide

Sigmund is a powerful command-line task manager designed to help you stay organized with a simple, conversational interface. Whether you are tracking daily chores, upcoming deadlines, or scheduled events, Sigmund keeps everything in one place.

## Adding Deadlines: `deadline`

Adds a task that needs to be completed by a specific date or time. After entering the command, Sigmund will prompt you for the "By" date.

**Usage:**
`deadline <description>`

**Example:**
`deadline Submit report`

**Expected Outcome:**
Sigmund will ask for the deadline. Once provided, the task is added to your list.
```
By: 25/Jun/2025 4pm
Got it. I've added this task:
  [D][ ] Submit report (by: Jun 25 2025)
Now you have 3 tasks in the list.
```

## Adding Todos: `todo`

Adds a simple task without any date or time attached.

**Usage:**
`todo <description>`

**Example:**
`todo Buy groceries`

**Expected Outcome:**
```
Got it. I've added this task:
  [T][ ] Buy groceries
Now you have 1 task in the list.
```

## Adding Events: `event`

Adds a task with a start and end time. After entering the description, Sigmund will prompt you for the "From" and "To" details.

**Usage:**
`event <description>`

**Example:**
`event Project meeting`

**Expected Outcome:**
```
From: 22/06/2025 14:00
To: 22/06/2025 16:00
Got it. I've added this task:
  [E][ ] Project meeting (from: Jun 22 2025 to: Jun 22 2025)
```

## Managing Tasks: `list`, `mark`, `delete`

View and organize your current tasks.

*   **List:** Type `list` or `ls` to see all current tasks.
*   **Mark/Tick:** Use `mark <number>` or `tick <number>` to complete a task.
*   **Unmark/Untick:** Use `unmark <number>` or `untick <number>` to revert a task to incomplete.
*   **Delete:** Use `delete <number>` to permanently remove a task.

**Example:**
`mark 1`

**Expected Outcome:**
```
Nice! I've marked this task as done:
  [T][X] Buy groceries
```

## Finding Tasks: `find`

Search for tasks by searching for a keyword in their description.

**Usage:**
`find <keyword>`

**Example:**
`find book`

**Expected Outcome:**
```
Here are the matching tasks in your list:
1. [T][ ] Read book
2. [D][ ] Return library book (by: Jun 30 2025)
```

## Supported Date and Time Formats

Sigmund is flexible with how you input dates. You can use slashes, spaces, 24-hour time, or 12-hour time (with or without minutes).

**Valid Formats Include:**
*   **Standard:** `25/06/2025 15:00`
*   **Named Months:** `25/Jun/2025 4pm` or `25 Jun 2025`
*   **Short Time:** `20/02/2026 4pm` (automatically recognized as 16:00)
*   **Date Only:** `22/12/2025` (defaults to midnight)

## Exiting the Program: `bye`

Closes the application.

**Usage:**
`bye` or `exit`

**Expected Outcome:**
```
BYE BYE! See you again!
```
