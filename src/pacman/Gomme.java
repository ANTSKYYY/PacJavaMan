package pacman;

public class Gomme extends Case {
    private boolean mangee = false;

    public boolean estMangee() {
        return mangee;
    }

    public void manger() {
        mangee = true;
    }

    @Override
    public char afficher() {
        return mangee ? ' ' : '⋅';
    }
}
