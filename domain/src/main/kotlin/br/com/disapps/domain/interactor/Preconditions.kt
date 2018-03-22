/**
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package br.com.disapps.domain.interactor

object Preconditions {

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if {@code expression} is false
     */
    fun checkArgument(expression: Boolean) {
        if (!expression) {
            throw IllegalArgumentException()
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     * string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    fun checkArgument(expression: Boolean, errorMessage: Any?) {
        if (!expression) {
            throw IllegalArgumentException(errorMessage.toString())
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalStateException if {@code expression} is false
     */
    fun checkState(expression: Boolean) {
        if (!expression) {
            throw IllegalStateException()
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     * string using {@link String#valueOf(Object)}
     * @throws IllegalStateException if {@code expression} is false
     */
    fun checkState(expression: Boolean, errorMessage: Any?) {
        if (!expression) {
            throw IllegalStateException(errorMessage.toString())
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    fun <T> checkNotNull(reference: T?): T {
        if (reference == null) {
            throw NullPointerException()
        }
        return reference
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     * string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    fun <T> checkNotNull(reference: T?, errorMessage: Any?): T {
        if (reference == null) {
            throw NullPointerException(errorMessage.toString())
        }
        return reference
    }

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array, list or string of
     * size
     * {@code size}. An element index may range from zero, inclusive, to {@code size}, exclusive.
     *
     * @param index a user-supplied index identifying an element of an array, list or string
     * @param size the size of that array, list or string
     * @param desc the text to use to describe this index in an error message
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is negative or is not less than {@code
     * size}
     * @throws IllegalArgumentException if {@code size} is negative
     */
    fun checkElementIndex(index: Int, size: Int, desc: String = "index"): Int {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException(badElementIndex(index, size, desc))
        }
        return index
    }

    private fun badElementIndex(index: Int, size: Int, desc: String): String {
        return if (index < 0) {
            format("%s (%s) must not be negative", desc, index)
        } else if (size < 0) {
            throw IllegalArgumentException("negative size: " + size)
        } else { // index >= size
            format("%s (%s) must be less than size (%s)", desc, index, size)
        }
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array, list or string of
     * size {@code size}. A position index may range from zero to {@code size}, inclusive.
     *
     * @param index a user-supplied index identifying a position in an array, list or string
     * @param size the size of that array, list or string
     * @param desc the text to use to describe this index in an error message
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is negative or is greater than {@code size}
     * @throws IllegalArgumentException if {@code size} is negative
     */
    fun checkPositionIndex(index: Int, size: Int, desc: String = "index"): Int {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index > size) {
            throw IndexOutOfBoundsException(badPositionIndex(index, size, desc))
        }
        return index
    }

    private fun badPositionIndex(index: Int, size: Int, desc: String): String {
        return if (index < 0) {
            format("%s (%s) must not be negative", desc, index)
        } else if (size < 0) {
            throw IllegalArgumentException("negative size: " + size)
        } else { // index > size
            format("%s (%s) must not be greater than size (%s)", desc, index, size)
        }
    }

    /**
     * Ensures that {@code start} and {@code end} specify a valid <i>positions</i> in an array, list
     * or string of size {@code size}, and are in order. A position index may range from zero to
     * {@code size}, inclusive.
     *
     * @param start a user-supplied index identifying a starting position in an array, list or string
     * @param end a user-supplied index identifying a ending position in an array, list or string
     * @param size the size of that array, list or string
     * @throws IndexOutOfBoundsException if either index is negative or is greater than {@code size},
     * or if {@code end} is less than {@code start}
     * @throws IllegalArgumentException if {@code size} is negative
     */
    fun checkPositionIndexes(start: Int, end: Int, size: Int) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (start < 0 || end < start || end > size) {
            throw IndexOutOfBoundsException(badPositionIndexes(start, end, size))
        }
    }

    private fun badPositionIndexes(start: Int, end: Int, size: Int): String {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index")
        }
        return if (end < 0 || end > size) {
            badPositionIndex(end, size, "end index")
        } else format("end index (%s) must not be less than start index (%s)", end, start)
        // end < start
    }

    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These are matched by
     * position: the first {@code %s} gets {@code args[0]}, etc.  If there are more arguments than
     * placeholders, the unmatched arguments will be appended to the end of the formatted message in
     * square braces.
     *
     * @param template a non-null string containing 0 or more {@code %s} placeholders.
     * @param args the arguments to be substituted into the message template. Arguments are converted
     * to strings using {@link String#valueOf(Object)}. Arguments can be null.
     */
    // Note that this is somewhat-improperly used from Verify.java as well.
    internal fun format(template: String, vararg args: Any): String {
        val normTemplate = template.toString() // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        val builder = StringBuilder(normTemplate.length + 16 * args.size)
        var templateStart = 0
        var i = 0
        while (i < args.size) {
            val placeholderStart = normTemplate.indexOf("%s", templateStart)
            if (placeholderStart == -1) {
                break
            }
            builder.append(normTemplate.substring(templateStart, placeholderStart))
            builder.append(args[i++])
            templateStart = placeholderStart + 2
        }
        builder.append(normTemplate.substring(templateStart))

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.size) {
            builder.append(" [")
            builder.append(args[i++])
            while (i < args.size) {
                builder.append(", ")
                builder.append(args[i++])
            }
            builder.append(']')
        }

        return builder.toString()
    }
}
