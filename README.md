
# Rapizz Pizzeria 🍕

## Guide d'installation et d'exécution

---

### 🚦 Prérequis

- **Java JDK 17** ou version plus récente  
  [Télécharger ici](https://adoptopenjdk.net/)
- **Serveur MySQL** installé et en cours d’exécution  
  [Télécharger ici](https://dev.mysql.com/downloads/mysql/)
- (Optionnel) **Git** pour cloner le dépôt  
  [Télécharger ici](https://git-scm.com/downloads)

---

### 📥 Cloner ou Télécharger le Projet

```bash
git clone <repo-url>
cd Rapizz_Pizzeria
```

---

### ⚙️ Configurer MySQL


- Assurez-vous que MySQL est en cours d’exécution.
- **Paramètres par défaut** :
  - **Hôte** : `localhost`
  - **Port** : `3306`
  - **Utilisateur** : `rapizz`
  - **Mot de passe** : `pizza123`

# Ajout de l'utilisateur de la base (sous Debian)
  - mysql -u root -p
  - CREATE USER 'rapizz'@'localhost' IDENTIFIED BY 'pizza123';
  - GRANT ALL PRIVILEGES ON rapizz_pizzeria.* TO 'rapizz'@'localhost';
  - FLUSH PRIVILEGES;
Vous pouvez modifier ces informations dans  
  `src/utils/DatabaseInitializer.java`, `src/utils/DatabaseManager.java`, `src/modelBaseModel.java` si nécessaire.

---

### 🔗 Télécharger le Driver JDBC MySQL

- Le driver requis (`mysql-connector-java-9.3.0.jar`) est déjà présent dans le dossier `lib/`.
- S'il manque, [téléchargez-le ici](https://dev.mysql.com/downloads/connector/j/).

---

### 🛠️ Compiler le Projet

```bash
javac -cp .:lib/mysql-connector-java-9.3.0.jar -d build $(find src -name "*.java") && cp -r src/images build/
```

---

### ▶️ Exécuter le Programme

```bash
java -cp lib/mysql-connector-java-9.3.0.jar:build main.Main
```

---

### 🗄️ Initialisation de la Base de Données

- Au premier lancement, le programme créera automatiquement la base de données, les tables, et insérera les données initiales.
- Pour réinitialiser la base de données :  
  Supprimez la base `rapizz_pizzeria` dans MySQL et relancez le programme.

---

<details>
<summary>🕰️ Anciennes Commandes</summary>

```bash
javac -d build $(find src -name "*.java") && cp -r src/images build/
java -cp build main.Main
```
</details>










