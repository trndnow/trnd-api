package com.trnd.trndapi.events;

import com.trnd.trndapi.repository.UserRepository;
import com.trnd.trndapi.service.CodeGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RequiredArgsConstructor
public class CodeGeneratorTest {

    private final CodeGeneratorService codeGeneratorService;
    private Set<String> codeCache;

    @BeforeEach
    public void setUp() {
        codeCache = new HashSet<>();
    }

   // @Test
    public void whenGenerateUniqueCode_thenCorrectFormat() {
        String userType = "MERCHANT";
        String code = codeGeneratorService.generateUniqueCode(userType);

        assertNotNull(code);
        assertTrue(code.startsWith("M")); // Check if it starts with 'M'
        assertEquals(7, code.length()); // Check the length of the code (1 character + 6 digits)
    }

   // @Test
    public void whenGenerateUniqueCodeMultipleTimes_thenAllUnique() {
        String userType = "MERCHANT";
        Set<String> generatedCodes = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            String code = codeGeneratorService.generateUniqueCode(userType);
            assertFalse(generatedCodes.contains(code));
            generatedCodes.add(code);
        }
    }
}



