package common.util

import kotlin.test.Test
import kotlin.test.assertEquals

class StringEncodingTest {
    @Test
    fun testBasicEncoding() {
        assertEquals("hello", "hello".encoded())
        assertEquals("hello+world", "hello world".encoded())
        assertEquals("hello%21", "hello!".encoded())
    }

    @Test
    fun testSpecialCharacters() {
        assertEquals("%3C%3E%23%24%25", "<>#$%".encoded())
        assertEquals("%26%3D%3F%40", "&=?@".encoded())
        assertEquals("%5B%5D%7B%7D", "[]{}".encoded())
    }

    @Test
    fun testBasicDecoding() {
        assertEquals("hello", "hello".decoded())
        assertEquals("hello world", "hello+world".decoded())
        assertEquals("hello!", "hello%21".decoded())
    }

    @Test
    fun testSpecialCharactersDecoding() {
        assertEquals("<>#$%", "%3C%3E%23%24%25".decoded())
        assertEquals("&=?@", "%26%3D%3F%40".decoded())
        assertEquals("[]{}", "%5B%5D%7B%7D".decoded())
    }

    @Test
    fun testRoundTrip() {
        val testStrings = listOf(
            "Hello World!",
            "Special chars: @#$%^&*()",
            "Unicode: 你好世界",
            "Mixed content: ABC 123 !@# 你好"
        )
        
        testStrings.forEach { original ->
            val encoded = original.encoded()
            val decoded = encoded.decoded()
            assertEquals(original, decoded, "Round trip failed for: $original")
        }
    }

    @Test
    fun testHexPadding() {
        // Test values that would produce single-digit hex without padding
        assertEquals("%0A", "\n".encoded(), "Newline should be properly padded")
        assertEquals("%09", "\t".encoded(), "Tab should be properly padded")
        assertEquals("%01", 0x01.toChar().toString().encoded(), "Low ASCII values should be properly padded")
        assertEquals("%0F", 0x0F.toChar().toString().encoded(), "Single digit hex should be properly padded")
    }

    @Test
    fun testHexPaddingDecode() {
        // Ensure we can decode both padded and unpadded hex (for compatibility)
        assertEquals("\n", "%0A".decoded(), "Padded newline should decode correctly")
        assertEquals("\t", "%09".decoded(), "Padded tab should decode correctly")
        assertEquals(0x01.toChar().toString(), "%01".decoded(), "Padded low ASCII should decode correctly")
        assertEquals(0x0F.toChar().toString(), "%0F".decoded(), "Padded single digit hex should decode correctly")
    }

    @Test
    fun testInvalidHexSequences() {
        // Test invalid hex sequences
        assertEquals("%xy", "%xy".decoded(), "Invalid hex sequence should be returned as-is")
        assertEquals("%^&", "%^&".decoded(), "Invalid hex sequence should be returned as-is")
        assertEquals("test%GH", "test%GH".decoded(), "Invalid hex sequence should be returned as-is")
        
        // Test mixed valid and invalid sequences
        assertEquals("hello world%xy", "hello+world%xy".decoded())
        assertEquals("test 123%^&", "test+123%^&".decoded())
    }
}
