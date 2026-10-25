package at.example.espressoguide

data class BrewStep(val title: String, val description: String, val tip: String, val durationSeconds: Int = 0)

object BrewSteps {
    val espresso = listOf(
        BrewStep("Maschine aufheizen", "Maschine einschalten, Siebträger einspannen und Brühgruppe, Siebträger sowie Tasse gut aufwärmen.", "Je nach Maschine etwa 15–30 Minuten einplanen. Herstellerangaben beachten."),
        BrewStep("Bohnen abwiegen", "Für einen doppelten Espresso mit 18 g frisch gerösteten Bohnen starten.", "18 g sind ein Startwert: Die passende Menge hängt vom Sieb ab."),
        BrewStep("Fein mahlen", "Die Bohnen unmittelbar vor dem Bezug fein und möglichst gleichmäßig mahlen.", "Läuft der Espresso später zu schnell, feiner mahlen; läuft er zu langsam, gröber mahlen."),
        BrewStep("Kaffeemehl verteilen", "Kaffeemehl im trockenen Sieb gleichmäßig verteilen und größere Klumpen auflösen.", "Ein ebener Puck hilft, Channeling zu vermeiden."),
        BrewStep("Gerade tampen", "Das Mahlgut mit dem Tamper waagrecht und gleichmäßig verdichten. Rand des Siebs säubern.", "Wichtiger als maximale Kraft ist ein reproduzierbarer, gerader Tamp."),
        BrewStep("Brühgruppe spülen", "Vor dem Einspannen bei Bedarf kurz Wasser aus der Brühgruppe laufen lassen und den Siebträger sofort einspannen.", "Vorsicht: Brühgruppe und Wasser sind heiß."),
        BrewStep("Espresso beziehen", "Tasse auf die Waage stellen, Bezug starten und als Orientierung 36 g Getränk aus 18 g Kaffee anpeilen.", "Ein guter Startbereich sind ca. 25–30 Sekunden ab Pumpenstart. Geschmack ist entscheidend.", 30),
        BrewStep("Probieren & anpassen", "Espresso probieren. Zu sauer oder dünn? Etwas feiner mahlen oder länger extrahieren. Zu bitter oder trocken? Etwas gröber mahlen oder kürzer extrahieren.", "Immer nur einen Parameter auf einmal verändern."),
        BrewStep("Reinigen", "Puck ausklopfen, Sieb reinigen und Brühgruppe gemäß Herstellerempfehlung spülen bzw. reinigen.", "Dampflanze nach Milchgetränken sofort abwischen und kurz ausblasen.")
    )
}
