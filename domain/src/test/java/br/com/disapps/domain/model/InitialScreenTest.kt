package br.com.disapps.domain.model

import br.com.disapps.domain.mock.MockData
import org.junit.Test
import kotlin.test.assertEquals

class InitialScreenTest{

    private lateinit var initialScreen: InitialScreen

    @Test
    fun testCardsScreen(){
        initialScreen = InitialScreen.CARDS
        assertEquals(MockData.CARDS, initialScreen.toString())
    }

    @Test
    fun testLinesScreen(){
        initialScreen = InitialScreen.LINES
        assertEquals(MockData.LINES, initialScreen.toString())
    }


}