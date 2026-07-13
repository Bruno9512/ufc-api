/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.ufc;


import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author bruni
 */
@SpringBootTest
@ActiveProfiles("test")
public class UfcApplicationMainTest {
    
    @Test
    void deveExecutarMain(){
        UfcApplication.main(new String[]{});
    }
    
}
