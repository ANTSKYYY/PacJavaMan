package pacman;
import java.util.*;

public class Jeu {
    private Plateau plateau;
    private Pacman pacman;
    private Fantome[] fantomes;

    public Jeu() {
        int lignes = 15;
        int colonnes = 15;
        
        int pacmanX = 1; 
        int pacmanY = 1;

       // Spawn des fantomes
        List<int[]> ghostSpawn = Arrays.asList(
            new int[]{1, 13}, 
            new int[]{13, 1}, 
            new int[]{13, 13}, 
            new int[]{7, 7}
        );
        
        // PLateau Random
        plateau = Plateau.genererPlateau(lignes, colonnes, pacmanX, pacmanY, ghostSpawn);

        pacman = new Pacman(plateau, pacmanX, pacmanY);
        fantomes = new Fantome[] {
            new Fantome("I", plateau, 1, 13),
            new Fantome("J", plateau, 13, 1),
            new Fantome("L", plateau, 13, 13),
            new Fantome("M", plateau, 7, 7)
        };
    }
    
    private void displayErrorAndConfirm(Scanner sc, String message) {
        System.out.println(message);
        System.out.print("Appuyez sur ENTRÉE pour continuer...");
        sc.nextLine(); 
    }

    public void jouer() {
        boolean enCours = true;
        Scanner sc = new Scanner(System.in);
        int gameOverX = -1, gameOverY = -1; 
        char move = ' '; 

        while (enCours) {
            
            plateau.afficher(pacman, fantomes);
            
            System.out.print("Déplacement (z,q,s,d): ");
            String input = sc.nextLine().toLowerCase();
            
            
            
            if (input.isEmpty()) {
                displayErrorAndConfirm(sc, "⚠️ " + Couleur.ROUGE + "Erreur : Veuillez entrer une direction (z, q, s, d)." + Couleur.RESET);
                continue; 
            }
            
            if (input.length() > 1) {
                displayErrorAndConfirm(sc, "⚠️ " + Couleur.ROUGE + "Erreur : Veuillez entrer UNE SEULE lettre de direction." + Couleur.RESET);
                continue;
            }
            
            move = input.charAt(0);
            
            if (!(move == 'z' || move == 'q' || move == 's' || move == 'd')) {
                displayErrorAndConfirm(sc, "⚠️ " + Couleur.ROUGE + "Erreur : Caractère non reconnu. Utilisez z, q, s, ou d." + Couleur.RESET);
                continue; 
            }
            
            
            int oldPx = pacman.getX();
            int oldPy = pacman.getY();

            
            int newX = pacman.getX();
            int newY = pacman.getY();

            
            switch (move) {
                case 'z': newX--; break;
                case 's': newX++; break;
                case 'q': newY--; break;
                case 'd': newY++; break;
            }
            
            Case targetCase = null;
            try {
                targetCase = plateau.getCase(newX, newY);
            } catch (ArrayIndexOutOfBoundsException e) {
                targetCase = new Mur();
            }

            if (!(targetCase instanceof Mur)) {
                pacman.setX(newX);
                pacman.setY(newY);
            }
            
           
            int newPx = pacman.getX();
            int newPy = pacman.getY();


            
            for (Fantome f : fantomes) {
                int oldGx = f.getX();
                int oldGy = f.getY();
                
                
                f.deplacer();
                int newGx = f.getX();
                int newGy = f.getY();
                
                
                
                if (newGx == newPx && newGy == newPy) {
                    gameOverX = newPx; 
                    gameOverY = newPy;
                    enCours = false;
                    break;
                }

                
                if (newGx == oldPx && newGy == oldPy && oldGx == newPx && oldGy == newPy) {
                   
                    gameOverX = oldPx;
                    gameOverY = oldPy; 
                    enCours = false;
                    break;
                }
            }

            if (!enCours) break; 


           
            Case caseActuelle = plateau.getCase(pacman.getX(), pacman.getY());
            if (caseActuelle instanceof Gomme g) g.manger();
            
            // Victoire
            if (plateau.toutesGommesMangees()) {
                enCours = false;
            }
        } 

        // Affichage final après la fin de partie
        if (gameOverX != -1) {
            plateau.afficher(pacman, fantomes, gameOverX, gameOverY); 
            System.out.println("💀 " + Couleur.ROUGE + "Game Over !" + Couleur.RESET);
        } else if (plateau.toutesGommesMangees()) {
            plateau.afficher(pacman, fantomes);
            System.out.println("🏆 " + Couleur.JAUNE + "Bravo ! Toutes les gommes ont été mangées !" + Couleur.RESET);
        }

        System.out.print("\nFin de la partie. Appuyez sur ENTRÉE pour quitter...");
        sc.nextLine();
    }

    public static void main(String[] args) {
        new Jeu().jouer();
    }
}