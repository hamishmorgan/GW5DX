/*
 * Copyright (c) 2010-2013, University of Sussex
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of the University of Sussex nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package uk.ac.susx.tag.lib;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Hamish Morgan
 */
public class MiscUtilTest {

    public MiscUtilTest() {
    }

    /**
     * Test of printableUtf8Character method, of class MiscUtil.
     */
    @Test
    public void testPrintableUTF8() {
        System.out.println("printableUTF8");

        for (int i = 0; i <= 127; i++) {
            final char ch = (char) i;
            final String result = MiscUtil.printableUTF8(ch);
            assertNotNull(result);
            if (i > 32 && i != 127) {
                // String should simply contain the character
                assertTrue("String should have length 1", result.length() == 1);
                assertEquals("Result should be the input character.", ch,
                             result.charAt(0));
            } else {
                // String should contain some string name
                assertTrue("String should have length at least 1",
                           result.length() >= 1);
                assertTrue("Result should not be the input character.",
                           ch != result.charAt(0));
            }
        }
    }
}
