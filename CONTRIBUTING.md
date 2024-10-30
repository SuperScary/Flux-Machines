# Contribution Guidelines for Flux Machines

Thank you for your interest in contributing to Flux Machines! To ensure a smooth development process and maintain the quality of our codebase, please follow these guidelines when creating a pull request.

## Table of Contents
1. [Getting Started](#getting-started)
2. [Code Style Guidelines](#code-style-guidelines)
3. [Feature Proposals](#feature-proposals)
4. [Branching and Workflow](#branching-and-workflow)
5. [Testing Your Changes](#testing-your-changes)
6. [Creating a Pull Request](#creating-a-pull-request)
7. [Code of Conduct](#code-of-conduct)

---

### 1. Getting Started

1. **Fork the Repository**: Start by forking the main *Flux Machines* repository to your GitHub account.
2. **Clone the Repository**: Clone your fork locally to start working on it.
   ```bash
   git clone https://github.com/your-username/flux-machines.git
   ```
3. **Install Dependencies**: Make sure to install any dependencies required to build the mod. Refer to the README for the necessary setup instructions.

### 2. Code Style Guidelines

To keep the codebase clean and readable, please adhere to the following style guidelines:
- **Formatting**: Use consistent formatting (indent with spaces, not tabs, and maintain standard brace styles).
- **Naming Conventions**: Follow standard Java naming conventions (e.g., `camelCase` for variables, `PascalCase` for classes).
- **Comments and Documentation**: Add meaningful comments where necessary and document complex functions and classes.
- **Avoid Large Changes**: Keep pull requests focused. Avoid bundling multiple features or fixes into a single pull request.

### 3. Feature Proposals

If you’re considering a major change or new feature, please submit an issue first to discuss it with the maintainers and community. This ensures the feature aligns with the project's goals and that there isn’t overlapping work.

### 4. Branching and Workflow

1. **Create a Branch**: For each contribution, create a new branch with a descriptive name.
   ```bash
   git checkout -b feature/your-feature-name
   ```
2. **Commit Frequently**: Commit changes often with clear, concise messages. Each commit should ideally represent a single logical change.

### 5. Testing Your Changes

1. **Unit Tests**: If adding new functionality, include unit tests where possible. Place all tests in the `/src/test` directory.
2. **Minecraft Environment Testing**: Run the mod in a Minecraft testing environment to ensure compatibility and functionality.
3. **Bug Fixes**: For bug-related pull requests, include a description and steps for replicating the issue.

### 6. Creating a Pull Request

1. **Push Your Branch**: Once your changes are complete and tested, push your branch to your fork.
   ```bash
   git push origin feature/your-feature-name
   ```
2. **Submit the Pull Request**: Open a pull request (PR) on the main *Flux Machines* repository. Follow the PR template and include:
    - A descriptive title.
    - A summary of your changes.
    - Any related issue numbers.
    - Screenshots or videos, if applicable.
3. **Review Process**: Wait for maintainers to review your PR. Be open to feedback, and make requested changes promptly.

### 7. Code of Conduct

Please adhere to our [Code of Conduct](CODE_OF_CONDUCT.md) to foster a welcoming and respectful environment for all contributors.

Thank you for contributing to *Flux Machines*! We appreciate your efforts to improve the mod and make it even better.

---

This guide provides a structured approach to contributions, helping developers ensure their work aligns with the project’s goals and standards.