/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbmltodb;

/**
 *
 * @author Aafaque
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.Driver;
import java.sql.PreparedStatement;
import java.sql.Types;

public class Mysqlconn {
    
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
    
        public Mysqlconn(String address,String user, String pass,String dbname) {
		
		try {
//			new com.mysql.jdbc.Driver();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
// conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase?user=testuser&password=testpassword");
//			String connectionUrl = "jdbc:mysql://sbmldb.cv2lkks86nni.us-west-2.rds.amazonaws.com:3306/sbmldb";
                        String connectionUrl = "jdbc:mysql://" + address + ":3306/" + dbname;
			String connectionUser = user;
			String connectionPassword = pass;
			conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
		} catch (Exception e) {
			e.printStackTrace();
                }
//		} finally {
//			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
//		}
	}
        
         public void insertsbml(int level,int version)
        {
            try {
            
                String query = "insert into sbml (level,version)"
                + " values (?, ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setInt(1, level);
                preparedStmt.setInt(2, version);

                // execute the preparedstatement
                preparedStmt.execute();

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
         public void insertmodel(String id,String name,int SBML_level,int version,String notes,String annotation)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM model where id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
                    
                String query = "insert into model (id, name,SBML_level,version,notes,annotation)"
                + " values (?, ?, ?, ?,?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, name);
                preparedStmt.setInt(3, SBML_level);
                preparedStmt.setInt(4, version);
                preparedStmt.setString(5, notes);
                 preparedStmt.setString(6, annotation);

                // execute the preparedstatement
                preparedStmt.execute();
                
                }
            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
         
        public void insertexpcond(String id,String name,String descr,String ref,String uri)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM expcond where id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
                    
                String query = "insert into expcond (id, name ,descr,ref,uri_efo)"
                + " values (?, ?, ?, ?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, name);
                preparedStmt.setString(3, descr);
                preparedStmt.setString(4, ref);
                preparedStmt.setString(5, uri);

                // execute the preparedstatement
                preparedStmt.execute();
                
                }
            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertbioelement(String id,String name,String descr,String ref,String uri,String type)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM bioelement where id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
                    
                String query = "insert into bioelement (id, name ,descr,ref,uri,type)"
                + " values (?, ?, ?, ?,?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, name);
                preparedStmt.setString(3, descr);
                preparedStmt.setString(4, ref);
                preparedStmt.setString(5, uri);
                preparedStmt.setString(6, type);

                // execute the preparedstatement
                preparedStmt.execute();
                
                }
            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertbioelhasexpcond(String bioid,String expcondid,String ref,double value,double unit)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM bioelement_has_expcond where bioelement_id = ? and expcond_id = ? and value = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, bioid);
                ps.setString(2, expcondid);
                ps.setDouble(3, value);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
                    
                String query = "insert into bioelement_has_expcond (bioelement_id, expcond_id ,ref,value,unit)"
                + " values (?, ?, ?, ?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, bioid);
                preparedStmt.setString (2, expcondid);
                preparedStmt.setString(3, ref);
                preparedStmt.setDouble(4, value);
                if(unit == -1)
                    preparedStmt.setNull(5, Types.DOUBLE);
                else
                    preparedStmt.setDouble(5, unit);

                // execute the preparedstatement
                preparedStmt.execute();
                
                }
            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertbioelhasmodel(String bioid,String model_id)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM bioelement_has_model where bioelement_id = ?  and model_id = ?";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, bioid);
                ps.setString(2, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
                    
                String query = "insert into bioelement_has_model (bioelement_id, model_id)"
                + " values (?, ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, bioid);
                preparedStmt.setString (2, model_id);

                // execute the preparedstatement
                preparedStmt.execute();
                
                }
            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertcompartment(String id,String name,boolean constant,String model_id,double spacialDimensions,double size, String units)
        {
            try {
                
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM compartment where id = ? and model_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into compartment (id, name,constant,model_id,spacialDimensions,size,units)"
                + " values (?, ?, ?, ?,?,?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, name);
                preparedStmt.setBoolean(3, constant);
                preparedStmt.setString (4, model_id);
                preparedStmt.setDouble(5, (Double.isNaN(spacialDimensions) ? 0 : spacialDimensions));
                preparedStmt.setDouble(6, (Double.isNaN(size) ? 0 : size));
                preparedStmt.setString(7, units);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertspecies(String id,String name,String comp,double ia,double ic,String su,boolean hosu,boolean bc,boolean constant,String cf,String model_id,String annotation)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM species where id = ? and model_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
                
                String query = "insert into species (id, name, compartment, initialAmount, initialConcentration,substanceUnits,hasOnlySubstanceUnits,boundaryCondition,constant,conversionFactor,model_id,annotation)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, name);
                preparedStmt.setString (3, comp);
                preparedStmt.setDouble(4, (Double.isNaN(ia) ? 0 : ia));
                preparedStmt.setDouble    (5, (Double.isNaN(ic) ? 0 : ic));
                preparedStmt.setString    (6, su);
                preparedStmt.setBoolean(7, hosu);
                preparedStmt.setBoolean(8, bc);
                preparedStmt.setBoolean(9, constant);
                preparedStmt.setString    (10, cf);
                 preparedStmt.setString (11, model_id);
                 preparedStmt.setString (12, annotation);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertfunctionDefinition(String id,String xmlns,String model_id)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM functiondefinition where id = ? and model_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into functiondefinition (id, xmlns,model_id)"
                + " values (?, ?, ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, xmlns);
                preparedStmt.setString (3, model_id);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertlistofunitdefinition(String id,String name,String model_id)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM listofunitdefinitions where id = ? and model_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into listofunitdefinitions (id,name,model_id)"
                + " values (?, ?, ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, name);
                preparedStmt.setString (3, model_id);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertunitdefinition(String id,int scale,double exponent,double multiplier,String kind)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM listofunits where listofunitdefinitions_id = ? and kind = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, kind);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into listofunits (listofunitdefinitions_id,kind, scale,exponent,multiplier)"
                + " values (?, ?, ?,?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, kind);
                preparedStmt.setInt (3, scale);
                preparedStmt.setDouble(4, (Double.isNaN(exponent) ? 0 : exponent));
                preparedStmt.setDouble (5, (Double.isNaN(multiplier) ? 0 : multiplier));

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertlistofReactions(String id,String name, boolean reversible,boolean fast,String model_id,String compartment,String annotation)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM reaction where id = ? and model_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into reaction (id,name, reversible,fast,model_id,compartment,annotation)"
                + " values (?, ?, ?,?,?,?, ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, name);
                preparedStmt.setBoolean(3, reversible);
                preparedStmt.setBoolean(4, fast);
                preparedStmt.setString(5, model_id);
                preparedStmt.setString(6, compartment);
                preparedStmt.setString(7, annotation);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertSimplespeciesreference(String id,String species,String sboTerm,double stoichometry,String speciestype,boolean constant)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM simplespeciesreference where reaction_id = ? and species = ? and speciestype = ?";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, species);
                 ps.setString(3, speciestype);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into simplespeciesreference (reaction_id,species, sboTerm,stoichiometry,speciestype,constant)"
                + " values (?, ?, ?,?,?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, species);
                preparedStmt.setString(3, sboTerm);
                preparedStmt.setDouble(4, (Double.isNaN(stoichometry) ? 0 : stoichometry));
                preparedStmt.setString(5, speciestype);
                preparedStmt.setBoolean(6, constant);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertModifierspeciesreference(String id,String species,String sboTerm,String speciestype)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM modifierspeciesreference where reaction_id = ? and species = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, species);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into modifierspeciesreference (reaction_id,species, sboTerm,speciestype)"
                + " values (?, ?, ?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, species);
                preparedStmt.setString(3, sboTerm);
                preparedStmt.setString(4, speciestype);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
         public void insertKineticLaw(String id,String kid,String math,String annotation)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM kineticlaw where reaction_id = ? and kid = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, kid);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into kineticlaw (reaction_id,kid, math,annotation)"
                + " values (?, ?, ? , ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, kid);
                preparedStmt.setString(3, math);
                preparedStmt.setString(4, annotation);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertConstraint(String math,String message,String model_id)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM sbmlconstraint where model_id = ? ";
                ps = conn.prepareStatement(selectquery);
                 ps.setString(1, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into constraint (math,message,model_id)"
                + " values (?, ?, ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, math);
                preparedStmt.setString (2, message);
                preparedStmt.setString(3, model_id);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertevents(String id,String name,boolean UseValuesFromTriggerTime,String model_id)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM event where id = ? and model_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into event (id,name,UseValuesFromTriggerTime,model_id)"
                + " values (?, ?, ?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString (2, name);
                preparedStmt.setBoolean(3, UseValuesFromTriggerTime);
                preparedStmt.setString(4, model_id);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void inserteventtrigger(String event_id,boolean initialvalue,boolean persistent,String math)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM sbmltrigger where event_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, event_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into sbmltrigger (event_id,initialvalue,persisent,math)"
                + " values (?, ?, ?,?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, event_id);
                preparedStmt.setBoolean(2, initialvalue);
                preparedStmt.setBoolean(3, persistent);
                preparedStmt.setString(4, math);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void inserteventpriority(String event_id,String math)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM priority where event_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, event_id);
                ResultSet rs = ps.executeQuery();
               int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into priority (event_id,math)"
                + " values (?, ?, ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, event_id);
                preparedStmt.setString(2, math);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void inserteventdelay(String event_id,String math)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM delay where event_id = ?";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, event_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into delay (event_id,math)"
                + " values (?, ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, event_id);
                preparedStmt.setString(2, math);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void inserteventassignment(String event_id,String variable,String math)
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM eventassignment where event_id = ?";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, event_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into eventassignment (event_id,variable,math)"
                + " values (?, ? , ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, event_id);
                preparedStmt.setString(2, variable);
                preparedStmt.setString(3, math);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertparameters(String id,String name,double value,String units,boolean constant,String model_id   )
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM parameter where id = ? and model_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into parameter (id,name,value,units,constant,model_id)"
                + " values (?, ? , ?, ?, ? ,? )";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString(2, name);
                preparedStmt.setDouble(3, (Double.isNaN(value) ? 0 : value));
                preparedStmt.setString(4, units);
                preparedStmt.setBoolean(5, constant);
                preparedStmt.setString(6, model_id);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        public void insertrules(String id,String math,String type,String model_id   )
        {
            try {
                PreparedStatement ps  ;
                String selectquery = "SELECT count(*) FROM rules where id = ? and model_id = ? ";
                ps = conn.prepareStatement(selectquery);
                ps.setString(1, id);
                 ps.setString(2, model_id);
                ResultSet rs = ps.executeQuery();
                int count=0;
                if (rs.next())
                    count = rs.getInt(1);
                if( count == 0 )
                {
            
                String query = "insert into rules (id,math,ruletype,model_id)"
                + " values (?, ? , ?, ?)";
 
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query); 
                preparedStmt.setString (1, id);
                preparedStmt.setString(2, math);
                preparedStmt.setString(3, type);
                preparedStmt.setString(4, model_id);

                // execute the preparedstatement
                preparedStmt.execute();
                }

            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
        
        
        
          public void connclose()
        {
            try {
                conn.close();
            }
            catch (Exception e) {
			e.printStackTrace();
            }
        }
}
