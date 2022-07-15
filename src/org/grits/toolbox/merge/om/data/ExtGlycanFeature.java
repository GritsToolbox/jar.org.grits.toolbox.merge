package org.grits.toolbox.merge.om.data;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.grits.toolbox.ms.om.data.CustomExtraData;
import org.grits.toolbox.ms.om.data.ReducingEnd;
import org.grits.toolbox.ms.om.io.xml.StringBooleanMapAdapter;
import org.grits.toolbox.ms.om.io.xml.StringDoubleMapAdapter;
import org.grits.toolbox.ms.om.io.xml.StringIntegerMapAdapter;
import org.grits.toolbox.ms.om.io.xml.StringStringMapAdapter;

/**
 * Merge report 'Extended Glycan Feature'. Extends the GlycanFeature object from the annotation object model with elements needed for the 
 * merge report.
 * 
 * @author D Brent Weatherly (dbrentw@uga.edu)
 *
 */
public class ExtGlycanFeature{
	private int expAnotationId;
	public final static String ExpAnnotationId = "ExpAnnotationId";	
	private Integer annotationId = null;
	public final static String AnnotationId = "AnnotationId";	
	private String stringAnnotationId = null;
	public final static String StringAnnotationId = "StringAnnotationId";	
	private String featureId;
	public final static String FeatureId = "FeatureId";	
	private int peakId;
	public final static String PeakId = "PeakId";	
	private String sequenceGWB;
	public final static String SequenceGWB = "SequenceGWB";	
	private String sequence;
	public final static String Sequence = "Sequence";	
	private String sequenceFormat;
	public final static String SequenceFormat = "SequenceFormat";	
	private double featureMz;
	public final static String FeatureMz = "FeatureMz";	
	private double intensity;
	public final static String Intensity = "Intensity";	
	private int charge;
	public final static String Charge = "Charge";	
	private boolean selected = true;
	
    @XmlElement(name="reducingEnd")
    private ReducingEnd m_reducingEnd = null;

	//	private double score;
	private HashMap<String, Integer> m_featureIntegerProp = new HashMap<String,Integer>();
	private HashMap<String, Double> m_featureDoubleProp = new HashMap<String,Double>();
	private HashMap<String, String> m_featureStringProp = new HashMap<String,String>();
	private HashMap<String, Boolean> m_featureBooleanProp = new HashMap<String,Boolean>();

	private HashMap<String, Integer> m_annotationIntegerProp = new HashMap<String,Integer>();
	private HashMap<String, Double> m_annotationDoubleProp = new HashMap<String,Double>();
	private HashMap<String, String> m_annotationStringProp = new HashMap<String,String>();
	private HashMap<String, Boolean> m_annotationBooleanProp = new HashMap<String,Boolean>();
	
	public final static String COMBO_DATA_SEPARATOR = ", ";

	@Override
	public boolean equals(Object obj) {
		if( ! (obj instanceof ExtGlycanFeature) ) {
			return false;
		}
		return this.getExpAnotationId() == ((ExtGlycanFeature) obj).getExpAnotationId() && 
				this.getStringAnnotationId().equals( ( (ExtGlycanFeature) obj).getStringAnnotationId() ); 
	}
	
	
	private static Object getExtGlycanFeatureData( ExtGlycanFeature _glycanFeature, String sCurKey ) {
		switch( sCurKey ) {
		case ExtGlycanFeature.ExpAnnotationId: 
			return _glycanFeature.getExpAnotationId();
		case ExtGlycanFeature.AnnotationId: 
			return _glycanFeature.getAnnotationId();
		case ExtGlycanFeature.StringAnnotationId: 
			return _glycanFeature.getStringAnnotationId();
		case ExtGlycanFeature.FeatureId:
			return _glycanFeature.getFeatureId();
		case ExtGlycanFeature.PeakId:
			return _glycanFeature.getPeakId();
		case ExtGlycanFeature.SequenceGWB:
			return _glycanFeature.getSequenceGWB();
		case ExtGlycanFeature.Sequence:
			return _glycanFeature.getSequence();
		case ExtGlycanFeature.SequenceFormat:
			return _glycanFeature.getSequenceFormat();
		case ExtGlycanFeature.FeatureMz:
			return _glycanFeature.getFeatureMz();
		case ExtGlycanFeature.Intensity:
			return _glycanFeature.getIntensity();
		case ExtGlycanFeature.Charge:
			return _glycanFeature.getCharge();
		}
		return null;
	}
	
