package info.infomila.billar.tests;

import info.infomila.billar.models.Soci;
import info.infomila.billar.ipersistence.BillarException;
import info.infomila.billar.ipersistence.BillarFactory;
import info.infomila.billar.ipersistence.IBillar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class P02_Proves
{
    public static void main(String[] args)
    {
        IBillar billar = null;
        
        try {
            billar = BillarFactory.getInstance("info.infomila.billar.persistence.Billar", "UP-MySQL");
        } 
        catch (BillarException ex) {
            Logger.getLogger(P02_Proves.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        try
        {
            System.out.println("CREEM UN SOCI I FEM EL INSERT");
            Soci soci1 = new Soci("12345678A", "Daviiid", "Marcoos", "Sabiii", "123456", null, true);
            System.out.println("SOCI CREAT");
            System.out.println(soci1);
            billar.insertSoci(soci1);
            
            System.out.println("SOCI INSERIT: ");
            System.out.println(soci1);
            
            System.out.println("SELECT DE TOTS ELS SOCIS DE LA BD");
            List<Soci> socis = billar.getSocis();
            socis.forEach((s) -> {
                System.out.println(s);
            });
            
            System.out.println("OBTENIM EL PRIMER I EL MODIFIQUEM");
            Soci soci2 = socis.get(0);
            soci2.setCognom2("proves");
            billar.updateSoci(soci2);
            System.out.println(soci2);
            
            System.out.println("ELIMINEM EL PRIMER I MIREM QUE ENS MOSTRI EL ACTIU A FALSE");
            billar.deleteSoci(soci2);
            
            System.out.println(soci2);
        }
        catch (BillarException ex)
        {
            System.out.println(ex);
            System.exit(1);
        }
        finally
        {
            try
            {
                billar.close();
            } catch (BillarException ex)
            {
                System.out.println(ex);
            }
        }
        
    }
}
