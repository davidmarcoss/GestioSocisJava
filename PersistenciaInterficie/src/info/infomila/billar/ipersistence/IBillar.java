package info.infomila.billar.ipersistence;

import info.infomila.billar.models.Soci;
import java.util.List;

public interface IBillar
{
    List<Soci> getSocis() throws BillarException;
    
    void insertSoci(Soci soci) throws BillarException;
    
    void updateSoci(Soci soci) throws BillarException;
    
    void deleteSoci(Soci soci) throws BillarException;
    
    void commit() throws BillarException;
    
    void rollback() throws BillarException;
    
    void close() throws BillarException;
}
