# Evaluation2 — Trois exercices Java (Maven)
Bref résumé

Répertoire racine contient 3 projets Java (maven) nommés typiquement :
exercice1/
exercice2/
exercice3/
Prérequis

Java JDK 17 (installation et JAVA_HOME configuré)
Maven 3.6+
(Pour exercice2) MySQL accessible si vous voulez exécuter l'application avec la base de données
Structure

exerciceX/
pom.xml
src/main/java/...
src/test/java/...
.gitignore (à la racine)
README.md (ce fichier)
Configurer et construire

Compiler un projet (depuis la racine) : mvn -f exercice2/pom.xml clean package
Compiler tous les projets (depuis la racine) : mvn -T 1C -pl exercice1,exercice2,exercice3 -am clean package (ou lancer mvn dans chaque sous-dossier)
Exécuter (exemple pour exercice2/TestApp)

Configurer la base de données (si nécessaire) : modifiez exercice2/src/main/resources/application.properties
spring.datasource.url
spring.datasource.username
spring.datasource.password
Depuis l'IDE : exécuter la classe ma.projet.presentation.TestApp
Depuis la ligne de commande (exemple rapide) : mvn -f exercice2/pom.xml exec:java -Dexec.mainClass="ma.projet.presentation.TestApp" (ou construire puis lancer avec java -cp ...)
Tests unitaires

Lancer les tests d'un projet : mvn -f exercice2/pom.xml test
Tous les tests : mvn -pl exercice1,exercice2,exercice3 test
Notes spécifiques

exercice2 utilise Hibernate + Spring (version conforme au pom). Si vous rencontrez des erreurs de versions, alignez les versions Spring/Hibernate dans exercice2/pom.xml.
Pour exécuter l'application hors IDE, assurez-vous que JAVA_HOME et le PATH pointent vers JDK 17.
Le .gitignore à la racine ignore les dossiers target/build et fichiers IDE temporaires.
