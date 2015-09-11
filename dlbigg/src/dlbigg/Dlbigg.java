/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlbigg;
import java.net.*;
import java.io.*;
/**
 *
 * @author Aafaque
 */
public class Dlbigg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String savepath = "\\biggdlfiles\\";
        String cwd = System.getProperty("user.dir");
        String saveTo = cwd + savepath;
        try {
			File file = new File("BIGG Models.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
                            	System.out.println(line);
				URL url = new URL("http://bigg.ucsd.edu/static/models/" + line  +".xml.gz");
                                URLConnection conn = url.openConnection();
                                InputStream in = conn.getInputStream();
                                FileOutputStream out = new FileOutputStream(saveTo + line.trim() + ".xml.gz");
                                byte[] b = new byte[1024];
                                int count;
                                while ((count = in.read(b)) >= 0) {
                                    out.write(b, 0, count);
                                }
                                out.flush(); out.close(); in.close();
                                System.out.println(line + ".xml.gz downloaded successfully");
			}
			fileReader.close();
                        
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void downloadZipFile(String save) {
        
    }
    
}
