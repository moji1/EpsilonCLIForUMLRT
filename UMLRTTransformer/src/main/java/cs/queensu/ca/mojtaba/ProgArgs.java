package cs.queensu.ca.mojtaba;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;



public class ProgArgs {
	public static class HelperPathConvertor implements IStringConverter<List<String>> {
		  @Override
		  public List<String> convert(String value) {
		    return Arrays.asList(value.split(","));
		  }
	}
	JCommander jCommander;
	@Parameter(names = { "-umlrtmodel","-m" }, description = "Path of UMLRTModel models and their alias e.g., m1:a1",required = true)
	private String modelPath;
	@Parameter(names = { "-script","-s" }, description = "Path of epsilon script",required = true)
	private String scriptPath;
	@Parameter(names = { "-language","-l" }, description = "The language of the script (Optional, default is EOL)")
	private String langauge="EOL";
	@Parameter(names = { "-args" }, description = "Args for the script, (Optional)")
	private String args;
	@Parameter(names = { "-outplace" }, description = "In outplace transformation mode, a new output model is created, default mode is outplace, (Optional)")
	private boolean outplace=true;
	@Parameter(names = { "-helermodel","-hm" },converter=HelperPathConvertor.class, description = "Path of Helpers models and their alias e.g., hm1:ha1,hm2:ha2, (Optional)")
	private List<String> helperModelsPath = new ArrayList<>();
	List<MutablePair<String,String>> modelsPathWithAlias = new ArrayList<MutablePair<String,String>>();
	public void parse(String[] args2) {
		if (jCommander==null)
			jCommander=new JCommander(this);
	     jCommander.setProgramName("UMLRTTransformer");
	     try {
	        jCommander.parse(args2);
	        parseModelsPath();
	     } catch (ParameterException e) {
			// TODO: handle exception
	        jCommander.usage();
	        System.exit(-1);
	     }
	 }
	public void runPorg() {
		if (scriptPath!="" &&  modelsPathWithAlias.size()>=1) {
			StandardCopyOption options=StandardCopyOption.REPLACE_EXISTING;
			File sourceFile= new File(modelsPathWithAlias.get(0).getKey());
			String targetFilePath="/"+sourceFile.toPath().subpath(0, sourceFile.toPath().getNameCount()-1)+"/Refined_"+sourceFile.toPath().getFileName();
			File targetFile= new File(targetFilePath);
			try {
				Files.copy(sourceFile.toPath(),targetFile.toPath(),options);
				modelsPathWithAlias.get(0).setLeft(targetFilePath);
				EpsilonUMLRTModelLoader epsilonUMLRTModelLoader= new EpsilonUMLRTModelLoader(modelsPathWithAlias);
				epsilonUMLRTModelLoader.loadModels();
				EOLExecutor eolExecutor=new EOLExecutor(scriptPath, epsilonUMLRTModelLoader.getModels());
				eolExecutor.execute();
				} catch (EolModelLoadingException | IOException |URISyntaxException e) {
					e.printStackTrace();
				}
		}else {
			System.out.println("Invalid argument"+"\n");
			jCommander.usage();
		}
	}
	private void parseModelsPath() {
		String tempStrArray1[]=modelPath.split(":");
		MutablePair<String, String> tempP1 = null;
		if (tempStrArray1.length==1)
			tempP1= new MutablePair<>(tempStrArray1[0],"UMLRTModel");
		else if (tempStrArray1.length==2)
			tempP1= new MutablePair<>(tempStrArray1[0],tempStrArray1[1]);
		else {
			System.out.println("Invalid Model Path and Alias : "+ modelPath);
			System.exit(-1);
		}
		modelsPathWithAlias.add(tempP1);
		for (String modelpath : helperModelsPath ) {
			String tempStrArray2[]=modelpath.split(":");
			MutablePair<String, String> tempP2;
			if ((tempStrArray2.length==2)) {
				tempP2= new MutablePair<>(tempStrArray2[0],tempStrArray2[1]);
				modelsPathWithAlias.add(tempP2);		
			}
			else {
				System.out.println("Invalid Model Path and Alias : "+ modelpath);
				System.exit(-1);
			}
			
		}
	}
}
