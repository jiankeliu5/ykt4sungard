package org.king.check.domain;



/**
 * AbstractTdeptworkconfinfo generated by MyEclipse - Hibernate Tools
 */

public abstract class AbstractTdeptworkconfinfo extends org.king.framework.domain.BaseObject implements java.io.Serializable {


    // Fields    

     private TdeptworkconfinfoId id;
     private String workinfoId;
     private String worktimeId;
     private String ifwork;


    // Constructors

    /** default constructor */
    public AbstractTdeptworkconfinfo() {
    }

    
    /** full constructor */
    public AbstractTdeptworkconfinfo(String workinfoId, String worktimeId, String ifwork) {
        this.workinfoId = workinfoId;
        this.worktimeId = worktimeId;
        this.ifwork = ifwork;
    }

   
    // Property accessors

    public TdeptworkconfinfoId getId() {
        return this.id;
    }
    
    public void setId(TdeptworkconfinfoId id) {
        this.id = id;
    }

    public String getWorkinfoId() {
        return this.workinfoId;
    }
    
    public void setWorkinfoId(String workinfoId) {
        this.workinfoId = workinfoId;
    }

    public String getWorktimeId() {
        return this.worktimeId;
    }
    
    public void setWorktimeId(String worktimeId) {
        this.worktimeId = worktimeId;
    }

    public String getIfwork() {
        return this.ifwork;
    }
    
    public void setIfwork(String ifwork) {
        this.ifwork = ifwork;
    }
   








}