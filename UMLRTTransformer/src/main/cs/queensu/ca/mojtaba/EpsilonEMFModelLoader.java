package cs.queensu.ca.mojtaba;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLPackage;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class EpsilonEMFModelLoader {
    private List<IModel> models;
    public List<IModel> getModels() {
		return models;
	}
	private List<MutablePair<String,String>> modelsPathWithAlias;
	protected  String metaModelsNamespacenamespaceURI;
	public void setMetaModelsNamespace(String metaModelsNamespacenamespaceURI) {
		this.metaModelsNamespacenamespaceURI=metaModelsNamespacenamespaceURI;
	}
	public void registerMetaModels() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl ());
		EPackage.Registry.INSTANCE.put(Ecore2XMLPackage.eNS_URI,Ecore2XMLPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);

	}
	protected void setDefaultmetaModelsNamespacenamespaceURI() {
		
	}
	public  EpsilonEMFModelLoader(String modelPath, String modelAlias){
		registerMetaModels();
		setDefaultmetaModelsNamespacenamespaceURI();
		models=new ArrayList<IModel>();
		modelsPathWithAlias= new ArrayList<MutablePair<String,String>>();
		this.modelsPathWithAlias.add(new MutablePair<>(modelPath,modelAlias));
	}
	public  EpsilonEMFModelLoader(List<MutablePair<String,String>> modelsPathWithAlias){
		registerMetaModels();
		setDefaultmetaModelsNamespacenamespaceURI();
		models=new ArrayList<IModel>();
		this.modelsPathWithAlias= new ArrayList<MutablePair<String,String>>();
		this.modelsPathWithAlias=modelsPathWithAlias;
		
	}
	
	public EolModule createModule() {
		return new EolModule();
	}
	
	protected Object execute(EolModule module)  throws EolRuntimeException {
		return module.execute();
	    
	}
	protected void loadModels() 
	          throws EolModelLoadingException, URISyntaxException {
		for (MutablePair<String,String> modelPath : this.modelsPathWithAlias) {
			models.add(loadModel(modelPath.getValue(), modelPath.getValue(), modelPath.getKey(),
					metaModelsNamespacenamespaceURI, true, true));
		}
	} 
	// loading EMF model
	protected EmfModel loadModel(String name, String aliases, 
			  String modelPath, String metamodel, boolean readOnLoad, boolean storeOnDisposal) 
	          throws EolModelLoadingException, URISyntaxException {
		EmfModel emfModel = new EmfModel();
	    StringProperties properties = new StringProperties();
	    properties.put(EmfModel.PROPERTY_NAME, name);
	    properties.put(EmfModel.PROPERTY_ALIASES, aliases);
	    properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodel);
	    properties.put(EmfModel.PROPERTY_MODEL_URI, "file:/" + new File(modelPath).getAbsolutePath());
	   // properties.put(EmfModel.PROPERTY_MODEL_URI, uri);
	  	properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
	  	properties.put(EmfModel.PROPERTY_EXPAND, true + "");
	  	properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
	    emfModel.load(properties, (IRelativePathResolver) null);
	    defineURIMap(emfModel); 
	    EcoreUtil.resolveAll(emfModel.getResource().getResourceSet());
		/*for (EObject o :emfModel.allContents())
				System.out.println(o.toString());*/

	    return emfModel;
	}
	// define URI map
	protected void defineURIMap(EmfModel model) {
		
	}
}
