package org.grits.toolbox.merge.om.data;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.grits.toolbox.ms.om.io.xml.StringDoubleMapAdapter;
import org.grits.toolbox.ms.om.io.xml.StringIntegerMapAdapter;

/**
 * Merge report 'Extended Peak'. Extends the Peak object from the annotation object model with elements needed for the 
 * merge report.
 * 
 * @author D Brent Weatherly (dbrentw@uga.edu)
 *
 */
public class ExtPeak{
	private Integer scanId = null;
	private int expAnnotationId;
	private int sourcePeakId;
	private int extPeakId;
    private Double m_mz = null;
    private Double m_intensity = null;
    private Double m_relativeIntensity = null;
    private Integer m_charge = null; 
    private Double m_precursorIntensity = null;
    private Double m_precursorMz = null;
    private Integer m_precursorCharge = null;
    
    // Note that there is no CustomExtractData list
    private HashMap<String, Double> m_doubleProp = new HashMap<String,Double>(); // Added 07/01 by DBW  mostly to hold extract mz
    private HashMap<String, Integer> m_integerProp = new HashMap<String,Integer>(); // Added 07/01 by DBW  mostly to hold extract mz
	
	public Integer getScanId() {
		return scanId;
	}
	@XmlAttribute(name="scanId")
	public void setScanId(Integer scanId) {
		this.scanId = scanId;
	}
	public int getSourcePeakId() {
		return sourcePeakId;
	}
	@XmlAttribute(name="sourcePeakId")
	public void setSourcePeakId(int sourcePeakId) {
		this.sourcePeakId = sourcePeakId;
	}

	public int getExtPeakId() {
		return extPeakId;
	}
	@XmlAttribute(name="extPeakId")
	public void setExtPeakId(int extPeakId) {
		this.extPeakId = extPeakId;
	}
	public int getExpAnnotationId() {
		return expAnnotationId;
	}
	@XmlAttribute(name="entryId")
	public void setExpAnnotationId(int expAnnotationId) {
		this.expAnnotationId = expAnnotationId;
	}
    public Double getMz()
    {
        return m_mz;
    }
    @XmlAttribute(name="mz")
    public void setMz(Double a_mz)
    {
        m_mz = a_mz;
    }
    public Double getIntensity()
    {
        return m_intensity;
    }
    @XmlAttribute(name="intensity")
    public void setIntensity(Double a_intensity)
    {
    	m_intensity = a_intensity;
    }
    @XmlAttribute(name="relativeIntensity")
    public void setRelativeIntensity(Double a_relativeIntensity)
    {
    	m_relativeIntensity = a_relativeIntensity;
    }
    public Double getRelativeIntensity()
    {
        return m_relativeIntensity;
    }
    public Double getPrecursorMz()
    {
        return m_precursorMz;
    }
    @XmlAttribute(name="precursorMz")
    public void setPrecursorMz(Double a_precursorMz)
    {
    	m_precursorMz = a_precursorMz;
    }    
    public Double getPrecursorIntensity()
    {
        return m_precursorIntensity;
    }
    @XmlAttribute(name="precursorIntensity")
    public void setPrecursorIntensity(Double a_precursorIntensity)
    {
    	m_precursorIntensity = a_precursorIntensity;
    }    
    public Integer getPrecursorCharge()
    {
        return m_precursorCharge;
    }
    @XmlAttribute(name="precursorCharge")
    public void setPrecursorCharge(Integer a_precursorCharge)
    {
    	m_precursorCharge = a_precursorCharge;
    }    
    public Integer getCharge()
    {
        return m_charge;
    }
    @XmlAttribute(name="charge")
    public void setCharge(Integer a_charge)
    {
        m_charge = a_charge;
    } 	
    public HashMap<String, Double> getDoubleProp()
    {
        return m_doubleProp;
    }

    @XmlJavaTypeAdapter(StringDoubleMapAdapter.class)
    public void setDoubleProp(HashMap<String, Double> a_doubleProp)
    {
        m_doubleProp = a_doubleProp;
    }

    public HashMap<String, Integer> getIntegerProp()
    {
        return m_integerProp;
    }

    @XmlJavaTypeAdapter(StringIntegerMapAdapter.class)
    public void setIntegerProp(HashMap<String, Integer> a_integerProp)
    {
    	m_integerProp = a_integerProp;
    }
    
    public boolean addIntegerProp(String a_key, Integer a_value)
    {
        boolean t_overwrite = false;
        if ( this.m_integerProp.get(a_key) != null )
        {
            t_overwrite = true;
        }
        this.m_integerProp.put(a_key, a_value);
        return t_overwrite;
    }

    public boolean addDoubleProp(String a_key, Double a_value)
    {
        boolean t_overwrite = false;
        if ( this.m_doubleProp.get(a_key) != null )
        {
            t_overwrite = true;
        }
        this.m_doubleProp.put(a_key, a_value);
        return t_overwrite;
    }

}
