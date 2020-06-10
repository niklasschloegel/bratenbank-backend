# **Webbasierte Anwendungen SS 2020**
### _Bratenbank-Backend von Niklas Schlögel_


## Einleitung
> In der aktuellen Situation ist gegenseitige Unterstützung besonders wichtig, unabhängig davon ist die Vermeidung von Verschwendung ein laufendes Anliegen. 
> Informationssysteme können dabei helfen.

_Wolfgang Weitz: Übung 3, Aufgabe 3 – 2020_ 

Die Bratenbank ist eine Plattform, auf der Benutzer übrig gebliebenen Braten anbieten, und andere Benutzer danach suchen können.

Dieses Repository beinhaltet das Backend, welches sich um Datenhaltung in Datenbanken kümmert, Programmlogik bereitstellt, so wie eine simple REST-Schnitstelle.

## Benutzen des Repositorys
Sobald das Repository geklont wurde, kann in dem entsprechenden Ordner mit<br>
`./gradlew bootRun`<br>
die Anwendung gestartet werden und ist dann unter [Localhost](http://localhost:8080/) für erste Tetstzwecke erreichbar.<br>
An den (lokalen) Server können dann REST-Anfragen gesendet werden, und unter folgenden Pfaden die Funktionalität getestet werden:
* [Registrierung](http://localhost:8080/benutzer)
    * Beinhaltet der Loginname 'alt' werden keine Daten in Datenbanken gespeichert, lediglich in Session-Attributen
* [Login](http://localhost:8080/login) – Bei erfolgreichem einloggen werden Sie auf die Übersichtsseite aktuell angebotener Braten weitergeleitet ([Angebote](http://localhost:8080/angebote)). Dort können Sie:
    * Braten editieren
    * Braten löschen
    * einen neuen Braten anlegen