	private static Object getExtGlycanFeatureData( ExtGlycanFeature _glycanFeature, CustomExtraData _ced ) {
		String sCurKey = _ced.getKey();
		for( String sKey2 : _glycanFeature.getFeatureBooleanProp().keySet() ) {
			if( sCurKey.equals(sKey2) ) {
				Boolean bVal = _glycanFeature.getFeatureBooleanProp().get(sKey2);
				if( bVal == null ) {
					bVal = Boolean.FALSE;
				}
				return (bVal ? "Yes" : "No");
			}
		}

		for( String sKey2 : _glycanFeature.getFeatureDoubleProp().keySet() ) {
			if( sCurKey.equals(sKey2) ) {
				Double dOrigVal = _glycanFeature.getFeatureDoubleProp().get(sKey2);
				if( dOrigVal == null )
					return null;
				Double dFormatVal = new Double( _ced.getDoubleFormat().format(dOrigVal) );
				return (dFormatVal);
			}
		}
		for( String sKey2 : _glycanFeature.getFeatureIntegerProp().keySet() ) {
			if( sCurKey.equals(sKey2) ) {
				return (_glycanFeature.getFeatureIntegerProp().get(sKey2));
			}
		}
		for( String sKey2 : _glycanFeature.getFeatureStringProp().keySet() ) {
			if( sCurKey.equals(sKey2) ) {
				return (_glycanFeature.getFeatureStringProp().get(sKey2));
			}
		}
		
		for( String sKey2 : _glycanFeature.getAnnotationBooleanProp().keySet() ) {
			if( sCurKey.equals(sKey2) ) {
				Boolean bVal = _glycanFeature.getAnnotationBooleanProp().get(sKey2);
				if( bVal == null ) {
					bVal = Boolean.FALSE;
				}
				return (bVal ? "Yes" : "No");
			}
		}
		for( String sKey2 : _glycanFeature.getAnnotationDoubleProp().keySet() ) {
			if( sCurKey.equals(sKey2) ) {
				Double dOrigVal = _glycanFeature.getAnnotationDoubleProp().get(sKey2);
				if( dOrigVal == null )
					return null;
				Double dFormatVal = new Double( _ced.getDoubleFormat().format(dOrigVal) );
				return (dFormatVal);
			}
		}
		for( String sKey2 : _glycanFeature.getAnnotationIntegerProp().keySet() ) {
			if( sCurKey.equals(sKey2) ) {
				return (_glycanFeature.getAnnotationIntegerProp().get(sKey2));
			}
		}
		for( String sKey2 : _glycanFeature.getAnnotationStringProp().keySet() ) {
			if( sCurKey.equals(sKey2) ) {
				return (_glycanFeature.getAnnotationStringProp().get(sKey2));
			}
		}

		return null;
	}

	public static Object getCombinedData(List<ExtGlycanFeature> _lFeatures, String _sKey, String _sDelimiter) {
		if( _lFeatures.size() == 1 ) {
			return getExtGlycanFeatureData(_lFeatures.get(0), _sKey);
		}
		StringBuilder sbCombined = new StringBuilder();
		int iCnt = 0;
		for( ExtGlycanFeature egf : _lFeatures ) {
			if( ! egf.getSelected() ) {
				continue;
			}
			// DBW 05-17-17. I was limiting the output, but I think it better to report everything and force users to do the annotation properly
//			if( iCnt++ > 5 )
//				continue;
			if( ! sbCombined.toString().equals("") ) {
				sbCombined.append(_sDelimiter);
			}
			sbCombined.append( getExtGlycanFeatureData(egf, _sKey) );
		}
		return sbCombined.toString();
	}

	public static Object getCombinedData(List<ExtGlycanFeature> _lFeatures, CustomExtraData _ced, String _sDelimiter) {
		if( _lFeatures.size() == 1 ) {
			return getExtGlycanFeatureData(_lFeatures.get(0), _ced);
		}
		StringBuilder sbCombined = new StringBuilder();
		int iCnt = 0;
		for( ExtGlycanFeature egf : _lFeatures ) {
			if( ! egf.getSelected() ) {
				continue;
			}
			// DBW 05-17-17. I was limiting the output, but I think it better to report everything and force users to do the annotation properly
//			if( iCnt++ > 5 )
//				continue;
			if( ! sbCombined.toString().equals("") ) {
				sbCombined.append(_sDelimiter);
			}
			sbCombined.append( getExtGlycanFeatureData(egf, _ced) );
		}
		return sbCombined.toString();
	}
	
	public Integer getAnnotationId() {
		return annotationId;
	}
	@XmlAttribute(name="annotationId")
	public void setAnnotationId(Integer annotationId) {
		this.annotationId = annotationId;
	}

	public String getStringAnnotationId() {
		return stringAnnotationId;
	}
	@XmlAttribute(name="glycanAnnotationId")
	public void setStringAnnotationId(String glycanAnnotationId) {
		this.stringAnnotationId = glycanAnnotationId;
	}

