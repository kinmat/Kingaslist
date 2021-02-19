# Kingaslist
Specifikáció
## Bemutatás
Egy apróhirdetés alkalmazás elkészítése mellett döntöttem (pl. a Craigslist), amin a
felhasználó tud feladni saját apróhirdetést, böngészhet a már feladott hirdetések közt, amik
különböző kategóriákba (pl, Munka, Háziállat) sorolhatóak. Ezek közt tud keresni, és
kategóriára szűrni.
## Főbb funkciók
- Az apróhirdetések adatait perzisztensen tárolja az alkalmazás (Room használatával
valószínűleg)
- Az apróhirdetésekre rá lehet kattintani és megnyitni őket, akkor látszik a feladó
elérhetősége, részletek (Fragment vagy új activity)
- Az hirdetések be vannak kategorizálva (1 hirdetésnek 1 kategória)
- A felhasználó kereshet kategória és cím szerint, ekkor azokat a hirdetéseket jeleníti
csak meg az alkalmazás
- Szűrés nélkül minden apróhirdetés látszik, lefele görgetve lehet köztük válogatni
(RecyclerView használatával)
- A felhasználó maga is adhat fel hirdetést, ehhez kell megadni kategóriát, címet, leírást,
elérhetőséget (egyedi nézet vagy fragment) , ezt az alkalmazás tárolja az
adatbázisban
- Az alkalmazáshoz egyedi témát is szeretnék készíteni.
