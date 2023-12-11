package com.example.testing

import org.junit.Assert.*
import org.junit.Test

class RegistrationUtilTest{
    @Test
    fun `empty username return false`(){
          val result = RegistrationUtil.vaildateRegistrationInput(
              "","125","125"
          )
        //assert(result)
        assertFalse(result)
    }
    @Test
    fun `vaild usernme and correctly repated password reutrns true `(){
        val result = RegistrationUtil.vaildateRegistrationInput(
            "benz","125","125"
        )

        assertTrue(result)
    }
    @Test
    fun `usernme and already exist reutrns false `(){
        val result = RegistrationUtil.vaildateRegistrationInput(
            "zyad","125","125"
        )

        assertFalse(result)
    }
    @Test
    fun `password is empty reutrns false `(){
        val result = RegistrationUtil.vaildateRegistrationInput(
            "zyad","","125"
        )

        assertFalse(result)
    }
    @Test
    fun `password != confirm password returnsfalse `(){
        val result = RegistrationUtil.vaildateRegistrationInput(
            "zyad","456","125"
        )

        assertFalse(result)
    }
    @Test
    fun `password contains less than 2 diigt returns false `(){
        val result = RegistrationUtil.vaildateRegistrationInput(
            "zyad","","125"
        )

        assertFalse(result)
    }
}