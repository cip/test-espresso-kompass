package at.example.espressoguide

import org.junit.Assert.*
import org.junit.Test

class BrewStepsTest {
    @Test fun hasCompleteSequentialGuide() {
        assertEquals(9, BrewSteps.espresso.size)
        assertEquals("Maschine aufheizen", BrewSteps.espresso.first().title)
        assertEquals("Reinigen", BrewSteps.espresso.last().title)
        assertTrue(BrewSteps.espresso.all { it.title.isNotBlank() && it.description.isNotBlank() && it.tip.isNotBlank() })
    }
    @Test fun extractionHasThirtySecondTimer() {
        assertEquals(30, BrewSteps.espresso.single { it.title == "Espresso beziehen" }.durationSeconds)
        assertEquals(1, BrewSteps.espresso.count { it.durationSeconds > 0 })
    }
}
