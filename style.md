# Style de l'équipe

## Format du code:
#### Nous avons décider que notre code allait respecté quelques règles:
+ Chaque opérateur doit être séparé d'un espace en avant et en arrière.
+ Tous les noms de nos variables et de méthodes doivent être en français.
    - Les nom des méthodes doivent respecter le style Java et commencent par une lettre minuscule et les autres mots débutent par une majuscule. Ex: `estValide`
+ Les noms des classes doivent respecter le style Java: commencent par une majuscule et de même ppour toutes les autres mots dans le nom. Ex: `JsonHash`
+ Chaque méthode doit être séparer des autres méthodes avec une seule ligne vide.
    - Les méthodes ne doivent préférablement pas dépasser dix lignes.
+ Les variables doivent être déclarées au début d'une méthode ou d'une classe. (Si possible)
+ Tous les messages d'erreur à écrire sur le fichier de sortie JSON doivent être écrits avec la méthode `ecrireMsgErreur`.
+ Si le code d'une boucle ne fait qu'une seule ligne, celui ci peut être écrit directement après la boucle. Ex: `for (int i = 0 ; i < activites.size(); i++) {voirCat(i, heure);}`

