package com.example.testing

import org.junit.Assert.*

import org.junit.Test

class MathUtilTest {

    @Test
    fun `test if n is equal to zero`() {
        val result= MathUtil.fib(0)
        val expected=0L
        assertEquals(expected,result)
    }
    @Test
    fun `test if n is equal to one`() {
        val result= MathUtil.fib(1)
        val expected=1L
        assertEquals(expected,result)
    }
    @Test
    fun `test if n is equal to n`() {
        val result= MathUtil.fib(5)
        val expected=3L
        assertEquals(expected,result)
    }
    
}