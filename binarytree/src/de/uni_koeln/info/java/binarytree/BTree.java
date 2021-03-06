package de.uni_koeln.info.java.binarytree;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Stack;

/*
 * Repräsentation eines Binärbaums:
 * 
 * (1) Auf jeden Knoten, außer der Wurzel, zeigt nur ein Knoten, der sein direkter Vorgänger ist
 * (2) Ein Binärbaum hat linke und rechter Verkettungen (max. zwei Kindknoten, diese wiederum sind potentielle Teilbäume) 
 * (3) Der Bau ist leer, wenn 'root' null ist 
 * (4) !!! WICHTIG FÜR DAS ERZEUGEN UND SUCHEN IM BAUM: Alle Datensätze mit < (kleinerem) Schlüsselwert befinden sich im LINKEN Unterbaum, alle Datensätze mit einem > (größeren) Schlüsselwert befinden sich im RECHTEN Unterbaum 
 * (5) Die Sortierfunktion erhält man gratis, sofern der Baum in der richtigen Reihenfolge (Travesierung) durchlaufen wird
 * (6) Die Laufzeit ist O(log n). Bei einem 'entarteten' Baum ist die Laufzeit O(n)
 * 
 * 
 * Hier ein Beispiel:
 * 	
 * Eingabesequenz = { 6, 9, 4, 13, 17, 1, 10, 5, 7, 3 }
 * 
 *					       (6)
 *					      /   \
 *					   (4)     (9)
 *					   / \     /  \	
 *					 (1) (5) (7)  (13)
 *					     /        /  \
 *					   (3)      (10) (17)
 *
 *  1) insert(6)
 * 
 *					        (6) <- Wurzel (root)
 *
 *  2) insert(9)
 * 
 *					        (6)
 *					      	   \
 *					            (9)
 *
 *  3) insert(4)
 * 
 *					        (6)
 *					       /   \
 *					     (4)   (9)
 * 
 * 
 *  3) insert(13)		   
 *  
 *					       (6)
 *					      /   \
 *					   (4)     (9)
 *					      		 \
 *					 			 (13)
 *
 *  4) insert(17)
 *   				       (6)
 *					      /   \
 *					   (4)     (9)
 *					   			 \
 *					 			 (13)
 *					     		    \
 *					   			    (17)
 *
 *  5) insert(1)
 *  
 *   					   (6)
 *					      /   \
 *					   (4)     (9)
 *					   /         \
 *					 (1)         (13)
 *					                \
 *					                (17)
 *
 *  6) insert(10)
 * 
 *  					   (6)
 *					      /   \
 *					   (4)     (9)
 *					   /          \
 *					 (1)          (13)
 *					              /  \
 *					            (10) (17)
 *
 *	7) insert(5) 
 *
 * 					       (6)
 *					      /   \
 *					   (4)     (9)
 *					   / \        \
 *					 (1) (5)      (13)
 *					              /  \
 *					            (10) (17)
 * 
 *	8) insert(7)
 *
 * 					       (6)
 *					      /   \
 *					   (4)     (9)
 *					   / \     /  \
 *					 (1) (5) (7)  (13)
 *					              /  \
 *					            (10) (17)
 *
 *	9) insert(3)
 *
 * 					       (6)
 *					      /   \
 *					   (4)     (9)
 *					   / \     /  \
 *					 (1) (5) (7)  (13)
 *					     /        /  \
 *					   (3)      (10) (17)
 *
 *
 * 
 * Hier ein Beispiel eines 'entarteten' Baums:
 * 
 * Eingabesequenz = { 1, 3, 4, 5, ... } 
 * 
 * 							 (1)
 * 						        \
 * 								(3)
 * 							       \
 * 								   (4)
 * 								      \
 * 									   (5)
 *									     \ ...
 *
 * NB! Eine sortierte Eingabe verändert die Laufzeit des Binärbaums zu O(n). Die Suche erfolgt linear, wie bei der einfach verketteten List.
 *
 * @author matana (Mihail Atanassov)
 * 
 */
