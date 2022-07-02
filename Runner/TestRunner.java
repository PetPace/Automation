package Runner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Tests.API.Auth.AuthLibrary;
import Tests.API.Server_Health.ServerHealthLibrary;

/*
 * Please read: https://www.tutorialspoint.com/junit/index.htm
 */

public class TestRunner {

    public static void main(String[] args) {

        System.out.println("Server Health controller starting...");

        Result serverHealthControllerResult = JUnitCore.runClasses(ServerHealthLibrary.class);
		
        for (Failure failure : serverHealthControllerResult.getFailures()) {
            System.out.println(failure.toString());
        }
            
        System.out.println("Server Health controller result: " + serverHealthControllerResult.wasSuccessful());
        
        System.out.println("Auth controller starting...");

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Result authControllerResult = JUnitCore.runClasses(AuthLibrary.class);
		
        for (Failure failure : authControllerResult.getFailures()) {
            System.out.println(failure.toString());
        }
            
        System.out.println("Auth controller result: " + authControllerResult.wasSuccessful());
        
    }
    
}
