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

package io.kstuff.text

import kotlin.test.Test

import io.kstuff.test.shouldBe

import io.kstuff.text.Wildcard.Companion.matches

class WildcardTest {

    @Test fun `should match simple text`() {
        val wildcard = Wildcard("Fred")
        wildcard matches "Fred" shouldBe true
        wildcard matches "Free" shouldBe false
        wildcard matches "Freddy" shouldBe false
    }

    @Test fun `should match simple text using extension function`() {
        val wildcard = Wildcard("Fred")
        "Fred" matches wildcard shouldBe true
        "Free" matches wildcard shouldBe false
        "Freddy" matches wildcard shouldBe false
    }

    @Test fun `should match text with single-character wildcard`() {
        val wildcard = Wildcard("Fre?")
        wildcard matches "Fred" shouldBe true
        wildcard matches "Free" shouldBe true
        wildcard matches "Freddy" shouldBe false
    }

    @Test fun `should match text with multi-character wildcard at end`() {
        val wildcard = Wildcard("Fre*")
        wildcard matches "Fred" shouldBe true
        wildcard matches "Free" shouldBe true
        wildcard matches "Freddy" shouldBe true
        wildcard matches "Friend" shouldBe false
    }

    @Test fun `should match text with multi-character wildcard at start`() {
        val wildcard = Wildcard("*'s dog")
        wildcard matches "Fred's dog" shouldBe true
        wildcard matches "Freddy's dog" shouldBe true
        wildcard matches "Joe's dog" shouldBe true
        wildcard matches "Fred's cat" shouldBe false
    }

    @Test fun `should match text with multi-character wildcard in middle`() {
        val wildcard = Wildcard("Fre*'s dog")
        wildcard matches "Fred's dog" shouldBe true
        wildcard matches "Free's dog" shouldBe true
        wildcard matches "Freddy's dog" shouldBe true
        wildcard matches "Freddy's cat" shouldBe false
        wildcard matches "Freddy's cat and Joe's dog" shouldBe true
    }

    @Test fun `should match text with custom single-character wildcard`() {
        val wildcard = Wildcard("Fre%", singleMatchChar = '%')
        wildcard matches "Fred" shouldBe true
        wildcard matches "Free" shouldBe true
        wildcard matches "Freddy" shouldBe false
    }

    @Test fun `should match text with custom multi-character wildcard`() {
        val wildcard = Wildcard("Fre%", multiMatchChar = '%')
        wildcard matches "Fred" shouldBe true
        wildcard matches "Free" shouldBe true
        wildcard matches "Freddy" shouldBe true
        wildcard matches "Friend" shouldBe false
    }

    @Test fun `should match text with multiple multi-character wildcards`() {
        val wildcard = Wildcard("abc*ghi*mno*xyz")
        wildcard matches "abcdefghijklmnopqrstuvwxyz" shouldBe true
        wildcard matches "abcghimnoxyz" shouldBe true
        wildcard matches "abcmnoghixyzmnoxyz" shouldBe true
        wildcard matches "abcdefghijklmno" shouldBe false
        wildcard matches "abcdefghijklmnoooooo" shouldBe false
        wildcard matches "abcdefghijkqqqxyz" shouldBe false
    }

    @Test fun `should match text with multiple multi-character wildcards at start and end`() {
        val wildcard = Wildcard("*sex*")
        wildcard matches "sex education" shouldBe true
        wildcard matches "Essex" shouldBe true
        wildcard matches "You sexy thing" shouldBe true
        wildcard matches "s e x y" shouldBe false
    }

    @Test fun `should match text with consecutive multi-character wildcards`() {
        val wildcard = Wildcard("File**.txt")
        wildcard matches "File.txt" shouldBe true
        wildcard matches "File1.txt" shouldBe true
        wildcard matches "File12.txt" shouldBe true
        wildcard matches "File123.txt" shouldBe true
    }

    @Test fun `should match using combination of single and multi-character wildcards`() {
        val wildcard = Wildcard("File?*.txt")
        wildcard matches "File.txt" shouldBe false
        wildcard matches "File1.txt" shouldBe true
        wildcard matches "File12.txt" shouldBe true
        wildcard matches "File123.txt" shouldBe true
    }

}
