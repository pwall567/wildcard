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

/**
 * A class to perform simple wildcard matching.
 *
 * @author  Peter Wall
 */
class Wildcard(
    /** The text pattern */
    private val pattern: String,
    /** The character used to represent a single character wildcard match in the pattern */
    private val singleMatchChar: Char = '?',
    /** The character used to represent a multi-character wildcard match in the pattern */
    private val multiMatchChar: Char = '*',
) {

    private val patternLength = pattern.length

    /**
     * Test whether a target string matches the pattern.
     */
    infix fun matches(target: CharSequence): Boolean = matches(target, 0, target.length, 0)

    private fun matches(target: CharSequence, targetStart: Int, targetEnd: Int, patternStart: Int): Boolean {
        var targetIndex = targetStart
        var patternIndex = patternStart
        while (patternIndex < patternLength) {
            when (val patternChar = pattern[patternIndex++]) {
                singleMatchChar -> {
                    if (targetIndex++ >= targetEnd)
                        return false
                }
                multiMatchChar -> {
                    if (patternIndex == patternLength)
                        return true
                    while (true) {
                        if (targetIndex >= targetEnd)
                            return false
                        if (matches(target, targetIndex++, targetEnd, patternIndex))
                            return true
                    }
                }
                else -> {
                    if (targetIndex >= targetEnd || target[targetIndex++] != patternChar)
                        return false
                }
            }
        }
        return targetIndex == targetEnd
    }

    companion object {

        /**
         * Test whether a string matches a given pattern.
         */
        infix fun CharSequence.matches(wildcard: Wildcard): Boolean = wildcard.matches(this, 0, length, 0)

    }

}
