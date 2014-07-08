/**
 * Main.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 *
 * main testing file for Logo interpreter
 */

package com.jgrindall.logojavacc;
import java.io.FileInputStream;
import java.io.File;
import java.util.Vector;
import java.io.FileNotFoundException;
import com.jgrindall.logo.comms.*;
class Main {  
    public static void main( String[] args ) throws ParseException, TokenMgrError {
        String basePath = "test/";
        Vector<String> tests = new Vector<String>();
        for(int i=1;i<=41;i++){
            tests.add("test"+i+".txt");
        }
        
        for(int i=0;i<=tests.size()-1;i++){
            String url = basePath+tests.elementAt(i);
            System.out.println("\nFILE "+url+"  ---------------------");
            try{
                FileInputStream fis  = new FileInputStream(  new File(url)  );
                LogoParser parser = new LogoParser( fis ) ;
                ProgramNode pn = parser.start();
                System.out.println("pn "+pn);
                LogoParserVisitor v = new LogoParserVisitorImpl(parser);
                System.out.println( pn.dump() );
                pn.childrenAccept(v, new TestLogoWriter());
            }
            catch(FileNotFoundException e){
                System.out.println("FileNotFoundException "+e.getMessage());
                
            }
            catch(ParseException e){
                System.out.println("ParseException "+e.getMessage());
               // e.printStackTrace();
            }
            catch(TokenMgrError e){
                System.out.println("TokenMgrError "+e.getMessage());
               
            }
        }
    }
}

