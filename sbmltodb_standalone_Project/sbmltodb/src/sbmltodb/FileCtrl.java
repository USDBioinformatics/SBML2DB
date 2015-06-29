/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sbmltodb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLStreamException;
import org.sbml.jsbml.*;

/**
 *
 * @author Mathialakan.Thavappi
 */

public class FileCtrl  {

    /**
     * Creates a new instance of FileCtrl
     */
    File file;
    SBMLDocument sBMLDocument;
    Model model;
    String name ="";
    String path;
    
    public FileCtrl() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
    public FileCtrl(File file) {
        this.file = file;
    }
    
    public InputStream getResourceAsStream(String resource)
        throws FileNotFoundException
         {
        String stripped = resource.startsWith("/") ? resource.substring(1) : resource;
        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("123 "+getClass().getClassLoader().getResource(resource));
        System.out.println("File uuuclass Path: "+classLoader.getResource(resource));
        if (classLoader != null) {
          stream = classLoader.getResourceAsStream(stripped);
        }
        if (stream == null) {
          stream = FileCtrl.class.getResourceAsStream(resource);
        }
        if (stream == null) {
          stream = FileCtrl.class.getClassLoader().getResourceAsStream(stripped);
        }
        if (stream == null) {
          throw new FileNotFoundException("Resource not found: " + resource);
        }
        return stream;
    }
    
    public void setFile(File file){
      
        this.file=file;
    }
    
    public File getFile(){
        return this.file; 
    }
    
    public void setSBMLDocument(SBMLDocument sBMLDocument){
        this.sBMLDocument =sBMLDocument; 
    }
    
    public SBMLDocument getSBMLDocument(){
        return this.sBMLDocument; 
    }
    
    public boolean autoSet(InputStream ins, File file){
        this.setFile(file);
        this.copyFile(ins);
        this.setSBMLDocument(this.read());
        return true;
        
    }
    
     
    
    public SBMLDocument read( ){   
        try{
            return SBMLReader.read(file);
            }catch(XMLStreamException e){
                e.printStackTrace();   
            }catch(SBMLException e){
                e.printStackTrace();
            } catch(IOException e){
                    e.printStackTrace();
            }
        return null;
    }
    
    public SBMLDocument read(File file ){
        try{
            return SBMLReader.read(file);
            }catch(XMLStreamException e){
                e.printStackTrace();   
            }catch(SBMLException e){
                e.printStackTrace();
            } catch(IOException e){
                    e.printStackTrace();
            }
        return null;
    }
    
     public SBMLDocument read(InputStream inputStream ){
        try{
            return SBMLReader.read(inputStream);
            }catch(XMLStreamException e){
                e.printStackTrace();   
            }catch(SBMLException e){
                e.printStackTrace();
            } 
        return null;
    }
   
    public SBMLDocument readXML(String string){
        try{ 
            SBMLReader sBMLReader = new SBMLReader();
            return sBMLReader.readSBMLFromString(string);
            }catch(javax.xml.stream.XMLStreamException ex){
            ex.getStackTrace();}
       return null;
    }
    
    public SBMLDocument read(String string){
        try{ 
            return JSBML.readSBMLFromFile(string);
           }catch(XMLStreamException e){
                e.printStackTrace();   
            }catch(SBMLException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
       return null;
    }
     
    public File write() {
       String name = null;
       File SBMLFile  = new File(name);
       try{
           JSBML.writeSBML(sBMLDocument, name);
           }catch(XMLStreamException e){
               e.printStackTrace();   
           }catch(SBMLException e){
               e.printStackTrace();
           } catch(IOException e){
               e.printStackTrace();
           }
               return SBMLFile;
    }
    
    public File writeFile(SBMLDocument sBMLDocument, String name ) {
       File SBMLFile  = new File(name);
       try{
           SBMLWriter.write(sBMLDocument, name,"" , String.valueOf(sBMLDocument.getVersion()));// JSBML.writeSBML(SBMLDoc, name);
           }catch(XMLStreamException e){
               e.printStackTrace();   
           }catch(SBMLException e){
               e.printStackTrace();
           } catch(IOException e){
               e.printStackTrace();
           }
        return SBMLFile;
    }
    
     public boolean writeFile(SBMLDocument sBMLDocument, File file ) {
       try{
           SBMLWriter.write(sBMLDocument, file,"" , String.valueOf(sBMLDocument.getVersion()));
           }catch(XMLStreamException e){
               e.printStackTrace();   
           }catch(SBMLException e){
               e.printStackTrace();
           } catch(IOException e){
               e.printStackTrace();
           }
        return true;
    }
    
    public File write(SBMLDocument sBMLDocument) {
       File SBMLFile  = new File(name);
       try{
           JSBML.writeSBML(sBMLDocument, name);
           }catch(XMLStreamException e){
               e.printStackTrace();   
           }catch(SBMLException e){
               e.printStackTrace();
           } catch(IOException e){
               e.printStackTrace();
           }
        return SBMLFile;
    }
	
    public boolean write( SBMLDocument sBMLDocument, String name) {
        try{
            JSBML.writeSBML(sBMLDocument, name);
            }catch(XMLStreamException e){
                e.printStackTrace();   
            }catch(SBMLException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
        return true;
    }
    
    public String writeString( SBMLDocument sBMLDocument) {
         try{
            return JSBML.writeSBMLToString(sBMLDocument);
            }catch(XMLStreamException e){
                e.printStackTrace();   
            }catch(SBMLException e){
                e.printStackTrace();
            } 
        return null;
     }
     
   
    
    public boolean copyFile(InputStream ins, OutputStream outs ){    
         try{ 
            int read=0;
            byte[] bytes= new byte[1024];
            while((read=ins.read(bytes)) != -1){
                outs.write(bytes, 0, read);
                outs.flush();
                }
            ins.close();
            outs.close();
            }catch(IOException e){
            e.printStackTrace();
            }
        return true;
    }
    
     public boolean copyFile(InputStream ins){    
         try{ 
              FileOutputStream outs = new FileOutputStream(file); 
            int read=0;
            byte[] bytes= new byte[1024];
            while((read=ins.read(bytes)) != -1){
                outs.write(bytes, 0, read);
                outs.flush();
                }
            ins.close();
            outs.close();
            }catch(IOException e){
            e.printStackTrace();
            }
        return true;
    }
     
     public boolean copyFile(File source){          
         try{ 
             System.out.println("source.: " + source);
             System.out.println("file.: " + file);
                FileInputStream ins = new FileInputStream(source);
               FileOutputStream outs = new FileOutputStream(file); 
            int read=0;
            byte[] bytes= new byte[1024];
            while((read=ins.read(bytes)) != -1){
                outs.write(bytes, 0, read);
                outs.flush();
                }
            ins.close();
            outs.close();
            }catch(IOException e){
            e.printStackTrace();
            }
        return true;
    }
}
