package util.system;

import java.io.File;
import java.io.IOException;

public class Application {
	public static void relaunch(String jarDir, String nameJar, boolean shouldRelaunch) {
        try {
            if (shouldRelaunch) {
                String javaPath = System.getProperty("java.home") + "/bin/java";
                String classpath = System.getProperty("java.class.path");
                String command = javaPath + " -cp " + classpath + " -jar " + jarDir + File.separator + nameJar;
                ProcessBuilder builder = new ProcessBuilder(command.split("\\s+"));
                builder.start();
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
