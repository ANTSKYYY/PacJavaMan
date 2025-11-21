import java.util.ArrayList;


public class TP2Map<K extends Comparable<K>, V> {

    private Noeud<K, V> racine;

    public TP2Map() {
        this.racine = null;
    }
    
    public void ajoute(K clef, V valeur) {
        if (this.racine == null) {
            this.racine = new Noeud<>(clef, valeur);
        } else {
            
            this.racine.put(clef, valeur); 
        }
    }

    public void clear() {
        this.racine = null;
    }

    public boolean containsKey(K key) {
        if (this.racine == null) {
            return false;
        }
        return this.racine.containsKey(key);
    }

    public boolean containsValue(V value) {
        if (this.racine == null) {
            return false;
        }
      
        return this.racine.containsValue(value);
    }

    public V getOrDefault(K key, V defaultValue) {
        if (this.racine == null) {
            return defaultValue;
        }
        
        
        V result = this.racine.get(key);

       
        return result != null ? result : defaultValue;
    }
    
    public V get(K key) {
        return this.getOrDefault(key, null);
    }
  
    public boolean isEmpty() {
        return this.racine == null;
    }

    public V put(K key, V value) {
        if (this.racine == null) {
            this.racine = new Noeud<>(key, value);
            return null; 
        }
        
        return this.racine.put(key, value);
    }

    public V remplace(K key, V value) {
        if (this.racine == null) {
            return null; 
        }
        return this.racine.remplace(key, value);
    }

    public int size() {
        if (this.racine == null) {
            return 0;
        }
        return this.racine.size(); 
    }  
    public ArrayList<V> values() {
        ArrayList<V> listeDesValeurs = new ArrayList<>();
        
        if (this.racine != null) {
            this.racine.recupereValues(listeDesValeurs);
        }
   
        return listeDesValeurs;
    }
}