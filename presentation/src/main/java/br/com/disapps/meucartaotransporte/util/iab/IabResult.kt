/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.disapps.meucartaotransporte.util.iab

/**
 * Represents the result of an in-app billing operation.
 * A result is composed of a response code (an integer) and possibly a
 * message (String). You can get those by calling
 * [.getResponse] and [.getMessage], respectively. You
 * can also inquire whether a result is a success or a failure by
 * calling [.isSuccess] and [.isFailure].
 */
class IabResult(response: Int = 0, message: String?) {
    var response: Int = 0
        internal set
    var message: String
        internal set
    val isSuccess: Boolean
        get() = response == IabHelper.BILLING_RESPONSE_RESULT_OK
    val isFailure: Boolean
        get() = !isSuccess

    init {
        this.response = response

        if (message == null || message.trim { it <= ' ' }.isEmpty()) {
            this.message = IabHelper.getResponseDesc(this.response)
        } else {
            this.message = "$message (response:"+ IabHelper.getResponseDesc(this.response)+")"
        }
    }

    override fun toString(): String {
        return "IabResult: $message"
    }
}

