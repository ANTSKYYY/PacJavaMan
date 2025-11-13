
---

# 🕹️ PARTIE 1 : PacmanJava (Jeu en Console)

Ce projet est une implémentation simplifiée du jeu classique Pac-Man en Java, conçue pour être jouée directement dans le terminal. Il utilise la programmation orientée objet (POO) pour gérer les entités du jeu (personnages, plateau, cases) 

---

## ✨ Fonctionnalités Implémentées

Le jeu inclut les mécaniques suivantes :

* **Génération Procédurale :** Le plateau de jeu est généré aléatoirement au lancement (taille 15x15), garantissant un chemin solvable et multiple (labyrinthe avec boucles) vers toutes les gommes.
* **Mouvement et Collision :**
    * Gestion des collisions avec les murs pour Pac-Man et les Fantômes.
    * Détection avancée des collisions : le jeu se termine si Pac-Man et un Fantôme se retrouvent sur la même case ou s'ils se croisent au milieu du chemin durant le même tour.
* **Affichage Dynamique :** L'écran de la console est effacé à chaque tour pour un rafraîchissement fluide.
* **Validation Robuste de l'Entrée :** Le jeu n'accepte qu'un seul caractère à la fois pour le déplacement (`z`, `q`, `s`, `d`). Toute autre entrée est rejetée sans avancer le tour des Fantômes.
* **Fin de Jeu :** Affichage d'un 'X' rouge sur le point de collision en cas de Game Over, ou d'un message de victoire après avoir mangé toutes les gommes, avec attente de confirmation avant de quitter.

---

## 💡 Note sur la Génération de Plateau (Fonctionnalité Bonus)

La méthode clé pour la rejouabilité, `public static Plateau genererPlateau(...)`, a été développée avec l'aide d'une Intelligence Artificielle (IA), car elle représente un défi algorithmique complexe (la génération de labyrinthes parfaits avec cycles) qui dépasse les exigences initiales du projet.

Cette fonction est un bonus qui garantit un plateau toujours unique, solvable, et avec plusieurs chemins d'accès aux gommes.

### Explications Algorithmiques (Ligne par Ligne)

La méthode utilise l'algorithme de **Retour Arrière Récursif (Recursive Backtracker)**, une variante de la recherche en profondeur (DFS), pour créer le labyrinthe.

| Section | Code | Description Détaillée |
| :---: | :--- | :--- |
| **Initialisation** | `Plateau p = new Plateau(lignes, colonnes);` | Crée une instance du plateau. |
| **Étape 1** | `for (int i=0... p.grille[i][j] = new Mur();` | Initialise toute la grille avec des murs (`Mur`). |
| **Point de Départ** | `p.grille[startX][startY] = new CaseVide(); stack.push(new int[]{startX, startY});` | Définit la case de départ comme vide et commence le creusement. |
| **Étape 2: Creusement DFS** | `while (!stack.isEmpty()) { ... }` | Boucle principale qui creuse des chemins à travers les murs en utilisant une pile (DFS). |
| **Étape 3: Création de Boucles** | `double P_loop = 0.15; for (int i=1...` | **Ajout du bonus.** Parcourt les murs pour en supprimer aléatoirement (15% de chance) afin de créer des cycles (plusieurs chemins). |
| **Étape 4: Placement des Gommes** | `if (p.grille[i][j] instanceof CaseVide) { ... }` | Parcourt toutes les cases vides créées. |
| **Gommes** | `if (!isSpawn && rand.nextDouble() < 0.5) { p.grille[i][j] = new Gomme(); }` | 50% de chance de placer une gomme si la case n'est pas un point de spawn de personnage. |

---

## 🛠️ Comment Jouer et Lancer le Projet

### Compilation et Exécution

1.  **Compiler les fichiers :** Ouvrez votre terminal dans le répertoire `src` et compilez les classes :
    ```bash
    javac pacman/*.java
    ```

2.  **Lancer le jeu :** Exécutez la classe principale `Jeu` :
    ```bash
    java pacman.Jeu
    ```

### Commandes de Jeu

Le jeu utilise les contrôles du clavier AZERTY :

| Touche | Action |
| :----: | :----- |
| **Z** | Haut |
| **S** | Bas |
| **Q** | Gauche |
| **D** | Droite |

---

# 🚧 PARTIE 2 : Map (En Cours)

* **Statut :** En cours de travail.


---

## 📜 Licence

Ce projet est distribué sous la licence **Creative Commons Zero v1.0 Universal** (CC0 1.0 Universal). Vous pouvez utiliser, modifier et distribuer le code librement.
