package info.infomila.billar.tests;

import info.infomila.billar.models.Soci;
import info.infomila.billar.ipersistence.BillarException;
import info.infomila.billar.ipersistence.BillarFactory;
import info.infomila.billar.ipersistence.IBillar;
import info.infomila.billar.models.Grup;
import info.infomila.billar.models.Partida;
import info.infomila.billar.models.Torneig;
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
            /*System.out.println("CREEM UN SOCI I FEM EL INSERT");
            Soci soci1 = new Soci("12345678A", "Daviiid", "Marcoos", "Sabiii", "123456", null, true);
            System.out.println("SOCI CREAT");
            System.out.println(soci1);
            billar.insertSoci(soci1);
            
            System.out.println("SOCI INSERIT: ");
            System.out.println(soci1);
            
            System.out.println("SELECT DE TOTS ELS SOCIS DE LA BD");
            List<Soci> socis = billar.getSocis(0);
            socis.forEach((s) -> {
                System.out.println(s);
            });
            
            System.out.println("OBTENIM EL PRIMER I EL MODIFIQUEM");
            Soci soci2 = socis.get(0);
            soci2.setCognom2("proves");
            billar.updateSoci(soci2);
            System.out.println(soci2);*/
            
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            System.out.println("FEM LOGIN (12345678A)/(bcc67d8524948bbd873e4df12c89b182)");
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            Soci s = billar.login("12345678A","bcc67d8524948bbd873e4df12c89b182");
            System.out.println(s);
            System.out.println("");
            
            Grup g = billar.getGrupByTorneigOfSoci(1,1);
            System.out.println(g.getDescripcio());
            
            /*System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            System.out.println("GET TORNEJOS ACTIUS");
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            List<Torneig>tornejos = billar.getTornejosObertsInscripcio(1);
            tornejos.forEach((torneig) -> {
                System.out.println(torneig);
            });

            
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            System.out.println("FEM UNA INSCRIPCIÓ AL SOCI");
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            billar.ferInscripcio(s, tornejos.get(tornejos.size() - 1));
            billar.commit();
            System.out.println("Inscripcio feta");
       
            
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            System.out.println("GET TORNEJOS ON PARTICIPO");
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            tornejos = billar.getTornejosActiusOnParticipo(s.getId());
            tornejos.forEach((torneig) -> {
                System.out.println(torneig);
            });
            
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            System.out.println("TORNEIG AMB ID 2");
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            Torneig torneig = billar.getTorneigById(2);
            System.out.println(torneig);
            
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            System.out.println("PARTIDA AMB ID 13");
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            Partida partida = billar.getPartidaById(13);
            System.out.println(partida);
            
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            System.out.println("PARTIDA AMB ID 13 AMB ESTAT JUGAT");
            System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
            partida.setEstatPartida(Partida.EstatPartida.JUGAT);
            billar.updatePartida(partida);
            billar.commit();
            System.out.println(partida);
            
            /*System.out.println("ELIMINEM EL PRIMER I MIREM QUE ENS MOSTRI EL ACTIU A FALSE");
            billar.deleteSoci(soci2);
            
            System.out.println(soci2);*/
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
