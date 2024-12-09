# ProjetASD3

Written by MORINEAU LUDOVIC, BARBE BRYAN

# COMPILATION

La compilation doit se faire avec la commande suivante dans le répertoire du programme

javac -source 1.7 -target 1.7 -d MorineauBarbe/bin MorineauBarbe/src/*.java

Pour l'éxécuter il faut utiliser la commance suivante: 

java -classpath MorineauBarbe/bin MonBoTablo TYPE chemin_fichier_entree chemin_dossier_sortie

TYPE = 1 ou 2 en fonction de la structure voulu
	- 1 pour un PQuadtree
	- 2 pour un P3tree

# FICHIER ENTREE

Voici les spécifications attendues par le programme pour le fichier en entrée :
	- La première ligne contient soit : P4 pour les PQuadtree ou P3 pour les P3tree //Détermine le type d'arbre à utiliser
	- La seconde ligne contient la taille de l'image en pixel.
	- La troisième ligne contient m, le nombre de subdivision à effectuer.

	- Les m suivantes lignes contiennent les subdivisions telle que : X, Y, C, C, C ,C avec X et Y des entiers et C des Couleurs //pour les PQuadtrees.
	- Les m suivantes lignes contiennent les subdivisions telle que : X, Y, C, C, C, directions avec X et Y des entiers, C des Couleurs ainsi qu'une direction //pour les P3tree.

	- La ligne m+3 contient l'épaisseur des intersections
	- La ligne m+4 contient k, le nombre de recoloriages à effectuer.
	- Les lignes de m+5 à m+5+k contiennent les recoloriages tel que : X, Y avec X et Y des entiers et C une Couleur.

Les Couleurs disponibles sont ’R’ (rouge), ’B’ (bleu), ’J’ (jaune), ’G’ (gris clair) et ’N’ (noir).
Les directions autorisées pour les P3tree sont "up", "down", "left", "right".

Voir test.txt pour avoir un exemple commenté, ou PQuatree.txt et P3Tree.txt pour des exemples non commenté.

#STRUCTURE

Les structures utilisés sont des PKtree. Nous avons 2 variantes de celles-ci :

Avec K = 4. Chaque subdivisions créée 4 enfants dans les directions cardinales suivantes Nord-Ouest, Nord-Est, Sud-Est, Sud-Ouest.

Avec K = 3. Chaque subdivisions créée 3 enfants. Les directions des enfants dépendant de la directions de coupures, le fils le plus en haut à gauche est le premier, puis on tourne en suivant le sens chronologique.


# SPECIFICITE

L'origine de l'image se situe en bas à droite.


