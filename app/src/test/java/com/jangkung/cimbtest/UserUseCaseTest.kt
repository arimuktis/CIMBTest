package com.jangkung.cimbtest

import com.jangkung.cimbtest.feature.user.domain.model.User
import com.jangkung.cimbtest.feature.user.domain.usecase.GetActiveAdultUserNamesUseCase
import com.jangkung.cimbtest.feature.user.domain.usecase.SearchUsersUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class UserUseCaseTest {

    private val testUsers = listOf(
        User(1, "Andi", 25, true),
        User(2, "Budi", 17, true),
        User(3, "Citra", 30, false),
        User(4, "Deni", 22, true)
    )

    // --- REQUIREMENT 1 TESTS ---
    private val getActiveAdultUserNames = GetActiveAdultUserNamesUseCase()

    @Test
    fun `getActiveAdultUserNames returns only active users over or equal to 18 sorted by name`() {
        val result = getActiveAdultUserNames(testUsers)
        
        // Budi is excluded because age 17 (< 18)
        // Citra is excluded because isActive = false
        // Andi and Deni are valid. Sorted alphabetically -> Andi, Deni
        assertEquals(listOf("Andi", "Deni"), result)
    }

    // --- REQUIREMENT 5 TESTS ---
    private val searchUsers = SearchUsersUseCase()

    @Test
    fun `searchUsers with empty keyword returns all users sorted by name`() {
        val result = searchUsers(testUsers, "")
        
        // Expect all 4 users sorted by name
        val expected = listOf(
            testUsers[0], // Andi
            testUsers[1], // Budi
            testUsers[2], // Citra
            testUsers[3]  // Deni
        )
        assertEquals(expected, result)
    }

    @Test
    fun `searchUsers is case insensitive`() {
        val resultLower = searchUsers(testUsers, "andi")
        val resultUpper = searchUsers(testUsers, "ANDI")
        val resultMixed = searchUsers(testUsers, "AnDi")

        assertEquals(1, resultLower.size)
        assertEquals("Andi", resultLower.first().name)
        
        assertEquals(resultLower, resultUpper)
        assertEquals(resultLower, resultMixed)
    }

    @Test
    fun `searchUsers returns partial match`() {
        val result = searchUsers(testUsers, "di")
        
        // 'Andi' and 'Budi' and 'Deni' all contain 'di' (if 'Deni' -> Wait, 'Deni' does not contain 'di'. 'Andi' (di), 'Budi' (di).)
        val expected = listOf(
            testUsers[0], // Andi
            testUsers[1]  // Budi
        )
        assertEquals(expected, result)
    }
}
