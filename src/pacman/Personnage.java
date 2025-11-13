package pacman;

public abstract class Personnage {
    protected int x, y;
    protected Plateau plateau;

    public Personnage(Plateau p, int x, int y) {
        this.plateau = p;
        this.x = x;
        this.y = y;
    }

    public abstract void deplacer();

    public int getX() { return x; }
    public int getY() { return y; }
    
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}