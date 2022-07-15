package org.grits.toolbox.merge.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.grits.toolbox.merge.om.data.MergeReport;

public class Deserialize {
	
	public MergeReport deserialize(String inputPath){
		try{
		MergeReport outputGraph = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(MergeReport.class);
        File f = new File(inputPath);
        if(f != null && f.exists()){
            jaxbContext = JAXBContext.newInstance(MergeReport.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            outputGraph = (MergeReport) jaxbUnmarshaller.unmarshal(f);
            return outputGraph;        
        }
        else{
            return null;
        }
    }catch(Exception e){
        e.printStackTrace();
        return null;
    }
	}

}
