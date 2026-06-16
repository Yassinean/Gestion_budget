# 💰 Budget Manager

Application desktop de gestion de budget personnel développée en **Java 17**, **JavaFX** et **PostgreSQL**.

Cette application permet aux utilisateurs de gérer leurs budgets, catégories, revenus et dépenses afin de suivre leur situation financière à travers un tableau de bord interactif.

---

# 🚀 Fonctionnalités

## 👤 Gestion des comptes

- Création de compte utilisateur
- Connexion sécurisée
- Gestion du profil utilisateur

## 💼 Gestion des budgets

- Création de plusieurs budgets
- Modification d'un budget
- Suppression d'un budget
- Gestion de la devise

## 📂 Gestion des catégories

- Création de catégories personnalisées
- Modification des catégories
- Suppression des catégories
- Association des catégories à un budget

## 💸 Gestion des transactions

- Ajout de revenus
- Ajout de dépenses
- Modification des transactions
- Suppression des transactions
- Recherche de transactions
- Filtrage par date

## 📊 Tableau de bord

- Total des revenus
- Total des dépenses
- Solde actuel
- Nombre total de transactions
- Répartition des dépenses par catégorie (Pie Chart)
- Évolution mensuelle des revenus et dépenses (Bar Chart)

---

# 🏗️ Architecture

Le projet suit une architecture en couches inspirée du modèle MVC.

```text
src/main/java
│
├── model
│   ├── Account
│   ├── Budget
│   ├── Category
│   └── Transaction
│
├── dao
│   ├── interfaces
│   └── implementations
│
├── service
│   ├── interfaces
│   └── implementations
│
├── controller
│
├── view
│   ├── fxml
│   └── css
│
├── util
│
└── config