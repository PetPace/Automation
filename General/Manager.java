package General;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Model.User;

public class Manager {
    
    private static Manager manager = null;

    private static Logger logger = LoggerFactory.getLogger(Manager.class);

    public User user = null;
           
    private Manager()
    {
        WriteInfoToLogger("System started...");
    }
    
    public static Manager getInstance()
    {
        if (manager == null)
            manager = new Manager();
 
        return manager;
    }

    public void WriteInfoToLogger(String text){
        String timeStamp = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new java.util.Date()); 
        String logText = timeStamp + " Info: " + text;
        logger.info(logText);
        System.out.println(logText);
    }

    public void WriteErrorToLogger(String text){
        String timeStamp = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new java.util.Date()); 
        String logText = timeStamp + " Error: " + text;
        logger.error(logText);
        System.out.println(logText);
    }

    public void WriteExceptionToLogger(Exception exception){
        String timeStamp = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new java.util.Date()); 
        String logText = timeStamp + " Exception: " + exception.getMessage();
        logger.error(logText);
        System.out.println(logText);
    }
}