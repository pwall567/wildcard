/*
 * @(#) WildcardTest.kt
 *
 * wildcard  Wildcard comparison
 * Copyright (c) 2024 Peter Wall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.pwall.text

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

import net.pwall.text.Wildcard.Companion.matches

class WildcardTest {

    @Test fun `should match simple text`() {
        val wildcard = Wildcard("Fred")
        assertTrue(wildcard matches "Fred")
        assertFalse(wildcard matches "Free")
        assertFalse(wildcard matches "Freddy")
    }

    @Test fun `should match simple text using extension function`() {
        val wildcard = Wildcard("Fred")
        assertTrue("Fred" matches wildcard)
        assertFalse("Free" matches wildcard)
        assertFalse("Freddy" matches wildcard)
    }

    @Test fun `should match text with single-character wildcard`() {
        val wildcard = Wildcard("Fre?")
        assertTrue(wildcard matches "Fred")
        assertTrue(wildcard matches "Free")
        assertFalse(wildcard matches "Freddy")
    }

    @Test fun `should match text with multi-character wildcard at end`() {
        val wildcard = Wildcard("Fre*")
        assertTrue(wildcard matches "Fred")
        assertTrue(wildcard matches "Free")
        assertTrue(wildcard matches "Freddy")
        assertFalse(wildcard matches "Friend")
    }

    @Test fun `should match text with multi-character wildcard at start`() {
        val wildcard = Wildcard("*'s dog")
        assertTrue(wildcard matches "Fred's dog")
        assertTrue(wildcard matches "Freddy's dog")
        assertTrue(wildcard matches "Joe's dog")
        assertFalse(wildcard matches "Fred's cat")
    }

    @Test fun `should match text with multi-character wildcard in middle`() {
        val wildcard = Wildcard("Fre*'s dog")
        assertTrue(wildcard matches "Fred's dog")
        assertTrue(wildcard matches "Free's dog")
        assertTrue(wildcard matches "Freddy's dog")
        assertFalse(wildcard matches "Freddy's cat")
        assertTrue(wildcard matches "Freddy's cat and Joe's dog")
    }

    @Test fun `should match text with custom single-character wildcard`() {
        val wildcard = Wildcard("Fre%", singleMatchChar = '%')
        assertTrue(wildcard matches "Fred")
        assertTrue(wildcard matches "Free")
        assertFalse(wildcard matches "Freddy")
    }

    @Test fun `should match text with custom multi-character wildcard`() {
        val wildcard = Wildcard("Fre%", multiMatchChar = '%')
        assertTrue(wildcard matches "Fred")
        assertTrue(wildcard matches "Free")
        assertTrue(wildcard matches "Freddy")
        assertFalse(wildcard matches "Friend")
    }

    @Test fun `should match text with multiple multi-character wildcards`() {
        val wildcard = Wildcard("abc*ghi*mno*xyz")
        assertTrue(wildcard matches "abcdefghijklmnopqrstuvwxyz")
        assertTrue(wildcard matches "abcghimnoxyz")
        assertTrue(wildcard matches "abcmnoghixyzmnoxyz")
        assertFalse(wildcard matches "abcdefghijklmno")
        assertFalse(wildcard matches "abcdefghijklmnoooooo")
        assertFalse(wildcard matches "abcdefghijkqqqxyz")
    }

    @Test fun `should match text with multiple multi-character wildcards at start and end`() {
        val wildcard = Wildcard("*sex*")
        assertTrue(wildcard matches "sex education")
        assertTrue(wildcard matches "Essex")
        assertTrue(wildcard matches "You sexy thing")
        assertFalse(wildcard matches "s e x y")
    }

    @Test fun `should match text with consecutive multi-character wildcards`() {
        val wildcard = Wildcard("File**.txt")
        assertTrue(wildcard matches "File.txt")
        assertTrue(wildcard matches "File1.txt")
        assertTrue(wildcard matches "File12.txt")
        assertTrue(wildcard matches "File123.txt")
    }

    @Test fun `should match using combination of single and multi-character wildcards`() {
        val wildcard = Wildcard("File?*.txt")
        assertFalse(wildcard matches "File.txt")
        assertTrue(wildcard matches "File1.txt")
        assertTrue(wildcard matches "File12.txt")
        assertTrue(wildcard matches "File123.txt")
    }

}
