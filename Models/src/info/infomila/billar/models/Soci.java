
package info.infomila.billar.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
@Table(name = "socis")
public class Soci implements Serializable
{
    @Id 
    @TableGenerator(name = "gen_soci",
            table = "comptadors",
            pkColumnName = "clau",
            pkColumnValue = "socis",
            valueColumnName = "next_val",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_soci")
    private int id;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 13, unique = true)
    private String nif;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String nom;
    
    @Column(length = 50)
    private String cognom1;
    
    @Column(length = 50)
    private String cognom2;

    @Basic(optional = false)
    @Column(name = "data_alta", nullable = false, length = 50)    
    private Date dataAlta;
    
    @Basic(optional = false)
    @Column(name = "password_hash", nullable = false, length = 32)    
    private String passwordHash;
    
    @Column()
    @Lob
    private byte[] foto;
    
    @OneToMany(mappedBy = "emPK.soci", fetch = FetchType.LAZY)
    private List<EstadisticaModalitat> estadistiques = new ArrayList<>();
    
    @Column()
    private boolean actiu;
    
    @Transient
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    protected Soci(){}

    public Soci(String nif, String nom, String cognom1, String cognom2, String passwordHash, byte[] foto, boolean actiu)
    {
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);
        setDataAlta(new Date());
        setPasswordHash(passwordHash);
        setFoto(foto);
        setActiu(actiu);
    }
    
    public int getId()
    {
        return id;
    }

    protected final void setId(int id)
    {
        this.id = id;
    }

    public String getNif()
    {
        return nif;
    }

    public final void setNif(String nif)
    {
        this.nif = nif;
    }

    public String getNom()
    {
        return nom;
    }

    public final void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getCognom1()
    {
        return cognom1;
    }

    public final void setCognom1(String cognom1)
    {
        this.cognom1 = cognom1;
    }

    public String getCognom2()
    {
        return cognom2;
    }

    public final void setCognom2(String cognom2)
    {
        this.cognom2 = cognom2;
    }

    public Date getDataAlta()
    {
        return dataAlta;
    }

    protected final void setDataAlta(Date dataAlta)
    {
        this.dataAlta = dataAlta;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public final void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    public byte[] getFoto()
    {
        return foto;
    }

    public final void setFoto(byte[] foto)
    {
        this.foto = foto;
    }

    public Iterator<EstadisticaModalitat> iteEstadistiques()
    {
        return estadistiques.iterator();
    }

    private final void setEstadistiques(List<EstadisticaModalitat> estadistiques)
    {
        this.estadistiques = estadistiques;
    }
    
    public void addEstadistica(EstadisticaModalitat estadistica)
    {
        if (!this.estadistiques.contains(estadistica))
        {
            this.estadistiques.add(estadistica);
        }
    }

    public boolean isActiu()
    {
        return actiu;
    }

    public void setActiu(boolean actiu)
    {
        this.actiu = actiu;
    }

    @Override
    public String toString()
    {
        return "Soci{" + "id=" + id + ", nif=" + nif + ", nom=" + nom + ", cognom1=" + cognom1 + ", cognom2=" + cognom2 + ", actiu=" + actiu + '}';
    }
    
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Soci other = (Soci) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }    
}
