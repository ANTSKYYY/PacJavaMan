package pacman;

import java.util.Stack;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Plateau {
    private Case[][] grille;

    public Plateau(int lignes, int colonnes) {
        grille = new Case[lignes][colonnes];
        for (int i = 0; i < lignes; i++)
            for (int j = 0; j < colonnes; j++)
                grille[i][j] = new CaseVide();
    }

    public static Plateau aPartirDeTexte(String txt, int lignes, int colonnes) {
        Plateau p = new Plateau(lignes, colonnes);
        int k = 0;
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                char c = txt.charAt(k++);
                switch (c) {
                    case 'X': p.grille[i][j] = new Mur(); break;
                    case '⋅': p.grille[i][j] = new Gomme(); break;
                    default:  p.grille[i][j] = new CaseVide();
                }
            }
        }
        return p;
    }

    
    public static Plateau genererPlateau(int lignes, int colonnes, int startX, int startY, List<int[]> ghostSpots) {
        Plateau p = new Plateau(lignes, colonnes);
        
        // 1. Initialiser la grille avec des murs
        for (int i = 0; i < lignes; i++)
            for (int j = 0; j < colonnes; j++)
                p.grille[i][j] = new Mur();
        
        Stack<int[]> stack = new Stack<>();
        Random rand = new Random();

        // Initialiser le point de départ
        p.grille[startX][startY] = new CaseVide();
        stack.push(new int[]{startX, startY});
        
        int[][] dirs = {{0, 2}, {0, -2}, {2, 0}, {-2, 0}};

        // 2. Creuser les chemins (Maze Generation - DFS)
        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int cx = current[0];
            int cy = current[1];
            
            List<int[]> neighbors = new ArrayList<>();
            
            for (int[] dir : dirs) {
                int nx = cx + dir[0];
                int ny = cy + dir[1];
                
                // Vérifie les limites et s'assure que la cellule voisine est encore un Mur
                if (nx > 0 && nx < lignes - 1 && ny > 0 && ny < colonnes - 1 && 
                    p.grille[nx][ny] instanceof Mur) {
                    neighbors.add(new int[]{nx, ny, dir[0], dir[1]}); 
                }
            }
            
            if (!neighbors.isEmpty()) {
                // Choisir un voisin aléatoire et creuser le mur entre eux
                int[] next = neighbors.get(rand.nextInt(neighbors.size()));
                int nx = next[0];
                int ny = next[1];
                int wall_x = cx + next[2] / 2;
                int wall_y = cy + next[3] / 2;
                
                p.grille[wall_x][wall_y] = new CaseVide(); 
                p.grille[nx][ny] = new CaseVide(); 
                stack.push(new int[]{nx, ny});
            } else {
                stack.pop(); 
            }
        }
        
        // 3. Phase de création de boucles (multiples chemins)
        double P_loop = 0.15; // 15% de chance d'ouvrir un mur
        for (int i = 1; i < lignes - 1; i++) {
            for (int j = 1; j < colonnes - 1; j++) {
                // On vérifie uniquement les murs (i.e. les cases non creusées)
                if (p.grille[i][j] instanceof Mur && rand.nextDouble() < P_loop) {
                    
                    boolean canOpen = false;
                    
                    // Si le mur est horizontal (au moins une CaseVide de chaque côté)
                    if (j > 0 && j < colonnes - 1 && 
                        p.grille[i][j - 1] instanceof CaseVide && p.grille[i][j + 1] instanceof CaseVide) {
                        canOpen = true;
                    }
                    // Si le mur est vertical
                    else if (i > 0 && i < lignes - 1 && 
                            p.grille[i - 1][j] instanceof CaseVide && p.grille[i + 1][j] instanceof CaseVide) {
                        canOpen = true;
                    }

                    if (canOpen) {
                        p.grille[i][j] = new CaseVide(); // Ouvrir le mur pour créer une boucle
                    }
                }
            }
        }

        
        // 4. Placer les Gommes (Dots) dans les chemins ouverts
        for (int i = 1; i < lignes - 1; i++) {
            for (int j = 1; j < colonnes - 1; j++) {
                if (p.grille[i][j] instanceof CaseVide) {
                    
                    // Vérifie si l'emplacement est un point de spawn (Pacman ou Fantôme)
                    boolean isSpawn = (i == startX && j == startY);
                    for (int[] spot : ghostSpots) {
                        if (i == spot[0] && j == spot[1]) {
                            isSpawn = true;
                            break;
                        }
                    }
                    
                    // 50% de chance de placer une gomme si ce n'est pas un point de spawn
                    if (!isSpawn && rand.nextDouble() < 0.5) { 
                        p.grille[i][j] = new Gomme();
                    }
                }
            }
        }

        return p;
    }


    public Case getCase(int x, int y) { return grille[x][y]; }

    public void setCase(int x, int y, Case c) { grille[x][y] = c; }

    public class ClearConsole {

        public static void clear() {
            try {
                if (System.getProperty("os.name").contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        public static void main(String[] args) {
            System.out.println("Avant le clear");
    
            
            clear();
    
            System.out.println("Après le clear");
        }
    }
    

    public void afficher(Personnage pacman, Fantome[] fantomes) {
        
        ClearConsole.clear();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                boolean aff = false;
    
                
                if (pacman.getX() == i && pacman.getY() == j) {
                    System.out.print(Couleur.JAUNE + "C" + Couleur.RESET);
                    aff = true;
                }
    
                
                for (Fantome f : fantomes) {
                    if (f.getX() == i && f.getY() == j) {
                        System.out.print(Couleur.ROUGE + f.getNom().charAt(0) + Couleur.RESET);
                        aff = true;
                        break;
                    }
                }
    
                
                if (!aff)
                    System.out.print(grille[i][j].afficher());
            }
            System.out.println();
        }
    }
    
    
    public void afficher(Personnage pacman, Fantome[] fantomes, int gameOverX, int gameOverY) {
        
        ClearConsole.clear();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                boolean aff = false;
    
                
                if (i == gameOverX && j == gameOverY) {
                    System.out.print(Couleur.ROUGE + "X" + Couleur.RESET);
                    aff = true;
                }
                
               
                for (Fantome f : fantomes) {
                    if (!aff && f.getX() == i && f.getY() == j) {
                        System.out.print(Couleur.ROUGE + f.getNom().charAt(0) + Couleur.RESET);
                        aff = true;
                        break;
                    }
                }
    
                
                if (!aff)
                    System.out.print(grille[i][j].afficher());
            }
            System.out.println();
        }
    }
    

    public boolean toutesGommesMangees() {
        for (int i = 0; i < grille.length; i++)
            for (int j = 0; j < grille[0].length; j++)
                if (grille[i][j] instanceof Gomme g && !g.estMangee())
                    return false;
        return true;
    }
}