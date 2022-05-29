package ru.msu.cmc.prac_web.WEBTEST;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonWebTest {
    private final String clientTitle = "Клиенты";

    @Test
    void clientsTest() {
        ChromeDriver driver = new ChromeDriver();
        //driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1024, 768));
        driver.get("http://localhost:8080/person");
        assertEquals(clientTitle, driver.getTitle());

    }
}