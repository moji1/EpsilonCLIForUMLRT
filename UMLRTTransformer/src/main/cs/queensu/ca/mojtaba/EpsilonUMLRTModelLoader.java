package cs.queensu.ca.mojtaba;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.MutablePair;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.URIMappingRegistryImpl;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.papyrusrt.codegen.cpp.profile.RTCppProperties.RTCppPropertiesPackage;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.UMLRTStateMachinesPackage;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.profile.standard.StandardPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

public class EpsilonUMLRTModelLoader extends EpsilonEMFModelLoader {
	public EpsilonUMLRTModelLoader(List<MutablePair<String,String>> modelsPathWithAlias) {
		super( modelsPathWithAlias);
		registerMetaModels();
		setDefaultmetaModelsNamespacenamespaceURI();
		// TODO Auto-generated constructor stub
	}
	public EpsilonUMLRTModelLoader(String modelPath, String modelAlias) {
		super(modelPath, modelAlias);
		registerMetaModels();
		setDefaultmetaModelsNamespacenamespaceURI();
	}
	@Override
	protected void setDefaultmetaModelsNamespacenamespaceURI() {
		// TODO Auto-generated method stub
		super.setDefaultmetaModelsNamespacenamespaceURI();
		this.metaModelsNamespacenamespaceURI = "http://www.eclipse.org/uml2/5.0.0/UML," +
				"http://www.eclipse.org/uml2/5.0.0/Types," +
				"http://www.eclipse.org/uml2/5.0.0/UML/Profile/Standard," +
				"http://www.eclipse.org/papyrus/umlrt/cppproperties," +
				"http://www.eclipse.org/papyrus/umlrt," +
				"http://www.eclipse.org/emf/2002/Ecore," +
				"http://www.eclipse.org/papyrus/umlrt/statemachine," ;//+
	}
	@Override
	public void registerMetaModels() {
		super.registerMetaModels();
		URI baseUri = URI.createURI("jar:file:"+this.getClass().getResource("/resources/org.eclipse.uml2.uml.resources_5.3.0.v20170605-1616.jar").getPath()+"!/");
		URIConverter.URI_MAP.put(URI.createURI( UMLResource.LIBRARIES_PATHMAP ), 
		baseUri.appendSegment( "libraries" ).appendSegment( "" ));
		URIConverter.URI_MAP.put(URI.createURI( UMLResource.METAMODELS_PATHMAP ), baseUri.appendSegment( "metamodels" ).appendSegment( "" ));
	    URIConverter.URI_MAP.put(URI.createURI( UMLResource.PROFILES_PATHMAP ), 
		baseUri.appendSegment( "profiles" ).appendSegment( "" ));   
		URIConverter.URI_MAP.put(URI.createURI("pathmap://UML_RT_PROFILE/UMLRealTimeSM-addendum.profile.uml"),
				 URI.createURI("file:"+this.getClass().getResource("/resources/UMLRealTimeSM-addendum.profile.uml").getPath()));
		URIConverter.URI_MAP.put(URI.createURI("pathmap://UMLRT_CPP/RTCppProperties.profile.uml"),
				URI.createURI("file:"+this.getClass().getResource("/resources/RTCppProperties.profile.uml").getPath()));
		URIConverter.URI_MAP.put(URI.createURI("pathmap://UML_RT_PROFILE/uml-rt.profile.uml"),
				URI.createURI("file:"+this.getClass().getResource("/resources/uml-rt.profile.uml").getPath()));
		URIConverter.URI_MAP.put(URI.createURI("pathmap://UMLRTRTSLIB/UMLRT-RTS.uml"),
				URI.createURI("file:"+this.getClass().getResource("/resources/UMLRT-RTS.uml").getPath()));
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("uml", new UMLResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
		EPackage.Registry.INSTANCE.put(UMLRealTimePackage.eNS_URI, UMLRealTimePackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(RTCppPropertiesPackage.eNS_URI, RTCppPropertiesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(UMLRTStateMachinesPackage.eNS_URI, UMLRTStateMachinesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(StandardPackage.eNS_URI, StandardPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
	}

}
