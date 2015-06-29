/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aafaque
 */

package sbmltodb;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.io.File ;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.sbml.jsbml.* ;
import org.apache.commons.io.FileUtils;


public class Sbmltodb {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws UnsupportedEncodingException {
       //  TODO code application logic here
        String modelid ;
         String str;
         String expcond ;
         
        String server,user,password,dbname,filepath;
                
        if(args.length == 0)
        {
            server = "localhost";
            user = "root" ;
            password = "root" ;
            dbname = "sbmldb2" ;
            filepath = "F:/Files/new" ;
        }
        
        else
        {
            server = args[0];
            user = args[1];
            password = args[2];
            dbname = args[3];
            filepath = args[4];
        }
         
        File folder = new File(filepath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".xml"))
            {
               SBMLDocument doc = new FileCtrl().read(file); 
                System.out.println(doc);
              
        Mysqlconn sql = new Mysqlconn(server,user,password,dbname);
        
        if( doc.getModel().getId().equals("") )
        {
        modelid = doc.getModel().getName();
        }
        else
        {
            modelid = doc.getModel().getId();
        }
        
        /* Inserting SBML table */
      //  sql.insertsbml(doc.getModel().getLevel(), doc.getModel().getVersion());
        
        /* Inserting model table */
        
        str = doc.getModel().getAnnotationString().replaceAll("<annotation>", "");
        str = str.replaceAll("</annotation>", "");
        if(!doc.getModel().getAnnotation().equals(null))
            sql.insertmodel(modelid,doc.getModel().getName(),doc.getModel().getLevel(),doc.getModel().getVersion(),doc.getModel().getNotesString(),str.trim());
        else
             sql.insertmodel(modelid,doc.getModel().getName(),doc.getModel().getLevel(),doc.getModel().getVersion(),doc.getModel().getNotesString(),"");
            
        /* Inserting Experimental condition */
        
        sql.insertexpcond("expcond_" + modelid , "Experimental Condition of " + modelid ,"Experimental Condition of " + modelid , "","");
        
        /* Inserting Compartments */
        for (Compartment sp : doc.getModel().getListOfCompartments()) {
                sql.insertcompartment(sp.getId() , sp.getName() , sp.getConstant(), modelid,sp.getSpatialDimensions(),sp.getSize(),sp.getUnits());
        }
        
         /* Inserting Species */
        for (Species sp : doc.getModel().getListOfSpecies())
        {
            //annot = sp.getAnnotation();
            str = sp.getAnnotationString().replaceAll("<annotation>", "");
            str = str.replaceAll("</annotation>", "");
            if(!sp.getAnnotation().equals(null))
                 sql.insertspecies(sp.getId() , sp.getName() , sp.getCompartment(), sp.getInitialAmount(),sp.getInitialConcentration(),sp.getSubstanceUnits(),sp.getHasOnlySubstanceUnits(),sp.getBoundaryCondition(),sp.getConstant(),sp.getConversionFactor(),modelid,str.trim());
            else
                sql.insertspecies(sp.getId() , sp.getName() , sp.getCompartment(), sp.getInitialAmount(),sp.getInitialConcentration(),sp.getSubstanceUnits(),sp.getHasOnlySubstanceUnits(),sp.getBoundaryCondition(),sp.getConstant(),sp.getConversionFactor(),modelid,"");
            
            sql.insertbioelement(sp.getId() , sp.getName() , sp.getName(), "" , "", "species");
            sql.insertbioelhasmodel(sp.getId(), modelid);
            sql.insertbioelhasexpcond(sp.getId(), "expcond_" + modelid, "" , sp.getInitialConcentration() , -1);
        }
        
        /* Inserting Function Definitions */
        for (FunctionDefinition sp : doc.getModel().getListOfFunctionDefinitions())
        {
            sql.insertfunctionDefinition(sp.getId() , sp.getMath().toString(), modelid);
        }
        
         /* Inserting Unit Definitions */
        for (UnitDefinition ud : doc.getModel().getListOfUnitDefinitions())
        {
            sql.insertlistofunitdefinition(ud.getId() , ud.getName() , modelid);
            
            for (Unit u : ud.getListOfUnits())
            {
                sql.insertunitdefinition(ud.getId(),u.getScale(),u.getExponent() , u.getMultiplier(),u.getKind().toString());
            }
        }
        
         /* Inserting Reations and Species Reference */
        for (Reaction react : doc.getModel().getListOfReactions())
        {
            str = react.getAnnotationString().replaceAll("<annotation>", "");
            str = str.replaceAll("</annotation>", "");
            if(!react.getAnnotation().equals(null))
                sql.insertlistofReactions(react.getId(), react.getName(), react.getReversible(), react.getFast(),modelid,react.getCompartment(),str.trim());
            else
                sql.insertlistofReactions(react.getId(), react.getName(), react.getReversible(), react.getFast(),modelid,react.getCompartment(),"");
            
            for (SpeciesReference sr : react.getListOfReactants())
            {
                sql.insertSimplespeciesreference(react.getId(),sr.getSpecies(),sr.getSBOTermID() ,sr.getStoichiometry(),"reactants",sr.getConstant());
            }
            for (SpeciesReference sr : react.getListOfProducts())
            {
                sql.insertSimplespeciesreference(react.getId(),sr.getSpecies(),sr.getSBOTermID() ,sr.getStoichiometry(),"products",sr.getConstant());
            }
            for (ModifierSpeciesReference sr : react.getListOfModifiers())
            {
                sql.insertModifierspeciesreference(react.getId(),sr.getSpecies(),sr.getSBOTermID() ,"modifiers");
            }
              
//            if(react.getKineticLaw().getAnnotationString() != "")
            str = react.getKineticLaw().getAnnotationString().replaceAll("<annotation>", "");
            str = str.replaceAll("</annotation>", "");
            if(!react.getKineticLaw().getAnnotation().equals(null))
            sql.insertKineticLaw(react.getId(),react.getKineticLaw().getMetaId(),react.getKineticLaw().getMath().toString(),str.trim());
            else
                sql.insertKineticLaw(react.getId(),react.getKineticLaw().getMetaId(),react.getKineticLaw().getMath().toString(),"");
        }
        
         /* Inserting Constraints  */
            for (Constraint sp : doc.getModel().getListOfConstraints())
            {
                sql.insertConstraint(sp.getMath().toString(), sp.getMessageString(), modelid);
            }
        
         /* Inserting Constraint Definitions */
            for (Constraint sp : doc.getModel().getListOfConstraints())
            {
                sql.insertConstraint(sp.getMath().toString(), sp.getMessageString(), modelid);
            }
        
                    /* Inserting Events */
           for (Event e : doc.getModel().getListOfEvents())
           {
               sql.insertevents(e.getId(), e.getName(), e.getUseValuesFromTriggerTime(), modelid);
               sql.inserteventtrigger(e.getId(),e.getTrigger().getInitialValue(),e.getTrigger().getPersistent(),e.getTrigger().getMath().toString());
               sql.inserteventpriority(e.getId(),e.getPriority().getMath().toString());
               sql.inserteventdelay(e.getId(),e.getDelay().getMath().toString());
               
                 for (EventAssignment ea : e.getListOfEventAssignments())
                 {
                     sql.inserteventassignment(e.getId(), ea.getVariable(), ea.getMath().toString());
                 }
           }
        
               /* Inserting Parameter List */
           for (Parameter p : doc.getModel().getListOfParameters())
           {
               sql.insertparameters(p.getId(), p.getName(), p.getValue(), p.getUnits(), p.getConstant() ,modelid);
           }
           
              /* Inserting Rule */
           for(Rule r : doc.getModel().getListOfRules())
           {
               if(r.isAssignment())
               sql.insertrules(r.getMetaId(),r.getMath().toString(), "assignmentrule" ,modelid);
           }


        sql.connclose();
        
        } 
        }
    
    }
}