package sample.actuator;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class HelloWorldServiceTest {

	@Test
	public void expectedMessage() {
		HelloWorldService helloWorldService = new HelloWorldService();
		assertEquals("Expected correct message","Spring boot says hello from a Docker container",helloWorldService.getHelloMessage());
	}
	 
	@Test
	public void testMessage(){
		
		HelloWorldService helloWorldService = mock(HelloWorldService.class);
		
		when(helloWorldService.getHelloMessage()).thenReturn("Hola Hola").thenReturn("Hello Hello");
		
		assertEquals("Hola Hola", "Hola Hola", helloWorldService.getHelloMessage());
		
		assertEquals("Hello Hello", "Hello Hello", helloWorldService.getHelloMessage());
		
	}
}
