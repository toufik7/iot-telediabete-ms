# iot-telediabete-ms

Envoi des données depuis le Glucomètre du patient vers la platform

## Données Glucomètre :
     - Id : (Long)
     - Date : year, month, day, hour, min (int)
     - Value : glycémie (int)
     - deviceKey : (string)

## Pourquoi deviceKey ?
	Si le patient utilise un autre glucomètre, ou plusieurs glucomètres , on aura qu’a téléverser les donnée avec le deviceKey correspondant, 
	Le paient peut avoir plusieurs deviceKeys
  
  
Etapes : 
### Intialiser les variables
![image](https://user-images.githubusercontent.com/52804863/134324951-4d3a34e3-9bd8-44b9-9dd1-592bb40fb6da.png)

### Etablire une connexion Wifi avec AT commande
	- AT : Test de démarrage
	- AT+CWMODE : Mode WIFI  ( 1: Client )
	- AT+CWJAP : Connexion au point d’accès
	- AT+CIFSR : Adresse IP local 
	
![image](https://user-images.githubusercontent.com/52804863/134324981-11d2ca24-80b4-495e-96e4-ad7b9503cd56.png)

### Recuperer les donnees de glucemie depuis MYSignal
![image](https://user-images.githubusercontent.com/52804863/134324995-d4599ccf-df9e-4c43-b616-0bb7c9b9fc3a.png)

### Envoyer les donnees vers le microservice IOT
	- AT+CIPMUX : Choix de connexion multiple (1) ou unique (0).
	- AT+CIPSTART : Établir une connexion TCP ou inscrire port UDP, démarrer la connexion
	- AT+CIPSEND : Envoyer des données
	- AT+CIPCLOSE : Fermeture de la connexion TCP ou UDP
![image](https://user-images.githubusercontent.com/52804863/134325021-d4d77b01-f91b-4c62-99cd-c447b8854ec2.png)

### Enregistrer les données glycémie dans MySQL
![image](https://user-images.githubusercontent.com/52804863/134325064-9332654f-c8a4-4348-a9e7-a5a70353370d.png)


### Afficher les données glycémie dans la platform
![image](https://user-images.githubusercontent.com/52804863/134325041-e5c6ab65-e99f-42f4-a0f0-6207010d2b4c.png)











