package cs.queensu.ca.mojtaba;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.models.IModel;

public class EOLExecutor {
	private List<IModel> models;
	private String scriptPath;
	EolModule module;
	EOLExecutor(String scriptPath, List<IModel> models){
		this.scriptPath=scriptPath;
		this.models=models;
		module = new EolModule();
	}
	// execute the script against the models provided
	void execute() {
		 
	    try {
	    	module.parse(getFileURI(this.scriptPath));
	    	if (module.getParseProblems().size() > 0) {
	    		System.err.println("Parse errors occured...");
	    		for (ParseProblem problem : module.getParseProblems()) {
	    			System.err.println(problem.toString());
	    			}
	    		return;
	    	}
	    	for (IModel m: models)
	    		module.getContext().getModelRepository().addModel(m);
			module.execute();
		    module.getContext().getModelRepository().dispose();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// return uri for a file path
	protected URI getFileURI(String fileName) throws URISyntaxException {
		return UriUtil.fileToUri(new File(fileName));

	}	
}
