# Klient av bysykkel api

For å bygge kjør kommando:
`./mvnw clean install`

For å starte programmet kjør kommando: `java -jar target/hegelest-bysykkel-0.0.1-SNAPSHOT.jar`


Programmet vil liste ut alle stasjoner, med tilhørende informasjon om antall ledige sykler og antall ledige låser. 

Det tilbyr også et REST basert api som kan nåes på url http://localhost:8080/stasjonsinformasjon. Et http get-kall hit vil returnere alle stasjoner med tilhørende informasjon om antall ledige sykler og antall ledige låser.

Her kan man også legge på en queryparameter på url'en for å spørre etter en spesifikk stasjon, ved å skrive for eksempel <br>
```http://localhost:8080/stasjonsinformasjon?prefix=ø```
<br> vil man få returnert alle stasjoner som har navn som starter med ø.
