import java.util.ArrayList;

public class Noeud<K extends Comparable<K>, V> {

   
    K clef;
    V valeur;
    Noeud<K, V> gauche;
    Noeud<K, V> droit;

    public Noeud(K clef, V valeur) {
        this.clef = clef;
        this.valeur = valeur;
        this.gauche = null;
        this.droit = null;
    }


    public void ajoute(K clef, V valeur) {
        int comparaison = clef.compareTo(this.clef);

        if (comparaison < 0) { 
            if (this.gauche == null) {
                this.gauche = new Noeud<>(clef, valeur);
            } else {
                this.gauche.ajoute(clef, valeur);
            }
        } else if (comparaison > 0) { 
            if (this.droit == null) {
                this.droit = new Noeud<>(clef, valeur);
            } else {
                this.droit.ajoute(clef, valeur);
            }
        } else {
            
            this.valeur = valeur;
        }
    }

   
    public boolean containsKey(K key) {
        int comparaison = key.compareTo(this.clef);

        if (comparaison == 0) {
            return true; 
        } 
        
        if (comparaison < 0) {
            
            return (this.gauche != null) && this.gauche.containsKey(key);
        } else {
            
            return (this.droit != null) && this.droit.containsKey(key);
        }
    }

  
    public boolean containsValue(V value) {
       
        boolean valeurTrouveeSurMoi = (this.valeur == null) ? (value == null) : this.valeur.equals(value);
        if (valeurTrouveeSurMoi) {
            return true;
        }

        
        if (this.gauche != null && this.gauche.containsValue(value)) {
            return true;
        }

        if (this.droit != null && this.droit.containsValue(value)) {
            return true;
        }

        return false;
    }

  
    public V getOrDefault(K key, V defaultValue) {
        int comparaison = key.compareTo(this.clef);

        if (comparaison == 0) {
            return this.valeur;
        }

        if (comparaison < 0) {
            if (this.gauche == null) {
                return defaultValue; 
            }
            return this.gauche.getOrDefault(key, defaultValue);

        } else {
            if (this.droit == null) {
                return defaultValue; 
            }
            return this.droit.getOrDefault(key, defaultValue);
        }
    }
    
   
    public V put(K key, V value) {
        int comparaison = key.compareTo(this.clef);

        if (comparaison == 0) {
            
            V ancienneValeur = this.valeur;
            this.valeur = value;
            return ancienneValeur;
        } 
        
        if (comparaison < 0) { 
            if (this.gauche == null) {
                this.gauche = new Noeud<>(key, value);
                return null;
            }
            return this.gauche.put(key, value);

        } else { 
            if (this.droit == null) {
                this.droit = new Noeud<>(key, value);
                return null;
            }
            return this.droit.put(key, value);
        }
    }
    public V remplace(K key, V value) {
        int comparaison = key.compareTo(this.clef);

        if (comparaison == 0) {
            V ancienneValeur = this.valeur;
            this.valeur = value;
            return ancienneValeur;
        }

        if (comparaison < 0) { 
            if (this.gauche == null) {
                return null; 
            }
            return this.gauche.remplace(key, value);
            
        } else {
            if (this.droit == null) {
                return null;
            }
            return this.droit.remplace(key, value);
        }
    }

  
    public int size() {
        int compteur = 1; 

        if (this.gauche != null) {
            compteur += this.gauche.size();
        }
        if (this.droit != null) {
            compteur += this.droit.size();
        }
        
        return compteur;
    }

    
    public void recupereValues(ArrayList<V> liste) {
        if (this.gauche != null) {
            this.gauche.recupereValues(liste);
        }
        
        liste.add(this.valeur); 
        
        if (this.droit != null) {
            this.droit.recupereValues(liste);
        }
    }
    
    
   
}