	public double getIntensity() {
		return intensity;
	}
	@XmlAttribute(name="intensity")
	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	public int getCharge() {
		return charge;
	}
	@XmlAttribute(name="charge")
	public void setCharge(int charge) {
		this.charge = charge;
	}
	
	public String getSequenceGWB() {
		return sequenceGWB;
	}
	@XmlAttribute(name="seqGWB")
	public void setSequenceGWB(String sequence) {
		this.sequenceGWB = sequence;
	}

	public String getSequence() {
		return sequence;
	}
	@XmlAttribute(name="seq")
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getSequenceFormat() {
		return sequenceFormat;
	}
	@XmlAttribute(name="seqFormat")
	public void setSequenceFormat(String sequenceFormat) {
		this.sequenceFormat = sequenceFormat;
	}

	public double getFeatureMz() {
		return featureMz;
	}
	@XmlAttribute(name="featureMz")
	public void setFeatureMz(double featureMz) {
		this.featureMz = featureMz;
	}

	public String getFeatureId() {
		return featureId;
	}
	@XmlAttribute(name="featureId")
	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	public int getPeakId() {
		return peakId;
	}
	@XmlAttribute(name="annotatedPeakId")
	public void setPeakId(int peakId) {
		this.peakId = peakId;
	}
	public int getExpAnotationId() {
		return expAnotationId;
	}
	@XmlAttribute(name="entryId")
	public void setExpAnotationId(int expAnotationId) {
		this.expAnotationId = expAnotationId;
	}

	public boolean getSelected() {
		return selected;
	}
	@XmlAttribute(name="selected")
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@XmlJavaTypeAdapter(StringIntegerMapAdapter.class)
	public HashMap<String, Integer> getFeatureIntegerProp()
	{
		return m_featureIntegerProp;
	}

	public void setFeatureIntegerProp(HashMap<String, Integer> a_integerProp)
	{
		m_featureIntegerProp = a_integerProp;
	}
	@XmlJavaTypeAdapter(StringDoubleMapAdapter.class)
	public HashMap<String, Double> getFeatureDoubleProp()
	{
		return m_featureDoubleProp;
	}

	public void setFeatureDoubleProp(HashMap<String, Double> a_doubleProp)
	{
		m_featureDoubleProp = a_doubleProp;
	}
	@XmlJavaTypeAdapter(StringStringMapAdapter.class)
	public HashMap<String, String> getFeatureStringProp()
	{
		return m_featureStringProp;
	}

	public void setFeatureStringProp(HashMap<String, String> a_stringProp)
	{
		m_featureStringProp = a_stringProp;
	}
	@XmlJavaTypeAdapter(StringBooleanMapAdapter.class)
	public HashMap<String, Boolean> getFeatureBooleanProp()
	{
		return m_featureBooleanProp;
	}

	public void setFeatureBooleanProp(HashMap<String, Boolean> a_booleanProp)
	{
		m_featureBooleanProp = a_booleanProp;
	}

	@XmlJavaTypeAdapter(StringIntegerMapAdapter.class)
	public HashMap<String, Integer> getAnnotationIntegerProp()
	{
		return m_annotationIntegerProp;
	}

	public void setAnnotationIntegerProp(HashMap<String, Integer> a_integerProp)
	{
		m_annotationIntegerProp = a_integerProp;
	}
	@XmlJavaTypeAdapter(StringDoubleMapAdapter.class)
	public HashMap<String, Double> getAnnotationDoubleProp()
	{
		return m_annotationDoubleProp;
	}

	public void setAnnotationDoubleProp(HashMap<String, Double> a_doubleProp)
	{
		m_annotationDoubleProp = a_doubleProp;
	}
	@XmlJavaTypeAdapter(StringStringMapAdapter.class)
	public HashMap<String, String> getAnnotationStringProp()
	{
		return m_annotationStringProp;
	}
	public void setAnnotationStringProp(HashMap<String, String> a_stringProp)
	{
		m_annotationStringProp = a_stringProp;
	}
	@XmlJavaTypeAdapter(StringBooleanMapAdapter.class)
	public HashMap<String, Boolean> getAnnotationBooleanProp()
	{
		return m_annotationBooleanProp;
	}

	public void setAnnotationBooleanProp(HashMap<String, Boolean> a_booleanProp)
	{
		m_annotationBooleanProp = a_booleanProp;
	}
	
    @XmlTransient
    public ReducingEnd getReducingEnd()
    {
        return m_reducingEnd;
    }
    public void setReducingEnd(ReducingEnd a_reducingEnd)
    {
        m_reducingEnd = a_reducingEnd;
    }

}
