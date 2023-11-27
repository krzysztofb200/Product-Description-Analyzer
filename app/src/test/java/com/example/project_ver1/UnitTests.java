package com.example.project_ver1;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTests {
    @Test
    public void date_isCorrect(){
        assertTrue(
                AddCodeActivity.isDateValid("28", "11", "2023")
        );
        assertTrue(
                AddCodeActivity.isDateValid("11", "2", "2024")
        );
        assertTrue(
                AddCodeActivity.isDateValid("30", "4", "2025")
        );
        assertTrue(
                AddCodeActivity.isDateValid("17", "12", "2026")
        );
    }

    @Test
    public void timeChange(){
        assertEquals(
                "01", ListEdit.pad(1)
        );
        assertEquals(
                "03", ListEdit.pad(3)
        );
        assertEquals(
                "05", ListEdit.pad(5)
        );
        assertEquals(
                "07", ListEdit.pad(7)
        );
        assertEquals(
                "09", ListEdit.pad(9)
        );
        assertEquals(
                "11", ListEdit.pad(11)
        );
    }
}