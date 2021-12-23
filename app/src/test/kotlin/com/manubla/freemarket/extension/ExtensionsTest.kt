package com.manubla.freemarket.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `validate any extensions`() {
        val nullAny: Any? = null
        assertEquals(nullAny.isNull(), true)
        assertEquals(nullAny.notNull(), false)

        val nonNullAny: Any = 0
        assertEquals(nonNullAny.isNull(), false)
        assertEquals(nonNullAny.notNull(), true)
    }

    @Test
    fun `validate boolean extensions`() {
        val nullBool: Boolean? = null
        assertEquals(nullBool.toNotNullable(), false)

        val nonNullBool = true
        assertEquals(nonNullBool.toNotNullable(), nonNullBool)
    }

    @Test
    fun `validate string extensions`() {
        val nullString: String? = null
        assertEquals(nullString.toNotNullable(), "")
        assertEquals(nullString.toNotNullable(), String.empty())

        val nonNullString = "string"
        assertEquals(nonNullString.toNotNullable(), nonNullString)

        assertEquals(String.empty(), "")
        assertEquals(Char.space(), ' ')
    }

    @Test
    fun `validate number extensions`() {
        assertEquals(Int.zero(), 0)
        assertEquals(Long.zero(), 0L)
        assertEquals(Double.zero(), 0.0, 0.0)

        val nullInt: Int? = null
        assertEquals(nullInt.toNotNullable(), 0)
        assertEquals(nullInt.toNotNullable(), Int.zero())

        val nonNullInt = 1
        assertEquals(nonNullInt.toNotNullable(), nonNullInt)

        val nullLong: Long? = null
        assertEquals(nullLong.toNotNullable(), 0L)
        assertEquals(nullLong.toNotNullable(), Long.zero())

        val nonNullLong = 1L
        assertEquals(nonNullLong.toNotNullable(), nonNullLong)

        val nullDouble: Double? = null
        assertEquals(nullDouble.toNotNullable(), 0.0, 0.0)
        assertEquals(nullDouble.toNotNullable(), Double.zero(), 0.0)

        val nonNullDouble = 1.0
        assertEquals(nonNullDouble.toNotNullable(), nonNullDouble, 0.0)
    }

}