public class BTree<T extends Comparable<T>> {
	
	
	// Die Wurzel des Binärbaums
	BTreeNode<T> root;

	public BTree() {
		// Default-Konstruktor
	}
	
	public BTree(T[] input) {
		for (T value : input) {
			insert(value);
		}
	}

	public boolean insert(T value) {
		if (root == null) { // Baum ist leer?
			root = new BTreeNode<T>(value); // Wurzel wird erzeugt...
			return true;	
		}
		
		else
			return insert(root, value); // Rekursives Einfügen des Wertes 'value' im Baum
	}

	private boolean insert(BTreeNode<T> currentNode, T value) {
		
		if (currentNode != null) {
			
		    if (0 < value.compareTo(currentNode.value)) { // Wert ist kleiner, als der Wert im aktuellen Knoten, d.h. wir gehen nach links...
			if (currentNode.left == null) { // Linker Teilbaum leer?
				currentNode.left = new BTreeNode<T>(value); // Erzege neuen Knoten
				return true;
			} else 
				return insert(currentNode.left, value); // Linker Teilbaum nicht leer, d.h. wir steigen ab (rekursiv)
		}
		

		else if (0 > value.compareTo(currentNode.value)) { // Wert ist größer, als der Wert im aktuellen Knoten, wir gehen nach rechts... 
			if (currentNode.right == null) { // Rechter Teilbaum leer?
				currentNode.right = new BTreeNode<T>(value); // Erzege neuen Knoten
				return true;
			} else 
				return insert(currentNode.right, value); // Rechter Teilbaum nicht leer, d.h. wir steigen ab (rekursiv)
		}
		}
		return false;
		
		
	}
	
	public void print() {
		if (root != null)
			print(root);
		System.out.println();
	}

	/**
	 * WURZEL = W
	 * LINKER_KNOTEN = l (rekursiv)
	 * RECHTER_KNOTEN = r (rekursiv)
	 * 
	 * Es gibt mehrere Möglichkeiten die Knoten eines Baumes zu durchlaufen, dieser Vorgang wird auch Travesierung genannt.
	 * Es gibt u.a. folgende Travesierungsarten:
	 * 
	 * 1) pre-order :: W-l-r
	 * 2) post-order :: l-r-W
	 * 3) in-order ::  l-W-r 
	 * 
	 * @param currentNode
	 */
	private void print(BTreeNode<T> currentNode) {
		/*
		 * Hier eine in-order Implementation (l-W-r). Diese Travesierungsart hat
		 * den Vorteil, dass die in den Knoten gespeicherten Werte in einer sortierten
		 * Reihenfolge durchlaufen werden, denn für jeden Teilbaum gilt:
		 * (1) linker Knoten (Kindknoten) kapselt einen kleineren Wert, als der Elternknoten
		 * (2) Wurzel (Elternknoten) kapselt einen größeren Wert, als der linke Kindknoten
		 * (3) rechter Knoten (Kindknoten) kapselt einen größeren Wert, als der Elternknoten
		 */
		
		// l
		if (currentNode.left != null) // Abbruchbedingung, falls linker Knoten ein Blatt ist
			print(currentNode.left);

		// W
		System.out.print(currentNode.value + " ");

		// r
		if (currentNode.right != null) // Abbruchbedingung, falls rechter Knoten ein Blatt ist
			print(currentNode.right);
	}
	
