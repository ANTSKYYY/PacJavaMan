package pacman;

public class Fantome extends Personnage {
    private String nom;

    public Fantome(String nom, Plateau p, int x, int y) {
        super(p, x, y);
        this.nom = nom;
    }

    public void deplacer() {
        for (int i = 0; i < 4; i++) {
            int newX = this.x;
            int newY = this.y;
            int dir = (int)(Math.random() * 4); 

            switch (dir) {
                case 0: newX--; break; 
                case 1: newX++; break; 
                case 2: newY--; break; 
                case 3: newY++; break; 
            }
            
            Case targetCase = null;
            try {
                targetCase = plateau.getCase(newX, newY);
            } catch (ArrayIndexOutOfBoundsException e) {
                
                continue; 
            }

            if (!(targetCase instanceof Mur)) {

                this.x = newX;
                this.y = newY;
                return; 
            }
        }
    }

    public String getNom() { return nom; }
}