package util.filelib.interfaces;

import util.filelib.PropertiesEnhanced;

public interface PropertyConfigurable {
	
	public boolean validateConfigurationDefault();
	
	public void setDefaultConfiguration();
	
	public boolean validateConfiguration();
	
	public void setConfiguration();
	
	public PropertiesEnhanced getConfiguration();
	
}