	/**
	 * Gibt <code>true</code> zurück, wenn der gesuchte Wert im Baum vorhanden ist,
	 * andernfalls <code>false</code>.
	 * 
	 * @param value
	 * @return
	 */
	public boolean contains(T value) {
	// Hilfsvariable die für den Anfang den Wert von root annimmt, aber verändert werden soll
	BTreeNode<T> current = root; 

	while (current != null) { // Abbruchbedingung: Blatt erreicht, also current == null
		if (0 > value.compareTo(current.value)) // falls value größer current.value, steige rechts ab
			current = current.right;
		else if (0 < value.compareTo(current.value)) // ebenso links
			current = current.left;
		else							// weder kleiner noch größer? also value == current.value, also true
			return true; 
		
	}
	
	return false;
	}

/**
 * Erzeugt aus dem Binärbaum eine sortierte Liste.
 * 
 * @param <T>
 * 
 * @return List
 */
public List<T> asSortedList() {
	
	// alle benötigten Datentypen initialisieren
	
	List<T> toReturn = new ArrayList<T>();
	
	Stack<BTreeNode<T>> stack = new Stack<BTreeNode<T>>();
	
	BTreeNode<T> current = root;
	
// In meinen eigenen Worten folgende Schleife:
	// Während current existiert:
		// falls current.right existiert, steige den Baum rechts ab, indem:
			//lege current auf den Stapel, um später wieder aufsteigen zu können
			// current wird zu seinem rechten Kind
				// FALLS current ein Blatt ist:
					// den Wert von current in die Liste schreiben
					// einmal im Baum aufsteigen
					// das rechte Blatt, welches man jetzt durchlaufen hat, löschen/auf null
	
		// falls current.left existiert:
			// s.o., nur mit links statt rechts
	
		// falls current bereits ein Blatt ist, so ist sein Wert bereits durch die vorherigen 
		// Abfragen eingegeben bzw. wird außerhalb der Schleife in die Liste geschrieben
			// setze lösche current
				// falls noch Einträge im Stapel sind (aka: Baum noch nicht ganz durchlaufen)
					// current = oberster Eintrag im Stapel
	
	// Das ist mit Sicherheit keine elegante Lösung, und das Debuggen zeigt, dass viele Knoten mehrmals besucht werden
	// ABER sie funktioniert und ich habe sie ohne StackOverflow o.ä. gefunden und alleine entwickelt 8-)

	
	while (current != null) {
		if (current.right != null) {
			stack.push(current);
			current = current.right;
//			System.out.println(current.value);
			if (current.left == null && current.right == null){
				toReturn.add(current.value);
				current = stack.pop();
				current.right = null;
			}
		} 
		else if (current.left != null) {
			stack.push(current);
			current = current.left;
//			System.out.println(current.value);
			if (current.left == null && current.right == null){
				toReturn.add(current.value);
				current = stack.pop();
				current.left = null;
			}
		}
		else if (current.left == null && current.right == null){
//			toReturn.add(current.value);
			current = null;
			if (!stack.empty()) {
				current = stack.pop();
//				System.out.println(current.value);

			}
			
		}
		
	}
	toReturn.add(root.value); // da dieser Wert von der Schleife nicht erfasst wird, noch drauf packen
	
	
	// Liste sortieren, zurückgeben.
	Collections.sort(toReturn);
	
	return toReturn;
}

	/**
	 * Erzeugt eine sortierte Liste, deren Elemente alle kleiner als 'value' sind.
	 * @param value
	 * @return List
	 */
	public List<T> elementsSmallerThan(T value) {
		
		// Datenstrukturen erzeugen
		List<T> list = asSortedList();
		
		ArrayList<T> rTurn = new ArrayList<T>(list);
		ArrayList<T> toReturn = new ArrayList<T>();
		
		// alle Werte die kleiner sind als Value umkopieren
		
		for (T item : rTurn) {
			if(0 < value.compareTo(item))
				toReturn.add(item);
		}
		
		//ich weiß nicht, wieso die folgende Schleife nicht genau das gleiche tut
		//aber sie tut es nicht ¯\_(ツ)_/¯ 
		// bezieht sich auf frühe Version des Programms mit int input
		
		
//		for (int i = 0; i < rTurn.size(); i++) {
//			if (rTurn.get(i) < value)
//				rTurn.remove(i);
//		}

		
		return toReturn;
	}


}
